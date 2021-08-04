package RayTracer;

import RayTracer.Cameras.*;
import RayTracer.Lights.*;
import RayTracer.Materials.*;
import RayTracer.Objects.*;
import RayTracer.Samplers.*;
import RayTracer.Tracers.*;
import RayTracer.Utilities.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SceneManager {
    public static final int WINDOW_SIZE = 700;
    public static volatile BufferedImage render = new BufferedImage(WINDOW_SIZE, WINDOW_SIZE, BufferedImage.TYPE_INT_RGB);
    public static boolean displayMessages = true;

    World w;
    private int num_samples;

    public SceneManager(int num_samples) {
        w = new World();
        this.num_samples = num_samples;
    }

    public void buildWorld() {
        w.build(num_samples);
    }
    
    public void renderWorld() {
        w.renderScene();
    }

    public void addObject(GeometricObject... objects) {
        w.add_object(objects);
    }
    
    public void addLight(Light... lights) {
        w.add_light(lights);
    }

    public void set_num_samples(int n) {
        this.num_samples = n;
    }

    public void singleSphereSetup() {
        AreaLighting areaLighting = new AreaLighting(w);
        w.set_tracer(areaLighting);

        Pinhole camera = new Pinhole();
        camera.set_eye(25, 20, -45);
        camera.set_lookat(0, 1, 0);
        camera.set_view_distance(5000);
        camera.compute_uvw();
        w.set_camera(camera);

        AmbientOccluder ambientOccluder = new AmbientOccluder();
        ambientOccluder.set_sampler(new MultiJittered(num_samples));
        ambientOccluder.set_min_amount(new RGBColor(0));
        ambientOccluder.set_color(RGBColor.white);
        ambientOccluder.scale_radiance(1.0);
        w.set_ambient(ambientOccluder);

        Matte matte_ptr1 = new Matte();
        matte_ptr1.set_ka(0.75);
        matte_ptr1.set_kd(0.0);
        matte_ptr1.set_cd(RGBColor.orange);

        Sphere sphere_ptr = new Sphere(new Point3D(0, 1, 0), 1);
        sphere_ptr.set_material(matte_ptr1);

        Matte matte_ptr2 = new Matte();
        matte_ptr2.set_ka(0.75);
        matte_ptr2.set_kd(0);
        matte_ptr2.set_cd(RGBColor.white);

        Plane plane_ptr = new Plane(new Point3D(0, 0, 0), new Normal(0, 1, 0));
        plane_ptr.set_material(matte_ptr2);
        
        addObject(plane_ptr);
        addObject(sphere_ptr);
    }

    public void manySpheresSetup() {
        AreaLighting areaLighting = new AreaLighting(w);
        w.set_tracer(areaLighting);

        Pinhole camera = new Pinhole();
        camera.set_eye(0, 0, 500);
        camera.set_lookat(5, 0, 0);
        camera.set_view_distance(850.0);
        camera.set_zoom(2.2);
        camera.compute_uvw();
        w.set_camera(camera);

        AmbientOccluder ambientOccluder = new AmbientOccluder();
        ambientOccluder.set_sampler(new MultiJittered(num_samples));
        ambientOccluder.set_color(RGBColor.white);
        ambientOccluder.set_min_amount(new RGBColor(0));
        ambientOccluder.scale_radiance(0.5);
        w.set_ambient(ambientOccluder);

        PointLight light2 = new PointLight();
        light2.set_location(new Vector3D(200, 100, 300));
        light2.scale_radiance(3.0);
        light2.enable_shadows();
        addLight(light2);

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
        addObject(sphere_ptr1);


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
        addObject(sphere_ptr2);

        Phong matte_ptr3 = new Phong();
        matte_ptr3.set_ka(ka);
        matte_ptr3.set_kd(kd);
        matte_ptr3.set_ks(0.4);
        matte_ptr3.set_e(20);
        matte_ptr3.set_cd(RGBColor.darkGreen);
        Sphere sphere_ptr3 = new Sphere(new Point3D(40, 43, -100), 17);
        sphere_ptr3.set_material(matte_ptr3);
        addObject(sphere_ptr3);

        Matte matte_ptr4 = new Matte();
        matte_ptr4.set_ka(ka);
        matte_ptr4.set_kd(kd);
        matte_ptr4.set_cd(RGBColor.orange);
        Sphere sphere_ptr4 = new Sphere(new Point3D(-20, 28, -15), 20);
        sphere_ptr4.set_material(matte_ptr4);
        addObject(sphere_ptr4);

        Matte matte_ptr5 = new Matte();
        matte_ptr5.set_ka(ka);
        matte_ptr5.set_kd(kd);
        matte_ptr5.set_cd(RGBColor.green);
        Sphere sphere_ptr5 = new Sphere(new Point3D(-25, -7, -35), 27);
        sphere_ptr5.set_material(matte_ptr5);
        addObject(sphere_ptr5);

        Phong matte_ptr6 = new Phong();
        matte_ptr6.set_ka(ka);
        matte_ptr6.set_kd(kd);
        matte_ptr6.set_cd(RGBColor.lightGreen);
        matte_ptr6.set_e(10);
        matte_ptr6.set_ks(0.5);
        Sphere sphere_ptr6 = new Sphere(new Point3D(20, -27, -35), 25);
        sphere_ptr6.set_material(matte_ptr6);
        addObject(sphere_ptr6);

        Matte matte_ptr7 = new Matte();
        matte_ptr7.set_ka(ka);
        matte_ptr7.set_kd(kd);
        matte_ptr7.set_cd(RGBColor.green);
        Sphere sphere_ptr7 = new Sphere(new Point3D(35, 18, -35), 22);
        sphere_ptr7.set_material(matte_ptr7);
        addObject(sphere_ptr7);

        Matte matte_ptr8 = new Matte();
        matte_ptr8.set_ka(ka);
        matte_ptr8.set_kd(kd);
        matte_ptr8.set_cd(RGBColor.brown);
        Sphere sphere_ptr8 = new Sphere(new Point3D(-57, -17, -50), 15);
        sphere_ptr8.set_material(matte_ptr8);
        addObject(sphere_ptr8);

        Matte matte_ptr9 = new Matte();
        matte_ptr9.set_ka(ka);
        matte_ptr9.set_kd(kd);
        matte_ptr9.set_cd(RGBColor.lightGreen);
        Sphere sphere_ptr9 = new Sphere(new Point3D(-47, 16, -80), 23);
        sphere_ptr9.set_material(matte_ptr9);
        addObject(sphere_ptr9);

        Matte matte_ptr10 = new Matte();
        matte_ptr10.set_ka(ka);
        matte_ptr10.set_kd(kd);
        matte_ptr10.set_cd(RGBColor.darkGreen);
        Sphere sphere_ptr10 = new Sphere(new Point3D(-15, -32, -60), 22);
        sphere_ptr10.set_material(matte_ptr10);
        addObject(sphere_ptr10);

        Matte matte_ptr11 = new Matte();
        matte_ptr11.set_ka(ka);
        matte_ptr11.set_kd(kd);
        matte_ptr11.set_cd(RGBColor.darkYellow);
        Sphere sphere_ptr11 = new Sphere(new Point3D(-35, -37, -80), 22);
        sphere_ptr11.set_material(matte_ptr11);
        addObject(sphere_ptr11);

        Matte matte_ptr12 = new Matte();
        matte_ptr12.set_ka(ka);
        matte_ptr12.set_kd(kd);
        matte_ptr12.set_cd(RGBColor.darkYellow);
        Sphere sphere_ptr12 = new Sphere(new Point3D(10, 43, -80), 22);
        sphere_ptr12.set_material(matte_ptr12);
        addObject(sphere_ptr12);

        Matte matte_ptr13 = new Matte();
        matte_ptr13.set_ka(ka);
        matte_ptr13.set_kd(kd);
        matte_ptr13.set_cd(RGBColor.darkYellow);
        Sphere sphere_ptr13 = new Sphere(new Point3D(30, -7, -80), 10);
        sphere_ptr13.set_material(matte_ptr13);
        addObject(sphere_ptr13);

        Matte matte_ptr14 = new Matte();
        matte_ptr14.set_ka(ka);
        matte_ptr14.set_kd(kd);
        matte_ptr14.set_cd(RGBColor.darkGreen);
        Sphere sphere_ptr14 = new Sphere(new Point3D(-40, 48, -110), 18);
        sphere_ptr14.set_material(matte_ptr14);
        addObject(sphere_ptr14);

        Phong matte_ptr15 = new Phong();
        matte_ptr15.set_ka(0.5);
        matte_ptr15.set_kd(0.5);
        matte_ptr15.set_cd(RGBColor.brown);
        matte_ptr15.set_e(10);
        matte_ptr15.set_ks(0.5);

        Sphere sphere_ptr15 = new Sphere(new Point3D(-10, 53, -120), 18);
        sphere_ptr15.set_material(matte_ptr15);
        addObject(sphere_ptr15);

        Matte matte_ptr16 = new Matte();
        matte_ptr16.set_ka(ka);
        matte_ptr16.set_kd(kd);
        matte_ptr16.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr16 = new Sphere(new Point3D(-55, -52, -100), 10);
        sphere_ptr16.set_material(matte_ptr16);
        addObject(sphere_ptr16);

        Matte matte_ptr17 = new Matte();
        matte_ptr17.set_ka(ka);
        matte_ptr17.set_kd(kd);
        matte_ptr17.set_cd(RGBColor.brown);
        Sphere sphere_ptr17 = new Sphere(new Point3D(5, -52, -100), 15);
        sphere_ptr17.set_material(matte_ptr17);
        addObject(sphere_ptr17);

        Phong matte_ptr18 = new Phong();
        matte_ptr18.set_ka(ka);
        matte_ptr18.set_kd(kd);
        matte_ptr18.set_cd(RGBColor.darkPurple);
        matte_ptr18.set_ks(0.4);
        matte_ptr18.set_e(100);
        Sphere sphere_ptr18 = new Sphere(new Point3D(-20, -57, -120), 15);
        sphere_ptr18.set_material(matte_ptr18);
        addObject(sphere_ptr18);

        Matte matte_ptr19 = new Matte();
        matte_ptr19.set_ka(ka);
        matte_ptr19.set_kd(kd);
        matte_ptr19.set_cd(RGBColor.darkGreen);
        Sphere sphere_ptr19 = new Sphere(new Point3D(55, -27, -100), 17);
        sphere_ptr19.set_material(matte_ptr19);
        addObject(sphere_ptr19);

        Matte matte_ptr20 = new Matte();
        matte_ptr20.set_ka(ka);
        matte_ptr20.set_kd(kd);
        matte_ptr20.set_cd(RGBColor.brown);
        Sphere sphere_ptr20 = new Sphere(new Point3D(50, -47, -120), 15);
        sphere_ptr20.set_material(matte_ptr20);
        addObject(sphere_ptr20);

        Matte matte_ptr21 = new Matte();
        matte_ptr21.set_ka(ka);
        matte_ptr21.set_kd(kd);
        matte_ptr21.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr21 = new Sphere(new Point3D(70, -42, -150), 10);
        sphere_ptr21.set_material(matte_ptr21);
        addObject(sphere_ptr21);

        Matte matte_ptr22 = new Matte();
        matte_ptr22.set_ka(ka);
        matte_ptr22.set_kd(kd);
        matte_ptr22.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr22 = new Sphere(new Point3D(5, 73, -130), 12);
        sphere_ptr22.set_material(matte_ptr22);
        addObject(sphere_ptr22);

        Matte matte_ptr23 = new Matte();
        matte_ptr23.set_ka(ka);
        matte_ptr23.set_kd(kd);
        matte_ptr23.set_cd(RGBColor.darkPurple);
        Sphere sphere_ptr23 = new Sphere(new Point3D(66, 21, -130), 13);
        sphere_ptr23.set_material(matte_ptr23);
        addObject(sphere_ptr23);

        Matte matte_ptr24 = new Matte();
        matte_ptr24.set_ka(ka);
        matte_ptr24.set_kd(kd);
        matte_ptr24.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr24 = new Sphere(new Point3D(72, -12, -140), 12);
        sphere_ptr24.set_material(matte_ptr24);
        addObject(sphere_ptr24);

        Matte matte_ptr25 = new Matte();
        matte_ptr25.set_ka(ka);
        matte_ptr25.set_kd(kd);
        matte_ptr25.set_cd(RGBColor.green);
        Sphere sphere_ptr25 = new Sphere(new Point3D(64, 5, -160), 11);
        sphere_ptr25.set_material(matte_ptr25);
        addObject(sphere_ptr25);

        Matte matte_ptr26 = new Matte();
        matte_ptr26.set_ka(ka);
        matte_ptr26.set_kd(kd);
        matte_ptr26.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr26 = new Sphere(new Point3D(55, 38, -160), 12);
        sphere_ptr26.set_material(matte_ptr26);
        addObject(sphere_ptr26);

        Matte matte_ptr27 = new Matte();
        matte_ptr27.set_ka(ka);
        matte_ptr27.set_kd(kd);
        matte_ptr27.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr27 = new Sphere(new Point3D(-73, -2, -160), 12);
        sphere_ptr27.set_material(matte_ptr27);
        addObject(sphere_ptr27);

        Matte matte_ptr28 = new Matte();
        matte_ptr28.set_ka(ka);
        matte_ptr28.set_kd(kd);
        matte_ptr28.set_cd(RGBColor.darkPurple);
        Sphere sphere_ptr28 = new Sphere(new Point3D(30, -62, -140), 15);
        sphere_ptr28.set_material(matte_ptr28);
        addObject(sphere_ptr28);

        Matte matte_ptr29 = new Matte();
        matte_ptr29.set_ka(ka);
        matte_ptr29.set_kd(kd);
        matte_ptr29.set_cd(RGBColor.darkPurple);
        Sphere sphere_ptr29 = new Sphere(new Point3D(25, 63, -140), 15);
        sphere_ptr29.set_material(matte_ptr29);
        addObject(sphere_ptr29);

        Matte matte_ptr30 = new Matte();
        matte_ptr30.set_ka(ka);
        matte_ptr30.set_kd(kd);
        matte_ptr30.set_cd(RGBColor.darkPurple);
        Sphere sphere_ptr30 = new Sphere(new Point3D(-60, 46, -140), 15);
        sphere_ptr30.set_material(matte_ptr30);
        addObject(sphere_ptr30);

        Matte matte_ptr31 = new Matte();
        matte_ptr31.set_ka(ka);
        matte_ptr31.set_kd(kd);
        matte_ptr31.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr31 = new Sphere(new Point3D(-30, 68, -130), 12);
        sphere_ptr31.set_material(matte_ptr31);
        addObject(sphere_ptr31);

        Matte matte_ptr32 = new Matte();
        matte_ptr32.set_ka(ka);
        matte_ptr32.set_kd(kd);
        matte_ptr32.set_cd(RGBColor.green);
        Sphere sphere_ptr32 = new Sphere(new Point3D(58, 56, -180), 11);
        sphere_ptr32.set_material(matte_ptr32);
        addObject(sphere_ptr32);

        Matte matte_ptr33 = new Matte();
        matte_ptr33.set_ka(ka);
        matte_ptr33.set_kd(kd);
        matte_ptr33.set_cd(RGBColor.green);
        Sphere sphere_ptr33 = new Sphere(new Point3D(-63, -39, -180), 11);
        sphere_ptr33.set_material(matte_ptr33);
        addObject(sphere_ptr33);

        Matte matte_ptr34 = new Matte();
        matte_ptr34.set_ka(ka);
        matte_ptr34.set_kd(kd);
        matte_ptr34.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr34 = new Sphere(new Point3D(46, 68, -200), 10);
        sphere_ptr34.set_material(matte_ptr34);
        addObject(sphere_ptr34);

        Matte matte_ptr35 = new Matte();
        matte_ptr35.set_ka(ka);
        matte_ptr35.set_kd(kd);
        matte_ptr35.set_cd(RGBColor.lightPurple);
        Sphere sphere_ptr35 = new Sphere(new Point3D(-3, -72, -130), 12);
        sphere_ptr35.set_material(matte_ptr35);
        addObject(sphere_ptr35);
    }

    public void cornellBoxSetup() {
        Pinhole pinhole_ptr = new Pinhole();
        pinhole_ptr.set_eye(new Point3D(27.6, 27.4, -80.0));
        pinhole_ptr.set_lookat(new Point3D(27.6, 27.4, 0.0));
        pinhole_ptr.set_view_distance(400);
        pinhole_ptr.set_zoom(2.2);
        pinhole_ptr.compute_uvw();
        w.set_camera(pinhole_ptr);

        AreaLighting tracer = new AreaLighting(w);
        w.set_tracer(tracer);

        AmbientOccluder ambientOccluder = new AmbientOccluder();
        ambientOccluder.scale_radiance(0);
        ambientOccluder.set_min_amount(RGBColor.black);
        ambientOccluder.set_color(RGBColor.white);
        ambientOccluder.set_sampler(new MultiJittered(num_samples));
        w.set_ambient(ambientOccluder);

        Point3D p0;
        Vector3D a, b;
        Normal normal;

        // box dimensions

        double width 	= 55.28;   	// x direction
        double height 	= 54.88;  	// y direction
        double depth 	= 55.92;	// z direction

        Emissive emissive_ptr = new Emissive();
        emissive_ptr.set_ce(new RGBColor(1.0, 0.73, 0.4));
        emissive_ptr.scale_radiance(300);

        p0 = new Point3D(21.3, height - 0.001, 22.7);
        a = new Vector3D(0.0, 0.0, 10.5);
        b = new Vector3D(13.0, 0.0, 0.0);
        normal = new Normal(0.0, -1.0, 0.0);
        Rectangle light_ptr = new Rectangle(p0, a, b, normal);
        light_ptr.set_material(emissive_ptr);
        w.add_object(light_ptr);

        AreaLight areaLight = new AreaLight();
        areaLight.enable_shadows();
        areaLight.set_object(light_ptr);
        w.add_light(areaLight);

        Phong spheremat = new Phong();
        spheremat.set_e(100);
        spheremat.set_ks(0.5);
        spheremat.set_cd(RGBColor.lightGreen);
        spheremat.set_ka(0.5);
        spheremat.set_kd(0.5);
        Sphere sphere = new Sphere(new Point3D(30, 30, 22.7), 5);
        sphere.set_material(spheremat);


        // left wall

        Matte matte_ptr1 = new Matte();
        matte_ptr1.set_ka(0.0);
        matte_ptr1.set_kd(0.6);
        matte_ptr1.set_cd(new RGBColor(0.57, 0.025, 0.025));

        p0 = new Point3D(width, 0.0, 0.0);
        a = new Vector3D(0.0, 0.0, depth);
        b = new Vector3D(0.0, height, 0.0);
        normal = new Normal(-1.0, 0.0, 0.0);
        Rectangle left_wall_ptr = new Rectangle(p0, a, b, normal);
        left_wall_ptr.set_material(matte_ptr1);
        w.add_object(left_wall_ptr);


        // right wall

        Matte matte_ptr2 = new Matte();
        matte_ptr2.set_ka(0.0);
        matte_ptr2.set_kd(0.6);
        matte_ptr2.set_cd(new RGBColor(0.37, 0.59, 0.2));

        p0 = new Point3D(0.0, 0.0, 0.0);
        a = new Vector3D(0.0, 0.0, depth);
        b = new Vector3D(0.0, height, 0.0);
        normal = new Normal(1.0, 0.0, 0.0);
        Rectangle right_wall_ptr = new Rectangle(p0, a, b, normal);
        right_wall_ptr.set_material(matte_ptr2);
        w.add_object(right_wall_ptr);


        // back wall

        Matte matte_ptr3 = new Matte();
        matte_ptr3.set_ka(0.0);
        matte_ptr3.set_kd(0.6);
        matte_ptr3.set_cd(new RGBColor(1.0));	 // white

        p0 = new Point3D(0.0, 0.0, depth);
        a = new Vector3D(width, 0.0, 0.0);
        b = new Vector3D(0.0, height, 0.0);
        normal = new Normal(0.0, 0.0, -1.0);
        Rectangle back_wall_ptr = new Rectangle(p0, a, b, normal);
        back_wall_ptr.set_material(matte_ptr3);
        w.add_object(back_wall_ptr);


        // floor

        p0 = new Point3D(0.0, 0.0, 0.0);
        a = new Vector3D(0.0, 0.0, depth);
        b = new Vector3D(width, 0.0, 0.0);
        normal = new Normal(0.0, 1.0, 0.0);
        Rectangle floor_ptr = new Rectangle(p0, a, b, normal);
        floor_ptr.set_material(matte_ptr3);
        w.add_object(floor_ptr);


        // ceiling

        p0 = new Point3D(0.0, height, 0.0);
        a = new Vector3D(0.0, 0.0, depth);
        b = new Vector3D(width, 0.0, 0.0);
        normal = new Normal(0.0, -1.0, 0.0);
        Rectangle ceiling_ptr = new Rectangle(p0, a, b, normal);
        ceiling_ptr.set_material(matte_ptr3);
        w.add_object(ceiling_ptr);

        addObject(sphere);


        // the two boxes defined as 5 rectangles each

        // short box

        // top

        p0 = new Point3D(13.0, 16.5, 6.5);
        a = new Vector3D(-4.8, 0.0, 16.0);
        b = new Vector3D(16.0, 0.0, 4.9);
        normal = new Normal(0.0, 1.0, 0.0);
        Rectangle short_top_ptr = new Rectangle(p0, a, b, normal);
        short_top_ptr.set_material(matte_ptr3);
        //w.add_object(short_top_ptr);


        // side 1

        p0 = new Point3D(13.0, 0.0, 6.5);
        a = new Vector3D(-4.8, 0.0, 16.0);
        b = new Vector3D(0.0, 16.5, 0.0);
        Rectangle short_side_ptr1 = new Rectangle(p0, a, b);
        short_side_ptr1.set_material(matte_ptr3);
        //w.add_object(short_side_ptr1);


        // side 2

        p0 = new Point3D(8.2, 0.0, 22.5);
        a = new Vector3D(15.8, 0.0, 4.7);
        Rectangle short_side_ptr2 = new Rectangle(p0, a, b);
        short_side_ptr2.set_material(matte_ptr3);
        //w.add_object(short_side_ptr2);


        // side 3

        p0 = new Point3D(24.2, 0.0, 27.4);
        a = new Vector3D(4.8, 0.0, -16.0);
        Rectangle short_side_ptr3 = new Rectangle(p0, a, b);
        short_side_ptr3.set_material(matte_ptr3);
        //w.add_object(short_side_ptr3);


        // side 4

        p0 = new Point3D(29.0, 0.0, 11.4);
        a = new Vector3D(-16.0, 0.0, -4.9);
        Rectangle short_side_ptr4 = new Rectangle(p0, a, b);
        short_side_ptr4.set_material(matte_ptr3);
        //w.add_object(short_side_ptr4);


        // tall box

        // top

        p0 = new Point3D(42.3, 33.0, 24.7);
        a = new Vector3D(-15.8, 0.0, 4.9);
        b = new Vector3D(4.9, 0.0, 15.9);
        normal = new Normal(0.0, 1.0, 0.0);
        Rectangle tall_top_ptr = new Rectangle(p0, a, b, normal);
        tall_top_ptr.set_material(matte_ptr3);
        //w.add_object(tall_top_ptr);


        // side 1

        p0 = new Point3D(42.3, 0.0, 24.7);
        a = new Vector3D(-15.8, 0.0, 4.9);
        b = new Vector3D(0.0, 33.0, 0.0);
        Rectangle tall_side_ptr1 = new Rectangle(p0, a, b);
        tall_side_ptr1.set_material(matte_ptr3);
        //w.add_object(tall_side_ptr1);


        // side 2

        p0 = new Point3D(26.5, 0.0, 29.6);
        a = new Vector3D(4.9, 0.0, 15.9);
        Rectangle tall_side_ptr2 = new Rectangle(p0, a, b);
        tall_side_ptr2.set_material(matte_ptr3);
        //w.add_object(tall_side_ptr2);


        // side 3

        p0 = new Point3D(31.4, 0.0, 45.5);
        a = new Vector3D(15.8, 0.0, -4.9);
        Rectangle tall_side_ptr3 = new Rectangle(p0, a, b);
        tall_side_ptr3.set_material(matte_ptr3);
        //w.add_object(tall_side_ptr3);


        // side 4

        p0 = new Point3D(47.2, 0.0, 40.6);
        a = new Vector3D(-4.9, 0.0, -15.9);
        Rectangle tall_side_ptr4 = new Rectangle(p0, a, b);
        tall_side_ptr4.set_material(matte_ptr3);
        //w.add_object(tall_side_ptr4);
    }

    public void standfordBunnySetup() {
        //coming soon
    }

    public void areaLightSetup() {
        Pinhole camera = new Pinhole();
        camera.set_eye(-20, 10, 20);
        camera.set_lookat(0, 2, 0);
        camera.set_view_distance(1080);
        camera.compute_uvw();
        w.set_camera(camera);

        AreaLighting areaLight = new AreaLighting(w);
        w.set_tracer(areaLight);

        Ambient ambient = new Ambient();
        ambient.scale_radiance(0.0);
        w.set_ambient(ambient);

        Emissive emissive_ptr = new Emissive();
        emissive_ptr.scale_radiance(40.0);
        emissive_ptr.set_ce(RGBColor.white);

        double width = 4.0;
        double height = 4.0;
        Point3D center = new Point3D(0.0, 7.0, -7.0);
        Point3D p0 = new Point3D(-0.5 *  width, center.y - 0.5 * height, center.z);
        Vector3D a = new Vector3D(width, 0.0, 0.0);
        Vector3D b = new Vector3D(0.0, height, 0.0);
        Normal normal = new Normal(0, 0, 1);

        Rectangle rectangle_ptr = new Rectangle(p0, a, b, normal);
        rectangle_ptr.set_material(emissive_ptr);
        rectangle_ptr.set_sampler(new MultiJittered(num_samples));

        AreaLight area_light_ptr = new AreaLight();
        area_light_ptr.set_object(rectangle_ptr);
        area_light_ptr.enable_shadows();
        addLight(area_light_ptr);

        Matte matte_ptr1 = new Matte();
        matte_ptr1.set_ka(0.25);
        matte_ptr1.set_kd(0.75);
        matte_ptr1.set_cd(new RGBColor(0.4, 0.7, 0.4));     // green
        
        // ground plane

        Matte matte_ptr2 = new Matte();
        matte_ptr2.set_ka(0.1);
        matte_ptr2.set_kd(0.90);
        matte_ptr2.set_cd(RGBColor.white);

        Plane plane_ptr = new Plane(new Point3D(0.0), new Normal(0, 1, 0));
        plane_ptr.set_material(matte_ptr2);
        addObject(plane_ptr);

        Sphere sphere = new Sphere(new Point3D(0, 3, 0), 2);
        Matte matte = new Matte();
        matte.set_kd(0.5);
        matte.set_ka(0.5);
        matte.set_cd(RGBColor.red);
        sphere.set_material(matte);
        addObject(sphere);

        addObject(rectangle_ptr);
    }

    public static void saveToFile(BufferedImage image) {
        File f = new File("output.png");
        try {
            ImageIO.write(image, "PNG", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved to file.");
    }
}
