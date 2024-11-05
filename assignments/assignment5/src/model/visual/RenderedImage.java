package model.visual;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;


import factories.Factory;
import model.pixels.Pixel;
import model.pixels.RGB;
import utility.ExtractUtility;

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

  @Override
  public Image colorCorrect() {
    int width = getWidth();
    int height = getHeight();

    int[] redFreq = new int[256];
    int[] greenFreq = new int[256];
    int[] blueFreq = new int[256];

    for (int column = 0; column < width; column++) {
      for (int row = 0; row < height; row++) {
        Pixel pixel = getPixel(row, column);
        redFreq[pixel.getRed()]++;
        greenFreq[pixel.getGreen()]++;
        blueFreq[pixel.getBlue()]++;
      }
    }

    int redPeak = findMeaningfulPeak(redFreq);
    int greenPeak = findMeaningfulPeak(greenFreq);
    int bluePeak = findMeaningfulPeak(blueFreq);

    int avgPeak = (redPeak + greenPeak + bluePeak) / 3;

    int redOffset = avgPeak - redPeak;
    int greenOffset = avgPeak - greenPeak;
    int blueOffset = avgPeak - bluePeak;

    Pixel[][] newPixels = new Pixel[height][width];
    for (int column = 0; column < width; column++) {
      for (int row = 0; row < height; row++) {
        Pixel oldPixel = getPixel(row, column);
        int newRed = oldPixel.getRed() + redOffset;
        int newGreen = oldPixel.getGreen() + greenOffset;
        int newBlue = oldPixel.getBlue() + blueOffset;
        newPixels[row][column] = new RGB(newRed, newGreen, newBlue);
      }
    }

    return Factory.createImage(newPixels);
  }

  /**
   * Finds the meaningful peak in a frequency array, ignoring extremities.
   * Only considers values between 10 and 245 to avoid dark/blown-out regions.
   *
   * @param freq the frequency array
   * @return the value where the meaningful peak occurs
   */
  private int findMeaningfulPeak(int[] freq) {
    int maxFreq = 0;
    int peakValue = 0;

    // Only consider values between 10 and 245
    for (int i = 10; i < 245; i++) {
      if (freq[i] > maxFreq) {
        maxFreq = freq[i];
        peakValue = i;
      }
    }

    return peakValue;
  }

  /**
   * Adjusts the levels of the image using the specified black, mid, and white points.
   * Quadratic Transformation used
   * <a href="https://northeastern.instructure.com/courses/192553/assignments/2490204">...</a>
   *
   * @param black the black point value (0-255)
   * @param mid the mid point value (0-255)
   * @param white the white point value (0-255)
   * @return a new Image with adjusted levels
   * @throws IllegalArgumentException if any of the values are out of range (0-255)
   *                                  or not in ascending order
   */
  @Override
  public Image levelsAdjust(int black, int mid, int white) throws IllegalArgumentException {
    validateLevels(black, mid, white);

    double a = fittingCoefficientA(black, mid, white);
    double aA = fittingCoefficientAa(black, mid, white);
    double aB = fittingCoefficientAb(black, mid, white);
    double aC = fittingCoefficientAc(black, mid, white);

    double coeffA = aA / a;
    double coeffB = aB / a;
    double coeffC = aC / a;

    int height = this.getHeight();
    int width = this.getWidth();
    Pixel[][] adjustedPixels = new Pixel[height][width];

    for (int column = 0; column < width; column++) {
      for (int row = 0; row < height; row++) {
        adjustedPixels[row][column] = this.getPixel(row, column)
                                          .quadraticTransform(coeffA, coeffB, coeffC);
      }
    }

    return Factory.createImage(adjustedPixels);
  }

  /**
   * Validates the black, mid, and white points for levels adjustment.
   *
   * @param black the black point value
   * @param mid   the mid point value
   * @param white the white point value
   * @throws IllegalArgumentException if any of the values are out of range (0-255)
   *                                  or not in ascending order
   */
  private void validateLevels(int black, int mid, int white)
          throws IllegalArgumentException {
    if (black < 0 || black > 255 || mid < 0 || mid > 255 || white < 0 || white > 255) {
      throw new IllegalArgumentException("Levels must be between 0 and 255");
    }
    if (black >= mid || mid >= white) {
      throw new IllegalArgumentException("Levels must be in ascending order");
    }
  }

  private double fittingCoefficientA(int black, int mid, int white) {
    return Math.pow(black, 2) * (mid - white)
            - black * (Math.pow(mid, 2) - Math.pow(white, 2))
            + Math.pow(mid, 2) * white - Math.pow(white, 2) * mid;
  }

  private double fittingCoefficientAa(int black, int mid, int white) {
    return (-black) * (128 - 255) + 128 * white - 255 * mid;
  }

  private double fittingCoefficientAb(int black, int mid, int white) {
    return Math.pow(black, 2) * (128 - 255) +
            255 * Math.pow(mid, 2) - 128 * Math.pow(white, 2);
  }

  private double fittingCoefficientAc(int black, int mid, int white) {
    return Math.pow(black, 2) * (255 * mid - 128 * white)
            - black * (255 * Math.pow(mid, 2) - 128 * Math.pow(white, 2));
  }
}