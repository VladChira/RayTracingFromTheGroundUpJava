package RayTracer.Utilities;

public class Point2D {
    public double x, y;

    public Point2D() {
        x = 0;
        y = 0;
    }

    public Point2D(double a) {
        this.x = a;
        this.y = a;
    }

    public Point2D(double x1, double y1) {
        this.x = x1;
        this.y = y1;
    }

    public Point2D(Point2D p) {
        this.x = p.x;
        this.y = p.y;
    }

    public void setTo(Point2D p) {
        if (this.equalsTo(p)) return;
        this.x = p.x;
        this.y = p.y;
    }

    public Point2D multiplyBy(double a) {
        this.x *= a;
        this.y *= a;
        return this;
    }

    public boolean equalsTo(Point2D p) {
        return (this.x == p.x && this.y == p.y);
    }
}
