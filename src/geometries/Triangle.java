/**

 The Triangle class represents a triangle in three-dimensional space.
 It is a Polygon subclass and is defined by its three vertices.
 * @author Michal and Adina (with help of chatGPT)
 */
package geometries;
import primitives.Point;

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
}