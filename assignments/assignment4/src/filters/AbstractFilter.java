package filters;

import java.util.Objects;

import factories.ImageFactory;
import model.pixels.Pixel;
import model.visual.Image;

/**
 * Abstract class for applying filters to an image.
 * Provides a method to apply a filter to an image.
 */
public class AbstractFilter implements Filter {

  /**
   * The filter option to apply.
   */
  protected final FilterOption filterOption;

  /**
   * Constructs an abstract filter with the given filter option.
   * @param filterOption the filter option to apply
   */
  protected AbstractFilter(FilterOption filterOption) {
    Objects.requireNonNull(filterOption);
    this.filterOption = filterOption;
  }


  @Override
  public Image applyFilter(Image image) {
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
        newPixelArray[x][y] = image.getPixel(x,y).createPixel((int) redSum,(int) greenSum,(int) blueSum); // Pixel Factory
      }
    }

    return ImageFactory.createImage(newPixelArray);
  }
}
