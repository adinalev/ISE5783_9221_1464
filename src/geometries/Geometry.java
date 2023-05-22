package geometries;
import primitives.Color;
import primitives.Material;
import primitives.Vector;
import primitives.Point;

/**

 The Geometry interface represents a geometric shape in 3D space.
 * @author Michal and Adina (with help of chatGPT)
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * Gets the material of this geometry shape.
     *
     * @return The material of the geometry shape.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material of this geometry shape.
     *
     * @param material The material to set.
     * @return The updated Geometry object for method chaining.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Returns the normal vector to this geometry shape at the specified point.
     *
     * @param point The point on this geometry shape at which to calculate the normal vector.
     * @return A vector representing the normal to this geometry shape at the specified point.
     */
    public abstract Vector getNormal(Point point);

    /**
     * Gets the emission color of this geometry shape.
     *
     * @return The emission color of the geometry shape.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of this geometry shape.
     *
     * @param emission The emission color to set.
     * @return The updated Geometry object for method chaining.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
}