package RayTracer.Objects;

import RayTracer.ShadeRec;
import RayTracer.Utilities.*;

public class Disk extends GeometricObject {

    Point3D center;
    Normal normal;
    double radius;

    @Override
    public HitInformation hit(Ray ray, double tmin, ShadeRec s) {
        double a = (center.subtract(ray.o)).dotProduct(new Vector3D(normal));
        double b = ray.d.dotProduct(new Vector3D(normal));
        double t_ = a / b;
        if (t_ <= Constants.kEpsilon) return new HitInformation(false, t_, s);
        Point3D p = ray.o.addTo(ray.d.multiplyBy(t_));
        if (center.d_squared(p) < radius * radius) {
            s.normal = normal;
            s.local_hit_point = p;
            return new HitInformation(true, t_, s);
        }
        return new HitInformation(false, t_, s);

    }

    @Override
    public Normal get_normal(Point3D sample_point) {
        return normal;
    }
}
