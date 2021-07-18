package RayTracer.BRDFs;

import RayTracer.ShadeRec;
import RayTracer.Utilities.Constants;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Vector3D;

/**
 * The simplest BRDF is the Lambertian BRDF. It represents perfect diffuse reflection.
 * Light is scattered equally in all directions (see 13.8).
 */

public class Lambertian extends BRDF {

    public double kd;
    public RGBColor cd;

    public Lambertian() {

    }

    @Override
    public RGBColor f(ShadeRec sr, Vector3D wo, Vector3D wi) {
        return (cd.multiplyBy(kd).multiplyBy(Constants.invPI));
    }

    @Override
    public RGBColor rho(ShadeRec sr, Vector3D wo) {
        return (cd.multiplyBy(kd));
    }

    public void set_kd(double kd) {
        this.kd = kd;
    }

    public void set_cd(RGBColor c) {
        cd = c;
    }
}
