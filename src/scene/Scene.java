package scene;

import lighting.AmbientLight;
import lighting.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Passive Data Structure which represents a scene for the picture being built.
 * @Author Michal and Adina
 */
public class Scene {
    // the name of the scene
    private String name;

    // the background color of the scene (default is black)
    private Color background = Color.BLACK;

    // the ambient light for the scene (default is black)
    private AmbientLight ambientLight = AmbientLight.NONE;

    // the 3D model (default is an empty model)
    private Geometries geometries = new Geometries();

    private List<LightSource> lights = new LinkedList<>();

    private Scene(SceneBuilder sb) {
        name = sb.name;
        background = sb.background;
        ambientLight = sb.ambientLight;
        geometries = sb.geometries;
        lights = sb.lights;
    }

    public String getName() {
        return name;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Geometries getGeometries() {
        return geometries;
    }

    public List<LightSource> getLights() {
        return lights;
    }

    /**
     * Constructor which initializes the name of the scene and creates an empty collection of geometries
     * @param name to initialize the name of the scene
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }

    public void render() {
        // Code to render the scene goes here
    }

    /**
     * Builder class for constructing a Scene object.
     * @Author Michal and Adina
     */
    public static class SceneBuilder {
        private String name;
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = AmbientLight.NONE;
        private Geometries geometries = new Geometries();
        private List<LightSource> lights = new LinkedList<>();

        public SceneBuilder(String scene) {
            this.name = scene;
        }

        /**
         * Sets the name of the scene.
         *
         * @param name the name of the scene
         * @return the SceneBuilder instance for method chaining
         */
        public SceneBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the background color of the scene.
         *
         * @param background the background color
         * @return the SceneBuilder instance for method chaining
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /**
         * Sets the ambient light of the scene.
         *
         * @param ambientLight the ambient light
         * @return the SceneBuilder instance for method chaining
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * Sets the geometries of the scene.
         *
         * @param geometries the geometries of the scene
         * @return the SceneBuilder instance for method chaining
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        /**
         * Sets the light sources of the scene.
         *
         * @param lights the light sources of the scene
         * @return the SceneBuilder instance for method chaining
         */
        public SceneBuilder setLights(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        /**
         * Builds and returns a new Scene object with the specified properties.
         *
         * @return a new Scene object
         */
        public Scene build() {
            return new Scene(this);
        }
    }
}
