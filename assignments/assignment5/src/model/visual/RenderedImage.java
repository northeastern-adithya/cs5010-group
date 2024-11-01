package model.visual;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import factories.Factory;
import model.pixels.Pixel;
import model.pixels.RGB;

/**
 * RenderedImage class that implements Image interface
 * and represents an image containing pixels.
 */
public class RenderedImage implements Image {
  /**
   * The pixel array of the image.
   * The first index represents the x-coordinate of the pixel.
   * The second index represents the y-coordinate of the pixel.
   * Images are a combination of pixels.
   */
  private final Pixel[][] pixels;

  /**
   * Constructs a RenderedImage object with the given pixel array.
   *
   * @param pixels the pixel array of the image
   * @throws NullPointerException     if the pixel array is null.
   * @throws IllegalArgumentException if the pixel array is empty.
   */
  public RenderedImage(Pixel[][] pixels) {
    Objects.requireNonNull(pixels, "Pixel array cannot be null");
    if (pixels.length == 0 || pixels[0].length == 0) {
      throw new IllegalArgumentException("Pixel array cannot be empty");
    }
    this.pixels = pixels;
  }

  @Override
  public Pixel getPixel(int x, int y) {
    return pixels[x][y];
  }


  @Override
  public int getWidth() {
    return pixels.length;
  }

  @Override
  public int getHeight() {
    return pixels[0].length;
  }

  @Override
  public Image createRedComponent() {
    return transformImage(Pixel::createRedComponent);
  }

  @Override
  public Image createGreenComponent() {
    return transformImage(Pixel::createGreenComponent);
  }

  @Override
  public Image createBlueComponent() {
    return transformImage(Pixel::createBlueComponent);
  }

  @Override
  public Image adjustImageBrightness(int factor) {
    return transformImage(pixel -> pixel.adjustBrightness(factor));
  }

  @Override
  public Image getLuma() {
    return transformImage(Pixel::getLuma);
  }

  @Override
  public Image getSepia() {
    return transformImage(Pixel::getSepia);
  }

  @Override
  public Image getIntensity() {
    return transformImage(Pixel::getIntensity);
  }

  @Override
  public Image getValue() {
    return transformImage(Pixel::getValue);
  }

  @Override
  public String toString() {
    return "RenderedImage{"
            + "pixels=" + Arrays.deepToString(pixels)
            + '}';
  }

  @Override
  public Image horizontalFlip() {
    int height = this.getHeight();
    int width = this.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = this.getPixel(width - x - 1, y);
      }
    }
    return Factory.createImage(newPixelArray);
  }

  @Override
  public Image verticalFlip() {
    int height = this.getHeight();
    int width = this.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = this.getPixel(x, height - y - 1);
      }
    }
    return Factory.createImage(newPixelArray);
  }

  /**
   * Helper method to transform the image using the given transformation.
   * Transformation is applied to each pixel in the image.
   */
  private Image transformImage(Function<Pixel, Pixel> transformation) {
    int height = this.getHeight();
    int width = this.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = transformation.apply(this.getPixel(x, y));
      }
    }
    return new RenderedImage(newPixelArray);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Image)) {
      return false;
    }
    if (obj instanceof RenderedImage) {
      RenderedImage that = (RenderedImage) obj;
      return that.equalsRenderedImage(this);
    }
    return obj.equals(this);
  }

  protected boolean equalsRenderedImage(RenderedImage that) {
    return Arrays.deepEquals(this.pixels, that.pixels);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(pixels);
  }


  @Override
  public Image createHistogram() {
    // Create frequency arrays for each channel (0-255)
    int[] redFreq = new int[256];
    int[] greenFreq = new int[256];
    int[] blueFreq = new int[256];

    // Count frequencies
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        Pixel pixel = getPixel(x, y);
        redFreq[pixel.getRed()]++;
        greenFreq[pixel.getGreen()]++;
        blueFreq[pixel.getBlue()]++;
      }
    }

    // Find maximum frequency for scaling
    int maxFreq = 0;
    for (int i = 0; i < 256; i++) {
      maxFreq = Math.max(maxFreq, redFreq[i]);
      maxFreq = Math.max(maxFreq, greenFreq[i]);
      maxFreq = Math.max(maxFreq, blueFreq[i]);
    }

    // Create 256x256 histogram image
    Pixel[][] histogramPixels = new Pixel[256][256];

    // Initialize with white background and gray grid
    for (int x = 0; x < 256; x++) {
      for (int y = 0; y < 256; y++) {
        // Add grid lines every 32 pixels
        if (x % 32 == 0 || y % 32 == 0) {
          histogramPixels[x][y] = new RGB(240, 240, 240); // Light gray
        } else {
          histogramPixels[x][y] = new RGB(255, 255, 255); // White
        }
      }
    }

    // Draw the histogram lines
    for (int x = 0; x < 255; x++) {
      // Calculate scaled heights for current and next points
      int redHeight1 = (int)((redFreq[x] * 255.0) / maxFreq);
      int redHeight2 = (int)((redFreq[x + 1] * 255.0) / maxFreq);
      int greenHeight1 = (int)((greenFreq[x] * 255.0) / maxFreq);
      int greenHeight2 = (int)((greenFreq[x + 1] * 255.0) / maxFreq);
      int blueHeight1 = (int)((blueFreq[x] * 255.0) / maxFreq);
      int blueHeight2 = (int)((blueFreq[x + 1] * 255.0) / maxFreq);

      // Draw lines between consecutive points
      drawLine(histogramPixels, x, 255 - redHeight1, x + 1, 255 - redHeight2,
              new RGB(255, 0, 0)); // Red channel
      drawLine(histogramPixels, x, 255 - greenHeight1, x + 1, 255 - greenHeight2,
              new RGB(0, 255, 0)); // Green channel
      drawLine(histogramPixels, x, 255 - blueHeight1, x + 1, 255 - blueHeight2,
              new RGB(0, 0, 255)); // Blue channel
    }

    return Factory.createImage(histogramPixels);
  }

  // Helper method to draw a line between two points using Bresenham's algorithm
  private void drawLine(Pixel[][] pixels, int x1, int y1, int x2, int y2, Pixel color) {
    int dx = Math.abs(x2 - x1);
    int dy = Math.abs(y2 - y1);
    int sx = x1 < x2 ? 1 : -1;
    int sy = y1 < y2 ? 1 : -1;
    int err = dx - dy;

    while (true) {
      if (x1 >= 0 && x1 < 256 && y1 >= 0 && y1 < 256) {
        pixels[x1][y1] = color;
      }

      if (x1 == x2 && y1 == y2) {
        break;
      }

      int e2 = 2 * err;
      if (e2 > -dy) {
        err = err - dy;
        x1 = x1 + sx;
      }
      if (e2 < dx) {
        err = err + dx;
        y1 = y1 + sy;
      }
    }
  }
}