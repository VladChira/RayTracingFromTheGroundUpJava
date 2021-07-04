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
        eye.setTo(c.eye);
        lookat.setTo(c.lookat);
        up.setTo(c.up);

        u.setTo(c.u);
        v.setTo(c.v);
        w.setTo(c.w);
    }

    public BufferedImage render_scene(World world_ptr) {
        return new BufferedImage(world_ptr.hres,world_ptr.vres, BufferedImage.TYPE_INT_RGB);
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