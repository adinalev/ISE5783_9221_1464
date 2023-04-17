package geometries;

import org.junit.jupiter.api.Test;
import geometries.Plane;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {
    @Test
    void testGetNormal() {
        // ====== Equivalence partition Tests ======
        Point p1 = new Point(1,0,0);
        Point p2 = new Point(0,1,0);
        Point p3 = new Point(0,0,1);
        Plane plane = new Plane(p1, p2, p3);
        double value = Math.sqrt(1d / 3);
        Vector expected = new Vector(value, value, value);
        Vector actual = plane.getNormal(new Point(1,0,0));
        assertEquals(expected, actual, "Plane getNormal failed.");
        assertEquals(1, expected.length(), "Vector is not normalized");
    }

    @Test
    void testConstructor() {
        // ====== Boundary Value Analysis Tests ======
        // TC1: first and second points are equal
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1,2,3), new Point(1,2,3), new Point(2,3,4)), "First and second points cannot be equal.");
        //TC2: points are on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1,2,3), new Point(2,4,6), new Point(3,6,9)), "The points are on the same line.");
    }
}