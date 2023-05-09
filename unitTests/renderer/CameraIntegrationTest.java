package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Vector;
import primitives.Point;
import geometries.Sphere;
import primitives.Ray;
import renderer.Camera;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * class created in order to check integration between creating rays form the camera and calculating
 * intersections between rays and geometric bodies.
 * Three test functions are included (Integration of the sphere, plane, and triangle)
 */
public class CameraIntegrationTest {
    private int countIntersections3(Camera camera, Intersectable inters) {
        camera.setVPSize(3, 3).setVPDistance(1);
        List<Point> result = null;
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                var intersection = inters.findIntersections(camera.constructRay(3, 3, j, i));
                if (intersection != null) {
                    if (result == null) {
                        result = new LinkedList();
                    }
                    result.addAll(intersection);
                }
                sum += intersection == null ? 0 : intersection.size();

            }
        }
        return sum;
    }

    @Test
    void testSphereIntegration() {
        // TC1: Through the center pixel
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -2), new Vector(0, 1, 0)).setVPDistance(1);
        Sphere sphere = new Sphere(1d, new Point(0, 0, -3));
        Ray ray = camera.constructRay(3, 3, 1, 1);
        List<Point> result = sphere.findIntersections(ray);
        assertEquals(2, result.size(), "Sphere TC1 failed.");

        // TC2: Big sphere, 18 intersection points
        camera = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3, 3);
        sphere = new Sphere(2.5d, new Point(0, 0, -2.5));
        assertEquals(18, countIntersections3(camera, sphere), "TC2 failed.");

        // TC3: Medium sphere, 10 intersection points
        sphere = new Sphere(2d, new Point(0,0,-2));
        assertEquals(10, countIntersections3(camera,sphere), "TC3 failed.");

        // TC4: Circle bigger than view plane,9 intersection points
        sphere = new Sphere(4d, new Point(0, 0, -0.5));
        assertEquals(9, countIntersections3(camera, sphere), "TC4 failed.");

        // TC5: Sphere on other side of view plane, 0 intersection points
        sphere = new Sphere(0.5d, new Point(0, 0, 1));
        assertEquals(0, countIntersections3(camera, sphere), "TC2 failed.");
    }

    @Test
    void testPlaneIntegration() {
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -2), new Vector(0, 1, 0)).setVPDistance(1);

        // TC1: Plane is parallel, 9 intersection points
        Plane plane = new Plane(new Point(0, 0, -3), new Vector(0, 0, -2));
        assertEquals(9, countIntersections3(camera, plane), "TC1 failed.");

        // TC2: Plane is on a small slant compared to the view plane, 9 intersection points
        plane = new Plane(new Point(20.82, -21.62, 0), new Point(0,0,-7.74), new Point(-3.1, -11.37, 0));
        assertEquals(9, countIntersections3(camera, plane), "TC2 failed.");

        // TC3: Plane is on a big slant compared to the view plane, 6 intersection points
        plane = new Plane(new Point(0,0,-5), new Vector(0,1,1));
        assertEquals(6, countIntersections3(camera, plane), "TC3 failed.");
    }

    @Test
    void testTriangleIntegration() {
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -2), new Vector(0, 1, 0)).setVPDistance(1);

        // TC1: Small triangle right in front of view plane, 1 intersection point
        Triangle triangle = new Triangle(new Point(0,1,-2), new Point(1,-1,-2), new Point(-1,-1,-2));
        assertEquals(1, countIntersections3(camera, triangle), "TC1 failed.");

        // TC2: Thin and tall triangle, 2 intersection points
        triangle = new Triangle(new Point(0,20,-2), new Point(1,-1,-2), new Point(-1,-1,-2));
        assertEquals(2, countIntersections3(camera, triangle), "TC2 failed.");

    }
}
