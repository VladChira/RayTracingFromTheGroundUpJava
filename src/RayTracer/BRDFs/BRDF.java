package RayTracer.BRDFs;

import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Vector3D;

import static RayTracer.Utilities.RGBColor.black;

/**
 * Bidirectional reflectance distribution function (BRDF) specifies the contribution of irradiance
 * (arriving in an element of solid angle centered on the direction wi) to the reflected radiance in the direction wo
 * This is a base class from which other BRDFs inherit from (see 13.6).
 */

public class BRDF {
    public BRDF() {

    }

    public RGBColor f(ShadeRec sr, Vector3D wo, Vector3D wi) {
        return (black);
    }

    public RGBColor sample_f(ShadeRec sr, Vector3D wo, Vector3D wi) {
        return (black);
    }

    public RGBColor sample_f(ShadeRec sr, Vector3D wo, Vector3D wi, float pdf) {
        return (black);
    }

    public RGBColor rho(ShadeRec sr, Vector3D wo) {
        return (black);
    }
}
