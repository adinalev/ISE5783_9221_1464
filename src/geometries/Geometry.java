package geometries;
import primitives.Vector;
import primitives.Point;

/**

 The Geometry interface represents a geometric shape in 3D space.
 * @author Michal and Adina (with help of chatGPT)
 */
public interface Geometry {

    /**

     Returns the normal vector to this geometry shape at the specified point.
     @param point The point on this geometry shape at which to calculate the normal vector.
     @return A vector representing the normal to this geometry shape at the specified point.
     */
    public Vector getNormal(Point point);
}