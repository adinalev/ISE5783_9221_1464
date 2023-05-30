
package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

/**

 Represents a point light source in the scene.
 */
public class PointLight extends Light implements LightSource {
    private final Point position;
    private Double3 kC = Double3.ONE;
    private Double3 kL = Double3.ZERO;
    private Double3 kQ = Double3.ZERO;

    /**

     Sets the constant attenuation factor of the point light.
     @param kC The constant attenuation factor.
     @return The updated PointLight object.
     */
    public PointLight setKc(double kC) {
        this.kC = new Double3(kC);
        return this;
    }
    /**

     Sets the linear attenuation factor of the point light.
     @param kL The linear attenuation factor.
     @return The updated PointLight object.
     */
    public PointLight setKl(double kL) {
        this.kL = new Double3(kL);
        return this;
    }
    /**

     Sets the quadratic attenuation factor of the point light.
     @param kQ The quadratic attenuation factor.
     @return The updated PointLight object.
     */
    public PointLight setKq(double kQ) {
        this.kQ = new Double3(kQ);
        return this;
    }
    /**

     Constructs a new PointLight with the specified color and position.
     @param color The color of the light.
     @param position The position of the light.
     */
    public PointLight(Color color, Point position) {
        super(color);
        this.position = position;
    }
    /**

     Gets the intensity of the point light at the specified point.
     @param p The point at which to calculate the intensity.
     @return The intensity of the light.
     */
    public Color getIntensity(Point p) {
        double d = p.distance(position);
        double d2 = p.distanceSquared(position);
        Color i0 = getIntensity();
        Double3 coeff = (kC.add(kL.scale(d))).add(kQ.scale(d2));
        return i0.reduce(coeff);
    }
    /**

     Gets the direction vector from the point light to the specified point.
     @param p The point at which to calculate the direction.
     @return The direction vector of the light.
     */
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    /**
     * Gets the distance between a point light and a given point (of the object)
     * @param point The point to which we're finding the distance to.
     * @return The distance between the point light and the point on the object
     */
    public double getDistance(Point point) {
        return point.distance(position);
    }

    public PointLight setkC(Double3 kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(Double3 kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(Double3 kQ) {
        this.kQ = kQ;
        return this;
    }
}