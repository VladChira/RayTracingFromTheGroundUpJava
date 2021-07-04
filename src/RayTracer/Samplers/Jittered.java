package RayTracer.Samplers;

import RayTracer.Utilities.Point2D;

@Deprecated
public class Jittered extends Sampler {

    public Jittered(int samples, int sets) {
        super(samples, sets);
        generateSamples();
    }

    @Override
    public void generateSamples() {
        int n = (int) Math.sqrt(num_samples);
        for (int p = 0; p < num_sets; p++) {

            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    Point2D sp = new Point2D((k + rand.nextDouble()) / n, (j + rand.nextDouble()) / n);
                    samples.add(sp);
                }
            }
        }
    }
}
