package lighting;

import primitives.Color;
import primitives.Double3;

/**

 Represents a light source in the scene.
 */
abstract class Light {
    private final Color intensity;

    /**

     Constructs a new Light with the specified intensity.
     @param intensity The intensity of the light.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }
    /**

     Gets the intensity of the light.
     @return The intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}
