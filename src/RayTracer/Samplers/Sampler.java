package RayTracer.Samplers;

import RayTracer.Utilities.Point2D;
import RayTracer.Utilities.Point3D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Sampler {
    public int num_samples;
    public int num_sets;
    public int jump;
    public int count;
    protected Random rand;

    public ArrayList<Point2D> samples;
    public ArrayList<Integer> shuffled_indices;
    public ArrayList<Point2D> disk_samples;
    public ArrayList<Point3D> hemisphere_samples;

    public Sampler(int samples) {
        this.num_samples = samples;
        this.num_sets = 83; //nr of sets
        count = 0;
        jump = 0;
        this.samples = new ArrayList<>();
        this.shuffled_indices = new ArrayList<>();
        this.disk_samples = new ArrayList<>();
        this.hemisphere_samples = new ArrayList<>();
        rand = new Random();
    }

    public Sampler(int samples, int sets) {
        this.num_samples = samples;
        this.num_sets = sets;
        count = 0;
        jump = 0;
        this.samples = new ArrayList<>();
        this.shuffled_indices = new ArrayList<>();
        setup_shuffled_indices();
        this.disk_samples = new ArrayList<>();
        this.hemisphere_samples = new ArrayList<>();
        rand = new Random();
    }

    public Point2D sample_unit_square() {
        if (count % num_samples == 0) {
            int randInteger = rand.nextInt() & Integer.MAX_VALUE;
            jump = (randInteger % num_sets) * num_samples;
        }
        return (samples.get((jump + shuffled_indices.get((count++) % num_samples))));
        //return samples.get(jump + (count++) % num_samples);
    }

    public Point2D sample_unit_disk() {
        if (count % num_samples == 0) {
            int randInteger = rand.nextInt() & Integer.MAX_VALUE;
            jump = (randInteger % num_sets) * num_samples;
        }
        //return (disk_samples.get((jump + shuffled_indices.get((count++) % num_samples))));
        return disk_samples.get(jump + (count++) % num_samples);
    }

    public Point3D sample_hemisphere() {
        if (count % num_samples == 0) {
            int randInteger = rand.nextInt() & Integer.MAX_VALUE;
            jump = (randInteger % num_sets) * num_samples;
        }
        return (hemisphere_samples.get((jump + shuffled_indices.get((count++) % num_samples))));
        //return hemisphere_samples.get(jump + (count++) % num_samples);
    }

    public void map_samples_to_unit_disk() {
        int size = samples.size();
        double r, phi;
        Point2D sp = new Point2D();
        for (int i = 0; i < size; i++) disk_samples.add(new Point2D());
        for (int j = 0; j < size; j++) {
            sp.x = 2.0 * samples.get(j).x - 1.0;
            sp.y = 2.0 * samples.get(j).y - 1.0;

            if (sp.x > -sp.y) {
                if (sp.x > sp.y) {
                    r = sp.x;
                    phi = sp.y / sp.x;
                } else {
                    r = sp.y;
                    phi = 2.0 - sp.x / sp.y;
                }
            } else {
                if (sp.x < sp.y) {
                    r = -sp.x;
                    phi = 4.0 + sp.y / sp.x;
                } else {
                    r = -sp.y;
                    if (sp.y != 0.0) {
                        phi = 6.0 - sp.x / sp.y;
                    } else {
                        phi = 0.0;
                    }
                }
            }
            phi *= Math.PI / 4.0;

            disk_samples.get(j).x = r * Math.cos(phi);
            disk_samples.get(j).y = r * Math.sin(phi);
        }
    }

    public void map_samples_to_hemisphere(final double e) {
        for (Point2D sample : samples) {
            double cos_phi = Math.cos(2.0 * Math.PI * sample.x);
            double sin_phi = Math.sin(2.0 * Math.PI * sample.x);
            double cos_theta = Math.pow(sample.y, 1.0 / (e + 1.0));
            double sin_theta = Math.sqrt(1.0 - cos_theta * cos_theta);
            double pu = sin_theta * cos_phi;
            double pv = sin_theta * sin_phi;
            double pw = cos_theta;
            hemisphere_samples.add(new Point3D(pu, pv, pw));
        }
    }


    public void generateSamples() {

    }

    public void shuffle_x_coordinates() {
        for (int p = 0; p < num_sets; p++)
            for (int i = 0; i < num_samples - 1; i++) {
                int target = (rand.nextInt() & Integer.MAX_VALUE) % num_samples + p * num_samples;
                double temp = samples.get(i + p * num_samples + 1).x;
                samples.get(i + p * num_samples + 1).x = samples.get(target).x;
                samples.get(target).x = temp;
            }
    }

    public void shuffle_y_coordinates() {
        for (int p = 0; p < num_sets; p++)
            for (int i = 0; i < num_samples - 1; i++) {
                int target = (rand.nextInt() & Integer.MAX_VALUE) % num_samples + p * num_samples;
                double temp = samples.get(i + p * num_samples + 1).y;
                samples.get(i + p * num_samples + 1).y = samples.get(target).y;
                samples.get(target).y = temp;
            }
    }

    public void setup_shuffled_indices() {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int j = 0; j < num_samples; j++) indices.add(j);
        for (int p = 0; p < num_sets; p++) {
            Collections.shuffle(indices);
            for (int j = 0; j < num_samples; j++)
                shuffled_indices.add(indices.get(j));
        }
    }
}
