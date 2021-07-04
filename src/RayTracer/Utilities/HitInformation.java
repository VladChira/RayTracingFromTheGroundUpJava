package RayTracer.Utilities;

import RayTracer.ShadeRec;

public class HitInformation {
    public double t;
    public boolean hit;
    public ShadeRec sr;

    public HitInformation(boolean hit, double t, ShadeRec sr) {
        this.hit = hit;
        this.t = t;
        this.sr = sr;
    }
}