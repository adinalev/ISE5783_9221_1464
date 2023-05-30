/**

 Represents a ray in 3D space, defined by a starting point and a direction vector.
 @author Michal and Adina (with help of chatGPT)
 */
package primitives;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import geometries.Intersectable.GeoPoint;
import geometries.Intersectable.GeoPoint;

public class Ray {
    /**
     * Amount to move the ray's head away from the geometry when making shadow, reflection, and refraction rays
     */
    private static final double DELTA = 0.1;
    /**
     * The starting point of the ray.
     */
    protected final Point p0;

    /**
     * The direction vector of the ray.
     */
    protected final Vector dir;

    /**
     * Creates a new Ray with the specified starting point and direction vector.
     * The direction vector is normalized to have a length of 1.
     *
     * @param p0  The starting point of the ray.
     * @param dir The direction vector of the ray.
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }
    public Ray(Point point, Vector dir, Vector normal) {
        this.dir = dir.normalize();
        Vector deltaV = normal.dotProduct(this.dir) >= 0 ? normal.scale(DELTA) : normal.scale(-DELTA);
        p0 = point.add(deltaV);
    }

    /**
     * Returns the starting point of the ray.
     *
     * @return The starting point of the ray.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction vector of the ray.
     *
     * @return The direction vector of the ray.
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Checks if this ray is equal to the specified object.
     * Two rays are equal if their starting points and direction vectors are equal.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    /**
     * Computes the hash code of this ray.
     *
     * @return The hash code of this ray.
     */
    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    /**
     * Returns a string representation of this ray.
     *
     * @return A string representation of this ray.
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    public Point getPoint(double t) {
        if (isZero(t)) {
            return p0;
        }
        return p0.add(dir.scale(t));
    }

    /**
     * Finds the closest point from a list of points.
     *
     * @param points The list of points to search.
     * @return The closest point.
     */
    public Point findClosestPoint(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return null;
        }
        GeoPoint closestGeoPoint = findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList());
        return closestGeoPoint != null ? closestGeoPoint.point : null;
    }

    /**
     * Finds the closest GeoPoint from a list of GeoPoints.
     *
     * @param geoPointList The list of GeoPoints to search.
     * @return The closest GeoPoint.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointList) {
        if (geoPointList.isEmpty()) {
            return null;
        }
        double shortestDistance = this.p0.distance(geoPointList.get(0).point);
        double tempDistance = 0;
        GeoPoint closest = geoPointList.get(0);

        for (GeoPoint geo : geoPointList) {
            tempDistance = this.p0.distance(geo.point);
            if (tempDistance < shortestDistance) {
                shortestDistance = tempDistance;
                closest = geo;
            }
        }

        return new GeoPoint(closest.geometry, closest.point);
    }
}