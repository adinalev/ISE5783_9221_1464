package elements;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**

 Represents a spotlight in the scene, which is a type of point light with a specific direction.
 */
public class SpotLight extends PointLight {
    private Vector direction;
    private double angle;

    /**

     Constructs a new SpotLight with the specified color, position, and direction.
     @param color The color of the light.
     @param position The position of the light.
     @param direction The direction of the light.
     */
    public SpotLight(Color color, Point position, Vector direction) {
        super(color, position);
        this.direction = direction;
    }
    /**

     Gets the intensity of the spotlight at the specified point.
     @param p The point at which to calculate the intensity.
     @return The intensity of the light.
     */
    public Color getIntensity(Point p) {
        Color i0 = getIntensity();
        double dotProd = direction.dotProduct(getL(p));
        double max = Math.max(dotProd, 0);
        Color iL = super.getIntensity(p);
        return iL.scale(max);
    }
    /**

     Gets the direction vector from the spotlight to the specified point.
     @param p The point at which to calculate the direction.
     @return The direction vector of the light.
     */
    public Vector getL(Point p) {
        return super.getL(p);
    }

    public SpotLight setNarrowBeam(double n) {
        this.angle = n;
        return this;
    }
}
