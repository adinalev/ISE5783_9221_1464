package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;
import primitives.Util;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**

 The Cylinder class represents a 3D cylinder shape defined by its base radius, axis ray, and height.
 It is a subclass of the Tube class.
 @author Michal and Adina (with help of chatGPT)
 */
public class Cylinder extends Tube {

    /*
    this is variable which represents the height of the cylinder
     */
    private double height;

    /**

     Constructs a new Cylinder object with the specified radius, axis ray, and height.
     @param radius The radius of the cylinder base.
     @param axisRay The axis ray of the cylinder.
     @param height The height of the cylinder.
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }
    /**

     Returns the height of this cylinder.
     @return The height of this cylinder.
     */
    public double getHeight() {
        return height;
    }

    @Override
    //normal of cylinder
    public Vector getNormal(Point point) {
        Point p0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        if (point.equals(p0))
            return v;

        // projection of P-p0 on the ray:
        Vector u = point.subtract(p0);

        // distance from p0 to the o who is in from of point
        double t = alignZero(u.dotProduct(v));

        // if the point is at a base
        if (t == 0 || isZero(height - t))
            return v;

        //the other point on the axis facing the given point
        Point o = p0.add(v.scale(t));

        return point.subtract(o).normalize();
    }
}