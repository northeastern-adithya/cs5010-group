package model;

/**
 * The {@code MaskInterface} defines methods for applying various image operations selectively based
 * on a mask. A mask is a black-and-white image where black pixels indicate areas to apply the
 * operation and white pixels indicate areas to leave unchanged.
 */
public interface MaskInterface {

  /**
   * Retrieves the current image being processed.
   *
   * @return the current image as a 2D array of {@code Pixel} objects
   */
  Pixel[][] getImage();

  /**
   * Sets the current image to be processed.
   *
   * @param image the image as a 2D array of {@code Pixel} objects
   * @throws IllegalArgumentException if the provided image is {@code null}
   */
  void setImage(Pixel[][] image);

  /**
   * Determines whether the mask should be applied to a given pixel.
   *
   * @param maskPixel the pixel from the mask being checked
   * @return {@code true} if the mask pixel is black (RGB values are all 0), {@code false} otherwise
   * @throws IllegalArgumentException if the mask pixel is {@code null}
   */
  boolean shouldApplyMask(Pixel maskPixel);

  /**
   * Applies the red color component operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of {@code Pixel} objects
   */
  void applyRedComponentWithMask(Pixel[][] mask);

  /**
   * Applies the green color component operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of {@code Pixel} objects
   */
  void applyGreenComponentWithMask(Pixel[][] mask);

  /**
   * Applies the blue color component operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of {@code Pixel} objects
   */
  void applyBlueComponentWithMask(Pixel[][] mask);

  /**
   * Applies the specified color component (red, green, or blue) operation to the image, restricted
   * by a mask.
   *
   * @param mask      the mask image as a 2D array of {@code Pixel} objects
   * @param component the color component to apply (e.g., "red", "green", "blue")
   */
  void applyColorComponentWithMask(Pixel[][] mask, String component);

  /**
   * Applies the value component (maximum of RGB values) to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of {@code Pixel} objects
   */
  void applyValueWithMask(Pixel[][] mask);

  /**
   * Applies the intensity component (average of RGB values) to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of {@code Pixel} objects
   */
  void applyIntensityWithMask(Pixel[][] mask);

  /**
   * Applies the luma component (weighted sum of RGB values) to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of {@code Pixel} objects
   */
  void applyLumaWithMask(Pixel[][] mask);

  /**
   * Applies a blur filter to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of {@code Pixel} objects
   */
  void applyBlurWithMask(Pixel[][] mask);

  /**
   * Applies a grayscale operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of {@code Pixel} objects
   */
  void applyGreyScaleWithMask(Pixel[][] mask);

  /**
   * Applies a sharpen filter to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of {@code Pixel} objects
   */
  void applySharpenWithMask(Pixel[][] mask);

  /**
   * Applies a sepia-tone effect to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of {@code Pixel} objects
   */
  void applySepiaWithMask(Pixel[][] mask);
}