package RayTracer.Samplers;

import RayTracer.Utilities.Point2D;

/**
 * Multi-Jittered sampling as described by Chiu et al (1994).
 * See 5.3.4
 */
public class MultiJittered extends Sampler {
    public MultiJittered(int samples) {
        super(samples);
        generateSamples();
        setup_shuffled_indices();
    }

    @Override
    public void generateSamples() {
        int n = (int) Math.sqrt(num_samples);
        double subcell_width = 1.0 / num_samples;
        for (int j = 0; j < num_samples * num_sets; j++) {
            samples.add(new Point2D());
        }

        for (int p = 0; p < num_sets; p++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    samples.get(i * n + j + p * num_samples).x = (i * n + j) * subcell_width + (rand.nextDouble() * subcell_width);
                    samples.get(i * n + j + p * num_samples).y = (j * n + i) * subcell_width + (rand.nextDouble() * subcell_width);
                }
            }
        }
        shuffle_x_coordinates();
        shuffle_y_coordinates();
    }
}
