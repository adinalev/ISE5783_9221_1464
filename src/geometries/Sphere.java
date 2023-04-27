/**

 The Sphere class represents a sphere in three-dimensional space.
 It extends the RadialGeometry abstract class and implements the Geometry interface.
 The sphere is defined by its radius and center point.
 * @author Michal and Adina (with help of chatGPT)
 */
package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

public class Sphere extends RadialGeometry {
    /*
     * this is the center of the sphere
     */
    final Point center;
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
        return point.subtract(center).normalize();
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
    /**

     Returns a list of intersection points between a given ray and the sphere.

     @param ray the ray to find intersections with

     @return a list of intersection points between the ray and the sphere. Returns null if there are no intersections.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        if (P0.equals(center)) {
            return List.of(center.add(v.scale(radius)));
        }

        Vector U = center.subtract(P0);

        double tm = alignZero(v.dotProduct(U));
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));

        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
            Point P1 = ray.getPoint(t1);
            Point P2 = ray.getPoint(t2);
            return List.of(P1,P2);
        }
        if (t1 > 0) {
            Point P1 = ray.getPoint(t1);
            return List.of(P1);
        }
        if (t2 > 0) {
            Point P2 = ray.getPoint(t2);
            return List.of(P2);
        }
        return null;
    }
}
