package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**

 The Geometries class represents a collection of intersectable objects.

 It implements the Intersectable interface and provides methods to add new geometries to the collection

 and to find the intersection points between a given ray and the collection of geometries.

 @Author Michal and Adina (with the help of chatgpt)
 */
public class Geometries implements Intersectable {
    /**

     A list of intersectable geometries.
     */
    private List<Intersectable> intersectables = null;

    /**

     Constructs an empty collection of geometries.
     */
    public Geometries() {
        intersectables = new LinkedList<>();
    }
    /**

     Constructs a collection of geometries from a variable number of intersectable objects.
     @param geometries A variable number of intersectable objects to add to the collection.
     */
    public Geometries(Intersectable... geometries) {
        this();
        add(geometries);
    }
    /**

     Adds one or more intersectable geometries to the collection.
     @param geometries One or more intersectable geometries to add to the collection.
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(intersectables, geometries);
    }
    /**

     Finds the intersection points between the given ray and the collection of geometries.
     If no intersection points are found, returns null.
     @param ray The ray to intersect with the collection of geometries.
     @return A list of intersection points between the given ray and the collection of geometries, or null if no intersection points are found.
     */
    public List<Point> findIntersections(Ray ray) {
        if (intersectables.isEmpty()) {
            return null;
        }
        List<Point> list = null;
        for(Intersectable inter : intersectables) {
            List<Point> smallList = inter.findIntersections(ray);
            if (smallList != null) {
                if (list == null)
                    list = new LinkedList<>();
                // intersectables.add(inter);
                list.addAll(smallList);
            }
        }
        if(list != null)
            return list;
        else return null;
    }
}