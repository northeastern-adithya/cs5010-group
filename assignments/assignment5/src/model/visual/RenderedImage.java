package model.visual;

import java.awt.Graphics2D;
import java.awt.Color;
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
    int width = getWidth();
    int height = getHeight();

    // Create frequency arrays for each channel (0-255)
    int[] redFreq = new int[256];
    int[] greenFreq = new int[256];
    int[] blueFreq = new int[256];

    // Count frequencies
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
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

    // Create BufferedImage for histogram
    BufferedImage histogramImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = histogramImage.createGraphics();

    // Initialize with white background and gray grid
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 256, 256);
    g2d.setColor(Color.LIGHT_GRAY);
    for (int i = 0; i <= 256; i += 13) {
      g2d.drawLine(i, 0, i, 256);
      g2d.drawLine(0, i, 256, i);
    }

    // Draw the histogram lines
    for (int x = 0; x < 255; x++) {
      int redHeight1 = (int)((redFreq[x] * 255.0) / maxFreq);
      int redHeight2 = (int)((redFreq[x + 1] * 255.0) / maxFreq);
      int greenHeight1 = (int)((greenFreq[x] * 255.0) / maxFreq);
      int greenHeight2 = (int)((greenFreq[x + 1] * 255.0) / maxFreq);
      int blueHeight1 = (int)((blueFreq[x] * 255.0) / maxFreq);
      int blueHeight2 = (int)((blueFreq[x + 1] * 255.0) / maxFreq);

      g2d.setColor(Color.RED);
      g2d.drawLine(x, 255 - redHeight1, x + 1, 255 - redHeight2);
      g2d.setColor(Color.GREEN);
      g2d.drawLine(x, 255 - greenHeight1, x + 1, 255 - greenHeight2);
      g2d.setColor(Color.BLUE);
      g2d.drawLine(x, 255 - blueHeight1, x + 1, 255 - blueHeight2);
    }

    g2d.dispose();

    // Convert BufferedImage to Pixel array
    Pixel[][] histogramPixels = new Pixel[256][256];
    for (int x = 0; x < 256; x++) {
      for (int y = 0; y < 256; y++) {
        int rgb = histogramImage.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        histogramPixels[x][y] = new RGB(red, green, blue);
      }
    }

    return Factory.createImage(histogramPixels);
  }

  @Override
  public Image colorCorrect() {
    int width = getWidth();
    int height = getHeight();

    // Create arrays to store frequency of each color value
    int[] redFreq = new int[256];
    int[] greenFreq = new int[256];
    int[] blueFreq = new int[256];

    // Count frequencies for each channel
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel pixel = getPixel(x, y);
        redFreq[pixel.getRed()]++;
        greenFreq[pixel.getGreen()]++;
        blueFreq[pixel.getBlue()]++;
      }
    }

    // Find meaningful peaks (ignoring extremities)
    int redPeak = findMeaningfulPeak(redFreq);
    int greenPeak = findMeaningfulPeak(greenFreq);
    int bluePeak = findMeaningfulPeak(blueFreq);

    // Calculate average peak position
    int avgPeak = (redPeak + greenPeak + bluePeak) / 3;

    // Calculate offsets for each channel
    int redOffset = avgPeak - redPeak;
    int greenOffset = avgPeak - greenPeak;
    int blueOffset = avgPeak - bluePeak;

    // Create new image with adjusted colors
    Pixel[][] newPixels = new Pixel[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel oldPixel = getPixel(x, y);
        int newRed = clamp(oldPixel.getRed() + redOffset);
        int newGreen = clamp(oldPixel.getGreen() + greenOffset);
        int newBlue = clamp(oldPixel.getBlue() + blueOffset);
        newPixels[x][y] = new RGB(newRed, newGreen, newBlue);
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
   * Clamps a value between 0 and 255.
   *
   * @param value the value to clamp
   * @return the clamped value
   */
  private int clamp(int value) {
    return Math.max(0, Math.min(255, value));
  }
}