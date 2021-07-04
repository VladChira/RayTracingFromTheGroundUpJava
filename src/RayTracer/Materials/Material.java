package RayTracer.Materials;

import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;

import static RayTracer.Utilities.RGBColor.black;

public class Material {
    public Material() {

    }

    Material(Material material) {

    }

    public RGBColor shade(ShadeRec sr) {
        return (black);
    }
}
