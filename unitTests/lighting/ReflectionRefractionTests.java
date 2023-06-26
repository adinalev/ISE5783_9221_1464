///**
// *
// */
//package lighting;
//
//import static java.awt.Color.*;
//
//import org.junit.jupiter.api.Test;
//
//import geometries.Sphere;
//import geometries.Triangle;
//import lighting.AmbientLight;
//import lighting.SpotLight;
//import primitives.*;
//import renderer.*;
//import scene.Scene;
//
///** Tests for reflection and transparency functionality, test for partial
// * shadows
// * (with transparency)
// * @author dzilb */
//public class ReflectionRefractionTests {
//   private Scene scene = new Scene("Test scene");
//
//   /** Produce a picture of a sphere lighted by a spotlight */
//   @Test
//   public void twoSpheres() {
//      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//         .setVPSize(150, 150).setVPDistance(1000);
//
//      scene.geometries.add( //
//                           new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
//                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
//                           new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
//                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
//      scene.lights.add( //
//                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
//                          .setKl(0.0004).setKq(0.0000006));
//
//      camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
//         .setRayTracer(new RayTracerBasic(scene)) //
//         .renderImage() //
//         .writeToImage();
//   }
//
//   /** Produce a picture of a sphere lighted by a spotlight */
//   @Test
//   public void twoSpheresOnMirrors() {
//      Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//         .setVPSize(2500, 2500).setVPDistance(10000); //
//
//      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
//
//      scene.geometries.add( //
//                           new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
//                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
//                                 .setKt(new Double3(0.5, 0, 0))),
//                           new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
//                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
//                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
//                                        new Point(670, 670, 3000)) //
//                              .setEmission(new Color(20, 20, 20)) //
//                              .setMaterial(new Material().setKr(1)),
//                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
//                                        new Point(-1500, -1500, -2000)) //
//                              .setEmission(new Color(20, 20, 20)) //
//                              .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));
//
//      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
//         .setKl(0.00001).setKq(0.000005));
//
//      ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
//      camera.setImageWriter(imageWriter) //
//         .setRayTracer(new RayTracerBasic(scene)) //
//         .renderImage() //
//         .writeToImage();
//   }
//
//   /** Produce a picture of a two triangles lighted by a spotlight with a
//    * partially
//    * transparent Sphere producing partial shadow */
//   @Test
//   public void trianglesTransparentSphere() {
//      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//         .setVPSize(200, 200).setVPDistance(1000);
//
//      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
//
//      scene.geometries.add( //
//                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
//                                        new Point(75, 75, -150)) //
//                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
//                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
//                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
//                           new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
//                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
//
//      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
//         .setKl(4E-5).setKq(2E-7));
//
//      ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
//      camera.setImageWriter(imageWriter) //
//         .setRayTracer(new RayTracerBasic(scene)) //
//         .renderImage() //
//         .writeToImage();
//   }
//}

/**
 *
 */
package lighting;

import static java.awt.Color.*;

import geometries.Polygon;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 */
public class ReflectionRefractionTests {
   private Scene scene = new Scene("Test scene");

   /** Produce a picture of a sphere lighted by a spotlight */
   @Test
   public void twoSpheres() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPSize(150, 150).setVPDistance(1000);

      scene.getGeometries().add( //
              new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                      .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
              new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.getLights().add( //
              new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                      .setKl(0.0004).setKq(0.0000006));

      camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
              .setRayTracer(new RayTracerBasic(scene).setSuperSamplingON(false).setNumOfSSRays(20).setSuperSamplingGridSize(9)) //
              .renderImage() //
              .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spotlight */
   @Test
   public void twoSpheresOnMirrors() throws IllegalAccessException {
      Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPSize(2500, 2500).setVPDistance(10000); //

      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

      scene.getGeometries().add( //
              new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                              .setKt(new Double3(0.5, 0, 0))),
              new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
              new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                      new Point(670, 670, 3000)) //
                      .setEmission(new Color(20, 20, 20)) //
                      .setMaterial(new Material().setKr(1)),
              new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                      new Point(-1500, -1500, -2000)) //
                      .setEmission(new Color(20, 20, 20)) //
                      .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

      scene.getLights().add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
              .setKl(0.00001).setKq(0.000005));

      ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
      camera.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene).setSuperSamplingON(false).setSuperSamplingGridSize(9).setNumOfSSRays(20)) //
              .renderImage() //
              .writeToImage();
   }

   /** Produce a picture of a two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() throws IllegalAccessException {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPSize(200, 200).setVPDistance(1000);

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      scene.getGeometries().add( //
              new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                      new Point(75, 75, -150)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
              new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
              new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

      scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
              .setKl(4E-5).setKq(2E-7));

      ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
      camera.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene)) //
              .renderImage() //
              .writeToImage();
   }

   @Test
   public void my_picture() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPSize(200, 200).setVPDistance(1000);

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      scene.getGeometries().add(
              new Triangle(new Point(50, -50, 0), new Point(-50, -50, 0), new Point(0, 0, -50)) //
                      .setEmission(new Color(200, 80, 79.61)) //
                      .setMaterial(new Material().setKt(0.9).setKs(0.4).setShininess(20).setKr(0.01)), //
              new Sphere(40d, new Point(0, 0, -40)).setEmission(new Color(GRAY)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0.4)), //
              new Sphere(5d, new Point(0, 0, -40)).setEmission(new Color(YELLOW)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0)), //
              new Sphere(7d, new Point(-35, 19, -20)).setEmission(new Color(BLUE)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0)), //
              new Sphere(7d, new Point(35, 19, -20)).setEmission(new Color(BLUE)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0)), //
              new Sphere(7d, new Point(15, 37, -20)).setEmission(new Color(BLUE)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0)), //
              new Sphere(7d, new Point(-15, 37, -20)).setEmission(new Color(BLUE)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0)), //
              new Sphere(7d, new Point(38, -6, -20)).setEmission(new Color(BLUE)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0)), //
              new Sphere(7d, new Point(-38, -6, -20)).setEmission(new Color(BLUE)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0)), //
              new Triangle(new Point(-38, -6, -40), new Point(-35, 19, -40), new Point(0, 0, -40)).setEmission(new Color(200, 80, 79.61)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0.1)), //
              new Triangle(new Point(15, 37, -40), new Point(-15, 37, -40), new Point(0, 0, -40)).setEmission(new Color(200, 80, 79.61)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0.1)), //
              new Triangle(new Point(38, -6, -40), new Point(35, 19, -40), new Point(0, 0, -40)).setEmission(new Color(200, 80, 79.61)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0.1)) //
             // new Polygon(new Point(-35, 57, -50), new Point(-5, 57, -50), new Point(15, 39, -50), new Point(-55, 39, -50)).setEmission(new Color(PINK)) //
               //       .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0.1)) //
      );

      scene.getLights().add(new SpotLight(new Color(400, 300, 200), new Point(-250, 200, -50), new Vector(175, 25, -75)) //
              .setKl(0.00001).setKq(0.000005));

      ImageWriter imageWriter = new ImageWriter("myTest", 600, 600);
      camera.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene).setSuperSamplingON(false).setNumOfSSRays(20).setSuperSamplingGridSize(9)) //
              .renderImage() //
              .writeToImage();
   }

}
