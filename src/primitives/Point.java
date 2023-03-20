package primitives;

import java.util.Objects;

/**
 * class representing 3D coordinates
 * @author Michal and Adina (with help of chatGPT)
 */
public class Point {
    final Double3 xyz;

    /**
     * Constructs and initializes a point with the specified x, y, and z coordinates.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param z the z coordinate of the point
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructs and initializes a point from the specified Double3 object.
     *
     * @param double3 the Double3 object containing the x, y, and z coordinates of the point
     */
    public Point(Double3 double3) {
        this(double3.d1, double3.d2, double3.d3);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return xyz.equals(point.xyz);
    }

    /**
     * Returns a hash code value for the point.
     *
     * @return a hash code value for this point.
     */
    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    /**
     * Returns a string representation of the point.
     *
     * @return a string representation of the point.
     */
    @Override
    public String toString() {
        return "Point: " + xyz;
    }

    /**
     * Computes the Euclidean distance between this point and the specified point.
     *
     * @param other the other point
     * @return the Euclidean distance between this point and the specified point.
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Calculates the square of the Euclidean distance between this point and the specified point.
     *
     * @param other the other point
     * @return the square of the Euclidean distance between this point and the specified point.
     */
    public double distanceSquared(Point other) {
        return (other.xyz.d1 - xyz.d1) * (other.xyz.d1 - xyz.d1) +
                (other.xyz.d2 - xyz.d2) * (other.xyz.d2 - xyz.d2) +
                (other.xyz.d3 - xyz.d3) * (other.xyz.d3 - xyz.d3);
    }
    /**

     Adds the specified vector to this point and returns the result as a new point.
     @param vector The vector to add to this point.
     @return A new point representing the result of adding the specified vector to this point.
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }
    /**

     Subtracts the specified point from this point and returns the result as a new vector.
     @param secondPoint The point to subtract from this point.
     @return A new vector representing the result of subtracting the specified point from this point.
     */
    public Vector subtract(Point secondPoint) {
        return new Vector(xyz.subtract(secondPoint.xyz));
    }
}
