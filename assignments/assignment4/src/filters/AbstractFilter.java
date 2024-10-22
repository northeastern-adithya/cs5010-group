package filters;

import factories.PixelFactory;
import model.color.Pixel;
import model.color.RGB;
import model.visual.Image;
import model.visual.RenderedImage;

public class AbstractFilter implements Filter {

  protected final FilterOptions filterOption;

  protected AbstractFilter(FilterOptions filterOption) {
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

        // Apply kernel multiplication for each channel
        for (int ky = -radius; ky <= radius; ky++) {
          for (int kx = -radius; kx <= radius; kx++) {
            int pixelX = x + kx;
            int pixelY = y + ky;

            // Only process if pixel is within image bounds
            if (pixelX >= 0 && pixelX < width && pixelY >= 0 && pixelY < height) {
              Pixel pixel = image.getPixel(pixelX, pixelY);
              double kernelValue = filterOption.getKernel()[ky + radius][kx + radius];

              redSum += pixel.getRed() * kernelValue;
              greenSum += pixel.getGreen() * kernelValue;
              blueSum += pixel.getBlue() * kernelValue;
            }
          }
        }
        newPixelArray[x][y] = PixelFactory.createRGBPixel((int) redSum, (int) greenSum, (int) blueSum); // Pixel Factory
      }
    }

    return new RenderedImage(newPixelArray);
  }
}
