package RayTracer.Materials;

import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Vector3D;

/**
 *  The self emitting material is used for the direct rendering of
 *  the light, not for the illumination of other surfaces.
 */

public class Emissive implements Material {

    double ls;
    RGBColor ce;

    public void scale_radiance(double l) {
        this.ls = l;
    }

    public void set_ce(RGBColor ce) {
        this.ce = ce;
    }

    public RGBColor get_Le(ShadeRec sr) {
        return ce.multiplyBy(ls);
    }

    @Override
    public RGBColor shade(ShadeRec sr) {
        return RGBColor.black;
    }

    @Override
    public RGBColor area_light_shade(ShadeRec sr) {
        if((new Vector3D(sr.normal).negate()).dotProduct(sr.ray.d) > 0.0)
            return ce.multiplyBy(ls); //only shades on side of the surface
        else return RGBColor.black;
    }
}
