package RayTracer.Tracers;

import RayTracer.Materials.Matte;
import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Ray;
import RayTracer.World;

public class RayCast extends Tracer {
    public RayCast() {

    }

    public RayCast(World world_ptr) {
        this.world_ptr = world_ptr;
    }

    @Override
    public RGBColor trace_ray(Ray ray) {
        ShadeRec sr = new ShadeRec(world_ptr.hit_objects(ray));
        if (sr.hit_an_object) {
            sr.ray.setTo(ray);
            return (sr.material_ptr.shade(sr));
        } else
            return world_ptr.backgroundColor;
    }

    @Override
    public RGBColor trace_ray(Ray ray, int depth) {
        ShadeRec sr = new ShadeRec(world_ptr.hit_objects(ray));

        if (sr.hit_an_object) {
            sr.ray.setTo(ray);
            return (new RGBColor(sr.material_ptr.shade(sr)));
        } else
            return world_ptr.backgroundColor;
    }

}
