package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Vector;
class SphereTests {

    @Test
    void testGetNormal() {
        // ====== Equivalence Partition Test ======
        Sphere sphere = new Sphere(5.0, new Point(0,0,0));
        assertEquals(new Vector(1,0,0), sphere.getNormal(new Point(5,0,0)), "Sphere getNormal failed.");
    }
}