package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Testing Triangles
 * @author Michal and Adina */
class TriangleTests {

    /**

    Tests the {@link Triangle#findIntersections(Ray)} method.
    */
    @Test
    void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(0,0,3), new Point(-2,0,0), new Point(0,3,0));
        // ====== Equivalence Partitions Test ======
        // TC1 : point is outside the triangle next to a side
        Ray ray = new Ray(new Point(0,-1,1), new Vector(-2, 1, -2));
        assertEquals(null, triangle.findIntersections(ray), "TC1 of EP triangle test failed.");

        // TC2 : point is outside the triangle, in between the lines shooting out from vertices
        ray = new Ray(new Point(-1,2,1), new Vector(-1,-1,-1));
        assertEquals(null, triangle.findIntersections(ray), "TC2 of EP triangle test failed.");

        // TC3 : point is inside the triangle
        triangle = new Triangle(new Point(1, 0, 0), new Point(2, 0, 0), new Point(1, -2, 0));
        ray = new Ray(new Point(1.5, -1, -2), new Vector(0, 1, 4).normalize());
        List<Point> result = triangle.findIntersections(ray);
        assertEquals(1, result.size(), "There should be 1 intersection point.");
        assertEquals(List.of(new Point(1.5, -0.5, 0)), result, "TC3 of EP triangle failed.");

        // ====== Boundary Value Analysis ======
        // TC1 : point is found on a line extending from a vertex of the triangle
        ray = new Ray(new Point(0, 5, 10), new Vector(0, 0, -1));
        assertEquals(null, triangle.findIntersections(ray), "TC1 of BVA triangle failed.");

        // TC2 : point is found on an edge of the triangle
        ray = new Ray(new Point(5, 5, 10), new Vector(0, 0, -1));
        assertEquals(null, triangle.findIntersections(ray), "TC2 of BVA triangle failed.");

        // TC3 : point is found at a vertex
        ray = new Ray(new Point(0, 5, 10), new Vector(0, 0, -1));
        assertEquals(null, triangle.findIntersections(ray), "TC3 of BVA triangle failed.");
    }
}