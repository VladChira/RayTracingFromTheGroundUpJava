package RayTracer.Utilities;

public class Ray {
    public Point3D o; //origin
    public Vector3D d; //direction

    public Ray() {
        o = new Point3D(0.0);
        d = new Vector3D(0.0, 0.0, 1.0);
    }

    public Ray(Point3D origin, Vector3D dir) {
        this.o = new Point3D();
        this.d = new Vector3D();
        this.o.setTo(origin);
        this.d.setTo(dir);
    }

    public Ray(Ray ray) {
        this.o.setTo(ray.o);
        this.d.setTo(ray.d);
    }

    public void setTo(Ray ray) {
        this.o.setTo(ray.o);
        this.d.setTo(ray.d);
    }
}