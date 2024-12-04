package model;

public class ImageImplementationV2 extends ImageImplementation implements ImageModelV2 {

  /**
   * applies the dithering effect to the current image, reducing it to a greyscale.
   */
  @Override
  public void applyDithering() {
    applyDithering(100);
  }

  /**
   * Applies Floyd-Steinberg dithering to the current image, reducing it to a greyscale image.
   */
  @Override
  public void applyDithering(double p) {
    if (image == null) {
      System.out.println("No image loaded.");
      return;
    }

    if (p < 0 || p > 100) {
      throw new IllegalArgumentException("Split Percentage must be between 0 and 100.");
    }

    Pixel[][] ditheredImage = computeDitheredImage();

    // replace pixels in split with dithered Image
    int width = image[0].length;
    int splitPoint = (int) (width * (p / 100));
    int height = image.length;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (col < splitPoint) {
          image[row][col] = ditheredImage[row][col];
        } else if (col == splitPoint) {
          image[row][col] = new Pixel(0, 0, 0);
        } else {
          // leave the same image
        }
      }
    }
  }

  private Pixel[][] computeDitheredImage() {
    Pixel[][] intensity = this.getIntensity();

    for(int row = 0; row < intensity.length; row++) {
      for(int col = 0; col < intensity[0].length; col++) {
        Pixel pixel = intensity[row][col];

        int oldColor = pixel.get(0);
        int newColor = oldColor < 128 ? 0 : 255;

        pixel.set(0, newColor);
        pixel.set(1, newColor);
        pixel.set(2, newColor);

        int error = oldColor - newColor;

        if (col + 1 < intensity[0].length) {
          adjustPixel(intensity[row][col + 1], (7 * error) / 16);
        }
        if (row + 1 < intensity.length) {
          adjustPixel(intensity[row + 1][col], (5 * error) / 16);
        }
        if (row + 1 < intensity.length && col > 0) {
          adjustPixel(intensity[row + 1][col - 1],(3 * error) / 16);
        }
        if (row + 1 < intensity.length && col + 1 < intensity[0].length) {
          adjustPixel(intensity[row + 1][col + 1], (1 * error) / 16);
        }
      }
    }
    return intensity;
  }


  /**
   * Adjusts the grayscale value of a pixel by adding the specified error,
   * ensuring it stays within [0, 255].
   *
   * @param pixel the pixel to adjust
   * @param error the error value to add
   */
  private void adjustPixel(Pixel pixel, int error) {
    int newGray = clamp(pixel.get(0) + error);
    pixel.set(0, newGray);
    pixel.set(1, newGray);
    pixel.set(2, newGray);
  }
}
