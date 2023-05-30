package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**

 Represents a light source in the scene.
 */
public interface LightSource {
     /*

     Gets the intensity of the light at the specified point.
     @param p The point at which to calculate the intensity.
     @return The intensity of the light.
     */
    public Color getIntensity(Point p);
    /**

     Gets the direction vector of the light at the specified point.
     @param p The point at which to calculate the direction.
     @return The direction vector of the light.
     */
    Vector getL(Point p);

    /**
     * Gets the distance between a LightSource and a given point (of the object)
     * @param point The point to which we're finding the distance to.
     * @return The distance between the LightSource and the point on the object
     */
    double getDistance(Point point);

}