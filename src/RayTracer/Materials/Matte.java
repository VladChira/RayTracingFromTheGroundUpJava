package RayTracer.Materials;

import RayTracer.BRDFs.Lambertian;
import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Ray;
import RayTracer.Utilities.Vector3D;

/**
 * A matte surface is represented here with 2 BRDFs: a diffuse Lambertian
 * and an ambient Lambertian (see 14.7).
 */
public class Matte implements Material {
    public Lambertian ambient_brdf;
    public Lambertian diffuse_brdf;

    public Matte() {
        ambient_brdf = new Lambertian();
        diffuse_brdf = new Lambertian();
    }

    public Matte(Matte m) {
        ambient_brdf = m.ambient_brdf;
        diffuse_brdf = m.diffuse_brdf;
    }

    public void set_ka(double ka) {
        ambient_brdf.set_kd(ka);
    }

    public void set_kd(double kd) {
        diffuse_brdf.set_kd(kd);
    }

    public void set_cd(RGBColor c) {
        ambient_brdf.set_cd(c);
        diffuse_brdf.set_cd(c);
    }

    @Override
    public RGBColor shade(ShadeRec sr) {
        Vector3D wo = new Vector3D(sr.ray.d.negate());
        RGBColor t1 = ambient_brdf.rho(sr, wo);
        RGBColor t2 = sr.w.ambient.L(sr);
        RGBColor L = t1.multiplyBy(t2);

        double accR = L.r;
        double accG = L.g;
        double accB = L.b;
        int num_lights = sr.w.lights.size();
        for (int j = 0; j < num_lights; j++) {
            Vector3D wi = new Vector3D((sr.w.lights.get(j)).get_direction(sr));
            double ndotwi = wi.dotProduct(new Vector3D(sr.normal));

            if (ndotwi > 0.0) {
                boolean in_shadow = false;
                if (sr.w.lights.get(j).casts_shadows()) {
                    Ray shadowRay = new Ray(sr.hit_point, wi);
                    in_shadow = sr.w.lights.get(j).in_shadow(shadowRay, sr);
                }
                if (!in_shadow) {
                    RGBColor f1 = diffuse_brdf.f(sr, wo, wi).multiplyBy(sr.w.lights.get(j).L(sr));
                    RGBColor newL = f1.multiplyBy(ndotwi);
                    double r1 = newL.r;
                    double r2 = newL.g;
                    double r3 = newL.b;
                    accR += r1;
                    accG += r2;
                    accB += r3;
                }
            }
        }
        return new RGBColor(accR, accG, accB);
    }

    @Override
    public RGBColor area_light_shade(ShadeRec sr) {
        Vector3D wo = new Vector3D(sr.ray.d.negate());
        RGBColor t1 = ambient_brdf.rho(sr, wo);
        RGBColor t2 = sr.w.ambient.L(sr);
        RGBColor L = t1.multiplyBy(t2);

        double accR = L.r;
        double accG = L.g;
        double accB = L.b;
        int num_lights = sr.w.lights.size();
        for (int j = 0; j < num_lights; j++) {
            Vector3D wi = new Vector3D((sr.w.lights.get(j)).get_direction(sr));
            double ndotwi = wi.dotProduct(new Vector3D(sr.normal));

            if (ndotwi > 0.0) {
                boolean in_shadow = false;
                if (sr.w.lights.get(j).casts_shadows()) {
                    Ray shadowRay = new Ray(sr.hit_point, wi);
                    in_shadow = sr.w.lights.get(j).in_shadow(shadowRay, sr);
                }
                if (!in_shadow) {
                    RGBColor f1 = diffuse_brdf.f(sr, wo, wi).multiplyBy(sr.w.lights.get(j).L(sr)).multiplyBy(sr.w.lights.get(j).G(sr));
                    RGBColor newL = f1.multiplyBy(ndotwi).divide(sr.w.lights.get(j).pdf(sr));
                    double r1 = newL.r;
                    double r2 = newL.g;
                    double r3 = newL.b;
                    accR += r1;
                    accG += r2;
                    accB += r3;
                }
            }
        }
        return new RGBColor(accR, accG, accB);
    }

    @Override
    public RGBColor get_Le(ShadeRec sr) {
        return RGBColor.black;
    }
}