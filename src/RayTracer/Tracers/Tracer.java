package RayTracer.Tracers;

import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Ray;
import RayTracer.World;

public class Tracer {
    public World world_ptr;

    public Tracer() {

    }

    Tracer(World w) {
        this.world_ptr = w;
    }

    public RGBColor trace_ray(Ray ray) {
        return new RGBColor();
    }

    public RGBColor trace_ray(Ray ray, int depth) {
        return new RGBColor();
    }


}
