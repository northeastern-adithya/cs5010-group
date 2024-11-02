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


  private static double[][] haar(double[][] data) {
    double[][] squareMatrix = padToSquareMatrix(data);
    int squareMatrixLength = squareMatrix.length;

    double[][] haarTransformData = Arrays.copyOf(squareMatrix,
            squareMatrixLength);

    while (squareMatrixLength > 1) {

      for (int row = 0; row < squareMatrixLength; row++) {

        double[] transformedSubset =
                transform(haarTransformData[row], squareMatrixLength);
        System.arraycopy(transformedSubset, 0, haarTransformData[row], 0,
                squareMatrixLength);
      }

      for (int column = 0; column < squareMatrixLength; column++) {
        double[] columnSubset = new double[squareMatrixLength];
        for (int i = 0; i < squareMatrixLength; i++) {
          columnSubset[i] = haarTransformData[i][column];
        }
        double[] columnSequenceSubset = transform(columnSubset,
                squareMatrixLength);

        for (int i = 0; i < squareMatrixLength; i++) {
          haarTransformData[i][column] = columnSequenceSubset[i];
        }
      }
      squareMatrixLength /= 2;
    }

    return haarTransformData;
  }

  private static double[][] invhaar(double[][] data) {
    int length = 2;
    double[][] invhaarData = Arrays.copyOf(data, data.length);

    while (length <= invhaarData.length) {
      for (int column = 0; column < length; column++) {
        double[] columnSubset = new double[length];
        for (int i = 0; i < length; i++) {
          columnSubset[i] = invhaarData[i][column];
        }
        double[] invertedColumn = invert(columnSubset, length);
        for (int i = 0; i < length; i++) {
          invhaarData[i][column] = invertedColumn[i];
        }
      }
      for (int row = 0; row < length; row++) {
        double[] rowSubset = Arrays.copyOfRange(invhaarData[row], 0, length);
        double[] invertedRow = invert(rowSubset, length);
        System.arraycopy(invertedRow, 0, invhaarData[row], 0, length);
      }
      length *= 2;
    }
    return invhaarData;
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

  private static double[] transform(double[] data,
                                    int length) {
    double[] transformedData = new double[length];
    int halfLength = length / 2;
    for (int i = 0; i < halfLength; i++) {
      double firstValue = data[2 * i];
      double secondValue = data[2 * i + 1];
      transformedData[i] = (firstValue + secondValue) / Math.sqrt(2);
      transformedData[halfLength + i] =
              (firstValue - secondValue) / Math.sqrt(2);
    }
    return transformedData;
  }


  private static double[][] padToSquareMatrix(double[][] data) {
    int row = data.length;
    int column = data[0].length;

    int newLength = getNearestPowerOfTwo(Math.max(row, column));
    double[][] squareMatrix = new double[newLength][newLength];
    for (int i = 0; i < row; i++) {
      System.arraycopy(data[i], 0, squareMatrix[i], 0, column);
    }
    return squareMatrix;
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

  private static double[] invert(double[] data, int length) {
    double[] inverted = new double[length];
    int halfLength = length / 2;
    for (int i = 0; i < halfLength; i++) {
      double firstValue = data[i];
      double secondValue = data[halfLength + i];
      inverted[2 * i] = (firstValue + secondValue) / Math.sqrt(2);
      inverted[2 * i + 1] = (firstValue - secondValue) / Math.sqrt(2);
    }
    return inverted;
  }


  /**
   * Converts a 2D integer array to a 2D double array.
   *
   * @param data the 2D integer array to convert
   * @return the 2D double array
   */
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


  private int[][] compress(int[][] data, int percentage) {
    double[][] haarTransformed = haar(toDoubleArray(data));
    double[][] dataWithThreshold = computeDataWithThreshold(haarTransformed,
            percentage);
    double[][] invhaarData = invhaar(dataWithThreshold);
    return fromDoubleArray(invhaarData);
  }

  private double[][] computeDataWithThreshold(double[][] data,
                                              int percentage) {
    int height = data.length;
    int width = data[0].length;
    double[][] dataWithThreshold = Arrays.copyOf(data, height);
    for (int row = 0; row < height; row++) {
      double thresholdValue = getThresholdValue(data[row], percentage);
      for (int column = 0; column < width; column++) {
        if (Math.abs(data[row][column]) < thresholdValue) {
          dataWithThreshold[row][column] = 0;
        }
      }
    }
    return dataWithThreshold;
  }

  private double getThresholdValue(double[] data, int percentage) {
    int length = data.length;
    int thresholdIndex = (int) Math.ceil((double) (length * percentage) / 100);
    double[] sortedData = Arrays.copyOf(data, length);
    for (int i = 0; i < length; i++) {
      sortedData[i] = Math.abs(sortedData[i]);
    }
    Arrays.sort(sortedData);
    return sortedData[thresholdIndex];
  }

}
