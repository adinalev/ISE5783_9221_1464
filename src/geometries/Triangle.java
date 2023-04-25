/**

 The Triangle class represents a triangle in three-dimensional space.
 It is a Polygon subclass and is defined by its three vertices.
 * @author Michal and Adina (with help of chatGPT)
 */
package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;
import java.util.List;

public class Triangle extends Polygon {
    /**
     * Constructs a Triangle object with the specified vertices.
     * @param p1 vertex of the triangle
     * @param p2 vertex of the triangle
     * @param p3 vertex of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**

     Finds intersection points between the ray and the triangle.
     @param ray the ray to check for intersection with the triangle
     @return a list containing the intersection point(s) between the ray and the triangle, or null if there is no intersection
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        // get the vertices of the triangle
        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        Vector v1 = p1.subtract(p0);
        Vector v2 = p2.subtract(p0);
        Vector v3 = p3.subtract(p0);

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        // check if the ray intersects the triangle using the dot product of the ray's direction vector and the
        // normal vectors of the three edges of the triangle
        if (v.dotProduct(n1) > 0 && v.dotProduct(n2) > 0 && v.dotProduct(n3) > 0) {
            Plane plane = new Plane(p1, p2, p3);
            List<Point> list = plane.findIntersections(ray);
            return list;
        }
        if (v.dotProduct(n1) < 0 && v.dotProduct(n2) < 0 && v.dotProduct(n3) < 0) {
            Plane plane = new Plane(p1, p2, p3);
            List<Point> list = plane.findIntersections(ray);
            return list;
        }
        else
            // if there is no intersection, return null
            return null;
    }
}