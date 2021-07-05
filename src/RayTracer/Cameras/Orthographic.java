package RayTracer.Cameras;

import RayTracer.Utilities.*;
import RayTracer.World;
import javafx.scene.image.PixelWriter;

import java.awt.*;
import java.awt.image.BufferedImage;

@Deprecated
public class Orthographic extends Camera {

    public Orthographic() {
        super();
    }

    @Override
    public void render_scene(World world) {
        RGBColor pixelColor = new RGBColor();
        RGBColor accumulator;
        Ray ray = new Ray();
        double zw = 100.0;
        int depth = 0;
        Point2D pp = new Point2D();
        Point2D sp = new Point2D();

        ray.d = new Vector3D(0, 0, -1);

        for (int r = 0; r < world.vres; r++) {
            for (int c = 0; c <= world.hres; c++) {
                accumulator = new RGBColor();

                for (int j = 0; j < world.vp.num_samples; j++) {
                    sp = world.vp.sampler.sample_unit_square();

                    pp.x = world.vp.s * (c - 0.5 * world.hres + sp.x);
                    pp.y = world.vp.s * (r - 0.5 * world.vres + sp.y);
                    ray.o = new Point3D(pp.x, pp.y, zw);

                    RGBColor tracedPixel = world.tracer.trace_ray(ray, depth);
                    accumulator.add(tracedPixel);
                }
                accumulator.divideBy(world.vp.num_samples);
                pixelColor.setTo(accumulator);

                Color color = new Color((int)pixelColor.r * 255, (int)pixelColor.g * 255, (int)pixelColor.b * 255, 255);
                int colorRGB = color.getRGB();
                World.render.setRGB(r,c,colorRGB);
            }
        }
    }
}
