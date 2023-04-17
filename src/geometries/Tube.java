/**

 The Tube class represents an infinite tube in three-dimensional space.
 It extends the RadialGeometry abstract class and implements the Geometry interface.
 The tube is defined by its radius and axis Ray.
 * @author Michal and Adina (with help of chatGPT)
 */
package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;
import primitives.Util;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Tube extends RadialGeometry {
    protected Ray axisRay;

    /**
     * Constructs a Tube object with the specified radius and axis Ray.
     *
     * @param radius  the radius of the tube
     * @param axisRay the axis Ray of the tube
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * Returns the axis Ray of the tube.
     *
     * @return the axis Ray of the tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Returns the normal vector to the tube at the specified point.
     *
     * @param point a point on the tube
     * @return the normal vector to the tube at the specified point
     */
    public Vector getNormal(Point point) {
        //!!
        Point P0 = axisRay.getP0();
        Vector v = axisRay.getDir();
        Vector P0_P = point.subtract(P0);
        double t = alignZero(v.dotProduct(P0_P));
        if(isZero(t)) {
            return P0_P.normalize();
        }
        Point o = P0.add(v.scale(t));

        if(point.equals(o)) {
            throw new IllegalArgumentException("point cannot be on the tube axis");
        }

        Vector n = point.subtract(o).normalize();
        return n;
    }

    /**
     * Returns a string representation of the Tube object.
     *
     * @return a string representation of the Tube object
     */
    @Override
    public String toString() {
        return "Tube{" +
                super.toString().substring(15,super.toString().lastIndexOf("}")) +
                "axisRay=" + axisRay +
                '}';
    }
}