package RayTracer.Lights;

import RayTracer.Objects.GeometricObject;
import RayTracer.ShadeRec;
import RayTracer.Utilities.HitInformation;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Ray;
import RayTracer.Utilities.Vector3D;

import static RayTracer.Utilities.RGBColor.white;

public class PointLight implements Light {

    private double ls;
    private RGBColor color_;
    private Vector3D location;
    private boolean shadows;

    public PointLight() {
        ls = 1.0;
        color_ = new RGBColor(white);
        location = new Vector3D(0.0);
        shadows = false;
    }

    @Override
    public boolean in_shadow(Ray ray, ShadeRec sr) {
        double t = 0;
        double d = location.distance(ray.o);
        int num_objects = sr.w.objects.size();
        for (int j = 0; j < num_objects; j++) {
            HitInformation hitInfo = sr.w.objects.get(j).hit(ray, t, sr);
            t = hitInfo.t;
            if(hitInfo.hit && t < d) return true;
        }
        return false;
    }


    public Vector3D get_direction(ShadeRec sr) {
        return ((location.subtract(new Vector3D(sr.hit_point))).getHat());
    }

    public RGBColor L(ShadeRec sr) {
        return (color_.multiplyBy(ls));
    }

    public void scale_radiance(double ls) {
        this.ls = ls;
    }

    public void set_color(RGBColor c) {
        this.color_.setTo(c);
    }

    public void set_location(Vector3D l) {
        location.setTo(l);
    }


    public void enable_shadows() {
        shadows = true;
    }

    public void disable_shadows() {
        shadows = false;
    }

    public boolean casts_shadows() { return shadows; }
}
