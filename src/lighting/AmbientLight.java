package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents the ambient light in a scene.
 * Ambient light is light that is scattered and reflected throughout a scene
 * and is independent of the position and direction of objects and light sources.
 * This class defines the color and intensity of the ambient light.
 */
public class AmbientLight extends Light {

    /**
     * A static ambient light object with black color and zero intensity.
     * This can be used as a placeholder for when no ambient light is needed in a scene.
     */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    public AmbientLight() {super(Color.BLACK);}
    //public AmbientLight(Color iA, Double3 kA) {
    //    super(iA, kA);
    //}

    public AmbientLight(Color iA, double kA) {
        super(iA, new Double3(kA));
    }

    public AmbientLight(Color iA, Double3 kA) {
        super( iA.scale(kA));
    }

}
