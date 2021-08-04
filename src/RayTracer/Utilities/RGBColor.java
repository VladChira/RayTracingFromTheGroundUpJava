package RayTracer.Utilities;

import RayTracer.World;

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

    public RGBColor divide(double a) {
        RGBColor c = new RGBColor(this);
        c.divideBy(a);
        return c;
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

    public static RGBColor clamp_to_color(RGBColor raw_color) {
        RGBColor c = new RGBColor();
        c.setTo(raw_color);

        if (raw_color.r > 1.0 || raw_color.g > 1.0 || raw_color.b > 1.0) {
            c.r = 1.0;
            c.g = 0;
            c.b = 0;
        }
        return (c);
    }

    public static RGBColor max_to_one(RGBColor c) {
        if (c.r > 1.0) {
            c.r = 1.0;
            World.wasOutOfGamut = true;
        }
        if (c.g > 1.0) {
            c.g = 1.0;
            World.wasOutOfGamut = true;
        }
        if (c.b > 1.0) {
            c.b = 1.0;
            World.wasOutOfGamut = true;
        }

        return c;
    }

    public static final RGBColor white = new RGBColor(1, 1, 1);
    public static final RGBColor red = new RGBColor(1, 0, 0);
    public static final RGBColor green = new RGBColor(0, 1, 0);
    public static final RGBColor blue = new RGBColor(0, 0, 1);
    public static final RGBColor yellow = new RGBColor(1, 1, 0);
    public static final RGBColor magenta = new RGBColor(1, 0, 1);
    public static final RGBColor cyan = new RGBColor(0, 1, 1);
    public static final RGBColor grey = new RGBColor(0.5, 0.5, 0.5);
    public static final RGBColor brown = new RGBColor(0.29, 0.21, 0.109);
    public static final RGBColor darkGreen = new RGBColor(0.0, 0.392, 0.0);
    public static final RGBColor orange = new RGBColor(0.89, 0.40, 0.09);
    public static final RGBColor lightGreen = new RGBColor(0.564, 0.933, 0.564);
    public static final RGBColor darkYellow = new RGBColor(0.607, 0.529, 0.047);
    public static final RGBColor lightPurple = new RGBColor(0.694, 0.611, 0.850);
    public static final RGBColor darkPurple = new RGBColor(0.188, 0.098, 0.203);
}