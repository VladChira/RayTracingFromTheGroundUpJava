package RayTracer.Cameras;

import RayTracer.Samplers.*;
import RayTracer.SceneManager;
import RayTracer.Utilities.*;
import RayTracer.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ThinLens extends Camera {
    public double lens_radius;
    public double d;
    public double f;
    public double zoom = 1;
    public Sampler sampler;

    public ThinLens() {
        super();
    }

    public void set_samples(int samples) {
        sampler = new MultiJittered(samples);
        sampler.map_samples_to_unit_disk();
    }

    public void set_zoom(double zoom_factor) {
        this.zoom = zoom_factor;
    }

    public void set_focal_distance(double f) {
        this.f = f;
    }

    public void set_view_distance(double vpd) {
        this.d = vpd;
    }

    public void set_lens_radius(double lens_radius) {
        this.lens_radius = lens_radius;
    }

    public Vector3D ray_direction(Point2D pixel_point, Point2D lens_point) {
        Point2D p = new Point2D();
        p.x = pixel_point.x * f / d;
        p.y = pixel_point.y * f / d;

        Vector3D t1 = u.multiplyBy(p.x - lens_point.x);
        Vector3D t2 = v.multiplyBy(p.y - lens_point.y);
        Vector3D t3 = w.multiplyBy(f);
        Vector3D dir = t1.addTo(t2).subtract(t3);
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
        Point2D dp = new Point2D();
        Point2D lp = new Point2D();

        world.vp.s /= zoom;

        for (int r = 0; r < SceneManager.WINDOW_SIZE; r++) {
            if (r == SceneManager.WINDOW_SIZE / 4) System.out.println("25% completed...");
            if (r == SceneManager.WINDOW_SIZE / 2) System.out.println("50% completed...");
            if (r == 3 * SceneManager.WINDOW_SIZE / 4) System.out.println("75% completed...");

            for (int c = 0; c < SceneManager.WINDOW_SIZE; c++) {
                accumulator = new RGBColor();

                for (int j = 0; j < world.vp.num_samples; j++) {
                    sp = world.vp.sampler.sample_unit_square();
                    pp.x = world.vp.s * (c - 0.5 * SceneManager.WINDOW_SIZE + sp.x);
                    pp.y = world.vp.s * (r - 0.5 * SceneManager.WINDOW_SIZE + sp.y);

                    dp = sampler.sample_unit_disk();

                    lp.setTo(dp.multiplyBy(lens_radius));

                    Vector3D t1 = u.multiplyBy(lp.x).addTo(new Vector3D(eye.x, eye.y, eye.z));
                    Vector3D t2 = v.multiplyBy(lp.y);
                    ray.o = new Point3D(t1.addTo(t2).x, t1.addTo(t2).y, t1.addTo(t2).z);
                    ray.d = ray_direction(pp, lp);

                    RGBColor tracedPixel = world.tracer.trace_ray(ray, depth);
                    accumulator.add(tracedPixel);
                }
                accumulator.divideBy(world.vp.num_samples);
                pixelColor.setTo(accumulator);

                if(world.vp.show_out_of_gamut) pixelColor.setTo(RGBColor.clamp_to_color(pixelColor));
                pixelColor.setTo(RGBColor.max_to_one(pixelColor));
                Color color = new Color((int)(pixelColor.r * 255), (int)(pixelColor.g * 255), (int)(pixelColor.b * 255));
                int colorRGB = color.getRGB();
                SceneManager.render.setRGB(c, SceneManager.WINDOW_SIZE - r - 1, colorRGB);
            }
        }
        System.out.println("Finished. Elapsed time: " + (System.currentTimeMillis() - startTime)/1000.0 + " seconds");
        //saveToFile(World.render);
    }

    public void saveToFile(BufferedImage image) {
        File f = new File("output.png");
        try {
            ImageIO.write(image, "PNG", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved to file.");
    }
}
