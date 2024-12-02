package model;

/**
 * Implements the MaskInterface and operates on an image represented as a 2D array of Pixel objects.
 * Supports operations like blurring, grayscale conversion, intensity adjustment, luma
 * transformation, and RGB component extraction, applied to specific areas of the image based on a
 * mask. A mask is a black-and-white image where black pixels indicate areas to be affected by the
 * operation, and white pixels remain unchanged.
 */

public class ImageMaskProcessor implements MaskInterface {

  private Pixel[][] image;

  /**
   * Initializes an ImageMaskProcessor with the specified image.
   *
   * @param image the 2D array of  Pixel objects representing the image to be processed
   * @throws IllegalArgumentException if the provided image is null
   */
  public ImageMaskProcessor(Pixel[][] image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    this.image = image;
  }

  /**
   * Retrieves the current image being processed. *
   *
   * @return the current image as a 2D array of Pixel objects
   */
  @Override
  public Pixel[][] getImage() {
    return image;
  }

  /**
   * Sets the current image to be processed.
   *
   * @param image the new image to be processed, represented as a 2D array of Pixel objects
   * @throws IllegalArgumentException if the provided image is null
   */
  @Override
  public void setImage(Pixel[][] image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    this.image = image;
  }

  /**
   * Determines whether the mask should be applied to a given pixel.
   *
   * @param maskPixel the Pixel from the mask being checked
   * @return true if the mask pixel is black (RGB values are all 0), false otherwise
   * @throws IllegalArgumentException if the provided mask pixel is null
   */
  @Override
  public boolean shouldApplyMask(Pixel maskPixel) {
    if (maskPixel == null) {
      throw new IllegalArgumentException("Mask pixel cannot be null.");
    }
    return maskPixel.get(0) == 0 && maskPixel.get(1) == 0 && maskPixel.get(2) == 0;
  }

  /**
   * Applies the red color component operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of Pixel objects that determines where the operation
   *             is applied
   */
  @Override
  public void applyRedComponentWithMask(Pixel[][] mask) {
    applyColorComponentWithMask(mask, "red");
  }

  /**
   * Applies the green color component operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of Pixel objects that determines where the operation
   *             is applied
   */
  @Override
  public void applyGreenComponentWithMask(Pixel[][] mask) {
    applyColorComponentWithMask(mask, "green");
  }

  /**
   * Applies the blue color component operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of Pixel objects that determines where the operation
   *             is applied
   */
  @Override
  public void applyBlueComponentWithMask(Pixel[][] mask) {
    applyColorComponentWithMask(mask, "blue");
  }

  /**
   * Applies the specified color component operation to the image, restricted by a mask.
   *
   * @param mask      the mask image as a 2D array of {@code Pixel} objects
   * @param component the color component to apply (e.g., "red", "green", or "blue")
   * @throws IllegalArgumentException if the mask size does not match the image size
   */
  @Override
  public void applyColorComponentWithMask(Pixel[][] mask, String component) {
    if (image == null || mask == null) {
      throw new IllegalArgumentException("Image or mask is not loaded.");
    }
    if (image.length != mask.length || image[0].length != mask[0].length) {
      throw new IllegalArgumentException("Mask size must match the image size.");
    }

    Pixel[][] outputImage = new Pixel[image.length][image[0].length];
    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        if (shouldApplyMask(mask[row][col])) {
          Pixel pixel = image[row][col];
          int value;
          switch (component) {
            case "red":
              value = pixel.get(0);
              break;
            case "green":
              value = pixel.get(1);
              break;
            case "blue":
              value = pixel.get(2);
              break;
            default:
              throw new IllegalArgumentException("Invalid component: " + component);
          }
          outputImage[row][col] = new Pixel(value, value, value);
        } else {
          outputImage[row][col] = image[row][col];
        }
      }
    }
    this.image = outputImage;
  }

  /**
   * Applies the value component operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of {@code Pixel} objects
   * @throws IllegalArgumentException if the mask size does not match the image size
   */
  @Override
  public void applyValueWithMask(Pixel[][] mask) {
    if (image == null || mask == null) {
      throw new IllegalArgumentException("Image or mask is not loaded.");
    }
    if (image.length != mask.length || image[0].length != mask[0].length) {
      throw new IllegalArgumentException("Mask size must match the image size.");
    }

    Pixel[][] outputImage = new Pixel[image.length][image[0].length];

    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        Pixel pixel = image[row][col];
        int value = Math.max(Math.max(pixel.get(0), pixel.get(1)), pixel.get(2));

        if (shouldApplyMask(mask[row][col])) {
          // Apply value to masked areas
          outputImage[row][col] = new Pixel(value, value, value);
        } else {
          outputImage[row][col] = pixel;
        }
      }
    }

    this.image = outputImage;
  }

  /**
   * Applies the intensity operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of {@code Pixel} objects
   * @throws IllegalArgumentException if the mask size does not match the image size
   */
  @Override
  public void applyIntensityWithMask(Pixel[][] mask) {
    if (image == null || mask == null) {
      throw new IllegalArgumentException("Image or mask is not loaded.");
    }
    if (image.length != mask.length || image[0].length != mask[0].length) {
      throw new IllegalArgumentException("Mask size must match the image size.");
    }

    // Create the output image
    Pixel[][] outputImage = new Pixel[image.length][image[0].length];

    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        Pixel pixel = image[row][col];

        if (shouldApplyMask(mask[row][col])) {
          // Calculate intensity for masked areas
          int intensity = (pixel.get(0) + pixel.get(1) + pixel.get(2)) / 3;
          outputImage[row][col] = new Pixel(intensity, intensity, intensity);
        } else {
          outputImage[row][col] = pixel;
        }
      }
    }

    this.image = outputImage;
  }

  /**
   * Applies the luma operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of Pixel objects
   * @throws IllegalArgumentException if the mask size does not match the image size
   */
  @Override
  public void applyLumaWithMask(Pixel[][] mask) {
    if (image == null || mask == null) {
      throw new IllegalArgumentException("Image or mask is not loaded.");
    }
    if (image.length != mask.length || image[0].length != mask[0].length) {
      throw new IllegalArgumentException("Mask size must match the image size.");
    }

    Pixel[][] outputImage = new Pixel[image.length][image[0].length];

    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        Pixel pixel = image[row][col];
        int luma = (int) (0.2126 * pixel.get(0) + 0.7152 * pixel.get(1) + 0.0722 * pixel.get(2));

        if (shouldApplyMask(mask[row][col])) {
          // Apply luma to masked areas
          outputImage[row][col] = new Pixel(luma, luma, luma);
        } else {
          // Retain original pixel in unmasked areas
          outputImage[row][col] = pixel;
        }
      }
    }
    this.image = outputImage;
  }

  /**
   * Applies the blur operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of Pixel objects
   * @throws IllegalArgumentException if the mask size does not match the image size
   */
  @Override
  public void applyBlurWithMask(Pixel[][] mask) {
    if (image == null || mask == null) {
      throw new IllegalArgumentException("Image or mask is not loaded.");
    }
    if (image.length != mask.length || image[0].length != mask[0].length) {
      throw new IllegalArgumentException("Mask size must match the image size.");
    }
    double[][] blurKernel = {
        {1.0 / 16, 1.0 / 8, 1.0 / 16},
        {1.0 / 8, 1.0 / 4, 1.0 / 8},
        {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };
    processWithMask(mask, blurKernel);
  }

  /**
   * Applies the sharpen operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of Pixel objects
   * @throws IllegalArgumentException if the mask size does not match the image size
   */
  @Override
  public void applySharpenWithMask(Pixel[][] mask) {
    if (image == null || mask == null) {
      throw new IllegalArgumentException("Image or mask is not loaded.");
    }
    if (image.length != mask.length || image[0].length != mask[0].length) {
      throw new IllegalArgumentException("Mask size must match the image size.");
    }
    double[][] sharpenKernel = {
        {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };
    processWithMask(mask, sharpenKernel);
  }

  /**
   * Applies the greyscale operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of Pixel objects
   * @throws IllegalArgumentException if the mask size does not match the image size
   */
  @Override
  public void applyGreyScaleWithMask(Pixel[][] mask) {
    if (image == null || mask == null) {
      throw new IllegalArgumentException("Image or mask is not loaded.");
    }
    if (image.length != mask.length || image[0].length != mask[0].length) {
      throw new IllegalArgumentException("Mask size must match the image size.");
    }
    Pixel[][] outputImage = new Pixel[image.length][image[0].length];
    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        if (shouldApplyMask(mask[row][col])) {
          Pixel pixel = image[row][col];
          int grayValue = (int) (0.2126 * pixel.get(0) + 0.7152 * pixel.get(1) + 0.0722 * pixel.get(
              2));
          outputImage[row][col] = new Pixel(grayValue, grayValue, grayValue);
        } else {
          outputImage[row][col] = image[row][col];
        }
      }
    }
    this.image = outputImage;
  }

  /**
   * Applies the SEPIA operation to the image, restricted by a mask.
   *
   * @param mask the mask image as a 2D array of Pixel objects
   * @throws IllegalArgumentException if the mask size does not match the image size
   */
  @Override
  public void applySepiaWithMask(Pixel[][] mask) {
    if (image == null || mask == null) {
      throw new IllegalArgumentException("Image or mask is not loaded.");
    }
    if (image.length != mask.length || image[0].length != mask[0].length) {
      throw new IllegalArgumentException("Mask size must match the image size.");
    }
    Pixel[][] outputImage = new Pixel[image.length][image[0].length];
    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        if (shouldApplyMask(mask[row][col])) {
          Pixel pixel = image[row][col];
          int red = pixel.get(0);
          int green = pixel.get(1);
          int blue = pixel.get(2);

          int newRed = (int) (0.393 * red + 0.769 * green + 0.189 * blue);
          int newGreen = (int) (0.349 * red + 0.686 * green + 0.168 * blue);
          int newBlue = (int) (0.272 * red + 0.534 * green + 0.131 * blue);

          outputImage[row][col] = new Pixel(clamp(newRed), clamp(newGreen), clamp(newBlue));
        } else {
          outputImage[row][col] = image[row][col];
        }
      }
    }
    this.image = outputImage;
  }

  private Pixel[][] applyFilter(Pixel[][] image, double[][] kernel) {
    int height = image.length;
    int width = image[0].length;
    int kernelSize = kernel.length;
    int kernelRadius = kernelSize / 2;

    Pixel[][] outputImage = new Pixel[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        double red = 0.0;
        double green = 0.0;
        double blue = 0.0;

        for (int kr = -kernelRadius; kr <= kernelRadius; kr++) {
          for (int kc = -kernelRadius; kc <= kernelRadius; kc++) {
            int imageRow = Math.min(Math.max(row + kr, 0), height - 1);
            int imageCol = Math.min(Math.max(col + kc, 0), width - 1);

            Pixel pixel = image[imageRow][imageCol];
            double kernelValue = kernel[kr + kernelRadius][kc + kernelRadius];

            red += pixel.get(0) * kernelValue;
            green += pixel.get(1) * kernelValue;
            blue += pixel.get(2) * kernelValue;
          }
        }

        outputImage[row][col] = new Pixel(clamp((int) red), clamp((int) green), clamp((int) blue));
      }
    }

    return outputImage;
  }

  private void processWithMask(Pixel[][] mask, double[][] kernel) {
    if (image == null || mask == null) {
      throw new IllegalStateException("Image or mask is not loaded.");
    }
    if (image.length != mask.length || image[0].length != mask[0].length) {
      throw new IllegalArgumentException("Mask size must match the image size.");
    }

    Pixel[][] filteredImage = applyFilter(this.image, kernel);

    // Create a new image with the mask applied
    Pixel[][] outputImage = new Pixel[this.image.length][this.image[0].length];
    for (int row = 0; row < this.image.length; row++) {
      for (int col = 0; col < this.image[0].length; col++) {
        if (shouldApplyMask(mask[row][col])) {
          outputImage[row][col] = filteredImage[row][col];
        } else {
          outputImage[row][col] = this.image[row][col];
        }
      }
    }

    this.image = outputImage;
  }

  private int clamp(int value) {
    return Math.min(Math.max(value, 0), 255);
  }
}