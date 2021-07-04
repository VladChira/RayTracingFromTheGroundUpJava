package RayTracer.Tracers;

import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Ray;
import RayTracer.ShadeRec;
import RayTracer.World;

@Deprecated
public class MultipleObjects extends Tracer {
    public MultipleObjects() {

    }

    public MultipleObjects(World world_ptr) {
        this.world_ptr = world_ptr;
    }

    @Override
    public RGBColor trace_ray(Ray ray) {
        ShadeRec sr = new ShadeRec(world_ptr.hit_bare_bones_objects(ray));
        if (sr.hit_an_object) {
            sr.ray.setTo(ray);
            return (new RGBColor(sr.material_ptr.shade(sr)));
        }
        return (world_ptr.backgroundColor);
    }
}
