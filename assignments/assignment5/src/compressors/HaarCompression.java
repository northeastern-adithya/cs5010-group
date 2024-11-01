package compressors;

import java.util.Arrays;
import java.util.Objects;

import exception.ImageProcessorException;
import factories.Factory;
import model.pixels.Pixel;
import model.visual.Image;

public class HaarCompression implements Compression {

  public HaarCompression() {
  }

  private static int[] fromDoubleArray(double[] data) {
    int[] cleanedData = new int[data.length];
    for (int i = 0; i < data.length; i++) {
      cleanedData[i] = (int) data[i];
    }
    return cleanedData;
  }

  private static double[] transform(double[] sequence) {
    double[] paddedSequence = paddedSequence(sequence);
    int length = paddedSequence.length;
    double[] transformed = Arrays.copyOf(paddedSequence, length);
    while (length > 1) {
      double[] sequenceSubset = transformSequenceSubset(transformed, length);
      System.arraycopy(sequenceSubset, 0, transformed, 0, length);
      length /= 2;
    }
    return transformed;
  }

  private static double[] invert(double[] sequence) {
    double[] inverted = Arrays.copyOf(sequence, sequence.length);
    int length = 2;
    while (length <= sequence.length) {
      double[] sequenceSubset = invertSequenceSubset(inverted, length);
      System.arraycopy(sequenceSubset, 0, inverted, 0, length);
      length *= 2;
    }
    return inverted;
  }

  /**
   * Validates the given percentage for image compression.
   * Percentage must be between 0 and 100 exclusive.
   *
   * @param percentage the percentage to compress the image by
   * @throws ImageProcessorException if the percentage is invalid
   */
  private static void validatePercentage(int percentage) throws ImageProcessorException {
    if (percentage <= 0 || percentage >= 100) {
      throw new ImageProcessorException("Invalid compression percentage");
    }
  }

  private static double[] transformSequenceSubset(double[] sequence,
                                                  int length) {
    double[] transformed = new double[length];
    int halfLength = length / 2;
    for (int i = 0; i < halfLength; i++) {
      double firstValue = sequence[2 * i];
      double secondValue = sequence[2 * i + 1];
      transformed[i] = (firstValue + secondValue) / Math.sqrt(2);
      transformed[halfLength + i] = (firstValue - secondValue) / Math.sqrt(2);
    }
    return transformed;
  }

  private static double[] paddedSequence(double[] sequence) {
    int length = sequence.length;
    int paddedLength = getNearestPowerOfTwo(length);
    return Arrays.copyOf(sequence, paddedLength);
  }

  private static int getNearestPowerOfTwo(int number) {
    if (number <= 0) {
      return 1;
    }
    if ((number & (number - 1)) == 0) {
      return number;
    }

    int power = 1;
    while (power < number) {
      power *= 2;
    }
    return power;
  }

  private static double[] invertSequenceSubset(double[] sequence, int length) {
    double[] inverted = new double[length];
    int halfLength = length / 2;
    for (int i = 0; i < halfLength; i++) {
      double firstValue = sequence[i];
      double secondValue = sequence[halfLength + i];
      inverted[2 * i] = (firstValue + secondValue) / Math.sqrt(2);
      inverted[2 * i + 1] = (firstValue - secondValue) / Math.sqrt(2);
    }
    return inverted;
  }


  @Override
  public Image compress(Image image, int percentage) throws ImageProcessorException {
    validatePercentage(percentage);
    Objects.requireNonNull(image, "Image cannot be null");
    int[] compressedRed = compress(image.getRedChannel(), percentage);
    int[] compressedGreen = compress(image.getGreenChannel(), percentage);
    int[] compressedBlue = compress(image.getBlueChannel(), percentage);
    int height = image.getHeight();
    int width = image.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int index = y * width + x;
        int red = compressedRed[index];
        int green = compressedGreen[index];
        int blue = compressedBlue[index];
        newPixelArray[x][y] = image.getPixel(x, y).createPixel(red, green, blue);
      }
    }
    return Factory.createImage(newPixelArray);
  }

  private int[][] compress(int[][] data, int percentage) throws ImageProcessorException {
    validatePercentage(percentage);
    int[][] compressedData = new int[data.length][];
    for (int i = 0; i < data.length; i++) {
      compressedData[i] = compress(data[i], percentage);
    }
    return compressedData;
  }


  private int[] compress(int[] data, int percentage) throws ImageProcessorException {
    validatePercentage(percentage);
    double[] transformed = transform(toDoubleArray(data));
    double[] sortedTransformed = Arrays.copyOf(transformed, transformed.length);
    Arrays.sort(sortedTransformed);
    int thresholdIndex =
            (int) Math.ceil((percentage / 100.0) * sortedTransformed.length);
    double threshold = sortedTransformed[thresholdIndex];
    for (int i = 0; i < transformed.length; i++) {
      if (Math.abs(transformed[i]) <= threshold) {
        transformed[i] = 0;
      }
    }
    double[] invertedData = invert(transformed);
    return fromDoubleArray(invertedData);
  }

  private static double[] toDoubleArray(int[] data) {
    double[] doubleArray = new double[data.length];
    for (int i = 0; i < data.length; i++) {
      doubleArray[i] = data[i];
    }
    return doubleArray;
  }
}
