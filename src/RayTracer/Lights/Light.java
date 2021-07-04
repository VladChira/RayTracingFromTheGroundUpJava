package RayTracer.Lights;

import RayTracer.ShadeRec;
import RayTracer.Utilities.RGBColor;
import RayTracer.Utilities.Vector3D;

import static RayTracer.Utilities.RGBColor.black;

public class Light {
    public Light() {

    }

    public Vector3D get_direction(ShadeRec sr) {
        return new Vector3D(0, 0, 0);
    }

    public RGBColor L(ShadeRec sr) {
        return (black);
    }
}
