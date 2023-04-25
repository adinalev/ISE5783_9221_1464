package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

/**
 * The Intersectable interface represents an object that can be intersected by a ray.
 * Implementing classes should provide a method for finding the intersection points.
 * @author Michal and Adina (with help of chatGPT)
 */
public interface Intersectable {

    /**
     * Finds the intersection points between the specified ray and this object.
     *
     * @param ray the ray to intersect with this object
     * @return a list of intersection points between the ray and this object,
     *         or an empty list if there are no intersections
     */
    List<Point> findIntersections(Ray ray);
}
