package utility;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.function.Function;

import factories.Factory;
import model.pixels.Pixel;
import model.pixels.RGB;
import model.visual.Image;

/**
 * Utility class that contains methods to extract information from an image.
 */
public class ExtractUtility {
  private static final int HISTOGRAM_SIZE = 256;

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private ExtractUtility() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Creates a histogram image from the current image.
   *
   * @return a new Image representing the histogram of the current image
   */
  public static Image createHistogram(Image image) {
    int[] redFreq = calculateColorFrequencies(image, Pixel::getRed);
    int[] greenFreq = calculateColorFrequencies(image, Pixel::getGreen);
    int[] blueFreq = calculateColorFrequencies(image, Pixel::getBlue);

    int maxFreq = findMaxFrequency(redFreq, greenFreq, blueFreq);

    BufferedImage histogramImage = createHistogramImage(redFreq, greenFreq,
            blueFreq, maxFreq);

    Pixel[][] histogramPixels = convertBufferedImageToPixels(histogramImage);

    return Factory.createImage(histogramPixels);
  }

  private static int[] calculateColorFrequencies(Image image, Function<Pixel,
          Integer> transformation) {
    int[] colorFrequencies = new int[HISTOGRAM_SIZE];
    for (int column = 0; column < image.getWidth(); column++) {
      for (int row = 0; row < image.getHeight(); row++) {
        Pixel pixel = image.getPixel(row, column);
        colorFrequencies[transformation.apply(pixel)]++;
      }
    }
    return colorFrequencies;
  }

  /**
   * Finds the maximum frequency among the red, green, and blue frequency
   * arrays.
   *
   * @param redFreq   the frequency array for the red channel
   * @param greenFreq the frequency array for the green channel
   * @param blueFreq  the frequency array for the blue channel
   * @return the maximum frequency value among the three channels
   */
  private static int findMaxFrequency(int[] redFreq, int[] greenFreq,
                                      int[] blueFreq) {
    int maxFreq = 0;
    for (int i = 0; i < HISTOGRAM_SIZE; i++) {
      maxFreq = Math.max(maxFreq, redFreq[i]);
      maxFreq = Math.max(maxFreq, greenFreq[i]);
      maxFreq = Math.max(maxFreq, blueFreq[i]);
    }
    return maxFreq;
  }

  /**
   * Creates a histogram image from the red, green, and blue frequency arrays.
   *
   * @param redFreq   the frequency array for the red channel
   * @param greenFreq the frequency array for the green channel
   * @param blueFreq  the frequency array for the blue channel
   * @param maxFreq   the maximum frequency value among the three channels
   * @return a new BufferedImage representing the histogram
   */
  private static BufferedImage createHistogramImage(int[] redFreq,
                                                    int[] greenFreq,
                                                    int[] blueFreq,
                                                    int maxFreq) {
    BufferedImage histogramImage = new BufferedImage(HISTOGRAM_SIZE,
            HISTOGRAM_SIZE, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = histogramImage.createGraphics();

    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, HISTOGRAM_SIZE, HISTOGRAM_SIZE);
    g2d.setColor(Color.LIGHT_GRAY);
    for (int i = 0; i <= HISTOGRAM_SIZE; i += 13) {
      g2d.drawLine(i, 0, i, 256);
      g2d.drawLine(0, i, 256, i);
    }

    for (int x = 0; x < 255; x++) {
      int currRedHeight = (int) ((redFreq[x] * 255.0) / maxFreq);
      int nextRedHeight = (int) ((redFreq[x + 1] * 255.0) / maxFreq);
      int currGreenHeight = (int) ((greenFreq[x] * 255.0) / maxFreq);
      int nextGreenHeight = (int) ((greenFreq[x + 1] * 255.0) / maxFreq);
      int currBlueHeight = (int) ((blueFreq[x] * 255.0) / maxFreq);
      int nextBlueHeight = (int) ((blueFreq[x + 1] * 255.0) / maxFreq);

      g2d.setColor(Color.RED);
      g2d.drawLine(x, 255 - currRedHeight, x + 1, 255 - nextRedHeight);
      g2d.setColor(Color.GREEN);
      g2d.drawLine(x, 255 - currGreenHeight, x + 1, 255 - nextGreenHeight);
      g2d.setColor(Color.BLUE);
      g2d.drawLine(x, 255 - currBlueHeight, x + 1, 255 - nextBlueHeight);
    }

    g2d.dispose();
    return histogramImage;
  }

  /**
   * Converts a BufferedImage to a 2D array of Pixels.
   *
   * @param histogramImage the BufferedImage to convert
   * @return a 2D array of Pixels representing the image
   */
  private static Pixel[][] convertBufferedImageToPixels(BufferedImage histogramImage) {
    Pixel[][] histogramPixels = new Pixel[HISTOGRAM_SIZE][HISTOGRAM_SIZE];
    for (int y = 0; y < HISTOGRAM_SIZE; y++) {
      for (int x = 0; x < HISTOGRAM_SIZE; x++) {
        int rgb = histogramImage.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        histogramPixels[y][x] = new RGB(red, green, blue);
      }
    }
    return histogramPixels;
  }
}
