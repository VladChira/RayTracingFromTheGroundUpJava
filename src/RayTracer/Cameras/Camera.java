package RayTracer.Cameras;

import RayTracer.Utilities.Point3D;
import RayTracer.Utilities.Vector3D;
import RayTracer.World;
import javafx.scene.image.PixelWriter;

import java.awt.image.BufferedImage;

/**
 * Base class for all virtual cameras.
 */

public class Camera {

    public Point3D eye;
    public Point3D lookat;
    public double d;
    Vector3D u, v, w;
    Vector3D up;

    public Camera() {
        eye = new Point3D(0, 0, 500);
        lookat = new Point3D(0);
        up = new Vector3D(0, 1, 0);

        u = new Vector3D(1, 0, 0);
        v = new Vector3D(0, 1, 0);
        w = new Vector3D(0, 0, 1);
    }

    public Camera(Camera c) {
        eye = c.eye;
        lookat = c.lookat;
        up = c.up;

        u = c.u;
        v = c.v;
        w = c.w;

        d = c.d;
    }

    public void set_view_distance(double d) {
        this.d = d;
    }

    public void set_zoom(double zoom) {

    }

    public void render_scene(World world_ptr) {

    }

    public void set_eye(Point3D p) {
        eye.setTo(p);
    }

    public void set_eye(float x, float y, float z) {
        eye.setTo(new Point3D(x, y, z));
    }

    public void set_lookat(Point3D p) {
        lookat.setTo(p);
    }

    public void set_lookat(float x, float y, float z) {
        lookat.setTo(new Point3D(x, y, z));
    }

    public void set_up_vector(Vector3D up) {
        this.up.setTo(up);
    }

    public void set_up_vector(float x, float y, float z) {
        this.up.setTo(new Vector3D(x, y, z));
    }

    public void compute_uvw() {
        w.setTo(eye.subtract(lookat));
        w.normalize();
        u.setTo(up.crossProduct(w));
        u.normalize();
        v.setTo(w.crossProduct(u));

        if (eye.x == lookat.x && eye.z == lookat.z && eye.y > lookat.y) { // camera looking vertically down
            u = new Vector3D(0, 0, 1);
            v = new Vector3D(1, 0, 0);
            w = new Vector3D(0, 1, 0);
        }

        if (eye.x == lookat.x && eye.z == lookat.z && eye.y < lookat.y) { // camera looking vertically up
            u = new Vector3D(1, 0, 0);
            v = new Vector3D(0, 0, 1);
            w = new Vector3D(0, -1, 0);
        }
    }
}