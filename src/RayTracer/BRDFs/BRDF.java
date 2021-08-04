package RayTracer.BRDFs;

import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Vector3D;

/**
 * Bidirectional reflectance distribution function (BRDF) specifies the contribution of irradiance
 * (arriving in an element of solid angle centered on the direction wi) to the reflected radiance in the direction wo
 * This is a base interface from which other BRDFs inherit from (see 13.6).
 */

public interface BRDF {

    RGBColor f(ShadeRec sr, Vector3D wo, Vector3D wi);

    RGBColor sample_f(ShadeRec sr, Vector3D wo, Vector3D wi);

    RGBColor sample_f(ShadeRec sr, Vector3D wo, Vector3D wi, float pdf);

    RGBColor rho(ShadeRec sr, Vector3D wo);
}
