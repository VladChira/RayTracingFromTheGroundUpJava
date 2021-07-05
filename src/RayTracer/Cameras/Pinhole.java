package RayTracer.Cameras;

import RayTracer.Utilities.*;
import RayTracer.World;

import java.awt.*;

public class Pinhole extends Camera {

    private double d;
    private double zoom;

    public Pinhole() {
        super();
        d = 500;
        zoom = 1;
    }

    public void set_view_distance(double vpd) {
        this.d = vpd;
    }

    public void set_zoom(double zoom_factor) {
        this.zoom = zoom_factor;
    }

    public Vector3D get_direction(Point2D p) {
        Vector3D dir = new Vector3D();
        Vector3D c1 = u.multiplyBy(p.x);
        Vector3D c2 = v.multiplyBy(p.y);
        Vector3D c3 = w.multiplyBy(d);
        dir.setTo((c1.addTo(c2)).subtract(c3));
        dir.normalize();
        return dir;
    }

    @Override
    public void render_scene(World world) {
        System.out.println("Rendering...");
        long startTime = System.currentTimeMillis();
        RGBColor pixelColor = new RGBColor();
        RGBColor accumulator = new RGBColor();
        Ray ray = new Ray();
        int depth = 0;
        Point2D sp = new Point2D();
        Point2D pp = new Point2D();

        world.vp.s /= zoom;
        ray.o = eye;

        for (int r = 0; r < world.vres; r++) {
            for (int c = 0; c < world.hres; c++) {

                accumulator = new RGBColor();

                for (int j = 0; j < world.vp.num_samples; j++) {

                    sp = world.vp.sampler.sample_unit_square();
                    pp.x = world.vp.s * (c - 0.5 * world.hres + sp.x);
                    pp.y = world.vp.s * (r - 0.5 * world.vres + sp.y);
                    ray.d = get_direction(pp);
                    RGBColor tracedPixel = world.tracer.trace_ray(ray, depth);
                    accumulator.add(tracedPixel);
                }
                accumulator.divideBy(world.vp.num_samples);
                pixelColor.setTo(accumulator);

                Color color = new Color((int)(pixelColor.r * 255), (int)(pixelColor.g * 255), (int)(pixelColor.b * 255));
                int colorRGB = color.getRGB();
                World.render.setRGB(r,c,colorRGB);
            }
        }
        System.out.println("Finished. Elapsed time: " + (System.currentTimeMillis() - startTime)/1000.0 + " seconds");
    }
}