package RayTracer;

import RayTracer.Lights.*;
import RayTracer.Objects.*;
import RayTracer.Tracers.*;
import RayTracer.Utilities.*;
import RayTracer.Cameras.*;

import java.util.ArrayList;
import java.util.Arrays;

//TODO Use Builder Pattern

public class World {

    public ViewPlane vp;
    public RGBColor backgroundColor;
    public Tracer tracer;
    public Light ambient;
    public Camera camera;

    public ArrayList<Light> lights = new ArrayList<>();
    public ArrayList<GeometricObject> objects = new ArrayList<>();

    public static boolean wasOutOfGamut = false;

    public World() {
        backgroundColor = new RGBColor(RGBColor.black);
    }

    public void renderScene() {
        new Thread(() -> camera.render_scene(this)).start();
    }

    public void build(int num_samples) {
        if (SceneManager.displayMessages) System.out.println("Building world...");
        vp = new ViewPlane();
        vp.setPixelSize(1);
        vp.setSamples(num_samples);
        vp.show_out_of_gamut = false;
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

    public void set_camera(Camera c) {
        this.camera = c;
    }

    public void set_ambient(Light a) {
        if(!(a instanceof Ambient || a instanceof AmbientOccluder)) return;
        this.ambient = a;
    }

    public void set_tracer(Tracer t) {
        this.tracer = t;
    }

    public void add_object(GeometricObject... objs) {
        objects.addAll(Arrays.asList(objs));
    }

    public void add_light(Light... ls) {
        lights.addAll(Arrays.asList(ls));
    }

}