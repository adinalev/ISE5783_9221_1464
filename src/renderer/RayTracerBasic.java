package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;

import static java.awt.Color.BLACK;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The RayTracerBasic class extends the abstract RayTracerBase class
 * and implements the traceRay method.
 * This class represents a basic ray tracer that finds the closest
 * intersection point of a ray with the scene geometry and returns the color of that point.
 *
 * @Author Michal and Adina with the help of ChatGPT.
 */

public class RayTracerBasic extends RayTracerBase {
    // constant field for the amount of move the ray's head.
    private static final double DELTA = 0.1;
    private static final Double3 INITIAL_K = Double3.ONE;

    // constants for the recursion stop condition of reflection/transparency
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    private boolean superSamplingON = false;
    private int superSamplingGridSize; // for n, the grid will be 9x9

    private int numOfSSRays;
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Traces the ray to find the closest point in the list of intersection
     * points and colors that pixel.
     *
     * @param ray The Ray object representing the ray being traced.
     * @return
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint gp = findClosestIntersection(ray);

        // if the findClosestIntersection function returns null (meaning the list is null)
        // then return the background of the scene
        if (gp == null) {
            return scene.getBackground();
        }
        // return the color found
        return calcColor(gp, ray);
    }

    /**
     * Calculates the specular reflection component for a given material.
     *
     * @param ks             The specular reflection coefficient of the material.
     * @param l              The direction of the light source.
     * @param n              The surface normal at the point of reflection.
     * @param v              The direction from the point of reflection towards the viewer.
     * @param nShininess     The shininess factor of the material.
     * @param lightIntensity The intensity of the light source.
     * @return The color resulting from the specular reflection.
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n))));
        double vrMinus = Math.max(0, v.scale(-1).dotProduct(r));
        double vrn = Math.pow(vrMinus, nShininess);
        return lightIntensity.scale(ks.scale(vrn));
    }

    /**
     * Calculates the diffuse reflection component for a given material.
     *
     * @param kd        The diffuse reflection coefficient of the material.
     * @param l         The direction of the light source.
     * @param n         The surface normal at the point of reflection.
     * @param intensity The intensity of the light source.
     * @return The color resulting from the diffuse reflection.
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color intensity) {
        double ln = Math.abs(l.dotProduct(n));
        return intensity.scale(kd.scale(ln));
    }

    /**
     * Checks if a given point is unshaded by a light source.
     *
     * @param gp    The GeoPoint representing the point on the geometry to check for shading.
     * @param light The light source to consider.
     * @param l     The vector representing the direction from the point to the light source.
     * @param n     The surface normal vector at the point.
     * @param nl    The dot product between the surface normal and the light vector
     * @return {@code true} if the point is unshaded by the light source, {@code false} otherwise.
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1);
        Vector deltaVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(deltaVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay);
        if (intersections == null) {
            return true;
        }
        double _distance = light.getDistance(lightRay.getP0());

        for (GeoPoint geo : intersections) {
            if (geo.geometry.getMaterial().kT == Double3.ZERO) {
                if (lightRay.getP0().distance(geo.point) < _distance) {
                    return false;
                }
            }
        }
        return true;
    }

    /**

     * Calculates the transparency factor for a given point in the scene
     * with respect to a light source.
     * @param geoPoint The geometric point in the scene.
     * @param ls The light source.
     * @param l The direction vector from the light source to the point.
     * @param n The normal vector at the point.
     * @return The transparency factor as a Double3 object.
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1);
        //Vector deltaVector = n.scale(nl < 0 ? DELTA : -DELTA);
        //Point point = geoPoint.point.add(deltaVector);

        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay);
        if (intersections == null) {
            return Double3.ONE;
        }

        Double3 ktr = Double3.ONE;

        double distance = ls.getDistance(lightRay.getP0());

        for (GeoPoint geo : intersections) {
            //if (geo.geometry.getMaterial().kT == Double3.ZERO) {
            if (lightRay.getP0().distance(geo.point) < distance) { // lightRay.getP0().distance(geo.point) < distance
                ktr = geo.geometry.getMaterial().kT.product(ktr);
            }
        }
        return ktr;
    }

    /**

     * Constructs a reflected ray given an incident ray, a point of intersection,
     * and the surface normal at that point.
     * @param dir The direction of the ray.
     * @param point The point of intersection.
     * @param normal The surface normal at the intersection point.
     * @return The reflected ray.
     */
    public Ray constructReflectedRay(Vector dir, Point point, Vector normal) {
//        Vector r = dir.subtract(normal.scale(2 * normal.dotProduct(dir)));
//        return new Ray(point, r, normal);
        double vn = dir.dotProduct(normal);

        if (alignZero(vn) == 0) {
            return null;
        }

        Vector r = dir.subtract(normal.scale(2 * vn));
        return new Ray(point, r, normal);
    }

    /**

     * Constructs a refracted ray given a point of intersection, an incident ray,
     * and the surface normal at that point.
     * @param point The point of intersection.
     * @param ray The incident ray.
     * @param normal The surface normal at the intersection point.
     * @return The refracted ray.
     */
    public Ray constructRefractedRay(Point point, Ray ray, Vector normal) {
        return new Ray(point, ray.getDir(), normal);
    }

    /**

     * Finds the closest intersection point between a ray and the scene's geometries.
     * @param ray The ray to find intersections with.
     * @return The closest GeoPoint intersection, or null if no intersection is found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> geoPointList = scene.getGeometries().findGeoIntersections(ray);
        if (geoPointList == null) {
            return null;
        }
        return ray.findClosestGeoPoint(geoPointList);
    }

    /**
     * Calculate the local lighting effects of a geometry intersection point
     *
     * @param gp - of type GeoPoint
     * @param ray          - of type Ray
     * @return a color - based on effect of local factors
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Vector v = ray.getDir();
        Vector n = gp.getNormal();

        // dot product of v and n
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) {
            return Color.BLACK;
        }

        Material material = gp.geometry.getMaterial();
        int nShininess = material.nShininess;
        Double3 kd = material.kD;
        Double3 ks = material.kS;

        Color color = Color.BLACK;
        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0)
            {
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color intensity = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            calcDiffusive(kd, l, n, intensity),
                            calcSpecular(ks, l, n, v, nShininess, intensity));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the color of the point using the ambient
     * light intensity of the scene
     * @param gp point that's color is being calculated
     * @return the Color of that point
     */
    public Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.getAmbientLight().getIntensity());
    }

    /**

     * Calculates the color at a given intersection point considering local and global effects.
     * @param gp The GeoPoint representing the intersection.
     * @param ray The ray that intersects the geometry.
     * @param level The recursion level.
     * @param k The transparency factor.
     * @return The calculated color at the intersection point.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = gp.geometry.getEmission().add(calcLocalEffects(gp,ray,k));
        return level == 1 ? color : color.add(calcGlobalEffects(gp,ray,level,k));
    }


    /**

     * Calculates the global effects (reflection and refraction) at an intersection point.
     * @param gp The GeoPoint representing the intersection.
     * @param ray The incident ray.
     * @param level The recursion level.
     * @param k The transparency factor.
     * @return The color resulting from the global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kkr = k.product(material.kR);
        Double3 kkt = k.product(material.kT);

        if (!(kkr.lowerThan(MIN_CALC_COLOR_K))) {
            color = color.add(calcGlobalEffects(constructReflectedRay(ray.getDir(), gp.point,n), level, material.kR, kkr));
        }

        if (!(kkt.lowerThan(MIN_CALC_COLOR_K))) {
            color = color.add(calcGlobalEffects(constructRefractedRay(gp.point,ray,n), level, material.kT, kkt));
        }
        return color;
    }

//    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Color color) {
//        if (level == 0 || gp.geometry.getMaterial().kT == Double3.ZERO && gp.geometry.getMaterial().kR == Double3.ZERO) {
//            return color;
//        }
//
//        Vector n = gp.geometry.getNormal(gp.point);
//        Material material = gp.geometry.getMaterial();
//
//        Ray reflectedRay = constructReflectedRay(ray, gp.point, n);
//        Color reflectedColor = calcColor(gp, reflectedRay, level - 1, ).scale(material.kR); // GeoPoint gp, Ray ray, int level, Double3 k)
//        color = color.add(reflectedColor);
//
//        Ray refractedRay = constructRefractedRay(gp.point, ray, n);
//        Color refractedColor = calcColor(refractedRay, level - 1).scale(material.kT);
//        color = color.add(refractedColor);
//
//        return color;
//    }


    /**
     * Recursive helper method for calculating global effects (reflection and refraction).
     *
     * @param ray    The incident ray.
     * @param level  The recursion level.
     * @param kx     The transparency factor.
     * @param kkx    The product of the transparency factor and the material's reflection or refraction factor.
     * @return The color resulting from the global effects.
     */
    private Color calcGlobalEffects(Ray ray, int level, Double3 kx, Double3 kkx) {
        // Find the closest intersection point between the ray and scene objects
        GeoPoint gp = findClosestIntersection(ray);

        // If super sampling is enabled, shoot multiple rays (beam) and calculate the color
        if (superSamplingON == true) {
            List<Ray> beam = shootBeam(ray);
            Color beamColor = beamCalcColor(beam);

            // If there is no intersection point, return the background color;
            // otherwise, calculate the color recursively and add the beam color, then scale it by the transparency factor
            return (gp == null) ? scene.getBackground() : add(calcColor(gp, ray, level - 1, kkx), beamColor).scale(kx);
        }

        // If super sampling is not enabled, calculate the color recursively and scale it by the transparency factor
        return (gp == null) ? scene.getBackground() : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Sets the flag indicating whether super sampling is enabled or not.
     *
     * @param superSamplingON A boolean value indicating whether super sampling is enabled.
     * @return The updated RayTracerBasic object.
     */
    public RayTracerBasic setSuperSamplingON(boolean superSamplingON) {
        this.superSamplingON = superSamplingON;
        return this;
    }

    /**
     * Sets the grid size for super sampling.
     *
     * @param superSamplingGridSize The size of the super sampling grid.
     * @return The updated RayTracerBasic object.
     */
    public RayTracerBasic setSuperSamplingGridSize(int superSamplingGridSize) {
        this.superSamplingGridSize = superSamplingGridSize;
        return this;
    }


    /**
     * sets the number of rays to shoot for super sampling
     * @param numOfSSRays
     * @return Camera object for chaining
     */
    public RayTracerBasic setNumOfSSRays(int numOfSSRays) {
        this.numOfSSRays = numOfSSRays;
        return this;
    }

    /**
     * shoots multiple rays in the shape of a grid
     * @param mainRay
     * @return a list of rays
     */
    private List<Ray> shootBeam(Ray mainRay) {
        Point mrP0 = mainRay.getP0();
        List<Ray> beam = new LinkedList<Ray>();

        // get to the center of the target area (square)
        Point center = mrP0.add(mainRay.getDir());

        double halfSize = superSamplingGridSize / 2;
        // get the left corner point and use it as a basis of where to start from
        Point startingPoint = center.add(new Point(halfSize,halfSize,0));
        double intervalHelper = superSamplingGridSize/(Math.sqrt(numOfSSRays));

        // shoot rays in the shape of a grid
        for (int i = 0; i < Math.sqrt(numOfSSRays); i++) {
            for (int j = 0; j < Math.sqrt(numOfSSRays); j++) {
                Point interval = new Point(i*intervalHelper, j*intervalHelper, 0);

                // add the interval to the startingPoint to get a point in the grid
                Point gridPoint = startingPoint.add(interval);

                // create the ray for this new spot in the grid
                Ray shootRay = new Ray(mrP0, gridPoint.subtract(mrP0));

                // add this ray to the list of rays to shoot
                beam.add(shootRay);
            }
        }
        return beam;
    }

    /**
     * calculate the color
     * @param beam list of rays
     * @return the average of all the colors sent in
     */
    private Color beamCalcColor(List<Ray> beam) {
        Color result = Color.BLACK;

        //Color result = this.scene.getAmbientLight().getIntensity();

        // calculate the color of each ray and add them all together
        for (int i = 0; i < beam.size(); i++) {
            GeoPoint gp = findClosestIntersection(beam.get(i));

            //if it does not hit anything added the background
            if(gp == null) {
                result = result.add(scene.getBackground());
            }

            //else add the color of the first object it hits
            else {
                result = result.add(gp.geometry.getEmission());
            }
        }

        // divide the color by the total number of rays to get the average color
        return result.reduce(numOfSSRays);
    }

    /**
     * Adds two colors together.
     *
     * @param color1 The first color to be added.
     * @param color2 The second color to be added.
     * @return The resulting color from adding color1 and color2.
     */
    public Color add(Color color1, Color color2) {
        // Retrieve the RGB values of color1 and color2
        Double3 firstColor = color1.getRGB();
        Double3 secondColor = color2.getRGB();

        // Add the RGB values together
        Double3 added = firstColor.add(secondColor);

        // Create a new Color object from the resulting RGB values
        return new Color(added);
    }

}
