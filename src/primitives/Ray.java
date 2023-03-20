/**

 Represents a ray in 3D space, defined by a starting point and a direction vector.
 @author Michal and Adina (with help of chatGPT)
 */
package primitives;
import java.util.Objects;

public class Ray {
    /**
     * The starting point of the ray.
     */
    protected final Point p0;

    /**
     * The direction vector of the ray.
     */
    protected final Vector dir;

    /**
     * Creates a new Ray with the specified starting point and direction vector.
     * The direction vector is normalized to have a length of 1.
     * @param p0 The starting point of the ray.
     * @param dir The direction vector of the ray.
     */
    public Ray(Point p0, Vector dir) {
        dir.normalize(); // make sure this is ok with LoD!!!
        this.p0 = p0;
        this.dir = dir;
    }

    /**
     * Returns the starting point of the ray.
     * @return The starting point of the ray.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction vector of the ray.
     * @return The direction vector of the ray.
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Checks if this ray is equal to the specified object.
     * Two rays are equal if their starting points and direction vectors are equal.
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    /**
     * Computes the hash code of this ray.
     * @return The hash code of this ray.
     */
    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    /**
     * Returns a string representation of this ray.
     * @return A string representation of this ray.
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

}