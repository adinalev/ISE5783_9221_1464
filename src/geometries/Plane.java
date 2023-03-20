/**

 Represents a plane in 3D space.
 A plane is defined by a point on the plane and a normal vector that is perpendicular to the plane.
 * @author Michal and Adina (with help of chatGPT)
 */
package geometries;
import primitives.Vector;
import primitives.Point;

public class Plane {
    private Point q0; // point on the plane
    private Vector normal; // normal vector to the plane

    /**
     * Constructs a plane from three points on the plane.
     * @param p1 First point on the plane.
     * @param p2 Second point on the plane.
     * @param p3 Third point on the plane.
     */
    public Plane(Point p1, Point p2, Point p3) {
        normal = null; // will implement in a future stage
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
        this.normal = normal;
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
        return normal;
    }

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
}
