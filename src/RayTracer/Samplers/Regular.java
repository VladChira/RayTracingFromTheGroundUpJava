package RayTracer.Samplers;

import RayTracer.Utilities.Point2D;

public class Regular extends Sampler {

    public Regular(int samples) {
        super(samples);
        generateSamples();
    }

    @Override
    public void generateSamples() {
        int n = (int) Math.sqrt(num_samples);
        for (int j = 0; j < num_sets; j++)
            for (int p = 0; p < n; p++)
                for (int q = 0; q < n; q++)
                    samples.add(new Point2D((q + 0.5) / n, (p + 0.5) / n));
    }
}
