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
  public Image createHistogram() {
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

    int maxFreq = 0;
    for (int i = 0; i < 256; i++) {
      maxFreq = Math.max(maxFreq, redFreq[i]);
      maxFreq = Math.max(maxFreq, greenFreq[i]);
      maxFreq = Math.max(maxFreq, blueFreq[i]);
    }

    BufferedImage histogramImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = histogramImage.createGraphics();

    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 256, 256);
    g2d.setColor(Color.LIGHT_GRAY);
    for (int i = 0; i <= 256; i += 13) {
      g2d.drawLine(i, 0, i, 256);
      g2d.drawLine(0, i, 256, i);
    }

    for (int x = 0; x < 255; x++) {
      int currRedHeight = (int)((redFreq[x] * 255.0) / maxFreq);
      int nextRedHeight = (int)((redFreq[x + 1] * 255.0) / maxFreq);
      int currGreenHeight = (int)((greenFreq[x] * 255.0) / maxFreq);
      int nextGreenHeight = (int)((greenFreq[x + 1] * 255.0) / maxFreq);
      int currBlueHeight = (int)((blueFreq[x] * 255.0) / maxFreq);
      int nextBlueHeight = (int)((blueFreq[x + 1] * 255.0) / maxFreq);

      g2d.setColor(Color.RED);
      g2d.drawLine(x, 255 - currRedHeight, x + 1, 255 - nextRedHeight);
      g2d.setColor(Color.GREEN);
      g2d.drawLine(x, 255 - currGreenHeight, x + 1, 255 - nextGreenHeight);
      g2d.setColor(Color.BLUE);
      g2d.drawLine(x, 255 - currBlueHeight, x + 1, 255 - nextBlueHeight);
    }

    g2d.dispose();

    Pixel[][] histogramPixels = new Pixel[256][256];
    for (int y = 0; y < 256; y++) {
      for (int x = 0; x < 256; x++) {
        int rgb = histogramImage.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        histogramPixels[y][x] = new RGB(red, green, blue);
      }
    }

    return Factory.createImage(histogramPixels);
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
        int newRed = clamp(oldPixel.getRed() + redOffset);
        int newGreen = clamp(oldPixel.getGreen() + greenOffset);
        int newBlue = clamp(oldPixel.getBlue() + blueOffset);
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

  @Override
  public Image levelsAdjust(int black, int mid, int white) {
    if (black < 0 || black > 255 || mid < 0 || mid > 255 || white < 0 || white > 255) {
      throw new IllegalArgumentException("Level adjustment values must be between 0 and 255");
    }
    if (black >= mid || mid >= white) {
      throw new IllegalArgumentException("Values must be in ascending order: black < mid < white");
    }

    double A = Math.pow(black, 2) * (mid - white) - black * (Math.pow(mid, 2) - Math.pow(white, 2))
                + Math.pow(mid, 2) * white - Math.pow(white, 2) * mid;
    double Aa = (-black) * (128 - 255) + 128 * white - 255 * mid;
    double Ab = Math.pow(black, 2) * (128 - 255) + 255 * Math.pow(mid, 2) - 128 * Math.pow(white, 2);
    double Ac = Math.pow(black, 2) * (255 * mid - 128 * white)
                - black * (255 * Math.pow(mid, 2) - 128 * Math.pow(white, 2));

    double ac = Aa / A;
    double bc = Ab / A;
    double cc = Ac / A;

    int height = this.getHeight();
    int width = this.getWidth();
    Pixel[][] adjustedPixels = new Pixel[height][width];

    for (int column = 0; column < width; column++) {
      for (int row = 0; row < height; row++) {
        Pixel original = this.getPixel(row, column);

        int newRed = applyQuadraticTransform(original.getRed(), ac, bc, cc);
        int newGreen = applyQuadraticTransform(original.getGreen(), ac, bc, cc);
        int newBlue = applyQuadraticTransform(original.getBlue(), ac, bc, cc);

        adjustedPixels[row][column] = new RGB(newRed, newGreen, newBlue);
      }
    }

    return Factory.createImage(adjustedPixels);
  }

  /**
   * Applies the quadratic transformation to a single color value.
   *
   * @param value The original color value
   * @param coeffA Quadratic coefficient a
   * @param coeffB Quadratic coefficient b
   * @param coeffC Quadratic coefficient c
   * @return The transformed color value, clamped between 0 and 255
   */
  private int applyQuadraticTransform(int value, double coeffA, double coeffB, double coeffC) {
    double result = coeffA * Math.pow(value, 2) + coeffB * value + coeffC;

    return clamp((int) Math.round(result));
  }

  /**
   * Clamps a value between 0 and 255.
   *
   * @param value the value to clamp
   * @return the clamped value
   */
  private int clamp(int value) {
    return Math.max(0, Math.min(255, value));
  }
}