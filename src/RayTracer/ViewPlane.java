package RayTracer;

import RayTracer.Samplers.MultiJittered;
import RayTracer.Samplers.Regular;
import RayTracer.Samplers.Sampler;

public class ViewPlane {
    public double s;
    public int num_samples;
    public Sampler sampler;

    public double gamma;
    public double inv_gamma;
    boolean show_out_of_gamut;

    public ViewPlane() {
        s = 1.0;
        num_samples = 1;
        gamma = 1.0;
        inv_gamma = 1.0;
        show_out_of_gamut = false;
    }

    public ViewPlane(ViewPlane vp) {
        s = vp.s;
        num_samples = vp.num_samples;
        gamma = vp.gamma;
        inv_gamma = vp.inv_gamma;
        show_out_of_gamut = vp.show_out_of_gamut;
    }

    public void setPixelSize(double size) {
        s = size;
    }

    public void setGamma(double g) {
        gamma = g;
    }

    public void setGamutDisplay(boolean show) {
        show_out_of_gamut = show;
    }

    public void setSamples(int n) {
        if (!isPerfectSquare(n)) {
            System.out.println("Fatal error. Number of AA samples must be a perfect square");
            System.exit(-1);
        }
        num_samples = n;
        if (num_samples > 1) {
            sampler = new MultiJittered(num_samples);
        }
        else sampler = new Regular(num_samples);
    }

    private boolean isPerfectSquare(double x) {
        if (x >= 0) {
            int sr = (int) Math.sqrt(x);
            return ((sr * sr) == x);
        }
        return false;
    }

}
