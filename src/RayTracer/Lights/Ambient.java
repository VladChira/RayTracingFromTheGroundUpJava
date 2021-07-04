package RayTracer.Lights;

import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Vector3D;

public class Ambient extends Light {

    private double ls;
    private RGBColor color_;

    public Ambient() {
        ls = 1.0;
        color_ = new RGBColor(1.0);
    }

    public Ambient(Ambient a) {
        this.ls = a.ls;
        color_.setTo(a.color_);
    }

    public void scale_radiance(double b) {
        this.ls = b;
    }

    public void set_color(float c) {
        color_.setTo(new RGBColor(c, c, c));
    }

    public void set_color(double r, double g, double b) {
        color_.setTo(new RGBColor(r, g, b));
    }

    public void set_color(RGBColor c) {
        color_.setTo(c);
    }

    @Override
    public Vector3D get_direction(ShadeRec s) {
        return (new Vector3D(0.0));
    }

    @Override
    public RGBColor L(ShadeRec s) {
        return (color_.multiplyBy(ls));
    }
}
