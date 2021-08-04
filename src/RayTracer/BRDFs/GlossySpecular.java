package RayTracer.BRDFs;

import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Vector3D;

public class GlossySpecular implements BRDF {

    public double ks;
    public double e;
    public RGBColor cd;

    public GlossySpecular() {
        ks = 0;
        e = 0;
        cd = new RGBColor(0, 0, 0);
    }

    @Override
    public RGBColor f(ShadeRec sr, Vector3D wo, Vector3D wi) {
        RGBColor L = new RGBColor();
        Vector3D normalVector = new Vector3D(sr.normal);
        double ndotwi = normalVector.dotProduct(wi);
        Vector3D r = new Vector3D(wi.negate().addTo(normalVector.multiplyBy(2.0).multiplyBy(ndotwi)));
        double rdotwo = r.dotProduct(wo);
        if(rdotwo > 0.0) {
            L.setTo(cd.multiplyBy(ks).multiplyBy(Math.pow(rdotwo,e)));
        }
        return L;
    }

    @Override
    public RGBColor sample_f(ShadeRec sr, Vector3D wo, Vector3D wi) {
        return null;
    }

    @Override
    public RGBColor sample_f(ShadeRec sr, Vector3D wo, Vector3D wi, float pdf) {
        return null;
    }

    @Override
    public RGBColor rho(ShadeRec sr, Vector3D wo) {
        return RGBColor.black;
    }

    public void set_ks(double ks) {
        this.ks = ks;
    }

    public void set_cd(RGBColor c) {
        cd.setTo(c);
    }

    public void set_e(double e) {
        this.e = e;
    }
}
