package geometries;

import org.junit.jupiter.api.Test;
import geometries.Plane;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Testing Planes
 * @author Michal and Adina */
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

    /**

     Test method for {@link geometries.Plane#findIntersections(Ray)}.
     This method tests the intersection points between a given ray and a plane. It uses both Equivalence Partition Tests and Boundary Value Analysis.
     @see geometries.Plane
     */
    @Test
    void testFindIntersections(){
        Plane plane = new Plane(new Point(0,3,-7), new Point(1,3,3), new Point(2,3,4));

        // ====== Equivalence Partition Tests ======
        // Ray is not orthogonal nor parallel to the plane

        // TC1: Ray intersects plane
        Ray ray = new Ray(new Point(0,0,-5), new Vector(0,1,1));
        assertEquals(List.of(new Point(0,3,-2)), plane.findIntersections(ray), "TC1 failed in EP Plane findIntersections.");

        // TC2 : Ray does not intersect the plane
        ray = new Ray(new Point(0,0,-5), new Vector(0,-1,1));
        assertEquals(null, plane.findIntersections(ray), "TC2 does not work in EP Plane findIntersections.");

        // ====== Boundary Value Analysis ======
        // TC1 : Ray is parallel to the plane and included in the plane
        ray = new Ray(new Point(0,3,0), new Vector(0,0,1));
        assertEquals(null, plane.findIntersections(ray), "TC1 does not work in BVA Plane findIntersections.");

        //TC2 : Ray is parallel to the plane and not included in the plane
        Plane plane2 = new Plane(new Point(-4, 4, 1), new Vector(-2, -2, -1));
        ray = new Ray(new Point(1,2,3), new Vector(4,2.5,4));
        assertEquals(null, plane2.findIntersections(ray), "TC2 does not work in BVA Plane findIntersections.");

        // Ray is orthogonal to the plane
        // TC3 : Ray is orthogonal to the plane and before the plane
        ray = new Ray(new Point(0,0,-5), new Vector(0,1,0));
        assertEquals(List.of(new Point(0,3,-5)), plane.findIntersections(ray), "TC3 does not work in BVA plane findIntersections.");

        // TC4 : Ray is orthogonal to the plane and in the plane
        ray = new Ray(new Point(0,3,-5), new Vector(0,-1,0));
        assertEquals(null, plane.findIntersections(ray), "TC4 does not work in BVA plane findIntersections.");

        // TC5: Ray is orthogonal to the plane and after the plane
        ray = new Ray(new Point(0, 0, -5), new Vector(0, -1, 0));
        assertEquals(null, plane.findIntersections(ray), "TC5 does not work in BVA plane findIntersections.");

        // TC6 : Ray is neither orthogonal nor parallel to and begins at the plane
        ray = new Ray(new Point(0, 3, -5), new Vector(0, 1, 1));
        assertEquals(null, plane.findIntersections(ray), "TC6 does not work in BVA plane findIntersections.");

        // TC7 : Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane (Q)
        ray = new Ray(new Point(0, 3, -7), new Vector(0, 1, 1));
        assertEquals(null, plane.findIntersections(ray), "TC7 does not work in BVA plane findIntersections.");
    }
}