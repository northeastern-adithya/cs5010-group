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

  private static int[][] fromDoubleArray(double[][] data) {
    int[][] cleanedData = new int[data.length][data[0].length];
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        cleanedData[i][j] = (int) data[i][j];
      }
    }
    return cleanedData;
  }

  private static double[][] transform(double[][] sequence) {
    double[][] paddedSequence = padToSquareMatrix(sequence);
    int length = paddedSequence.length;
    double[][] transformed = Arrays.copyOf(paddedSequence, length);
    while (length > 1) {
      for (int i = 0; i < length; i++) {
        double[] rowSequenceSubset =
                transformSequenceSubset(transformed[i], length);
        System.arraycopy(rowSequenceSubset, 0, transformed[i], 0, length);
      }

      for (int j = 0; j < length; j++) {
        double[] columnSubset = new double[length];
        for (int i = 0; i < length; i++) {
          columnSubset[i] = transformed[i][j];
        }
        double[] columnSequenceSubset = transformSequenceSubset(columnSubset,
                length);

        for (int i = 0; i < length; i++) {
          transformed[i][j] = columnSequenceSubset[i];
        }
      }
      length /= 2;
    }

    return transformed;
  }

  private static double[][] invert(double[][] sequence) {
    int length = 2;
    double[][] inverted = Arrays.copyOf(sequence, sequence.length);
    while (length <= inverted.length) {
      for (int j = 0; j < length; j++) {
        double[] columnSubset = new double[length];
        for (int i = 0; i < length; i++) {
          columnSubset[i] = inverted[i][j];
        }
        double[] invertedColumn = invertSequenceSubset(columnSubset, length);
        for (int i = 0; i < length; i++) {
          inverted[i][j] = invertedColumn[i];
        }
      }
      for (int i = 0; i < length; i++) {
        double[] rowSubset = Arrays.copyOfRange(inverted[i], 0, length);
        double[] invertedRow = invertSequenceSubset(rowSubset, length);
        System.arraycopy(invertedRow, 0, inverted[i], 0, length);
      }
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


  private static double[][] padToSquareMatrix(double[][] sequence) {
    int row = sequence.length;
    int column = sequence[0].length;
    int newLength = getNearestPowerOfTwo(Math.max(row, column));
    double[][] paddedSequence = new double[newLength][newLength];
    for (int i = 0; i < row; i++) {
      System.arraycopy(sequence[i], 0, paddedSequence[i], 0, column);
    }
    return paddedSequence;
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


  private static double[][] toDoubleArray(int[][] data) {
    double[][] doubleArray = new double[data.length][data[0].length];
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        doubleArray[i][j] = data[i][j];
      }
    }
    return doubleArray;
  }

  @Override
  public Image compress(Image image, int percentage) throws ImageProcessorException {
    validatePercentage(percentage);
    Objects.requireNonNull(image, "Image cannot be null");
    int[][] compressedRed = compress(image.getRedChannel(), percentage);
    int[][] compressedGreen = compress(image.getGreenChannel(), percentage);
    int[][] compressedBlue = compress(image.getBlueChannel(), percentage);
    int height = image.getHeight();
    int width = image.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = Factory.createRGBPixel(compressedRed[y][x],
                compressedGreen[y][x], compressedBlue[y][x]);
      }
    }
    return Factory.createImage(newPixelArray);
  }


  private int[][] compress(int[][] data, int percentage) throws ImageProcessorException {
    validatePercentage(percentage);
    double[][] transformed = transform(toDoubleArray(data));
    double[][] dataWithThreshold = computeSequenceWithThreshold(transformed,
            percentage);
    double[][] invert = invert(dataWithThreshold);
    return fromDoubleArray(invert);
  }

  private double[][] computeSequenceWithThreshold(double[][] data,
                                                  int percentage) {
    int height = data.length;
    int width = data[0].length;
    double[][] dataWithThreshold = Arrays.copyOf(data, height);
    for (int i = 0; i < height; i++) {
      double thresholdValue = getThresholdValue(data[i], percentage);
      for (int j = 0; j < width; j++) {
        if (Math.abs(data[i][j]) < thresholdValue) {
          dataWithThreshold[i][j] = 0;
        }
      }
    }
    return dataWithThreshold;
  }

  private double getThresholdValue(double[] data,int percentage){
    int length = data.length;
    int thresholdIndex = (int) Math.ceil((double) (length * percentage) / 100);
    double[] sortedData = Arrays.copyOf(data, length);
    for(int i = 0; i < length; i++){
      sortedData[i] = Math.abs(sortedData[i]);
    }
    Arrays.sort(sortedData);
    return sortedData[thresholdIndex];
  }

}
