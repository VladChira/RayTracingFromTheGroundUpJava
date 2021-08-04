package RayTracer.Objects;

import RayTracer.Materials.Material;
import RayTracer.Materials.Matte;
import RayTracer.Utilities.*;
import RayTracer.ShadeRec;

public abstract class GeometricObject {
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

    public abstract HitInformation hit(Ray ray, double tmin, ShadeRec s);

    public abstract Normal get_normal(Point3D sample_point);

    public double pdf(ShadeRec sr) {
        return 1;
    }

    public Point3D sample() {
        return new Point3D();
    }
}
