package RayTracer.Utilities;

public class Vector3D {
    public double x, y, z;

    public Vector3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3D(double a) {
        this.x = a;
        this.y = a;
        this.z = a;
    }

    public Vector3D(double _x, double _y, double _z) {
        this.x = _x;
        this.y = _y;
        this.z = _z;
    }

    public Vector3D(Vector3D v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vector3D(Point3D p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public Vector3D(Normal n) {
        this.x = n.x;
        this.y = n.y;
        this.z = n.z;
    }

    public void setTo(Vector3D v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void setTo(Normal n) {
        this.x = n.x;
        this.y = n.y;
        this.z = n.z;
    }

    public void setTo(Point3D p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public double getLength() {
        return (Math.sqrt(((x * x + y * y + z * z))));
    }

    public double getLengthSquared() {
        return (x * x + y * y + z * z);
    }

    public void normalize() {
        double l = this.getLength();
        x /= l;
        y /= l;
        z /= l;
    }

    public Vector3D getNormalized() {
        Vector3D newV = new Vector3D(this);
        double l = newV.getLength();
        newV.x /= l;
        newV.y /= l;
        newV.z /= l;
        return newV;
    }

    public Vector3D getHat() {
        Vector3D v = new Vector3D();
        v.setTo(this);
        double l = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
        v.x /= l;
        v.y /= l;
        v.z /= l;
        return v;
    }

    public Vector3D multiplyBy(double a) {
        return new Vector3D(x * a, y * a, z * a);
    }

    public Vector3D divideBy(double a) {
        return new Vector3D(x / a, y / a, z / a);
    }

    public Vector3D addTo(Vector3D v) {
        return (new Vector3D(x + v.x, y + v.y, z + v.z));
    }

    public Vector3D subtract(Vector3D v) {
        return new Vector3D(x - v.x, y - v.y, z - v.z);
    }

    public double dotProduct(Vector3D v) {
        return (x * v.x + y * v.y + z * v.z);
    }

    public Vector3D crossProduct(Vector3D v) {
        return (new Vector3D(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x));
    }

    public Vector3D negate() {
        return new Vector3D(-this.x, -this.y, -this.z);
    }

    public double distance(Point3D b) {
        return Math.sqrt((this.x - b.x) * (this.x - b.x) + (this.y - b.y) * (this.y - b.y) + (this.z - b.z)*(this.z - b.z));
    }
}
