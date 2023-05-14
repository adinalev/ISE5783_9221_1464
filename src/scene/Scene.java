package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * Passive Data Structure which builds the scene for the picture that is being built.
 * @Author Michal and Adina
 */
public class Scene {
    // the name of the scene
    public String name;

    //the background color of the scene (default is black)
    public Color background = Color.BLACK;

    // the ambient light for the scene (default is black)
    public AmbientLight ambientLight = AmbientLight.NONE;

    // the 3D model (default is an empty model)
    public Geometries geometries = new Geometries();

    /**
     * Constructor which initializes the name of the scene and creates an empty collection of geometries
     * @param name to initialize the name of the scene
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }

    /**
     * Sets the background color of the scene
     * @param background
     * @return Scene object in order to allow chaining for the Builder pattern
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene
     * @param ambientLight
     * @return Scene object in order to allow chaining for the Builder pattern
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries which are found in the scene
     * @param geometries
     * @return Scene object in order to allow chaining for the Builder pattern
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }


}
