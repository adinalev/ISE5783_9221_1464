package renderer;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.ImageWriter;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**

 The Camera class represents a camera in a 3D environment. It contains information about the camera's location,

 orientation, and view plane properties. The camera is used to construct rays that are cast into the scene to create an

 image from the viewpoint of the camera.
 @Author Michal and Adina (with the help of chatgpt)
 */
public class Camera {

    // Variables for the camera's location and orientation
    private Point p0; // The location of the camera in 3D space
    private Vector vTo; // The vector pointing in the direction the camera is facing
    private Vector vUp; // The vector pointing up from the camera's position
    private Vector vRight; // The vector pointing to the right of the camera's position

    // Variables for the view plane
    private double width; // The width of the view plane
    private double height; // The height of the view plane
    private double distance; // The distance from the camera to the view plane

    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;
    /**

     Constructs a new Camera object with the given location, vUp, and vTo vectors.
     @param p0 the location of the camera in 3D space
     @param vUp the vector pointing up from the camera's position
     @param vTo the vector pointing in the direction the camera is facing
     @throws IllegalArgumentException if the vUp and vTo vectors are not orthogonal
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vTo and vUp are not orthogonal");
        }

        this.p0 = p0;

        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();

        vRight = this.vTo.crossProduct(this.vUp);
    }
    /**

     Returns the location of the camera in 3D space.
     @return the location of the camera
     */
    public Point getP0() {
        return p0;
    }
    /**

     Returns the vector pointing in the direction the camera is facing.
     @return the vTo vector of the camera
     */
    public Vector getVTo() {
        return vTo;
    }
    /**

     Returns the vector pointing up from the camera's position.
     @return the vUp vector of the camera
     */
    public Vector getVUp() {
        return vUp;
    }
    /**

     Returns the vector pointing to the right of the camera's position.
     @return the vRight vector of the camera
     */
    public Vector getVRight() {
        return vRight;
    }
    /**

     Sets the size of the camera's view plane.
     @param width the width of the view plane
     @param height the height of the view plane
     @return the Camera object with the updated view plane size
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }
    /**

     Sets the distance from the camera to the view plane.
     @param distance the distance from the camera to the view plane
     @return the Camera object with the updated distance
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }
    /**

     Constructs a Ray object representing the ray that would be cast from the camera's position through the specified
     pixel on the view plane.
     @param nX the number of columns
     @param nY the number of rows
     @param i
     @param j
     */
     public Ray constructRay(int nX, int nY, int j, int i) {
         //view plane center Point
         Point Pc = p0.add((vTo.scale(distance))); // view plane center

         Point Pij  = Pc; // pixel center - point[i,j]

         double Ry = height/nY; // pixel height
         double Rx = width/nX; // pixel width

         double Yi = -(i - (nY - 1)/2.0) * Ry;
         double Xj = (j - (nX -1)/2.0) * Rx;

         if (!isZero(Xj)) {
             Pij = Pij.add(vRight.scale(Xj));
         }
         if (!isZero(Yi)) {
             Pij = Pij.add(vUp.scale(Yi));
         }

         Vector Vij = Pij.subtract(p0);
         Ray ray = new Ray(p0, Vij);

         return ray;
     }

    /**
     * setter method to set the ImageWriter object
     * @param imagerWriter
     * @return Camera object in order to allow chaining for the Builder pattern
     */
     public Camera setImageWriter(ImageWriter imagerWriter) {
         this.imageWriter = imagerWriter;
         return this;
     }

    /**
     * setter method for the RayTracerBase object
     * @param rayTracerBase
     * @return Camera object in order to allow chaining for the Builder pattern
     */
     public Camera setRayTracerBase(RayTracerBase rayTracerBase) {
         this.rayTracerBase = rayTracerBase;
         return this;
     }

    /**
     * Calculate and set the color of each pixel.
     * @return the rendered image
     */
    public Camera renderImage() {
         // check if any of the fields are null
         if (p0 == null || vUp == null || vTo == null || vRight == null || width == 0 || height == 0 || distance == 0) {
             throw new MissingResourceException("Missing resources.", Camera.class.getName(), "");
         }
         // check if the ImageWriter field is null
         if (imageWriter == null) {
             throw new MissingResourceException("ImageWriter object cannot be null.", ImageWriter.class.getName(), "");
         }
        // check if the RayTracerBase field is null
        if (rayTracerBase == null) {
             throw new MissingResourceException("RayTracerBase object cannot be null.", RayTracerBase.class.getName(), "");
         }

         // set the columns and the rows
         int nX = imageWriter.getNx();
         int nY = imageWriter.getNy();

         // traverse through the rows and columms
         for (int row = 0; row < nY; row++) {
             for (int column = 0; column < nX; column++) {
                 Color color = castRay(nX, nY, row, column);
                 // store the color in the corresponding pixel
                this.imageWriter.writePixel(column, row, color);
             }
         }
         return this;
     }

    private Color castRay(int nX, int nY, int row, int column) {
        // construct a ray for each pixel
        Ray ray = this.constructRay(nX, nY, column, row);
        // calculate the color
        Color color = this.rayTracerBase.traceRay(ray);
        return color;
    }

    /**
     * Prints the grid over the scene
     * @param interval the amount of space which encapsulates the pixels
     * @param color the color of the  pixel
     */
     public void printGrid(int interval, Color color) {
         // check if the imageWriter field is null
         if (imageWriter == null) {
             throw new MissingResourceException("ImageWriter field cannot be null!", ImageWriter.class.getName(), "");
         }

         // set the rows and the columns
         int nX = imageWriter.getNx();
         int nY = imageWriter.getNy();

         // traverse through the columns and rows
         for (int column = 0; column < nX; column++) {
             for (int row = 0; row < nY; row++) {
                 // if we are found on the grid, insert the color given
                 if (column % interval == 0 || row % interval == 0) {
                     imageWriter.writePixel(column, row, color);
                 }
             }
         }

         imageWriter.writeToImage();
     }

    /**
     * Writes the image
     */
    public Camera writeToImage() {
         if (imageWriter == null) {
             throw new MissingResourceException("ImageWriter field cannot be null!", Camera.class.getName(), "");
         }
         // delegates the appropriate method of the ImageWriter.
         imageWriter.writeToImage();
         return this;
     }

    /**
     * set the rayTracerBase object
     * @param rayTracer
     * @return the Camera object
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracerBase = rayTracer;
        return this;
    }


}

