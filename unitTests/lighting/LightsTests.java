//package lighting;
//
//import static java.awt.Color.*;
//
//import org.junit.jupiter.api.Test;
//
//import geometries.*;
//import primitives.*;
//import renderer.*;
//import scene.Scene;
//
///** Test rendering a basic image
// * @author Dan */
//public class LightsTests {
//   private final Scene scene1 = new Scene("Test scene");
//   private final Scene scene2 = new Scene("Test scene")
//      .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
//
//   private final Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
//      .setVPSize(150, 150).setVPDistance(1000);
//   private final Camera camera2  = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
//      .setVPSize(200, 200).setVPDistance(1000);
//
//   private static final int SHININESS = 301;
//   private static final double  KD = 0.5;
//   private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);
//
//   private static final double  KS = 0.5;
//   private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
//
//   private final Material material = new Material().setKd(KD3).setKs(KS3).setShininess(SHININESS);
//   private final Color trianglesLightColor = new Color(800, 500, 250);
//   private final Color sphereLightColor = new Color(800, 500, 0);
//   private final Color sphereColor = new Color(BLUE).reduce(2);
//
//   private final Point sphereCenter = new Point(0, 0, -50);
//   private static final double SPHERE_RADIUS = 50d;
//
//   // The triangles' vertices:
//   private final Point[] vertices                =
//      {
//        // the shared left-bottom:
//        new Point(-110, -110, -150),
//        // the shared right-top:
//        new Point(95, 100, -150),
//        // the right-bottom
//        new Point(110, -110, -150),
//        // the left-top
//        new Point(-75, 78, 100)
//      };
//   private final Point sphereLightPosition = new Point(-50, -50, 25);
//   private final Point trianglesLightPosition = new Point(30, 10, -100);
//   private final Vector trianglesLightDirection = new Vector(-2, -2, -2);
//
//   private final Geometry sphere = new Sphere(SPHERE_RADIUS, sphereCenter)
//      .setEmission(sphereColor).setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
//   private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
//      .setMaterial(material);
//   private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
//      .setMaterial(material);
//
//   /** Produce a picture of a sphere lighted by a directional light */
//   @Test
//   public void sphereDirectional() {
//      scene1.geometries.add(sphere);
//      scene1.lights.add(new DirectionalLight(sphereLightColor, new Vector(1, 1, -0.5)));
//
//      ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
//      camera1.setImageWriter(imageWriter) //
//         .setRayTracer(new RayTracerBasic(scene1)) //
//         .renderImage() //
//         .writeToImage(); //
//   }
//
//   /** Produce a picture of a sphere lighted by a point light */
//   @Test
//   public void spherePoint() {
//      scene1.geometries.add(sphere);
//      scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
//         .setKl(0.001).setKq(0.0002));
//
//      ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
//      camera1.setImageWriter(imageWriter) //
//         .setRayTracer(new RayTracerBasic(scene1)) //
//         .renderImage() //
//         .writeToImage(); //
//   }
//
//   /** Produce a picture of a sphere lighted by a spotlight */
//   @Test
//   public void sphereSpot() {
//      scene1.geometries.add(sphere);
//      scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
//         .setKl(0.001).setKq(0.0001));
//
//      ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
//      camera1.setImageWriter(imageWriter) //
//         .setRayTracer(new RayTracerBasic(scene1)) //
//         .renderImage() //
//         .writeToImage(); //
//   }
//
//   /** Produce a picture of two triangles lighted by a directional light */
//   @Test
//   public void trianglesDirectional() {
//      scene2.geometries.add(triangle1, triangle2);
//      scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));
//
//      ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
//      camera2.setImageWriter(imageWriter) //
//         .setRayTracer(new RayTracerBasic(scene2)) //
//         .renderImage() //
//         .writeToImage(); //
//   }
//
//   /** Produce a picture of two triangles lighted by a point light */
//   @Test
//   public void trianglesPoint() {
//      scene2.geometries.add(triangle1, triangle2);
//      scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
//         .setKl(0.001).setKq(0.0002));
//
//      ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
//      camera2.setImageWriter(imageWriter) //
//         .setRayTracer(new RayTracerBasic(scene2)) //
//         .renderImage() //
//         .writeToImage(); //
//   }
//
//   /** Produce a picture of two triangles lighted by a spotlight */
//   @Test
//   public void trianglesSpot() {
//      scene2.geometries.add(triangle1, triangle2);
//      scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
//         .setKl(0.001).setKq(0.0001));
//
//      ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
//      camera2.setImageWriter(imageWriter) //
//         .setRayTracer(new RayTracerBasic(scene2)) //
//         .renderImage() //
//         .writeToImage(); //
//   }
//
//   /**
//
//    Test case for rendering a scene with a sphere and multiple lights.
//    Adds a sphere to the scene and sets up multiple lights, including a directional light, a spot light, and a point light.
//    Renders the image using a basic ray tracer and writes it to an image file.
//    */
//   @Test
//   void testSphereMultipleLights() {
//      scene1.geometries.add(sphere);
//      scene1.lights.add(new DirectionalLight(sphereLightColor, new Vector(1, 1, -0.5)));
//      scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
//              .setKl(0.005).setKq(0.0003));
//      scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
//              .setKl(0.005).setKq(0.0003));
//
//      ImageWriter imageWriter = new ImageWriter("lightSphereMany", 500, 500);
//      camera1.setImageWriter(imageWriter) //
//              .setRayTracer(new RayTracerBasic(scene1)) //
//              .renderImage() //
//              .writeToImage(); //
//   }
//
//   /**
//
//    Test case for rendering a scene with multiple triangles and multiple lights.
//    Adds multiple triangles to the scene and sets up multiple lights, including a point light, a spot light, and a directional light.
//    Renders the image using a basic ray tracer and writes it to an image file.
//    */
//   @Test
//   void testTrianglesMultipleLights() {
//      scene2.geometries.add(triangle1, triangle2);
//
//      scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
//              .setKl(0.001).setKq(0.02));
//      scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
//              .setKl(0.01).setKq(0.001));
//      scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));
//
//      ImageWriter imageWriter = new ImageWriter("MultipleLightsTriangle", 500, 500);
//      camera2.setImageWriter(imageWriter) //
//              .setRayTracer(new RayTracerBasic(scene2)) //
//              .renderImage() //
//              .writeToImage(); //
//   }
//}

package lighting;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
import static java.awt.Color.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
   private Scene scene1 = new Scene.SceneBuilder("Test scene")
           .build();
   private Scene scene2 = new Scene.SceneBuilder("Test scene") //
           .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)))
           .build();
   private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
           .setVPSize(150, 150) //
           .setVPDistance(1000);
   private Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
           .setVPSize(200, 200) //
           .setVPDistance(1000);

   private Point[] p = { // The Triangles' vertices:
           new Point(-110, -110, -150), // the shared left-bottom
           new Point(95, 100, -150), // the shared right-top
           new Point(110, -110, -150), // the right-bottom
           new Point(-75, 78, 100) }; // the left-top
   private Point trPL = new Point(30, 10, -100); // Triangles test Position of Light
   private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
   private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
   private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
   private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
   private Material material = new Material()
           .setKd(0.5)
           .setKs(0.5)
           .setShininess(300);

   private Geometry triangle1 = new Triangle(p[0], p[1], p[2]).setMaterial(material);
   private Geometry triangle2 = new Triangle(p[0], p[1], p[3]).setMaterial(material);
   private Geometry sphere = new Sphere(50d, new Point(0, 0, -50)) //
           .setEmission(new Color(BLUE).reduce(2)) //
           .setMaterial(material);

   /**
    * Produce a picture of a sphere lighted by a directional light
    */
   @Test
   public void sphereDirectional() {
      scene1.getGeometries().add(sphere);
      scene1.getLights().add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));

      ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
      camera1.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene1)) //
              .renderImage() //
              .writeToImage(); //
   }

   /**
    * Produce a picture of a sphere lighted by a point light
    */
   @Test
   public void spherePoint() {
      scene1.getGeometries().add(sphere);
      scene1.getLights().add(new PointLight(spCL, spPL).setKl(0.001).setKq(0.0002));

      ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
      camera1.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene1)) //
              .renderImage() //
              .writeToImage(); //
   }

   /**
    * Produce a picture of a sphere lighted by a spotlight
    */
   @Test
   public void sphereSpot() {
      scene1.getGeometries().add(sphere);
      scene1.getLights().add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setKl(0.001).setKq(0.0001));

      ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
      camera1.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene1)) //
              .renderImage() //
              .writeToImage(); //
   }

   /**
    * Produce a picture of two triangles lighted by a directional light
    */
   @Test
   public void trianglesDirectional() {
      scene2.getGeometries().add(triangle1, triangle2);
      scene2.getLights().add(new DirectionalLight(trCL, trDL));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
      camera2.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene2)) //
              .renderImage() //
              .writeToImage(); //
   }

   /**
    * Produce a picture of a two triangles lighted by a point light
    */
   @Test
   public void trianglesPoint() {
      scene2.getGeometries().add(triangle1, triangle2);
      scene2.getLights().add(new PointLight(trCL, trPL).setKl(0.001).setKq(0.0002));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
      camera2.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene2)) //
              .renderImage() //
              .writeToImage(); //
   }

   /**
    * Produce a picture of two triangles lighted by a spolight
    */
   @Test
   public void trianglesSpot() {
      scene2.getGeometries().add(triangle1, triangle2);
      scene2.getLights().add(new SpotLight(trCL, trPL, trDL).setKl(0.001).setKq(0.0001));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
      camera2.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene2)) //
              .renderImage() //
              .writeToImage(); //
   }

      @Test
   void testSphereMultipleLights() {
      Point sphereLightPosition = new Point(-50, -50, 25);
      Color sphereLightColor = new Color(800, 500, 0);
      scene1.getGeometries().add(sphere);
      scene1.getLights().add(new DirectionalLight(sphereLightColor, new Vector(1, 1, -0.5)));
      scene1.getLights().add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
              .setKl(0.005).setKq(0.0003));
      scene1.getLights().add(new PointLight(sphereLightColor, sphereLightPosition)
              .setKl(0.005).setKq(0.0003));

      ImageWriter imageWriter = new ImageWriter("lightSphereMany", 500, 500);
      camera1.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene1)) //
              .renderImage() //
              .writeToImage(); //
   }
}




