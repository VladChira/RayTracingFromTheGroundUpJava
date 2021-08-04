package RayTracer.Cameras;

import RayTracer.SceneManager;
import RayTracer.Utilities.*;
import RayTracer.World;

import java.awt.*;

public class Orthographic extends Camera {

    public Orthographic() {
        super();
    }

    @Override
    public void render_scene(World world) {
        System.out.println("Rendering...");
        long startTime = System.currentTimeMillis();

        RGBColor pixelColor = new RGBColor();
        RGBColor accumulator;
        Ray ray = new Ray();
        double zw = 100.0;
        int depth = 0;
        Point2D pp = new Point2D();
        Point2D sp = new Point2D();

        ray.d = new Vector3D(0, 0, -1);

        for (int r = 0; r < SceneManager.WINDOW_SIZE; r++) {
            if (r == SceneManager.WINDOW_SIZE / 4) System.out.println("25% completed...");
            if (r == SceneManager.WINDOW_SIZE / 2) System.out.println("50% completed...");
            if (r == 3 * SceneManager.WINDOW_SIZE / 4) System.out.println("75% completed...");
            for (int c = 0; c <= SceneManager.WINDOW_SIZE; c++) {
                accumulator = new RGBColor();

                for (int j = 0; j < world.vp.num_samples; j++) {
                    sp = world.vp.sampler.sample_unit_square();

                    pp.x = world.vp.s * (c - 0.5 * SceneManager.WINDOW_SIZE + sp.x);
                    pp.y = world.vp.s * (r - 0.5 * SceneManager.WINDOW_SIZE + sp.y);
                    ray.o = new Point3D(pp.x, pp.y, zw);

                    RGBColor tracedPixel = world.tracer.trace_ray(ray, depth);
                    accumulator.add(tracedPixel);
                }
                accumulator.divideBy(world.vp.num_samples);
                pixelColor.setTo(accumulator);

                if (world.vp.show_out_of_gamut) pixelColor.setTo(RGBColor.clamp_to_color(pixelColor));
                pixelColor.setTo(RGBColor.max_to_one(pixelColor));
                Color color = new Color((int) (pixelColor.r * 255), (int) (pixelColor.g * 255), (int) (pixelColor.b * 255));
                int colorRGB = color.getRGB();
                SceneManager.render.setRGB(c, SceneManager.WINDOW_SIZE - r - 1, colorRGB);
            }
        }
        if (World.wasOutOfGamut) System.out.println("Warning. Out of gamut colors were clamped.");
        System.out.println("Render finished. Elapsed time: " + (System.currentTimeMillis() - startTime) / 1000.0 + " seconds");
        //SceneManager.saveToFile(SceneManager.render);
    }
}
