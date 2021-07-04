package RayTracer.Lights;

import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Vector3D;

import static RayTracer.Utilities.RGBColor.white;

public class Directional extends Light {

    private double ls;
    private RGBColor color_;
    private Vector3D direction;

    public Directional() {
        ls = 1.0;
        color_ = new RGBColor(white);
        direction = new Vector3D(0, 1, 0);
    }

    public Vector3D get_direction(ShadeRec sr) {
        return direction;
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

    public void set_direction(Vector3D l) {
        direction.setTo(l);
    }


}
