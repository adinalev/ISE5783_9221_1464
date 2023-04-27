package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**

 This class contains JUnit tests for the Geometries class.
 The Geometries class represents a collection of geometric bodies and provides a method for finding intersections
 between a given ray and the collection of bodies.
 @Author Michal and Adina (with the help of chatgpt)
 */
public class GeometriesTests {

    /**

     Tests the {@link Geometries#findIntersections(Ray)} method.

     The tests include equivalence partition tests and boundary value analysis tests.
     */
    @Test
    void testFindIntersections() {
        // ====== Equivalence Partition Tests ======
        // TC1 : the collection is empty
        Ray ray = new Ray(new Point(0, 0, 2), new Vector(4, 0, 2));
        Geometries geom = new Geometries();
        assertEquals(null, geom.findIntersections(ray), "TC1 failed.");

        // TC2 : no geometric body is intersected
        Sphere sphere = new Sphere(1d, new Point(0,0,1));
        Plane plane = new Plane(new Point(-3,1,0), new Point(-4.18, 7, 0), new Point(-5.67, 5.27, 0));
        Triangle triangle = new Triangle(new Point(0,0,1), new Point(-2, 7, 0), new Point(-1.38, 2.45, 0));
        ray = new Ray(new Point(0,0,2), new Vector(4,0,2));
        Geometries geom1 = new Geometries(sphere, plane, triangle);
        assertEquals(null, geom1.findIntersections(ray), "TC1 failed.");

        // TC3: Only one geometric body is intersected
        Sphere sphere1 = new Sphere(1d, new Point(0,0,1));
        Triangle triangle1 = new Triangle(new Point(-6,0,0), new Point(0, -6, 0), new Point(-3,0,0));
        Geometries geom2 = new Geometries(sphere1, triangle1);
        ray = new Ray(new Point(0, 0, -1), new Vector(0, 0, 5).normalize());
        List<Point> result = geom2.findIntersections(ray);
        assertEquals(2, result.size(), "TC1 failed.");

        // TC4 : All the geometric bodies were intersected
        plane = new Plane(new Point(0, 3, -7), new Point(1, 3, 3), new Point(2, 3, 4));
        sphere = new Sphere(2, new Point(0, 0, 2));
        ray = new Ray(new Point(0, -5, 2), new Vector(0, 1, 0));
        Geometries geom4 = new Geometries(plane, sphere);
        result = geom4.findIntersections(ray);
        if (result.get(1).getXyzd2() < result.get(2).getXyzd2())
            result = List.of(result.get(0), result.get(2), result.get(1));
        assertEquals(List.of(new Point(0, 3, 2), new Point(0, 2, 2), new Point(0, -2, 2)), result, "TC5 failed.");

        // ====== Boundary Value Analysis ======

        // TC5: Some, but not all, geometric bodies were intersected
        plane = new Plane(new Point(0, 3, -7), new Point(1, 3, 3), new Point(2, 3, 4));
        sphere = new Sphere(2, new Point(0, 0, 2));
        Sphere sphere2 = new Sphere(2, new Point(100, 100, 100));
        ray = new Ray(new Point(0, -5, 2), new Vector(0, 1, 0));
        Geometries geom3 = new Geometries(plane, sphere, sphere2);
        result = geom3.findIntersections(ray);
        if (result.get(1).getXyzd2() < result.get(2).getXyzd2())
            result = List.of(result.get(0), result.get(2), result.get(1));
        assertEquals(List.of(new Point(0, 3, 2), new Point(0, 2, 2), new Point(0, -2, 2)), result, "TC5 failed.");
    }
}
