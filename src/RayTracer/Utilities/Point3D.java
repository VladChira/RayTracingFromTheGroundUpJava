package RayTracer.Utilities;

public class Point3D {
    public double x, y, z;

    public Point3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Point3D(double a) {
        this.x = a;
        this.y = a;
        this.z = a;
    }

    public Point3D(double _x, double _y, double _z) {
        this.x = _x;
        this.y = _y;
        this.z = _z;
    }

    public Point3D(Point3D p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public boolean equalsTo(Point3D p) {
        return (this.x == p.x && this.y == p.y && this.z == p.z);
    }

    public void setTo(Point3D p) {
        if (this.equalsTo(p)) return;
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public double distance(Point3D p) {
        double sqDist = (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y) + (z - p.z) * (z - p.z);
        return Math.sqrt((float) sqDist);
    }

    public Vector3D subtract(Point3D p) {
        return new Vector3D(x - p.x, y - p.y, z - p.z);
    }

    public Point3D subtract(Vector3D v) {
        return new Point3D(x - v.x, y - v.y, z - v.z);
    }

    public Point3D addTo(Vector3D v) {
        return (new Point3D(x + v.x, y + v.y, z + v.z));
    }

    public double d_squared(Point3D p) {
        return ((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y) + (z - p.z) * (z - p.z));
    }
}