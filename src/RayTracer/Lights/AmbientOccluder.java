package RayTracer.Lights;

import RayTracer.Samplers.*;
import RayTracer.ShadeRec;
import RayTracer.Utilities.*;

/**
 * Ambient occlusion is achieved by sampling the hemisphere above a certain point
 * and computing how much of that hemisphere is not blocked (occluded) by objects. See 17.1.
 */

public class AmbientOccluder implements Light {

    Vector3D u, v, w;
    Sampler s;
    RGBColor min_amount;
    RGBColor color;
    double ls;

    public AmbientOccluder() {
        u = new Vector3D();
        v = new Vector3D();
        w = new Vector3D();
        min_amount = new RGBColor();
        color = new RGBColor(RGBColor.white);
        ls = 1;
        s = new Regular(1);
        s.map_samples_to_hemisphere(1);
    }

    public void set_sampler(Sampler s) {
        this.s = s;
        s.map_samples_to_hemisphere(1);
    }

    public Vector3D get_direction(ShadeRec sr) {
        Point3D sp = s.sample_hemisphere();
        return u.multiplyBy(sp.x).addTo(v.multiplyBy(sp.y).addTo(w.multiplyBy(sp.z)));
    }

    public boolean in_shadow(Ray ray, ShadeRec sr) {
        int num_objects = sr.w.objects.size();
        for (int j = 0; j < num_objects; j++) {
            if ((sr.w.objects.get(j).hit(ray, 0, sr).hit)) return true;
        }
        return false;
    }

    public RGBColor L(ShadeRec sr) {
        w = new Vector3D(sr.normal);
        v = w.crossProduct(new Vector3D(0.0072, 1.0, 0.00340));
        v.normalize();
        u = v.crossProduct(w);

        Ray shadowRay = new Ray();
        shadowRay.o = sr.hit_point;
        shadowRay.d = get_direction(sr);

        if (in_shadow(shadowRay, sr)) return color.multiplyBy(min_amount.multiplyBy(ls));
        else return color.multiplyBy(ls);
    }

    @Override
    public boolean casts_shadows() {
        return false;
    }

    public void scale_radiance(double ls) {
        this.ls = ls;
    }

    public void set_color(RGBColor c) {
        this.color.setTo(c);
    }

    public void set_min_amount(RGBColor c) {
        this.min_amount = c;
    }
}
