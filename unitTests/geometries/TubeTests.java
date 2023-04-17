package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;
class TubeTests {

    @Test
    void testGetNormal() {
        Ray ray = new Ray(new Point(0,0,0), new Vector(1,0,0));
        Tube tube = new Tube(5.0, ray);

        // ====== Equivalence Partition Test ======
        Vector expected_EP = new Vector(0,1,0);
        Vector actual_EP = tube.getNormal(new Point(5,5,0));
        assertEquals(expected_EP, actual_EP, "Tube normal EP test failed");

        // ====== Boundary Value Analysis Test ======
        Vector expected_BVA = new Vector(0,1,0);
        Vector actual_BVA = tube.getNormal(new Point(5,5,0));
        assertEquals(expected_BVA, actual_BVA, "Tube normal BVA test failed");
    }
}