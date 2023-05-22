package primitives;


/**

 Represents the material properties of a geometric object.
 */
public class Material {
    public Double3 kD = new Double3(0, 0, 0);
    public Double3 kS = new Double3(0, 0, 0);
    public int nShininess = 0;

    /**

     Sets the diffuse reflection coefficient of the material.
     @param kD The diffuse reflection coefficient as a Double3 representing the RGB values.
     @return The Material object for method chaining.
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }
    /**

     Sets the diffuse reflection coefficient of the material.
     @param kD The diffuse reflection coefficient as a double value for all RGB components.
     @return The Material object for method chaining.
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
    /**

     Sets the specular reflection coefficient of the material.
     @param kS The specular reflection coefficient as a Double3 representing the RGB values.
     @return The Material object for method chaining.
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }
    /**

     Sets the specular reflection coefficient of the material.
     @param kS The specular reflection coefficient as a double value for all RGB components.
     @return The Material object for method chaining.
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }
    /**

     Sets the shininess factor of the material.
     @param nShininess The shininess factor.
     @return The Material object for method chaining.
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}

