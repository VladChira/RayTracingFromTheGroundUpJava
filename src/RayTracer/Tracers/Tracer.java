package RayTracer.Tracers;

import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Ray;
import RayTracer.World;

public abstract class Tracer {
    public World world_ptr;

    Tracer(World w) {
        this.world_ptr = w;
    }

    public abstract RGBColor trace_ray(Ray ray, int depth);


}
