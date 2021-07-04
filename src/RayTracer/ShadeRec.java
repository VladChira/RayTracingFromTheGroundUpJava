package RayTracer;

import RayTracer.Materials.Material;
import RayTracer.Materials.Matte;
import RayTracer.Utilities.Normal;
import RayTracer.Utilities.Point3D;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Ray;

public class ShadeRec {


    public boolean hit_an_object;
    public Material material_ptr;
    public Point3D hit_point = new Point3D();
    public Point3D local_hit_point = new Point3D();
    public Normal normal = new Normal();
    public Ray ray = new Ray();
    public World w;
    int depth;
    double t;

    public ShadeRec(World w) {
        hit_an_object = false;
        material_ptr = new Matte();
        hit_point = new Point3D();
        local_hit_point = new Point3D();
        normal = new Normal();
        ray = new Ray();
        depth = 0;
        t = 0.0;
        this.w = w;
    }

    public ShadeRec(ShadeRec sr) {
        hit_an_object = sr.hit_an_object;
        material_ptr = sr.material_ptr;
        hit_point.setTo(sr.hit_point);
        local_hit_point.setTo(sr.local_hit_point);
        normal.setTo(sr.normal);
        ray.setTo(sr.ray);
        depth = sr.depth;
        t = sr.t;
        w = sr.w;
    }

    public Material getMaterial() {
        return material_ptr;
    }

    public void setMaterial(Material material) {
        this.material_ptr = material;
    }
}
