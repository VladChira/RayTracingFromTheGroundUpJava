package RayTracer.Objects;

import RayTracer.Materials.Material;
import RayTracer.Materials.Matte;
import RayTracer.Utilities.HitInformation;
import RayTracer.Utilities.RGBColor;
import RayTracer.ShadeRec;
import RayTracer.Utilities.Ray;

public class GeometricObject {
    Material material_ptr;

    public GeometricObject() {
        material_ptr = new Matte();
    }

    public GeometricObject(GeometricObject object) {
        material_ptr = object.material_ptr;
    }

    public void set_material(Material m) {
        this.material_ptr = m;
    }

    public Material get_material() {
        return this.material_ptr;
    }

    public HitInformation hit(Ray ray, double tmin, ShadeRec s) {
        return new HitInformation(false, tmin, s);
    }
}
