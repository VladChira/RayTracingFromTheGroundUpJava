package RayTracer.Utilities;

public class Normal {
    public double x, y, z;


    public Normal() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Normal(double a) {
        this.x = a;
        this.y = a;
        this.z = a;
    }

    public Normal(double _x, double _y, double _z) {
        this.x = _x;
        this.y = _y;
        this.z = _z;
    }

    public Normal(Normal n) {
        this.x = n.x;
        this.y = n.y;
        this.z = n.z;
    }

    public Normal(Vector3D v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void setTo(Normal n) {
        this.x = n.x;
        this.y = n.y;
        this.z = n.z;
    }

    public void setTo(Vector3D v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void setTo(Point3D p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public double getLength() {
        return (Math.sqrt((float) (x * x + y * y + z * z)));
    }

    public Normal normalize() {
        double l = this.getLength();
        this.x /= l;
        this.y /= l;
        this.z /= l;
        return this;
    }

    public Normal multiplyBy(double a) {
        return (new Normal(x * a, y * a, z * a));
    }

    public Normal addTo(Normal n) {
        return new Normal(this.x + n.x, this.y + n.y, this.z + n.z);
    }

    public Normal subtract(Normal n) {
        return new Normal(this.x - n.x, this.y - n.y, this.z - n.z);
    }
}