package RayTracer.Lights;

import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Vector3D;

import static RayTracer.Utilities.RGBColor.white;

public class PointLight extends Light {

    private double ls;
    private RGBColor color_;
    private Vector3D location;

    public PointLight() {
        ls = 1.0;
        color_ = new RGBColor(white);
        location = new Vector3D(0.0);
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
}
