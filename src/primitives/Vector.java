/**
/*
 The Vector class represents a three-dimensional vector with an x, y, and z component.
 It extends the Point class and inherits its methods and properties.
 @author Michal and Adina (with help of chatGPT)
 */

package primitives;
public class Vector extends Point {

    /**
     * Constructs a Vector object with the specified x, y, and z components.
     *
     * @param x the x component of the vector
     * @param y the y component of the vector
     * @param z the z component of the vector
     * @throws IllegalArgumentException if the vector's components are all zero
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be ZERO");
        }
    }

    /**
     * Constructs a Vector object from a Double3 object.
     *
     * @param double3 the Double3 object representing the vector's components
     */
    public Vector(Double3 double3) {
        this(double3.d1, double3.d2, double3.d3);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be ZERO");
        }
    }

    /**
     * Checks if the vector is equal to the specified object.
     *
     * @param o the object to compare with
     * @return true if the object is equal to the vector, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }

    /**
     * Returns the length of the vector.
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns the squared length of the vector.
     *
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        double dx = xyz.d1;
        double dy = xyz.d2;
        double dz = xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Returns a normalized vector (a vector with the same direction, but with length 1).
     *
     * @return a normalized vector
     */
    public Vector normalize() {
        double len = length();
        return new Vector(xyz.reduce(len));
    }

    /**

     Adds the specified vector to this vector.
     @param vector The vector to add.
     @return The sum of this vector and the specified vector.
     */
    public Vector add(Vector vector){
        return new Vector(xyz.add(vector.xyz));
    }
    /**

     Scales this vector by the specified factor.
     @param num The scaling factor.
     @return The scaled vector.
     */
    public Vector scale(double num) {
      //  return new Vector(xyz.scale(num));
        return new Vector(xyz.d1 * num, xyz.d2 * num,xyz.d3 * num);
    }

    /**

     Computes the dot product of this vector and the specified vector.
     @param vector The vector to compute the dot product with.
     @return The dot product of this vector and the specified vector.
     */
    public double dotProduct(Vector vector) {
        return vector.xyz.d1 * this.xyz.d1 + vector.xyz.d2 * this.xyz.d2 + vector.xyz.d3 * this.xyz.d3;
    }
    /**

     Computes the cross product of this vector and the specified vector.
     @param vector The vector to compute the cross product with.
     @return The cross product of this vector and the specified vector.
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                this.xyz.d2 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d2,
                this.xyz.d3 * vector.xyz.d1 - this.xyz.d1 * vector.xyz.d3,
                this.xyz.d1 * vector.xyz.d2 - this.xyz.d2 * vector.xyz.d1);
    }

    /**
     * Computes the subtraction of one vector from another.
     * @param vector The vector to subtract from the vector that calls the function
     * @return the vector formed by the subtraction of the original vector by the vector called
     */
    public Vector subtract(Vector vector) {
        return new Vector(xyz.subtract(vector.xyz));
    }

    @Override
    public String toString() {
        return "Vector: " + xyz;
    }


}