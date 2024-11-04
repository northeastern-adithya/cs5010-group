package model.visual;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;


import factories.Factory;
import model.pixels.Pixel;

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
    return pixels[0].length;
  }

  @Override
  public int getHeight() {
    return pixels.length;
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
    Pixel[][] newPixelArray = new Pixel[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        newPixelArray[row][col] = this.getPixel(row, width - col - 1);
      }
    }

    return Factory.createImage(newPixelArray);
  }

  @Override
  public Image verticalFlip() {
    int height = this.getHeight();
    int width = this.getWidth();
    Pixel[][] newPixelArray = new Pixel[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        newPixelArray[row][col] = this.getPixel(height - row - 1, col);
      }
    }
    return Factory.createImage(newPixelArray);
  }

  @Override
  public int[][] getRedChannel() {
    return getChannel(Pixel::getRed);
  }

  @Override
  public int[][] getGreenChannel() {
    return getChannel(Pixel::getGreen);
  }

  @Override
  public int[][] getBlueChannel() {
    return getChannel(Pixel::getBlue);
  }

  /**
   * Helper method to transform the image using the given transformation.
   * Transformation is applied to each pixel in the image.
   */
  private Image transformImage(Function<Pixel, Pixel> transformation) {
    int height = this.getHeight();
    int width = this.getWidth();
    Pixel[][] newPixelArray = new Pixel[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        newPixelArray[row][col] = transformation.apply(this.getPixel(row, col));
      }
    }
    return new RenderedImage(newPixelArray);
  }

  /**
   * Helper method to get the channel of the image.
   * Channel is extracted from each pixel in the image.
   */
  private int[][] getChannel(Function<Pixel, Integer> channel) {
    int height = this.getHeight();
    int width = this.getWidth();
    int[][] channelArray = new int[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        channelArray[row][col] = channel.apply(this.getPixel(row, col));
      }
    }
    return channelArray;
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
}