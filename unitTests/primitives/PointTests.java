package primitives;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Testing Points
 * @author Michal and Adina */
class PointTests {
// he copied code into Shira's code - minute 53
    Point p = new Point(1,1,1);
    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared1() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(14d, new Point(1, 1, 1).distanceSquared(new Point(2, 3, 4)), 0.00001,  //
                "Wrong squared distance between the point and itself");
    }
    @Test
    void testDistanceSquared2() {
        // =============== Boundary Values Tests ==================
        // TC11: test distance with the same point
        assertEquals(0d, new Point(1, 2, 3).distanceSquared(new Point(1, 2, 3)), 0.0001, //
                "Wrong squared distance between the point and itself");
    }

    @Test
    @Disabled
    void testAllDistance() {
        testDistanceSquared1();
        testDistanceSquared1();
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(Math.sqrt(14), new Point(1, 1, 1).distance(new Point(2, 3, 4)), 0.0001, //
                "Wrong distance between the point and itself");

        // =============== Boundary Values Tests ==================
        // TC11: test distance with the same point
        assertEquals(0d, new Point(1, 2, 3).distance(new Point(1, 2, 3)), 0.0001, //
                "Wrong distance between the point and itself");
    }

    @Test
    void testAdd() {
        // ====== Equivalence Partition Tests ======
        assertEquals(new Point(3,2,1), p.add(new Vector(2,1,0)), "Add test failed.");
    }

    @Test
    void testSubtract1() {
        // ====== Equivalence Partition Tests ======
        // Simple Test
        assertEquals(new Vector(0,-1,4),p.subtract(new Point(1,2,-3)), "Subtract test failed.");
        // ====== Boundary Value Tests ======
        // Subtracting the same point from itself
        assertThrows(IllegalArgumentException.class, () -> new Point(3,2,1).subtract(new Point(3,2,1)), "Having a point of (0,0,0) must throw an exception");
        // !! ask about this!!
    }
    @Test
    void testSubtract2() {
        // want this test to pass because the test means that an exception is thrown.
        assertThrows(IllegalArgumentException.class, () -> p.subtract(p), "Vector(0,0,0) is not legal");
    }
}