package renderer;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

public class GlossySurfacesBlurredGlassTests {

    private Scene scene = new Scene("Test scene");
    @Test
    void testWithoutImprovements() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(1, 0, 0)) // // chairs on right side: 0,0,-1 and 0,1,0 // chairs on top: 0,0,-1 and 1,0,0
                .setVPSize(200, 200).setVPDistance(1000); // might have to change the vectors

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.getGeometries().add(
                // outer pool
                new Triangle(new Point(15,-15,0), new Point(15,15,0), new Point(-15,15,0)).setEmission(new Color(BLUE)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                       .setKt(0.5)), //
                // outer pool
                new Triangle(new Point(15,-15,0), new Point(-15,15,0), new Point(-15,-15,0)).setEmission(new Color(BLUE)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                        .setKt(0.5)), //
//                new Polygon(new Point(15,-15,0), new Point(15,15,0), new Point(-15,15,0), new Point(-15,-15,0)).setEmission(new Color(BLUE)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
  //                      .setKt(0.5)), //
                // ball in the pool
                new Sphere(4d, new Point(10,0,0)).setEmission(new Color(ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                        .setKt(0)), //
                // outer sphere in the pool
                //new Sphere(10d, new Point(-6.99,-5.23,0)), //
                // inner sphere in the pool
                //new Sphere(4d, new Point(-6.99,-5.23,0)), //
                // first chair:
                new Triangle(new Point(17.68, 6.91, 0), new Point(17.82, 11.84, 0), new Point(22.64, 11.36, 0)).setEmission(new Color(RED)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                        .setKt(0)), //
                new Triangle(new Point(17.68, 6.91, 0), new Point(22.78, 6.92, 0), new Point(22.64, 11.36, 0)).setEmission(new Color(RED)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                        .setKt(0)), //
                new Triangle(new Point(22.78, 6.92, 0), new Point(22.64, 11.36, 0), new Point(25.15, 7.79, 0)).setEmission(new Color(RED)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                        .setKt(0)), //
                new Triangle(new Point(25.25, 7.79, 0), new Point(25.2, 3.02, 0), new Point(22.78, 6.92, 0)).setEmission(new Color(RED)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                        .setKt(0)), //
                // second chair:
                new Triangle(new Point(18, 2.95, 0), new Point(22.61, 2.62, 0), new Point(22.8, -3.26, 0)).setEmission(new Color(RED)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                        .setKt(0)), //
                new Triangle(new Point(18, 2.95, 0), new Point(18.29, -2.92, 0), new Point(22.8, -3.26, 0)).setEmission(new Color(RED)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                        .setKt(0)), //
                new Triangle(new Point(22.61, 2.62,0), new Point(24.21, 0, 0), new Point(24.09, -6.08, 0)).setEmission(new Color(RED)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                        .setKt(0)), //
                new Triangle(new Point(22.61, 2.62,0), new Point(22.8, -3.26, 0), new Point(24.09, -6.08, 0)).setEmission(new Color(RED)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                        .setKt(0)), //
                // ladder
                new Polygon(new Point(51.06, -20.17, 0), new Point(51.03, -17.57, 0), new Point(46.12,-17.3, 0), new Point(46.05, -19.83, 0)).setEmission(new Color(WHITE)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                        .setKt(0)), //
                new Polygon(new Point(11.11,-15,0), new Point(11.03, -17.57, 0), new Point(6.12,-17.3, 0), new Point(6,-15,0)).setEmission(new Color(WHITE)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                        .setKt(0)), //
                new Polygon(new Point(11.11,-15,0), new Point(11.16, -12.86, 0), new Point(6.25,-12.59,0), new Point(6,-15,0)).setEmission(new Color(WHITE)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                .setKt(0))); //

        scene.getLights().add(new PointLight(new Color(YELLOW), new Point(-15, 0.48, 0))); // light inside the pool
        scene.getLights().add(new DirectionalLight(new Color(YELLOW), new Vector(0,0,-1)));
        scene.getLights().add(new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("testPicture", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();


    }

    @Test
    public void my_picture() {
        Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.getGeometries().add(
//                new Polygon(new Point(0,15,0), new Point(25,15,0), new Point(20,5,0), new Point(-5,5,0))
//                        .setEmission(new Color(150,75,0))
//                        .setMaterial(new Material().setShininess(30).setKt(0).setKr(0.4)), //
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

        ImageWriter imageWriter = new ImageWriter("myTest2", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene).setSuperSamplingON(true).setNumOfSSRays(20).setSuperSamplingGridSize(9)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    void newTest() {
        Camera camera = new Camera(new Point(150, 100, 4000), new Vector(0, 0, -1), new Vector(1, 0, 0)) // new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.getGeometries().add(
                // top drawer
                new Triangle(new Point(50,-50,100), /*top left corner*/new Point(50,25,100), /*bottom right*/new Point(0,-50,100))
                        .setEmission(new Color(120,75,0)) //
                        .setMaterial(new Material().setShininess(20).setKt(0).setKr(0.2).setKd(0.1).setKs(0.1)), //
                new Triangle(new Point(0,25,100), /*top left corner*/new Point(50,25,100), /*bottom right*/new Point(0,-50,100))
                        .setEmission(new Color(120,75,0)) //
                        .setMaterial(new Material().setShininess(20).setKt(0).setKr(0.2).setKd(0.1).setKs(0.1)), //
                new Sphere(8d, new Point(25, -12.5, 101))
                        .setEmission(new Color(gray)) //
                        .setMaterial(new Material().setShininess(40).setKt(0).setKr(0.4).setKd(0.1).setKs(0.1)), //

                // behind the drawer
                new Triangle(new Point(0,25,90), /*top left corner*/new Point(50,25,90), new Point(50, 58,-20))
                        .setEmission(new Color(120,75,0)) //
                        .setMaterial(new Material().setShininess(10).setKt(0).setKr(0).setKd(0.1).setKs(0.1)), //

//                // base of the desk
                new Triangle(new Point(50,-50,100), new Point(150,0,170), new Point(50,300,100))
                        .setEmission(new Color(150,75,0)) //
                        .setMaterial(new Material().setShininess(70).setKt(0).setKr(0.6).setKd(0.1).setKs(0.1)), //
                new Triangle(new Point(150,0,170), new Point(50,300,100), new Point(150,350,170))
                        .setEmission(new Color(150,75,0))
                        .setMaterial(new Material().setShininess(70).setKt(0).setKr(0.6).setKd(0.1).setKs(0.1)), //

                // right side of the desk
                new Triangle(new Point(50,-50,0), new Point(150,0,0), new Point(-50,0,0))
                        .setEmission(new Color(140,75,0))
                        .setMaterial(new Material().setShininess(70).setKt(0).setKr(0.6).setKd(0.1).setKs(0.1)), //
                new Triangle(new Point(-50,0,0), new Point(50,-50,0), new Point(-150,-50,0))
                        .setEmission(new Color(140,75,0))
                        .setMaterial(new Material().setShininess(70).setKt(0).setKr(0.6).setKd(0.1).setKs(0.1)), //

                // left side of the desk
                new Triangle(new Point(150,350,130), new Point(50,295,140), new Point(-50,350,130)) // 295 used to be 300
                        .setEmission(new Color(140,75,0))
                        .setMaterial(new Material().setShininess(70).setKt(0).setKr(0.6).setKd(0.1).setKs(0.1)), //
                new Triangle(new Point(-50,350,130), new Point(50,295,140), new Point(-150,300,140)) // 295 used to be 300
                        .setEmission(new Color(140,75,0))
                        .setMaterial(new Material().setShininess(70).setKt(0).setKr(0.6).setKd(0.1).setKs(0.1)), //

                // laptop bottom
                new Triangle(new Point(65, 150, 155), new Point(100, 175, 145), new Point(65, 250, 155))
                        .setEmission(new Color(black))
                        .setMaterial(new Material().setShininess(90).setKt(0).setKr(0.9).setKd(0.9).setKs(0.9)), //
                new Triangle(/*top*/new Point(100, 275, 145), /*top*/new Point(100, 175, 145), new Point(65, 250, 155))
                        .setEmission(new Color(black))
                        .setMaterial(new Material().setShininess(90).setKt(0).setKr(0.9).setKd(0.2).setKs(0.2)), //

                // laptop top
                new Triangle(/*top*/new Point(100, 275, 145), /*top*/new Point(100, 175, 145), new Point(160, 175, 180))
                        .setEmission(new Color(GRAY))
                        .setMaterial(new Material().setShininess(90).setKt(0).setKr(0.9).setKd(0.2).setKs(0.2)), //
                new Triangle(/*top*/new Point(100, 275, 145), /*top*/new Point(160, 175, 180), new Point(160, 275, 180))
                        .setEmission(new Color(GRAY))
                        .setMaterial(new Material().setShininess(90).setKt(0).setKr(0.9).setKd(0.2).setKs(0.2)), //

                // laptop screen
                new Triangle(/*top*/new Point(105, 270, 146), /*top*/new Point(105, 180, 146), new Point(155, 180, 181))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setShininess(500).setKt(0).setKr(10).setKd(0.6).setKs(0.6)), //
                new Triangle(/*top*/new Point(105, 270, 146), /*top*/new Point(155, 180, 181), new Point(155, 270, 181))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setShininess(500).setKt(0).setKr(10).setKd(0.6).setKs(0.6)), //

                // paper on desk
                new Triangle(new Point(90,0,181), new Point(130,20,111), new Point(130,70,111)) //171,101,101
                        .setEmission(new Color(white)) //
                        .setMaterial(new Material().setShininess(0).setKt(0).setKr(0).setKd(0.1).setKs(0.1)), //
                new Triangle(new Point(90,0,181), new Point(90,50,181), new Point(130,70,111)) // 171,171,101
                        .setEmission(new Color(white)) //
                        .setMaterial(new Material().setShininess(0).setKt(0).setKr(0).setKd(0.1).setKs(0.1)), //

                // paper weight on desk
                new Sphere(17d, new Point(120,30, 190))
                        .setEmission(new Color(pink)) //
                        .setMaterial(new Material().setShininess(10).setKt(0).setKr(0.4).setKd(0.1).setKs(0.1)), //

                // window

                // bottom panel
                new Triangle(new Point(170,0,100), new Point(175,0,100), new Point(170,350,100))
                        .setEmission(new Color(68,110,155)) //
                        .setMaterial(new Material().setShininess(30).setKt(0).setKr(0.4).setKd(0.1).setKs(0.1)), //
                new Triangle(new Point(175,0,100), new Point(175, 350, 100), new Point(170,350,100))
                        .setEmission(new Color(68,110,155)) //
                        .setMaterial(new Material().setShininess(30).setKt(0).setKr(0.4).setKd(0.1).setKs(0.1)), //

                // top panel
                new Triangle(new Point(350,0,100), new Point(355,0,100), new Point(350,350,100))
                        .setEmission(new Color(68,110,155)) //
                        .setMaterial(new Material().setShininess(30).setKt(0).setKr(0.4).setKd(0.1).setKs(0.1)), //
                new Triangle(new Point(355,0,100), new Point(355, 350, 100), new Point(350,350,100))
                        .setEmission(new Color(68,110,155)) //
                        .setMaterial(new Material().setShininess(30).setKt(0).setKr(0.4).setKd(0.1).setKs(0.1)), //

                // right side panel
                new Triangle(new Point(170,0,100), new Point(355,0,100), new Point(170,5,100))
                        .setEmission(new Color(68,110,155)) //
                        .setMaterial(new Material().setShininess(30).setKt(0).setKr(0.4).setKd(0.1).setKs(0.1)), //
                new Triangle(new Point(355,5,100), new Point(355, 0, 100), new Point(170,5,100))
                        .setEmission(new Color(68,110,155)) //
                        .setMaterial(new Material().setShininess(30).setKt(0).setKr(0.4).setKd(0.1).setKs(0.1)), //

                // left side panel
                new Triangle(new Point(170,350,100), new Point(170,345,100), new Point(355,345,100))
                        .setEmission(new Color(68,110,155)) //
                        .setMaterial(new Material().setShininess(30).setKt(0).setKr(0.4).setKd(0.1).setKs(0.1)), //
                new Triangle(new Point(170,350,100), new Point(355,350,100), new Point(355,345,100))
                        .setEmission(new Color(68,110,155)) //
                        .setMaterial(new Material().setShininess(30).setKt(0).setKr(0.4).setKd(0.1).setKs(0.1)), //

                // window screen
                new Triangle(new Point(170,350,99), new Point(170,0,99), new Point(355,0,99))
                        .setEmission(new Color(80,80,80)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(170,350,99), new Point(355,350,99), new Point(355,0,99))
                        .setEmission(new Color(80,80,80)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //

                // ball in window
                new Sphere(30d, new Point(270, 190, 80))
                        .setEmission(new Color(red)) //
                        .setMaterial(new Material().setShininess(50).setKt(0.4).setKr(0.7).setKd(0.1).setKs(0.1)), //

                // floor tiles middle row
                new Triangle(new Point(-150, 560,0), new Point(-50, 480,0), new Point(-50, 610,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-150, 560,0), new Point(-50, 480,0), new Point(-150, 430,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-50, 350,0), new Point(-50, 480,0), new Point(-150, 430,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-50, 350,0), new Point(-150, 300,0), new Point(-150, 430,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-50, 350,0), new Point(-150, 300,0), new Point(-50, 220,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-150, 130,0), new Point(-150, 300,0), new Point(-50, 220,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-150, 130,0), new Point(-50,90,0), new Point(-50, 220,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //

                new Triangle(new Point(-150, 130,0), new Point(-50,90,0), new Point(-150,0,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
        new Triangle(new Point(-50, -50,0), new Point(-50,90,0), new Point(-150,0,0))
                .setEmission(new Color(blue)) //
                .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
        new Triangle(new Point(-50, -50,0), new Point(-150,-130,0), new Point(-150,0,0))
                .setEmission(new Color(170, 51, 106)) //
                .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-50, -50,0), new Point(-150,-130,0), new Point(-50,-180,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-150, -260,0), new Point(-150,-130,0), new Point(-50,-180,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-150, -260,0), new Point(-50,-310,0), new Point(-50,-180,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-150, -260,0), new Point(-50,-310,0), new Point(-150,-390,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //


                // floor tiles front row
                new Triangle(new Point(-150, 560,0), new Point(-250, 480,0), new Point(-250, 610,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-150, 560,0), new Point(-250, 480,0), new Point(-150, 430,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-250, 350,0), new Point(-250, 480,0), new Point(-150, 430,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-250, 350,0), new Point(-150, 300,0), new Point(-150, 430,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-250, 350,0), new Point(-150, 300,0), new Point(-250, 220,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-150, 130,0), new Point(-150, 300,0), new Point(-250, 220,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-150, 130,0), new Point(-250,90,0), new Point(-250, 220,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //

                new Triangle(new Point(-150, 130,0), new Point(-250,90,0), new Point(-150,0,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-250, -50,0), new Point(-250,90,0), new Point(-150,0,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-250, -50,0), new Point(-150,-130,0), new Point(-150,0,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-250, -50,0), new Point(-150,-130,0), new Point(-250,-180,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-150, -260,0), new Point(-150,-130,0), new Point(-250,-180,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-150, -260,0), new Point(-250,-310,0), new Point(-250,-180,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-150, -260,0), new Point(-250,-310,0), new Point(-150,-390,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //

                // floor tiles back row
                new Triangle(new Point(50, 560,0), new Point(-50, 480,0), new Point(-50, 610,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(50, 560,0), new Point(-50, 480,0), new Point(50, 430,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-50, 350,0), new Point(-50, 480,0), new Point(50, 430,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-50, 350,0), new Point(50, 300,0), new Point(50, 430,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-50, 350,0), new Point(50, 300,0), new Point(-50, 220,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(50, 130,0), new Point(50, 300,0), new Point(-50, 220,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(50, 130,0), new Point(-50,90,0), new Point(-50, 220,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //

                new Triangle(new Point(50, 130,0), new Point(-50,90,0), new Point(50,0,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-50, -50,0), new Point(-50,90,0), new Point(50,0,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-50, -50,0), new Point(50,-130,0), new Point(50,0,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(-50, -50,0), new Point(50,-130,0), new Point(-50,-180,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(50, -260,0), new Point(50,-130,0), new Point(-50,-180,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(50, -260,0), new Point(-50,-310,0), new Point(-50,-180,0))
                        .setEmission(new Color(170, 51, 106)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(50, -260,0), new Point(-50,-310,0), new Point(50,-390,0))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //

                // picture on the wall
                new Triangle(new Point(265, -50,100), new Point(265,-200,100), new Point(320,-125,100))
                        .setEmission(new Color(blue)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)), //
                new Triangle(new Point(265, -50,100), new Point(265,-200,100), new Point(210,-125,100))
                        .setEmission(new Color(green)) //
                        .setMaterial(new Material().setShininess(200).setKt(1).setKr(0.9).setKd(0.5).setKs(0.4)) //


        );

        scene.getLights().add(new SpotLight(new Color(400, 300, 200), new Point(250, -200, -50), new Vector(175, 25, -75)) //
                .setKl(0.00001).setKq(0.000005));
        scene.getLights().add(new PointLight(new Color(WHITE), new Point(-800, -5000, -1000)) // 800,600,1000
                .setKl(0.00001).setKq(0.000005));
        scene.getLights().add(new SpotLight(new Color(WHITE), new Point(-500, -450, -1000), new Vector(-1,0,-1))
                .setKl(0.1).setKq(0.05));
        scene.getLights().add(new DirectionalLight(new Color(white),new Vector(1,0,-1)));

        ImageWriter imageWriter = new ImageWriter("desk", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene).setSuperSamplingON(false).setNumOfSSRays(20).setSuperSamplingGridSize(9)) //
                .renderImage() //
                .writeToImage();
    }
}



