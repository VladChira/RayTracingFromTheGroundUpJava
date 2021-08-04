package RayTracer.Materials;

import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;

public interface Material {

    RGBColor shade(ShadeRec sr);
    RGBColor area_light_shade(ShadeRec sr);
    RGBColor get_Le(ShadeRec sr);
}
