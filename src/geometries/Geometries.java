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
public class Geometries extends Intersectable {
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

     Finds the intersections between the plane and a given ray.

     Overrides the method in the Geometry class.

     @param ray The ray to intersect with the plane.

     @return A list of GeoPoints representing the intersections, or null if there are no intersections.
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        if (intersectables.isEmpty()) {
            return null;
        }
        List<GeoPoint> list = null;
        for(Intersectable inter : intersectables) {
            List<GeoPoint> smallList = inter.findGeoIntersections(ray);
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

    /**

     Helper method for finding the intersections between the geometries bodies in the scene and a given ray.
     Overrides the method in the Geometry class.
     @param ray The ray to intersect with the geometric bodies in the scene.
     @return A list of GeoPoints representing the intersections, or null if there are no intersections.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = null;
        for(Intersectable intersectable : intersectables) {
            List<GeoPoint> geometryIntersections = intersectable.findGeoIntersections(ray);
            if (!geometryIntersections.isEmpty())
                for (GeoPoint geo : geometryIntersections) {
                    intersections.add(geo);
                }
        }
        return intersections;
    }
}