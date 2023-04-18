/**

 Represents a plane in 3D space.
 A plane is defined by a point on the plane and a normal vector that is perpendicular to the plane.
 * @author Michal and Adina (with help of chatGPT)
 */
package geometries;
import primitives.Vector;
import primitives.Point;
import java.util.List;

public class Plane {
    final protected Point q0; // point on the plane
    final Vector normal; // normal vector to the plane

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

    // **need to figure out what it says
    //public List<Double3> findIntersections(Ray ray) {

    //}
}
