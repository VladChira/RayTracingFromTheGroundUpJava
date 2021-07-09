package RayTracer;

import RayTracer.Lights.*;
import RayTracer.Materials.*;
import RayTracer.Objects.*;
import RayTracer.Tracers.*;
import RayTracer.Utilities.*;
import RayTracer.Cameras.*;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class World {
    public static final int WINDOW_WIDTH = 700;
    public static final int WINDOW_HEIGHT = 700;
    public static boolean displayMessages = true;

    public static volatile BufferedImage render = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);

    public ViewPlane vp;
    public RGBColor backgroundColor;
    public Tracer tracer;
    public Ambient ambient;
    public Pinhole camera;

    public ArrayList<Light> lights = new ArrayList<>();
    public ArrayList<GeometricObject> objects = new ArrayList<>();

    public int hres = WINDOW_WIDTH;
    public int vres = WINDOW_HEIGHT;

    public boolean wasOutOfGamut = false;

    public World() {
        backgroundColor = new RGBColor(RGBColor.black);
    }

    public void renderScene() {
        new Thread(() -> {
            camera.render_scene(this);
        }).start();
    }


    public void build(RGBColor bgColor) {
        if (displayMessages) System.out.println("Building world...");
        vp = new ViewPlane();
        vp.setPixelSize(1);
        vp.setSamples(9);
        vp.show_out_of_gamut = false;

        backgroundColor = bgColor;
        tracer = new RayCast(this);

        ambient = new Ambient();
        ambient.scale_radiance(0.4);

        manySpheresSetup();
        //cornellBoxSetup();
    }

    @Deprecated
    public void display_pixel(int row, int column, RGBColor raw_color, PixelWriter pw) {

        RGBColor mapped_color;
        if (vp.show_out_of_gamut) mapped_color = clamp_to_color(raw_color);
        else mapped_color = max_to_one(raw_color);

        int x = column;
        int y = vres - row - 1;

        Color pixelColor = new Color(mapped_color.r, mapped_color.g, mapped_color.b, 1);
        pw.setColor(x, y, pixelColor);
    }

    @Deprecated
    public ShadeRec hit_bare_bones_objects(Ray ray) {
        ShadeRec sr = new ShadeRec(this);
        double t = 0;
        double tmin = Constants.kHugeValue;

        for (GeometricObject object : objects) {
            HitInformation hitInfo = object.hit(ray, t, sr);
            if (hitInfo.hit && hitInfo.t < tmin) {
                sr.hit_an_object = true;
                tmin = hitInfo.t;
            }
            sr.material_ptr = object.get_material();
        }
        return sr;
    }

    public ShadeRec hit_objects(Ray ray) {
        ShadeRec sr = new ShadeRec(this);
        double t = 0;
        Normal normal = new Normal();
        Point3D local_hit_point = new Point3D();
        double tmin = Constants.kHugeValue;

        for (GeometricObject object : objects) {
            HitInformation hitInfo = object.hit(ray, t, sr);
            t = hitInfo.t;
            sr = hitInfo.sr;

            if (hitInfo.hit && hitInfo.t < tmin) {
                tmin = hitInfo.t;
                sr.hit_an_object = true;
                sr.material_ptr = object.get_material();
                sr.hit_point.setTo(ray.o.addTo(ray.d.multiplyBy(t)));
                normal.setTo(sr.normal);
                local_hit_point.setTo(sr.local_hit_point);
            }
        }
        if (sr.hit_an_object) {
            sr.t = tmin;
            sr.normal.setTo(normal);
            sr.local_hit_point.setTo(local_hit_point);
        }
        return sr;
    }


    void add_object(GeometricObject... objs) {
        objects.addAll(Arrays.asList(objs));
    }

    void add_light(Light... ls) {
        lights.addAll(Arrays.asList(ls));
    }

    public RGBColor clamp_to_color(RGBColor raw_color) {
        RGBColor c = new RGBColor();
        c.setTo(raw_color);

        if (raw_color.r > 1.0 || raw_color.g > 1.0 || raw_color.b > 1.0) {
            wasOutOfGamut = true;
            c.r = 1.0;
            c.g = 0;
            c.b = 0;
        }
        return (c);
    }

    public RGBColor max_to_one(RGBColor c) {
        if(c.r > 1.0) c.r = 1.0;
        if(c.g > 1.0) c.g = 1.0;
        if(c.b > 1.0) c.b = 1.0;
        return c;
    }

    void cornellBoxSetup(){
        //coming soon
    }

    void manySpheresSetup() {

        camera = new Pinhole();
        camera.set_eye(0, 0, 500);
        camera.set_lookat(5, 0, 0);
        camera.set_view_distance(850.0);
        camera.set_zoom(2.2);
        camera.compute_uvw();

        PointLight light2 = new PointLight();
        light2.set_location(new Vector3D(100, 100, 200));
        light2.scale_radiance(3.0);
        add_light(light2);

        double ka = 0.3;
        double kd = 0.6;

        Phong matte_ptr1 = new Phong();
        matte_ptr1.set_ka(0.25);
        matte_ptr1.set_kd(0.6);
        matte_ptr1.set_ks(0.2);
        matte_ptr1.set_e(20);
        matte_ptr1.set_cd(RGBColor.yellow);

        Sphere sphere_ptr1 = new Sphere(new Point3D(5, 3, 0), 30);
        sphere_ptr1.set_material(matte_ptr1);
        add_object(sphere_ptr1);


        Matte matte_ptr2 = new Matte();
        matte_ptr2.set_ka(ka);
        matte_ptr2.set_kd(kd);
        matte_ptr2.set_cd(RGBColor.brown);

        Matte matte_ptr36 = new Matte();
        matte_ptr36.set_ka(ka);
        matte_ptr36.set_kd(kd);
        matte_ptr36.set_cd(RGBColor.grey);


        Sphere sphere_ptr2 = new Sphere(new Point3D(45, -7, -60), 20);
        sphere_ptr2.set_material(matte_ptr2);
        add_object(sphere_ptr2);

        Phong matte_ptr3 = new Phong();
        matte_ptr3.set_ka(ka);
        matte_ptr3.set_kd(kd);
        matte_ptr3.set_ks(0.4);
        matte_ptr3.set_e(20);
        matte_ptr3.set_cd(RGBColor.darkGreen);
        Sphere sphere_ptr3 = new Sphere(new Point3D(40, 43, -100), 17);
        sphere_ptr3.set_material(matte_ptr3);
        add_object(sphere_ptr3);

        Matte matte_ptr4 = new Matte();
        matte_ptr4.set_ka(ka);
        matte_ptr4.set_kd(kd);
        matte_ptr4.set_cd(RGBColor.orange);
        Sphere sphere_ptr4 = new Sphere(new Point3D(-20, 28, -15), 20);
        sphere_ptr4.set_material(matte_ptr4);
        add_object(sphere_ptr4);

        Matte matte_ptr5 = new Matte();
        matte_ptr5.set_ka(ka);
        matte_ptr5.set_kd(kd);
        matte_ptr5.set_cd(RGBColor.green);
        Sphere sphere_ptr5 = new Sphere(new Point3D(-25, -7, -35), 27);
        sphere_ptr5.set_material(matte_ptr5);
        add_object(sphere_ptr5);

        Phong matte_ptr6 = new Phong();
        matte_ptr6.set_ka(ka);
        matte_ptr6.set_kd(kd);
        matte_ptr6.set_cd(RGBColor.lightGreen);
        matte_ptr6.set_e(10);
        matte_ptr6.set_ks(0.5);
        Sphere sphere_ptr6 = new Sphere(new Point3D(20, -27, -35), 25);
        sphere_ptr6.set_material(matte_ptr6);
        add_object(sphere_ptr6);

        Matte matte_ptr7 = new Matte();
        matte_ptr7.set_ka(ka);
        matte_ptr7.set_kd(kd);
        matte_ptr7.set_cd(RGBColor.green);
        Sphere sphere_ptr7 = new Sphere(new Point3D(35, 18, -35), 22);
        sphere_ptr7.set_material(matte_ptr7);
        add_object(sphere_ptr7);

        Matte matte_ptr8 = new Matte();
        matte_ptr8.set_ka(ka);
        matte_ptr8.set_kd(kd);
        matte_ptr8.set_cd(RGBColor.brown);
        Sphere sphere_ptr8 = new Sphere(new Point3D(-57, -17, -50), 15);
        sphere_ptr8.set_material(matte_ptr8);
        add_object(sphere_ptr8);

        Matte matte_ptr9 = new Matte();
        matte_ptr9.set_ka(ka);
        matte_ptr9.set_kd(kd);
        matte_ptr9.set_cd(RGBColor.lightGreen);
        Sphere sphere_ptr9 = new Sphere(new Point3D(-47, 16, -80), 23);
        sphere_ptr9.set_material(matte_ptr9);
        add_object(sphere_ptr9);

        Matte matte_ptr10 = new Matte();
        matte_ptr10.set_ka(ka);
        matte_ptr10.set_kd(kd);
        matte_ptr10.set_cd(RGBColor.darkGreen);
        Sphere sphere_ptr10 = new Sphere(new Point3D(-15, -32, -60), 22);
        sphere_ptr10.set_material(matte_ptr10);
        add_object(sphere_ptr10);

        Matte matte_ptr11 = new Matte();
        matte_ptr11.set_ka(ka);
        matte_ptr11.set_kd(kd);
        matte_ptr11.set_cd(RGBColor.darkYellow);
        Sphere sphere_ptr11 = new Sphere(new Point3D(-35, -37, -80), 22);
        sphere_ptr11.set_material(matte_ptr11);
        add_object(sphere_ptr11);

        Matte matte_ptr12 = new Matte();
        matte_ptr12.set_ka(ka);
        matte_ptr12.set_kd(kd);
        matte_ptr12.set_cd(RGBColor.darkYellow);
        Sphere sphere_ptr12 = new Sphere(new Point3D(10, 43, -80), 22);
        sphere_ptr12.set_material(matte_ptr12);
        add_object(sphere_ptr12);

        Matte matte_ptr13 = new Matte();
        matte_ptr13.set_ka(ka);
        matte_ptr13.set_kd(kd);
        matte_ptr13.set_cd(RGBColor.darkYellow);
        Sphere sphere_ptr13 = new Sphere(new Point3D(30, -7, -80), 10);
        sphere_ptr13.set_material(matte_ptr13);
        add_object(sphere_ptr13);

        Matte matte_ptr14 = new Matte();
        matte_ptr14.set_ka(ka);
        matte_ptr14.set_kd(kd);
        matte_ptr14.set_cd(RGBColor.darkGreen);
        Sphere sphere_ptr14 = new Sphere(new Point3D(-40, 48, -110), 18);
        sphere_ptr14.set_material(matte_ptr14);
        add_object(sphere_ptr14);

        Phong matte_ptr15 = new Phong();
        matte_ptr15.set_ka(0.5);
        matte_ptr15.set_kd(0.5);
        matte_ptr15.set_cd(RGBColor.brown);
        matte_ptr15.set_e(10);
        matte_ptr15.set_ks(0.5);

        Sphere sphere_ptr15 = new Sphere(new Point3D(-10, 53, -120), 18);
        sphere_ptr15.set_material(matte_ptr15);
        add_object(sphere_ptr15);

        Matte matte_ptr16 = new Matte();
        matte_ptr16.set_ka(ka);
        matte_ptr16.set_kd(kd);
        matte_ptr16.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr16 = new Sphere(new Point3D(-55, -52, -100), 10);
        sphere_ptr16.set_material(matte_ptr16);
        add_object(sphere_ptr16);

        Matte matte_ptr17 = new Matte();
        matte_ptr17.set_ka(ka);
        matte_ptr17.set_kd(kd);
        matte_ptr17.set_cd(RGBColor.brown);
        Sphere sphere_ptr17 = new Sphere(new Point3D(5, -52, -100), 15);
        sphere_ptr17.set_material(matte_ptr17);
        add_object(sphere_ptr17);

        Phong matte_ptr18 = new Phong();
        matte_ptr18.set_ka(ka);
        matte_ptr18.set_kd(kd);
        matte_ptr18.set_cd(RGBColor.darkPurple);
        matte_ptr18.set_ks(0.4);
        matte_ptr18.set_e(100);
        Sphere sphere_ptr18 = new Sphere(new Point3D(-20, -57, -120), 15);
        sphere_ptr18.set_material(matte_ptr18);
        add_object(sphere_ptr18);

        Matte matte_ptr19 = new Matte();
        matte_ptr19.set_ka(ka);
        matte_ptr19.set_kd(kd);
        matte_ptr19.set_cd(RGBColor.darkGreen);
        Sphere sphere_ptr19 = new Sphere(new Point3D(55, -27, -100), 17);
        sphere_ptr19.set_material(matte_ptr19);
        add_object(sphere_ptr19);

        Matte matte_ptr20 = new Matte();
        matte_ptr20.set_ka(ka);
        matte_ptr20.set_kd(kd);
        matte_ptr20.set_cd(RGBColor.brown);
        Sphere sphere_ptr20 = new Sphere(new Point3D(50, -47, -120), 15);
        sphere_ptr20.set_material(matte_ptr20);
        add_object(sphere_ptr20);

        Matte matte_ptr21 = new Matte();
        matte_ptr21.set_ka(ka);
        matte_ptr21.set_kd(kd);
        matte_ptr21.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr21 = new Sphere(new Point3D(70, -42, -150), 10);
        sphere_ptr21.set_material(matte_ptr21);
        add_object(sphere_ptr21);

        Matte matte_ptr22 = new Matte();
        matte_ptr22.set_ka(ka);
        matte_ptr22.set_kd(kd);
        matte_ptr22.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr22 = new Sphere(new Point3D(5, 73, -130), 12);
        sphere_ptr22.set_material(matte_ptr22);
        add_object(sphere_ptr22);

        Matte matte_ptr23 = new Matte();
        matte_ptr23.set_ka(ka);
        matte_ptr23.set_kd(kd);
        matte_ptr23.set_cd(RGBColor.darkPurple);
        Sphere sphere_ptr23 = new Sphere(new Point3D(66, 21, -130), 13);
        sphere_ptr23.set_material(matte_ptr23);
        add_object(sphere_ptr23);

        Matte matte_ptr24 = new Matte();
        matte_ptr24.set_ka(ka);
        matte_ptr24.set_kd(kd);
        matte_ptr24.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr24 = new Sphere(new Point3D(72, -12, -140), 12);
        sphere_ptr24.set_material(matte_ptr24);
        add_object(sphere_ptr24);

        Matte matte_ptr25 = new Matte();
        matte_ptr25.set_ka(ka);
        matte_ptr25.set_kd(kd);
        matte_ptr25.set_cd(RGBColor.green);
        Sphere sphere_ptr25 = new Sphere(new Point3D(64, 5, -160), 11);
        sphere_ptr25.set_material(matte_ptr25);
        add_object(sphere_ptr25);

        Matte matte_ptr26 = new Matte();
        matte_ptr26.set_ka(ka);
        matte_ptr26.set_kd(kd);
        matte_ptr26.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr26 = new Sphere(new Point3D(55, 38, -160), 12);
        sphere_ptr26.set_material(matte_ptr26);
        add_object(sphere_ptr26);

        Matte matte_ptr27 = new Matte();
        matte_ptr27.set_ka(ka);
        matte_ptr27.set_kd(kd);
        matte_ptr27.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr27 = new Sphere(new Point3D(-73, -2, -160), 12);
        sphere_ptr27.set_material(matte_ptr27);
        add_object(sphere_ptr27);

        Matte matte_ptr28 = new Matte();
        matte_ptr28.set_ka(ka);
        matte_ptr28.set_kd(kd);
        matte_ptr28.set_cd(RGBColor.darkPurple);
        Sphere sphere_ptr28 = new Sphere(new Point3D(30, -62, -140), 15);
        sphere_ptr28.set_material(matte_ptr28);
        add_object(sphere_ptr28);

        Matte matte_ptr29 = new Matte();
        matte_ptr29.set_ka(ka);
        matte_ptr29.set_kd(kd);
        matte_ptr29.set_cd(RGBColor.darkPurple);
        Sphere sphere_ptr29 = new Sphere(new Point3D(25, 63, -140), 15);
        sphere_ptr29.set_material(matte_ptr29);
        add_object(sphere_ptr29);

        Matte matte_ptr30 = new Matte();
        matte_ptr30.set_ka(ka);
        matte_ptr30.set_kd(kd);
        matte_ptr30.set_cd(RGBColor.darkPurple);
        Sphere sphere_ptr30 = new Sphere(new Point3D(-60, 46, -140), 15);
        sphere_ptr30.set_material(matte_ptr30);
        add_object(sphere_ptr30);

        Matte matte_ptr31 = new Matte();
        matte_ptr31.set_ka(ka);
        matte_ptr31.set_kd(kd);
        matte_ptr31.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr31 = new Sphere(new Point3D(-30, 68, -130), 12);
        sphere_ptr31.set_material(matte_ptr31);
        add_object(sphere_ptr31);

        Matte matte_ptr32 = new Matte();
        matte_ptr32.set_ka(ka);
        matte_ptr32.set_kd(kd);
        matte_ptr32.set_cd(RGBColor.green);
        Sphere sphere_ptr32 = new Sphere(new Point3D(58, 56, -180), 11);
        sphere_ptr32.set_material(matte_ptr32);
        add_object(sphere_ptr32);

        Matte matte_ptr33 = new Matte();
        matte_ptr33.set_ka(ka);
        matte_ptr33.set_kd(kd);
        matte_ptr33.set_cd(RGBColor.green);
        Sphere sphere_ptr33 = new Sphere(new Point3D(-63, -39, -180), 11);
        sphere_ptr33.set_material(matte_ptr33);
        add_object(sphere_ptr33);

        Matte matte_ptr34 = new Matte();
        matte_ptr34.set_ka(ka);
        matte_ptr34.set_kd(kd);
        matte_ptr34.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr34 = new Sphere(new Point3D(46, 68, -200), 10);
        sphere_ptr34.set_material(matte_ptr34);
        add_object(sphere_ptr34);

        Matte matte_ptr35 = new Matte();
        matte_ptr35.set_ka(ka);
        matte_ptr35.set_kd(kd);
        matte_ptr35.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr35 = new Sphere(new Point3D(-3, -72, -130), 12);
        sphere_ptr35.set_material(matte_ptr35);
        add_object(sphere_ptr35);
    }
}