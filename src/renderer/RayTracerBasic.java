package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * The RayTracerBasic class extends the abstract RayTracerBase class
 * and implements the traceRay method.
 * This class represents a basic ray tracer that finds the closest
 * intersection point of a ray with the scene geometry and returns the color of that point.
 * @Author Michal and Adina with the help of ChatGPT.
 */

public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Traces the ray to find the closest point in the list of intersection
     * points and colors that pixel.
     * @param ray The Ray object representing the ray being traced.
     * @return
     */
    @Override
    public Color traceRay(Ray ray) {
        // Find the intersection point of the given ray with the geometries in the scene
        List<Point> result = scene.geometries.findIntersections(ray);

        // if there are no intersection points, return the background color of the scene
        if (result == null) {
            return scene.background;
        }

        // call the method to find the closest point to the head of the ray
        Point closest = ray.findClosestPoint(result);
        // calculate the color of that point
        Color color = calcColor(closest);
        // return the color found
        return color;
    }

    /**
     * Calculates the color of the point using the ambient
     * light intensity of the scene
     * @param p point that's color is being calculated
     * @return the Color of that point
     */
    public Color calcColor(Point p) {
        return scene.ambientLight.getIntensity();
    }
}
