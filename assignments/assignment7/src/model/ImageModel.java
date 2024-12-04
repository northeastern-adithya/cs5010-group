package model;

import java.awt.image.BufferedImage;

/**
 * Interface representing an image model for performing various image processing tasks. The
 * interface provides methods for loading, saving, manipulating, and extracting information from
 * images. The images are represented as 2D arrays of Pixel objects.
 */
public interface ImageModel {

  /**
   * Saves an image to the specified file path.
   *
   * @param filepath the file path where the image will be saved.
   * @param image    the 2D array of Pixel objects representing the image to be saved.
   */
  void saveImage(String filepath, Pixel[][] image);


  /**
   * Retrieves the RGB components of the image as a 3D array.
   *
   * @return a 3D array where each element contains the red, green, and blue components of the
   *         image.
   */
  int[][][] getRGBComponents();

  /**
   * Returns the luma (brightness) of the image. Luma is calculated using the formula: 0.2126 * R +
   * 0.7152 * G + 0.0722 * B.
   *
   * @return a 2D array of Pixel objects representing the grayscale luma image.
   */
  Pixel[][] getLuma();

  /**
   * Returns the intensity of the image, calculated as the average of the red, green, and blue
   * components.
   *
   * @return a 2D array of Pixel objects representing the grayscale intensity image.
   */
  Pixel[][] getIntensity();

  /**
   * Returns the value (maximum of the red, green, and blue components) for each pixel in the
   * image.
   *
   * @return a 2D array of {@link Pixel} objects representing the grayscale value image.
   */
  Pixel[][] getValue();

  /**
   * Flips the image horizontally.
   */
  void flipHorizontally();

  /**
   * Flips the image vertically.
   */
  void flipVertically();

  /**
   * Brightens the image by the specified amount.
   *
   * @param amount the amount by which to brighten the image. Values are clamped to the range [0,
   *               255].
   */
  void brighten(int amount);

  /**
   * Darkens the image by the specified amount.
   *
   * @param amount the amount by which to darken the image. Values are clamped to the range [0,
   *               255].
   */
  void darken(int amount);

  /**
   * Splits the image into its red, green, and blue channels. After splitting, the individual
   * channel images can be retrieved using the getRedChannelImage(), getGreenChannelImage(), and
   * getBlueChannelImage() methods.
   */
  void splitChannels();

  /**
   * Combines three grayscale images representing the red, green, and blue channels into a single
   * RGB image.
   *
   * @param red   the 2D array of integers representing the red channel.
   * @param green the 2D array of integers representing the green channel.
   * @param blue  the 2D array of integers representing the blue channel.
   */
  void combineChannels(int[][] red, int[][] green, int[][] blue);

  /**
   * Extracts a single color channel from an image.
   *
   * @param image   the 2D array of  model.Pixel objects representing the image
   * @param channel the color channel to extract (0 for red, 1 for green, 2 for blue)
   * @return a 2D array of integers representing the extracted color channel
   */
  int[][] extractChannel(Pixel[][] image, int channel);

  /**
   * Applies a blur filter to the image. This is typically done using a Gaussian blur kernel.
   */
  void applyBlur();

  /**
   * Provides a certain proportion of the image, from the left edge to the designated % width, a
   * Gaussian blur. Only the designated area of the image receives the blur; the rest of the image
   * is left unaltered.
   */
  void applyBlur(double p);

  /**
   * Applies a sharpen filter to the image. This is typically done using a sharpening kernel.
   */
  void applySharpen();

  /**
   * A sharpening filter is applied to a predetermined proportion of the image, starting from the
   * left edge and ending at the predetermined % width. While the rest of the image stays the same,
   * the sharpening effect improves the edges and details in the defined area.
   */
  void applySharpen(double p);

  /**
   * Applies a custom filter to the image using the provided kernel. The kernel must be a square
   * matrix.
   *
   * @param kernel the 2D array representing the convolution kernel to apply to the image.
   * @throws IllegalArgumentException if the kernel is null or invalid (e.g., not square).
   */
  void applyFilter(double[][] kernel);

  /**
   * Applies a convolution filter on a portion of the picture that is determined by the split point
   * and kernel. From the image's left edge to the designated split point, every pixel is subjected
   * to the filter.
   */

  void applyFilterToPart(double[][] kernel, int splitPoint);

  /**
   * Applies a grayscale filter to the image using the luma formula: 0.2126 * R + 0.7152 * G +
   * 0.0722 * B.
   */
  void applyGreyScale();

  /**
   * A grayscale filter is applied to a predetermined proportion of the image, starting from the
   * left edge and ending at the predetermined percentage width. While the rest of the image stays
   * the same, the grayscale effect changes the colored pixels inside the designated area to various
   * shades of gray according to their luminance.
   */
  void applyGreyScale(double p);

  /**
   * Applies a sepia filter to the image. This gives the image a warm, reddish-brown tone.
   */
  void applySepia();

  /**
   * A sepia filter is applied to a predetermined proportion of the image, starting at the left edge
   * and ending at the predetermined percentage width. The pixels inside the designated area are
   * given a warm, brownish tone by the sepia effect, giving the image a vintage appearance. The
   * rest of the picture doesn't alter.
   */
  void applySepia(double p);

  /**
   * Calculates the histogram of the currently loaded image.
   *
   * @return A BufferedImage representing the histogram.
   */
  BufferedImage calculateHistogram();

  /**
   * This method analyzes the intensity histogram for each color channel to find the peak intensity
   * within a specified range. It then calculates an average peak value and adjusts each channel's
   * intensity to align with this average, creating a balanced color distribution across the image.
   *
   * @throws IllegalStateException if no image is loaded before applying color correction.
   */
  void colorCorrect();

  /**
   * Adjusts each color channel (Red, Green, and Blue) according to the histogram peaks for each
   * channel, applying color correction to a predetermined proportion of the image from the left
   * edge up to the predetermined % width. This adjustment improves the image's color quality and
   * aesthetic appeal by balancing the distribution of colors within the designated area.
   */
  void colorCorrect(double p);

  /**
   * Applies predefined shadow, midtone, and highlight settings to modify the image's overall color
   * levels. This level adjustment procedure enhances the image's darker, midtone, and brighter
   * regions by performing a transformation to each pixel's color channels (Red, Green, and Blue)
   * based on the input values.
   *
   * @param shadow    the intensity value representing the shadow level, typically between 0 and
   *                  255. Affects the dark areas of the image.
   * @param mid       the intensity value representing the midtone level, typically between 0 and
   *                  255. Affects the mid-brightness areas of the image.
   * @param highlight the intensity value representing the highlight level, typically between 0 and
   *                  255. Affects the bright areas of the image.
   * @throws IllegalStateException if no image is loaded before applying levels adjustment.
   */
  void levelsAdjust(int shadow, int mid, int highlight);

  /**
   * Based on the given shadow, midtone, and highlight values, modulates the color levels of a
   * designated percentage of the image from the left edge up to the set % width.
   */
  void levelsAdjust(int shadow, int mid, int highlight, double p);

  /**
   * Compresses the image by applying a Haar wavelet transform to each color channel (Red, Green,
   * and Blue), thresholding low-magnitude coefficients to reduce data size, and then reconstructing
   * the image using the inverse Haar transform. The thresholding step removes details based on the
   * specified percentage, creating a lossy compression effect that reduces image complexity.
   *
   * @param thresholdPercentage the percentage threshold used to compress the image.
   * @throws IllegalStateException if the image channels are not loaded before compression.
   */
  void compressImage(double thresholdPercentage);

  /**
   * Gets the current image as a 2D array of {@link Pixel} objects.
   *
   * @return the 2D array of {@link Pixel} objects representing the current image.
   */
  Pixel[][] getImage();

  /**
   * Retrieves the red channel of the image after the channels have been split.
   *
   * @return a 2D array of {@link Pixel} objects representing the red channel image.
   */
  Pixel[][] getRedChannelImage();

  /**
   * Retrieves the green channel of the image after the channels have been split.
   *
   * @return a 2D array of {@link Pixel} objects representing the green channel image.
   */
  Pixel[][] getGreenChannelImage();

  /**
   * Retrieves the blue channel of the image after the channels have been split.
   *
   * @return a 2D array of {@link Pixel} objects representing the blue channel image.
   */
  Pixel[][] getBlueChannelImage();

  /**
   * Sets the image to the provided 2D array of {@link Pixel} objects.
   *
   * @param image the 2D array of {@link Pixel} objects representing the image to set.
   */
  void setImage(Pixel[][] image);

  /**
   * Downscales the current image to the specified width and height.
   *
   * @param newWidth  the desired width of the downscaled image, must be greater than 0
   * @param newHeight the desired height of the downscaled image, must be greater than 0
   */
  void downscaleImage(int newWidth, int newHeight);
}