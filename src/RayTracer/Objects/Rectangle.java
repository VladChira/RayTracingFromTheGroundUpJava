package RayTracer.Objects;

import RayTracer.Samplers.Regular;
import RayTracer.Samplers.Sampler;
import RayTracer.ShadeRec;
import RayTracer.Utilities.*;

public class Rectangle extends GeometricObject {

    public Point3D p0;
    public Vector3D a;
    public Vector3D b;
    public Normal normal;
    public Sampler sampler;
    public double area;
    public double inv_area;
    public double a_len_squared;
    public double b_len_squared;

    public Rectangle(Point3D p0, Vector3D a, Vector3D b, Normal n) {
        this.p0 = p0;
        this.a = a;
        this.b = b;
        this.normal = n;
        normal.normalize();
        area = a.getLength() * b.getLength();
        inv_area = 1.0 / area;
        a_len_squared = a.getLengthSquared();
        b_len_squared = b.getLengthSquared();
        sampler = new Regular(1);
    }

    public Rectangle(Point3D p0, Vector3D a, Vector3D b) {
        this.p0 = p0;
        this.a = a;
        this.b = b;
        normal = new Normal(a.crossProduct(b));
        normal.normalize();
        area = a.getLength() * b.getLength();
        inv_area = 1.0 / area;
        a_len_squared = a.getLengthSquared();
        b_len_squared = b.getLengthSquared();
        sampler = new Regular(1);
    }

    public void set_sampler(Sampler s) {
        this.sampler = s;
    }

    @Override
    public Point3D sample() {
        Point2D sample_point = sampler.sample_unit_square();
        return p0.addTo(a.multiplyBy(sample_point.x).addTo(b.multiplyBy(sample_point.y)));
    }

    @Override
    public HitInformation hit(Ray ray, double tmin, ShadeRec s) {
        double f1 = new Vector3D(normal).dotProduct(p0.subtract(ray.o));
        double f2 = new Vector3D(normal).dotProduct(ray.d);
        double t = f1 / f2;
        if(t <= Constants.kEpsilon) return new HitInformation(false,tmin,s);;

        Point3D p = ray.o.addTo(ray.d.multiplyBy(t));
        Vector3D d = p.subtract(p0);

        double ddota = d.dotProduct(a);
        if(ddota < 0.0 || ddota > a_len_squared) return new HitInformation(false,t,s);

        double ddotb = d.dotProduct(b);
        if(ddotb < 0.0 || ddotb > b_len_squared) return new HitInformation(false,t,s);

        s.normal = normal;
        s.local_hit_point = p;
        return new HitInformation(true, t,s);
    }

    @Override
    public Normal get_normal(Point3D sample_point) {
        return normal;
    }

    @Override
    public double pdf(ShadeRec sr) {
        return inv_area;
    }
}
