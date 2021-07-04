package RayTracer.Utilities;

public class RGBColor {
    public static final RGBColor black = new RGBColor(0, 0, 0);
    public double r, g, b;

    public RGBColor() {
        r = 0;
        g = 0;
        b = 0;
    }

    public RGBColor(double c) {
        r = c;
        g = c;
        b = c;
    }

    public RGBColor(double _r, double _g, double _b) {
        this.r = _r;
        this.g = _g;
        this.b = _b;
    }

    public RGBColor(RGBColor c) {
        this.r = c.r;
        this.g = c.g;
        this.b = c.b;
    }

    public void divideBy(double a) {
        this.r = this.r / a;
        this.g = this.g / a;
        this.b = this.b / a;
    }

    public RGBColor multiplyBy(double a) {
        return (new RGBColor(r * a, g * a, b * a));
    }

    public RGBColor multiplyBy(RGBColor c) {
        return (new RGBColor(r * c.r, g * c.g, b * c.b));
    }

    public boolean isEqualTo(RGBColor c) {
        return (this.r == c.r && this.g == c.g && this.b == c.b);
    }

    public RGBColor add(RGBColor c) {
        this.r += c.r;
        this.g += c.g;
        this.b += c.b;
        return this;
    }

    public void setTo(RGBColor c) {
        this.r = c.r;
        this.g = c.g;
        this.b = c.b;
    }

    public RGBColor addTo(RGBColor c) {
        return (new RGBColor(r + c.r, g + c.g, b + c.b));
    }


    public static final RGBColor white = new RGBColor(1, 1, 1);
    public static final RGBColor red = new RGBColor(1, 0, 0);
    public static final RGBColor green = new RGBColor(0, 1, 0);
    public static final RGBColor blue = new RGBColor(0, 0, 1);
    public static final RGBColor yellow = new RGBColor(1, 1, 0);
    public static final RGBColor magenta = new RGBColor(1, 0, 1);
    public static final RGBColor cyan = new RGBColor(0, 1, 1);
    public static RGBColor grey = new RGBColor(0.5, 0.5, 0.5);
    public static RGBColor brown = new RGBColor(0.647, 0.164, 0.164);
    public static RGBColor darkGreen = new RGBColor(0.0, 0.392, 0.0);
    public static RGBColor orange = new RGBColor(1, 0.27, 0);
    public static RGBColor lightGreen = new RGBColor(0.564, 0.933, 0.564);
    public static RGBColor darkYellow = new RGBColor(0.607, 0.529, 0.047);
    public static RGBColor lightPurple = new RGBColor(0.694, 0.611, 0.850);
    public static RGBColor darkPurple = new RGBColor(0.188, 0.098, 0.203);
}