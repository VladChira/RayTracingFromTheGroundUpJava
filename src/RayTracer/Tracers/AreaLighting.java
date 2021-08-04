package RayTracer.Tracers;

import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Ray;
import RayTracer.World;

public class AreaLighting extends Tracer{

    public AreaLighting(World w) {
        super(w);
    }

    @Override
    public RGBColor trace_ray(Ray ray, int depth) {
        ShadeRec sr = new ShadeRec(world_ptr.hit_objects(ray));
        if (sr.hit_an_object) {
            sr.ray.setTo(ray);
            return sr.material_ptr.area_light_shade(sr);
        } else
            return world_ptr.backgroundColor;
    }
}
