package utility;

import java.util.Objects;

import factories.Factory;
import model.enumeration.FilterOption;
import model.pixels.Pixel;
import model.visual.Image;

/**
 * Utility class for applying filters to an image.
 * Provides a method to apply a filter to an image.
 */
public class FilterUtils {

  private FilterUtils() {
    //Empty private constructor to prevent instantiation.
  }

  /**
   * Applies a filter to an image.
   * The filter is applied by involving the image with the filter's kernel.
   * For each pixel in the image, the kernel is centered on the pixel, and the
   * surrounding pixels are multiplied by the corresponding kernel values.
   * The results are summed to produce the new pixel value.
   *
   * @param image        the image to apply the filter to
   * @param filterOption the filter option to apply
   * @return the image with the filter applied
   */
  public static Image applyFilter(Image image, FilterOption filterOption)
          throws NullPointerException {
    Objects.requireNonNull(image);
    Objects.requireNonNull(filterOption);
    int width = image.getWidth();
    int height = image.getHeight();
    int radius = filterOption.getKernel().length / 2;
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        double redSum = 0;
        double greenSum = 0;
        double blueSum = 0;

        for (int ky = -radius; ky <= radius; ky++) {
          for (int kx = -radius; kx <= radius; kx++) {
            int pixelX = x + kx;
            int pixelY = y + ky;
            if (pixelX >= 0 && pixelX < width && pixelY >= 0 && pixelY < height) {
              Pixel pixel = image.getPixel(pixelX, pixelY);
              double kernelValue = filterOption.getKernel()[ky + radius][kx + radius];

              redSum += pixel.getRed() * kernelValue;
              greenSum += pixel.getGreen() * kernelValue;
              blueSum += pixel.getBlue() * kernelValue;
            }
          }
        }
        newPixelArray[x][y] = image.getPixel(x, y)
                                   .createPixel((int) redSum, (int) greenSum, (int) blueSum);
      }
    }

    return Factory.createImage(newPixelArray);
  }
}
