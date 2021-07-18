package RayTracer.Lights;

import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Ray;
import RayTracer.Utilities.Vector3D;

public interface Light {

    Vector3D get_direction(ShadeRec sr);

    RGBColor L(ShadeRec sr);

    boolean casts_shadows();

    boolean in_shadow(Ray r, ShadeRec sr);
}
