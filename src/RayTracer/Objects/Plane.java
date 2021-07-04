package RayTracer.Objects;

import RayTracer.Utilities.HitInformation;
import RayTracer.Utilities.Normal;
import RayTracer.ShadeRec;
import RayTracer.Utilities.Constants;
import RayTracer.Utilities.Point3D;
import RayTracer.Utilities.Ray;
import RayTracer.Utilities.Vector3D;

//TODO Bug: Plane intersecting other objects creates artifacts

public class Plane extends GeometricObject {
    public Point3D a; //point through which plane passes
    Normal n; //normal to the plane;

    public Plane() {
        a = new Point3D(0.0);
        n = new Normal(0, 1, 0);
    }

    public Plane(Point3D point, Normal n) {
        a = new Point3D();
        this.n = new Normal();
        a.setTo(point);
        this.n.setTo(n);

        this.n.normalize();
    }

    @Override
    public HitInformation hit(Ray ray, double tmin, ShadeRec sr) {
        ShadeRec newSr = new ShadeRec(sr);
        Vector3D component1 = a.subtract(ray.o);
        Vector3D vectorFromNormal = new Vector3D(n);
        double numerator = component1.dotProduct(vectorFromNormal);

        double denominator = ray.d.dotProduct(vectorFromNormal);

        float t = (float) (numerator / denominator);

        if (t > Constants.kEpsilon) {
            tmin = t;
            newSr.normal = n;
            newSr.local_hit_point = ray.o.addTo(ray.d.multiplyBy(t));

            return new HitInformation(true, tmin, newSr);
        }

        return new HitInformation(false, tmin, newSr);
    }
}
