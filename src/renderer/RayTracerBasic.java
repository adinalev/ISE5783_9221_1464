package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static java.awt.Color.BLACK;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The RayTracerBasic class extends the abstract RayTracerBase class
 * and implements the traceRay method.
 * This class represents a basic ray tracer that finds the closest
 * intersection point of a ray with the scene geometry and returns the color of that point.
 * @Author Michal and Adina with the help of ChatGPT.
 */

public class RayTracerBasic extends RayTracerBase {
    // constant field for the amount of move the ray's head.
    private static final double DELTA = 0.1;
    private static final Double3 INITIAL_K = Double3.ONE;

    // constants for the recursion stop condition of reflection/transparency
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
        GeoPoint gp = findClosestIntersection(ray);

        // if the findClosestIntersection function returns null (meaning the list is null)
        // then return the background of the scene
        if (gp == null) {
            return scene.background;
        }
        // return the color found
        return calcColor(gp, ray);
    }

    /**
     * Calculates the color of the point using the ambient
     * light intensity of the scene
     * @param gp point that's color is being calculated
     * @return the Color of that point
     */
    public Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = gp.geometry.getEmission().add(calcLocalEffects(gp,ray,k));
        return level == 1 ? color : color.add(calcGlobalEffects(gp,ray,level,k));
    }


    /**

     Calculates the local effects (diffuse and specular) of a given geometric point on a ray.
     @param gp The geometric point at which to calculate the local effects.
     @param ray The ray being traced.
     @return The color resulting from the local effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);

        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) {
            return color;
        }

        int nShininess = gp.geometry.getMaterial().nShininess;
        Double3 kd = gp.geometry.getMaterial().kD;
        Double3 ks = gp.geometry.getMaterial().kS;

        Color color2 = new Color(BLACK);
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if(nl*nv > 0 ) {
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                //if (unshaded(gp, lightSource, l, n, nl)) {
                    Color intensity = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(calcDiffusive(kd,l,n,intensity), calcSpecular(ks,l,n,v,nShininess,intensity));
                }
            }
        }
        return color;
    }

    /**

     Calculates the specular reflection component for a given material.
     @param ks The specular reflection coefficient of the material.
     @param l The direction of the light source.
     @param n The surface normal at the point of reflection.
     @param v The direction from the point of reflection towards the viewer.
     @param nShininess The shininess factor of the material.
     @param lightIntensity The intensity of the light source.
     @return The color resulting from the specular reflection.
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity){
        Vector r = l.subtract(n.scale(2*(l.dotProduct(n))));
        double vrMinus = Math.max(0, v.scale(-1).dotProduct(r));
        double vrn =Math.pow(vrMinus,nShininess);
        return lightIntensity.scale(ks.scale(vrn));
    }

    /**

     Calculates the diffuse reflection component for a given material.
     @param kd The diffuse reflection coefficient of the material.
     @param l The direction of the light source.
     @param n The surface normal at the point of reflection.
     @param intensity The intensity of the light source.
     @return The color resulting from the diffuse reflection.
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color intensity){
        double ln = Math.abs(l.dotProduct(n));
        return intensity.scale(kd.scale(ln));
    }

    /**

     Checks if a given point is unshaded by a light source.
     @param gp The GeoPoint representing the point on the geometry to check for shading.
     @param light The light source to consider.
     @param l The vector representing the direction from the point to the light source.
     @param n The surface normal vector at the point.
     @param nl The dot product between the surface normal and the light vector
     @return {@code true} if the point is unshaded by the light source, {@code false} otherwise.
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1);
        Vector deltaVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(deltaVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) {
            return true;
        }
        double _distance = light.getDistance(lightRay.getP0());

        for(GeoPoint geo : intersections) {
            if (geo.geometry.getMaterial().kT == Double3.ZERO) {
                if (lightRay.getP0().distance(geo.point) < _distance) {
                    return false;
                }
            }
        }
        return true;
    }

    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1);
        //Vector deltaVector = n.scale(nl < 0 ? DELTA : -DELTA);
        //Point point = geoPoint.point.add(deltaVector);

        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) {
            return Double3.ONE;
        }

        Double3 ktr = Double3.ONE;

        double distance = ls.getDistance(lightRay.getP0());

        for(GeoPoint geo : intersections) {
            //if (geo.geometry.getMaterial().kT == Double3.ZERO) {
            if (lightRay.getP0().distance(geo.point) < distance) {
                return geo.geometry.getMaterial().kT.product(ktr);
            }
        }
        return ktr;
    }

    public Ray constructReflectedRay(Ray ray, Point point, Vector normal) {
         Vector r = ray.getDir().subtract(normal.scale(2*normal.dotProduct(ray.getDir())));
         return new Ray(point, r, normal);
    }

    public Ray constructRefractedRay(Point point, Ray ray, Vector normal) {
        return new Ray(point, ray.getDir(), normal);
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> geoPointList = scene.geometries.findGeoIntersections(ray);
        if (geoPointList == null) {
            return null;
        }
        return ray.findClosestGeoPoint(geoPointList);
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point).normalize();
        Material material = gp.geometry.getMaterial();
        //Double3 kr = gp.geometry.getMaterial().kR;
        Double3 kkr = k.product(material.kR);
        Double3 kkt = k.product(material.kT);

        if (!(kkr.lowerThan(MIN_CALC_COLOR_K))) {
            color = color.add(calcGlobalEffects(constructReflectedRay(ray, gp.point,n), level, material.kR, kkr));
        }

        if (!(kkt.lowerThan(MIN_CALC_COLOR_K))) {
            color = color.add(calcGlobalEffects(constructRefractedRay(gp.point,ray,n), level, material.kT, kkt));
        }
        return color;
    }

    private Color calcGlobalEffects(Ray ray, int level, Double3 kx, Double3 kkx ) {
        GeoPoint gp = findClosestIntersection(ray);
        return(gp == null ? scene.background : calcColor(gp, ray, level-1, kkx).scale(kx));
    }


}