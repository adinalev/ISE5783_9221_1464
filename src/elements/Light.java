package elements;

import primitives.Color;
import primitives.Double3;

/**

 Represents a light source in the scene.
 */
abstract class Light {
    private Color intensity;

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
    /**

     Constructs a new Light with the specified ambient intensity and attenuation factors.
     @param iA The ambient intensity of the light.
     @param kA The attenuation factors of the light.
     */
    public Light(Color iA, Double3 kA) {
        this.intensity = iA.scale(kA);
    }
}
