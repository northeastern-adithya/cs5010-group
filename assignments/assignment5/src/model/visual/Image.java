package model.visual;


import model.pixels.Pixel;

/**
 * Represents an image.
 * Whatever the file format (jpg, png, bmp, etc.)
 * an image is basically a sequence of pixels.
 */
public interface Image {
  /**
   * Get the pixel at the specified coordinates.
   *
   * @param x the x coordinate of the pixel
   * @param y the y coordinate of the pixel
   * @return the pixel at the specified coordinates
   */
  Pixel getPixel(int x, int y);

  /**
   * Adjusts the brightness of the image by a given factor.
   *
   * @param factor the factor to adjust the brightness by
   *               (positive values increase brightness, negative values
   *               decrease brightness)
   * @return the image with the adjusted brightness by creating a new image
   *         object.
   */
  Image adjustImageBrightness(int factor);

  /**
   * Get the width of the image.
   * The width is the number of pixels in a row.
   *
   * @return the width of the image
   */
  int getWidth();

  /**
   * Get the height of the image.
   * The height is the number of rows in the image.
   *
   * @return the height of the image
   */
  int getHeight();

  /**
   * Creates a new image with the red component of the pixels.
   *
   * @return the image with the red component of the pixels by creating a new
   *         image object.
   */
  Image createRedComponent();

  /**
   * Creates a new image with the green component of the pixels.
   *
   * @return the image with the green component of the pixels by creating a
   *         new image object.
   */
  Image createGreenComponent();

  /**
   * Creates a new image with the blue component of the pixels.
   *
   * @return the image with the blue component of the pixels by creating a
   *         new image object.
   */
  Image createBlueComponent();

  /**
   * Returns the luma of the image.
   * Luma has weights of 0.2126 for red, 0.7152 for green, and 0.0722 for blue.
   *
   * @return the luma of the image by creating a new image object.
   */
  Image getLuma();

  /**
   * Returns the sepia of the image.
   * Photographs taken in the 19th and early 20th century had a characteristic
   * reddish brown tone. This is referred to as a sepia tone.
   *
   * @return the sepia of the image by creating a new image object.
   */
  Image getSepia();

  /**
   * Returns the intensity of the image.
   * This is the average of the three components for each pixel in an image.
   *
   * @return the intensity of the image by creating a new image object.
   */
  Image getIntensity();

  /**
   * Returns the value of the image.
   * This is the maximum value of the three components for each pixel of the
   * image.
   *
   * @return the value of the image by creating a new image object.
   */
  Image getValue();

  /**
   * Returns a new image with the horizontal flip of the original image.
   *
   * @return the image with the horizontal flip of the original image
   *         by creating a new image object.
   */
  Image horizontalFlip();

  /**
   * Returns a new image with the vertical flip of the original image.
   *
   * @return the image with the vertical flip of the original image by
   *         creating a new image object.
   */
  Image verticalFlip();


  /**
   * Returns the red pixel values of the image as a 2D array.
   *
   * @return the red pixel values of the image as a 2D array.
   */
  int[][] getRedChannel();

  /**
   * Returns the green pixel values of the image as a 2D array.
   *
   * @return the green pixel values of the image as a 2D array.
   */
  int[][] getGreenChannel();

  /**
   * Returns the blue pixel values of the image as a 2D array.
   *
   * @return the blue pixel values of the image as a 2D array.
   */
  int[][] getBlueChannel();

  /**
   * Applies color correction to the image.
   *
   * @return the color-corrected image by creating a new image object.
   */
  Image colorCorrect();

  /**
   * Adjusts the levels of the image.
   *
   * @param black the black point
   * @param mid   the mid point
   * @param white the white point
   * @return the image with adjusted levels by creating a new image object.
   */
  Image levelsAdjust(int black, int mid, int white);
}
