/**

 The RadialGeometry class represents a geometric shape with a radius.
 It is an abstract class that implements the Geometry interface.
 The radius is a protected field, and can be accessed through the getRadius() method.
 * @author Michal and Adina (with help of chatGPT)
 */
package geometries;
public abstract class RadialGeometry extends Geometry {
    final protected double radius;

    /**
     * Constructs a RadialGeometry object with the specified radius.
     *
     * @param radius the radius of the shape
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    /**
     * Returns the radius of the shape.
     *
     * @return the radius of the shape
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Returns a string representation of the RadialGeometry object.
     *
     * @return a string representation of the RadialGeometry object
     */
    @Override
    public String toString() {
        return "RadialGeometry{" +
                "radius=" + radius +
                '}';
    }
}
