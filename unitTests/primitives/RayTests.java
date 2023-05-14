package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RayTests {
    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(-2,1,0), new Vector(1,0,0));
        Point closest = new Point(1,1,0);
        Point p1 = new Point(2,1,0);
        Point p2 = new Point(3,1,0);

        // ****** Equivalence Partition Tests ******

        // TC01: The closest point to the ray's head is found somewhere in the middle of the list
        assertEquals(closest, ray.findClosestPoint(List.of(p1, closest, p2)), "TC01 failed.");

        // ****** Boundary Value Analysis Tests ******

        // TC11: An empty list
        assertEquals(null, ray.findClosestPoint(List.of()), "TC11 failed.");
        // TC12: A list where the closest point is the first point in the list
        assertEquals(closest, ray.findClosestPoint(List.of(closest, p1, p2)), "TC12 failed.");
        //TC13: A list where the closest point is the last point in the list
        assertEquals(closest, ray.findClosestPoint(List.of(p1, p2, closest)), "TC01 failed.");
    }
}
