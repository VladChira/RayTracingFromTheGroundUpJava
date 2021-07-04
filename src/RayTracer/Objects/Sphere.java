package RayTracer.Objects;

import RayTracer.Utilities.HitInformation;
import RayTracer.Utilities.Normal;
import RayTracer.ShadeRec;
import RayTracer.Utilities.Constants;
import RayTracer.Utilities.Point3D;
import RayTracer.Utilities.Ray;
import RayTracer.Utilities.Vector3D;

public class Sphere extends GeometricObject {
    public Point3D center;
    public double radius;


    public Sphere() {
        center = new Point3D();
        center.setTo(new Point3D(0.0));
        radius = 1.0;
    }

    public Sphere(Point3D center, double r) {
        this.center = new Point3D();
        this.center.setTo(center);
        this.radius = r;
    }

    public Sphere(Sphere sphere) {
        this.center.setTo(sphere.center);
        radius = sphere.radius;
    }

    public void setCenter(Point3D c) {
        center.setTo(c);
    }

    public Sphere clone() {
        return new Sphere(this);
    }

    public void setCenter(double x, double y, double z) {
        center.x = x;
        center.y = y;
        center.z = z;
    }

    public void setRadius(double r) {
        radius = r;
    }

    @Override
    public HitInformation hit(Ray ray, double tmin, ShadeRec sr) {
        ShadeRec newSr = new ShadeRec(sr);
        double t;
        Vector3D temp = ray.o.subtract(center);
        double a = ray.d.dotProduct(ray.d);
        double b = temp.multiplyBy(2.0).dotProduct(ray.d);
        double c = temp.dotProduct(temp) - radius * radius;
        double disc = b * b - 4.0 * a * c;

        if (disc < 0.0)
            return new HitInformation(false, tmin, newSr);
        else {
            double e = Math.sqrt((float) disc);
            double denom = 2.0 * a;
            t = (-b - e) / denom;    // smaller root

            if (t > Constants.kEpsilon) {
                tmin = t;
                Vector3D t1 = ray.d.multiplyBy(t);
                Vector3D t2 = t1.addTo(temp);
                newSr.normal = new Normal(t2.divideBy(radius));
                return new HitInformation(true, tmin, newSr);
            }

            t = (-b + e) / denom;    // larger root

            if (t > Constants.kEpsilon) {
                tmin = t;
                Vector3D t1 = ray.d.multiplyBy(t);
                Vector3D t2 = t1.addTo(temp);
                newSr.normal = new Normal(t2.divideBy(radius));
                return new HitInformation(true, tmin, newSr);
            }
        }

        return new HitInformation(false, tmin, newSr);
    }
}
