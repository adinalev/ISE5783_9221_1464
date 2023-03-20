/**

 The Sphere class represents a sphere in three-dimensional space.
 It extends the RadialGeometry abstract class and implements the Geometry interface.
 The sphere is defined by its radius and center point.
 * @author Michal and Adina (with help of chatGPT)
 */
package geometries;
import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry {
    private Point center;

    /**
     * Constructs a Sphere object with the specified radius and center point.
     *
     * @param radius the radius of the sphere
     * @param center the center point of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return the center point of the sphere
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Returns the normal vector to the sphere at the specified point.
     *
     * @param point a point on the sphere
     * @return the normal vector to the sphere at the specified point
     */
    public Vector getNormal(Point point) {
        return null;
    }

    /**
     * Returns a string representation of a sphere.
     *
     * @return a string representation of a sphere
     */
    @Override
    public String toString() {
        return "Sphere{" +
                super.toString().substring(15,super.toString().lastIndexOf("}")) +
                "center=" + center +
                '}';
    }
}
