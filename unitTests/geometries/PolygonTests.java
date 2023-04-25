package geometries;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/** Testing Polygons
 * @author Dan */
public class PolygonTests {

   @Test
   public void testConstructor() {
      // ============ Equivalence Partitions Tests ==============

      // TC01: Correct concave quadrangular with vertices in correct order
      try {
         new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
      } catch (IllegalArgumentException e) {
         fail("Failed constructing a correct polygon");
      }

      // TC02: Wrong vertices order
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                   "Constructed a polygon with wrong order of vertices");

      // TC03: Not in the same plane
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                   "Constructed a polygon with vertices that are not in the same plane");

      // TC04: Concave quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0.5, 0.25, 0.5)), //
                   "Constructed a concave polygon");

      // =============== Boundary Values Tests ==================

      // TC10: Vertex on a side of a quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0, 0.5, 0.5)),
                   "Constructed a polygon with vertix on a side");

      // TC11: Last point = first point
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                   "Constructed a polygon with vertice on a side");

      // TC12: Co-located points
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                   "Constructed a polygon with vertice on a side");

   }

   /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
   @Test
   public void testGetNormal() {
      // ============ Equivalence Partitions Tests ==============
      // TC01: There is a simple single test here - using a quad
      Point[] pts =
         { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
      Polygon pol = new Polygon(pts);
      // ensure there are no exceptions
      assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
      // generate the test result
      Vector result = pol.getNormal(new Point(0, 0, 1));
      // ensure |result| = 1
      assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
      // ensure the result is orthogonal to all the edges
      for (int i = 0; i < 3; ++i)
         assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
                    "Polygon's normal is not orthogonal to one of the edges");
   }
   /**

    Test case for the {@link Polygon#findIntersections(Ray)} method.
    */
   @Test
   void testFindIntersections() {
      Polygon polygon = new Polygon(
              new Point(1, 0, 0),
              new Point(0, 1, 0),
              new Point(-2, 0, 0),
              new Point(0,-1,0)
      );
      List<Point> result;

      // ====== Equivalence Partitions Tests ======

      //TC1: Ray is outside the polygon, and against the vertex
      assertEquals(null, polygon.findIntersections(new Ray(new Point(0, -2, 0), new Vector(0, 0, 4))), "TC2 in Polygon EP failed.");

      //TC2: Ray is outside the polygon, and against the edge
      assertEquals(null, polygon.findIntersections(new Ray(new Point(-1, -1, 0), new Vector(0, 0, 3))), "TC3 in Polygon EP failed.");

      //TC3: Ray inside the polygon
      assertEquals(null,polygon.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-1, 0, 0))), "TC4 in Polygon EP failed.");

      // ====== Boundary Values Tests ======

      //TC1: Ray is on the edge of the polygon
      result = polygon.findIntersections(new Ray(new Point(-2, 0, 3), new Vector(1.03d, 0.51d, -3)));
      assertEquals(1, result.size(), "There is supposed to be 1 intersection point");
      assertEquals(new Point(-0.97d, 0.51d, 0d), result.get(0), "TC1 in Polygon BVA failed.");

      ///TC2: Ray in vertex
      assertEquals(null, polygon.findIntersections(new Ray(new Point(0, 1, 0), new Vector(-2d, -1d, 3))),  "TC2 in Polygon BVA failed.");

      //TC3: Ray is found on the continuation of the edge.
      assertEquals(null, polygon.findIntersections(new Ray(new Point(-1, 2, 0), new Vector(-1d, -2d, 3))), "TC3 in Polygon BVA failed.");
   }
}
