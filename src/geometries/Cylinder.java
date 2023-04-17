package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;
/**

 The Cylinder class represents a 3D cylinder shape defined by its base radius, axis ray, and height.
 It is a subclass of the Tube class.
 @author Michal and Adina (with help of chatGPT)
 */
public class Cylinder extends Tube {
    private double height;

    /**

     Constructs a new Cylinder object with the specified radius, axis ray, and height.
     @param radius The radius of the cylinder base.
     @param axisRay The axis ray of the cylinder.
     @param height The height of the cylinder.
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }
    /**

     Returns the height of this cylinder.
     @return The height of this cylinder.
     */
    public double getHeight() {
        return height;
    }
}