package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**

 The RayTracerBase class is an abstract class that provides a base implementation for
 ray tracing algorithms in a 3D scene.
 @author Michal and Adina with the help of ChatGPT
 */
public abstract class RayTracerBase {
    /**

     The scene field contains the Scene object that represents the 3D scene being rendered.
     */
    protected Scene scene;

    /**

     This constructor creates a new RayTracerBase object with the specified Scene object.
     @param scene The Scene object representing the 3D scene to be rendered.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**

     The traceRay method is an abstract method that must be implemented by subclasses of
     RayTracerBase to perform the actual ray tracing.
     @param ray The Ray object representing the ray being traced.
     @return The Color object representing the color of the traced ray.
     */
    public abstract Color traceRay(Ray ray);
}
