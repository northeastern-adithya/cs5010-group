package controller;

/**
 * This interface defines the common features available for image manipulation in an image
 * processing application. It includes methods for loading and saving images, applying various
 * effects, and visualizing color components.
 */
public interface Features {

  /**
   * Loads an image into the system for further processing. The method will typically involve
   * opening a file dialog or providing a path to the image file.
   */
  void loadImage();

  /**
   * Saves the current image to a specified location. This method should handle saving the image in
   * a format (e.g., JPEG, PNG).
   */
  void saveImage();

  /**
   * Applies a sepia tone effect to the image, giving it a warm, brownish tint. This effect is
   * commonly used for an antique or vintage look.
   */
  void applySepiaEffect();

  /**
   * Applies a blur effect to the image, softening the details and reducing sharpness. This can be
   * used for artistic effects or to reduce noise in the image.
   */
  void applyBlurEffect();

  /**
   * Applies a sharpening effect to the image, enhancing the details and edges. This can help to
   * improve the clarity and definition of the image.
   */
  void applySharpenEffect();

  /**
   * Applies a grayscale effect to the image, converting it to shades of gray by removing all color
   * information.
   */
  void applyGreyscaleEffect();

  /**
   * Increases the brightness of the image, making the overall appearance lighter. The method should
   * allow control over the intensity of the brighten effect.
   */
  void applyBrightenEffect();

  /**
   * Flips the image vertically, reversing the position of pixels along the vertical axis.
   */
  void applyVerticalFlip();

  /**
   * Flips the image horizontally, reversing the position of pixels along the horizontal axis.
   */
  void applyHorizontalFlip();

  /**
   * Adjusts the levels of the image, such as contrast, brightness, and shadows, based on specified
   * parameters.
   */
  void applyLevelsAdjust();

  /**
   * Applies color correction to the image, which may include adjustments to color balance,
   * saturation, or temperature for improving color accuracy.
   */
  void applyColorCorrection();

  /**
   * Compresses the image based on a given percentage, potentially reducing its size while
   * maintaining an acceptable level of quality.
   */
  void applyCompress();

  /**
   * Downscales the image, reducing its resolution while maintaining the aspect ratio.
   */
  void applyDownscale();

  /**
   * Visualizes the red component of the image, displaying only the red color channel. This is
   * useful for understanding how each color channel contributes to the final image.
   */
  void visualizeRedComponent();

  /**
   * Visualizes the green component of the image, displaying only the green color channel. This
   * helps to see the contribution of the green channel in the image composition.
   */
  void visualizeGreenComponent();

  /**
   * Visualizes the blue component of the image, displaying only the blue color channel. This allows
   * observation of how the blue color channel affects the image.
   */
  void visualizeBlueComponent();


}
