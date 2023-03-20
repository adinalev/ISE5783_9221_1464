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
     * @param vertices the vertices of the triangle
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }
}