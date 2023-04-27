/**

 Represents a plane in 3D space.
 A plane is defined by a point on the plane and a normal vector that is perpendicular to the plane.
 * @author Michal and Adina (with help of chatGPT)
 */
package geometries;
import primitives.Vector;
import primitives.Point;
import primitives.Ray;
import java.util.List;
import static primitives.Util.*;

public class Plane implements Intersectable{
    /*
    * point on the plane
     */
    final protected Point q0;
    /*
    * normal vector to the plane
     */
    final Vector normal;

    /**
     * Constructs a plane from three points on the plane.
     * @param p1 First point on the plane.
     * @param p2 Second point on the plane.
     * @param p3 Third point on the plane.
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        Vector U = p1.subtract(p2);     // AB
        Vector V = p1.subtract(p3);     // AC
        Vector N = U.crossProduct(V);   // AB X AC
        //right hand rule
        this.normal = N.normalize();
    }

    /**
     * Constructs a plane from a point on the plane and a normal vector to the plane.
     * The normal vector is automatically normalized.
     * @param q0 Point on the plane.
     * @param normal Normal vector to the plane.
     */
    public Plane(Point q0, Vector normal) {
        normal.normalize();
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * Returns the point on the plane.
     * @return The point on the plane.
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * Returns the normal vector to the plane.
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return this.normal;
    }

    public Vector getNormal(Point point){ return this.normal; }

    /**
    * Returns a string representation of the plane in the format:
    * "Plane{q0=point, normal=vector}".
    * @return a string representation of the plane
     */
    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    /**

     Computes the intersection point(s) between a given ray and the plane.

     @param ray the ray to intersect with the plane

     @return a list containing the intersection point(s), or {@code null} if there are no intersection points
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        Vector n = normal;

        if(q0.equals(P0)) {
            return null;
        }
        Vector P0_Q0 = q0.subtract(P0);

        // numerator
        double nP0Q0 = alignZero(n.dotProduct(P0_Q0));

        if(isZero(nP0Q0)){
            return null;
        }

        //denominator
        double nv = alignZero(n.dotProduct(v));

        // if ray is lying in the plane axis
        if(isZero(nv)){
            return null;
        }

        double t = alignZero(nP0Q0 / nv);

        if (t <= 0){
            return null;
        }

        Point point = ray.getPoint(t);
        return List.of(point);

    }
}

