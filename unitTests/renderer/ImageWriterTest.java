package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This module will build our initial image, which will be
 * an image made up of one color for the backgroun and a
 * second for the grid.
 * We will build a grid of 10x16 squares on a screen (ViewPlane)
 * with a resolution of 500x800.
 */
class ImageWriterTest {
    /**
     * Creates an image of a grid with blue filling in each pixel and the pixel outlines in green.
     */
    @Test
    void testImageWriter() {
        // setting the columns
        int nX = 800;
        // setting the rows
        int nY = 500;

        // set the background color to blue
        Color background = new Color(java.awt.Color.BLUE);
        // sets the grid color to green
        Color grid = new Color(java.awt.Color.GREEN);

        int spacing = 50;

        // create an image writer object
        ImageWriter initialIm = new ImageWriter("initial", nX, nY);
        // traverse through the columns and rows
        for (int column = 0; column < nX; column++) {
            for (int row = 0; row < nY; row++) {
                // if we are found on the grid, color in the grid color
                if (column % spacing == 0 || row % 50 == 0) {
                    initialIm.writePixel(column, row, grid);
                } else {
                    // otherwise color in the background color
                    initialIm.writePixel(column, row, background);
                }
            }
        }
        initialIm.writeToImage();
    }
}