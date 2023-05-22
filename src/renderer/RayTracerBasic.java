package renderer;

import elements.LightSource;
import geometries.Intersectable;
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
        List<GeoPoint> result = scene.geometries.findGeoIntersections(ray);

        // if there are no intersection points, return the background color of the scene
        if (result == null) {
            return scene.background;
        }

        // call the method to find the closest point to the head of the ray
        GeoPoint closest = ray.findClosestGeoPoint(result);
        // calculate the color of that point
        Color color = calcColor(closest, ray);
        // return the color found
        return color;
    }

    /**
     * Calculates the color of the point using the ambient
     * light intensity of the scene
     * @param gp point that's color is being calculated
     * @return the Color of that point
     */
    public Color calcColor(GeoPoint gp, Ray ray) {
        //gp.geometry.getIntensity(gp.point);
        return scene.ambientLight.getIntensity().add(calcLocalEffects(gp, ray));
    }

    /*private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir (); Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v)); if (nv == 0) return color;
        Material mat = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(mat, nl)),
                        iL.scale(calcSpecular(mat, n, l, nl, v));
            }
        }
        return color;
    }*/
    /**

     Calculates the local effects (diffuse and specular) of a given geometric point on a ray.

     @param gp The geometric point at which to calculate the local effects.

     @param ray The ray being traced.

     @return The color resulting from the local effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = Util.alignZero(n.dotProduct(v));
        int nShininess = gp.geometry.getMaterial().nShininess;
        Double3 kd = gp.geometry.getMaterial().kD;
        Double3 ks = gp.geometry.getMaterial().kS;

        Color color = new Color(BLACK);
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if(nl*nv > 0 ) {
                Color intensity = lightSource.getIntensity(gp.point);
                color = color.add(calcDiffusive(kd,l,n,intensity), calcSpecular(ks,l,n,v,nShininess,intensity));
            }
        }
        return color;
    }

    /*private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl, Vector v) {
        Vector nln = n.scale(2 * nl);
        Vector fin = l.subtract(nln);
        Double3 r = new Double3(fin.getX(), fin.getY(), fin.getZ());
        return r;
    }*/

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
}
