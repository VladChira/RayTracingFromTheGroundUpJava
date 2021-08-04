package RayTracer.Lights;

import RayTracer.Materials.Material;
import RayTracer.Objects.GeometricObject;
import RayTracer.ShadeRec;
import RayTracer.Utilities.*;

public class AreaLight implements Light {

    public GeometricObject object;
    public Material material;
    public Point3D sample_point;
    public Normal light_normal;
    public Vector3D wi;

    public boolean shadows = false;

    public AreaLight() {

    }

    @Override
    public Vector3D get_direction(ShadeRec sr) {
        sample_point = object.sample();
        light_normal = object.get_normal(sample_point);
        wi = sample_point.subtract(sr.hit_point);
        wi.normalize();
        return wi;
    }

    @Override
    public RGBColor L(ShadeRec sr) {
        double ndotd = new Vector3D(light_normal).negate().dotProduct(wi);
        if(ndotd > 0.0)
            return material.get_Le(sr);
        else return RGBColor.black;
    }

    @Override
    public double G(ShadeRec sr) {
        double ndotd = new Vector3D(light_normal).negate().dotProduct(wi);
        double d2 = sample_point.d_squared(sr.hit_point);
        return  ndotd/d2;
    }

    public void set_object(GeometricObject object) {
        this.object = object;
        material = object.get_material();
    }

    @Override
    public double pdf(ShadeRec sr) {
        return object.pdf(sr);
    }

    @Override
    public boolean casts_shadows() {
        return shadows;
    }

    public void enable_shadows() {
        shadows = true;
    }

    public void disable_shadows() {
        shadows = false;
    }

    @Override
    public boolean in_shadow(Ray r, ShadeRec sr) {
        double t = 0;
        double ts = (sample_point.subtract(r.o).dotProduct(r.d));
        for(GeometricObject object: sr.w.objects) {
            HitInformation hitInfo = object.hit(r, t, sr);
            t = hitInfo.t;
            if(hitInfo.hit && t < ts) return true;
        }
        return false;
    }
}
