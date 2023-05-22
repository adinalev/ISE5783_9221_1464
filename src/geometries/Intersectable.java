package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import java.util.Objects;

/**
 * The Intersectable interface represents an object that can be intersected by a ray.
 * Implementing classes should provide a method for finding the intersection points.
 * @author Michal and Adina (with help of chatGPT)
 */
public abstract class Intersectable {

    /**
     * Finds the intersection points between the specified ray and this object.
     *
     * @param ray the ray to intersect with this object
     * @return a list of intersection points between the ray and this object,
     *         or an empty list if there are no intersections
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
    /**

     Finds the intersections between the geometry shape and a given ray.
     @param ray The ray to intersect with the geometry shape.
     @return A list of GeoPoints representing the intersections.
     */
    public abstract List<GeoPoint> findGeoIntersections(Ray ray);
    /**

     Helper method for finding the intersections between the geometry shape and a given ray.
     @param ray The ray to intersect with the geometry shape.
     @return A list of GeoPoints representing the intersections.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
    /**

     Represents a geometric intersection point between a geometry and a ray.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**

         Constructs a new GeoPoint with the given geometry and point.
         @param geometry The geometry associated with the intersection point.
         @param point The intersection point.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }
        /**

         Checks if this GeoPoint is equal to another object.
         @param o The object to compare.
         @return true if the objects are equal, false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }
        /**

         Computes the hash code for this GeoPoint.
         @return The hash code value for this GeoPoint.
         */
        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }
        /**

         Returns a string representation of this GeoPoint.
         @return A string representation of the GeoPoint.
         */
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
