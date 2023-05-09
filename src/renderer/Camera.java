package renderer;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
//    private Vector direction; // The direction of the camera's view

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
}

