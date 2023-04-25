package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/** Testing Spheres
 * @author Michal and Adina */
class SphereTests {

    @Test
    void testGetNormal() {
        // ====== Equivalence Partition Test ======
        Sphere sphere = new Sphere(5.0, new Point(0, 0, 0));
        assertEquals(new Vector(1, 0, 0), sphere.getNormal(new Point(5, 0, 0)), "Sphere getNormal failed.");
    }

    /**

     Tests the {@link Sphere#findIntersections(Ray)} method for various scenarios.
     */
    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point(1, 0, 0));

        // ====== Equivalence Partition Tests ======
        // TC1 : Ray line falls outside the sphere
        Ray r1 = new Ray(new Point(-2, 0, 0), new Vector(2, 2, 0));
        assertEquals(null, sphere.findIntersections(r1), "TC1 in sphere EP tests failed.");

        // TC2 : Ray starts before the sphere and crosses it twice
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "There should be 2 points.");
        if (result.get(0).getXyzd1() > result.get(1).getXyzd1())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "TC1 in sphere EP tests failed.");

        // TC3 : Ray starts inside the sphere and intersects the sphere once
        result = sphere.findIntersections(new Ray(new Point(0.5,0,0), new Vector(1,1,1)));
        assertEquals(1, result.size(), "There should be 1 intersection point.");
        assertEquals(List.of(new Point(1.1937129433613967, 0.6937129433613968, 0.6937129433613968)), result, "TC3 in sphere EP tests failed.");


        // TC4 : Ray starts after the sphere and does not intersect the sphere
        assertEquals(null, sphere.findIntersections(new Ray(new Point(2, 2, 0), new Vector(1, 1, 0))), "TC4 in sphere EP tests failed.");

        // ====== Boundary Value Analysis ======

        // TC1 : Ray starts at the edge of the sphere and goes outwards (0 intersections)
        assertEquals(null, sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 1, 0))), "TC1 in sphere BVA tests failed.");

        // TC2 : Ray head is on the edge of the sphere, it goes through the center of the circle (1 intersection)
        sphere = new Sphere(1d, new Point(0, 0, 1));
        result = sphere.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 0, -2)));
        assertEquals(1, result.size(), "There is supposed to be one intersection point.");
        assertEquals(List.of(new Point(0,0,0)), result, "TC2 in sphere BVA tests failed.");

        // TC3 : Ray starts outside the sphere and shoots tangent to the sphere (0 intersection points)
        sphere = new Sphere(1d, new Point(1, 0, 0));
        assertEquals(null, sphere.findIntersections(new Ray(new Point(-2, 0, 0), new Vector(2, 2, 0))), "TC3 in sphere BVA tests failed.");

        // TC4 : Ray starts on the sphere edge and is on the tangent line (0 intersections)
        assertEquals(null, sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 0, 1))), "TC4 in sphere BVA tests failed.");

        // TC5 : Ray is on tangent line but starts after it passes the edge of the sphere (0 intersections)
        assertEquals(null, sphere.findIntersections(new Ray(new Point(2, 0, 1), new Vector(0, 0, 1))), "TC5 in sphere BVA tests failed.");

        // TC6 : Ray starts at the edge of the sphere and goes inwards (1 intersection point)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0).normalize()));
        assertEquals(1, result.size(), "There should be one intersection point.");
        assertEquals(List.of(new Point(2,0,0)), result, "TC6 in sphere BVA tests failed.");

        // TC7: Ray starts at the edge of the sphere, and goes outwards opposite from the center (0 intersection points)
        sphere = new Sphere(1d, new Point(0, 0, 1));
        assertEquals(null, sphere.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 0, 2.39))), "TC7 in sphere BVA tests failed.");

        // TC8 : Ray starts at the center and shoots outwards (1 intersection)
        result = sphere.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 0, 1).normalize()));
        assertEquals(1, result.size(), "There should be one intersection point.");
        assertEquals(List.of(new Point(0, 0, 2)), result, "TC8 in sphere BVA tests failed.");

        // TC9 : Ray starts outside the sphere and falls on the line extending from the center (0 intersection points)
        assertEquals(null, sphere.findIntersections(new Ray(new Point(0, 0, 3), new Vector(0, 0, 2.5))), "TC9 in sphere BVA tests failed.");

        // TC10 : Ray starts after the center and falls on the line extending from the center (1 intersection)
        result = sphere.findIntersections(new Ray(new Point(0, 0, 1.46), new Vector(0, 0, 1.54).normalize()));
        assertEquals(1, result.size(), "There should be one intersection point.");
        assertEquals(List.of(new Point(0, 0, 2)), result, "TC10 in sphere BVA tests failed.");

        // TC11 : ray starts before the sphere and goes through the center (2 intersection points)
        result = sphere.findIntersections(new Ray(new Point(0, 0, -1), new Vector(0, 0, 5).normalize()));
        assertEquals(2, result.size(), "There should be two intersection points.");
        assertEquals(List.of(new Point(0, 0, 0), new Point(0,0,2)), result, "TC3 in sphere EP tests failed.");
    }
}