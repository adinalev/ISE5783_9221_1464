package elements;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents the ambient light in a scene.
 * Ambient light is light that is scattered and reflected throughout a scene
 * and is independent of the position and direction of objects and light sources.
 * This class defines the color and intensity of the ambient light.
 */
public class AmbientLight {
    // the original color of the light (the intensity of the light acc to RGB)
    final Color intensity;

    /**
     * A static ambient light object with black color and zero intensity.
     * This can be used as a placeholder for when no ambient light is needed in a scene.
     */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructs an AmbientLight object with a specified color and intensity coefficient.
     * The intensity of the light is calculated as the product of the color and intensity coefficient.
     *
     * @param iA the color of the ambient light
     * @param kA the intensity coefficient of the ambient light
     */
    public AmbientLight(Color iA, Double3 kA) {
        this.intensity = iA.scale(kA);
    }

    /**
     * Constructs an AmbientLight object with black color and zero intensity.
     */
    public AmbientLight() {
        this.intensity = Color.BLACK;
    }

    /**
     * Returns the intensity of the ambient light.
     *
     * @return the intensity of the ambient light
     */
    public Color getIntensity() {
        return intensity;
    }
}
