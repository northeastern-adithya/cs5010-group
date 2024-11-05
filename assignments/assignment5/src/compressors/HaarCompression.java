package compressors;

import java.util.Arrays;
import java.util.Objects;

import exception.ImageProcessorException;
import factories.Factory;
import model.pixels.Pixel;
import model.visual.Image;

/**
 * A class to represent the Haar compression of an image.
 * Haar compression is done first by padding the image to a square matrix,
 * then applying the Haar transform to the image, and finally
 * compressing the image by a given percentage.
 * The compression is done by setting the values below a threshold to 0.
 * The compressed image is then reconstructed by applying the inverse Haar
 * transform.
 */
public class HaarCompression implements Compression {

  /**
   * Constructs a HaarCompression object.
   * Empty constructor since there are no fields
   * for the HaarCompression class.
   */
  public HaarCompression() {
    // Empty constructor since there are no fields
  }

  /**
   * Converts the given 2D double array to a 2D integer array.
   *
   * @param data the 2D double array to convert
   * @return the 2D integer array
   */
  private static int[][] fromDoubleArray(double[][] data) {
    int[][] cleanedData = new int[data.length][data[0].length];
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        cleanedData[i][j] = (int) Math.round(data[i][j]);
      }
    }
    return cleanedData;
  }

  /**
   * Applies the Haar transform to the given 2D double array.
   *
   * @param data the 2D double array to apply the Haar transform to
   * @return the new 2D double array with the Haar transform applied
   */
  static double[][] haar(double[][] data) {
    // Pad the data to a square matrix
    double[][] squareMatrix = padToSquareMatrix(data);

    int squareMatrixLength = squareMatrix.length;

    double[][] haarTransformData = Arrays.copyOf(squareMatrix,
            squareMatrixLength);

    // Apply the Haar transform to the data until the square matrix length is 1
    while (squareMatrixLength > 1) {

      for (int row = 0; row < squareMatrixLength; row++) {

        // Transform the row part of the data
        double[] transformedSubset =
                transform(haarTransformData[row], squareMatrixLength);
        System.arraycopy(transformedSubset, 0, haarTransformData[row], 0,
                squareMatrixLength);
      }

      // Transform the column part of the data
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
      // Reduce the square matrix length by half
      squareMatrixLength /= 2;
    }

    return haarTransformData;
  }

  /**
   * Applies the inverse Haar transform to the given 2D double array.
   *
   * @param data the 2D double array to apply the inverse Haar transform to
   * @return the new 2D double array with the inverse Haar transform applied
   */
  static double[][] invhaar(double[][] data) {
    int length = 2;
    double[][] invhaarData = Arrays.copyOf(data, data.length);

    // Apply the inverse Haar transform until the length starting from 2
    // reaches the length of the data
    while (length <= invhaarData.length) {
      // In the inverse Haar transform, we first invert the column part of the
      // data
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
      // After column inversion, row is inverted
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

  /**
   * Applies the haar transform by calculating
   * the normalised average and normalised difference of the data.
   * reading two values at a time.
   *
   * @param data   the data to apply the transform to
   * @param length the length of the data to apply the transform to
   * @return the transformed data
   */
  static double[] transform(double[] data,
                            int length) {
    double[] transformedData = new double[length];
    int halfLength = length / 2;
    for (int i = 0; i < halfLength; i++) {
      double firstValue = data[2 * i];
      double secondValue = data[2 * i + 1];
      // Calculating normalised average and normalised difference
      // Normalised average is added in the first half
      // Normalised difference is added in the second half
      transformedData[i] = (firstValue + secondValue) / Math.sqrt(2);
      transformedData[halfLength + i] =
              (firstValue - secondValue) / Math.sqrt(2);
    }
    return transformedData;
  }

  /**
   * Pads the given 2D double array to a square matrix.
   * Pads to the nearest power of 2 taking the maximum of the row and column.
   *
   * @param data the 2D double array to pad
   * @return the new square matrix
   */
  static double[][] padToSquareMatrix(double[][] data) {
    int row = data.length;
    int column = data[0].length;

    int newLength = getNearestPowerOfTwo(Math.max(row, column));
    double[][] squareMatrix = new double[newLength][newLength];
    for (int i = 0; i < row; i++) {
      System.arraycopy(data[i], 0, squareMatrix[i], 0, column);
    }
    return squareMatrix;
  }

  /**
   * Gets the nearest power of two for the given number.
   *
   * @param number the number to get the nearest power of two for
   * @return the nearest power of two
   */
  private static int getNearestPowerOfTwo(int number) {
    if (number <= 0) {
      return 1;
    }
    // If the number is already a power of 2, return the number
    if ((number & (number - 1)) == 0) {
      return number;
    }

    // Find the nearest power of 2
    int power = 1;
    while (power < number) {
      power *= 2;
    }
    return power;
  }

  /**
   * Inverts the given data by calculating the normalised sum and difference.
   *
   * @param data   the data to invert
   * @param length the length of the data to invert
   * @return the inverted data
   */
  private static double[] invert(double[] data, int length) {
    double[] inverted = new double[length];
    int halfLength = length / 2;
    for (int i = 0; i < halfLength; i++) {
      // Taking the first average term and difference term
      // and computing their normalised sum and difference
      // to get the original values.
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

  /**
   * Compresses the given image by the given percentage.
   * Individual channels are compressed by the given percentage.
   *
   * @param image      the image to compress
   * @param percentage the percentage by which to compress the image
   * @return the new compressed image
   * @throws ImageProcessorException if the image cannot be compressed
   */
  @Override
  public Image compress(Image image, int percentage) throws ImageProcessorException {
    validatePercentage(percentage);
    Objects.requireNonNull(image, "Image cannot be null");
    double[][] haarRed = haar(
            toDoubleArray(image.getRedChannel()));
    double[][] haarGreen = haar(toDoubleArray(image.getGreenChannel()));
    double[][] haarBlue = haar(toDoubleArray(image.getBlueChannel()));
    double[][] thresholdRed = computeDataWithThreshold(haarRed,
            getThresholdValue(haarRed, percentage)
            );
    double[][] thresholdGreen = computeDataWithThreshold(haarGreen,
            getThresholdValue(haarGreen, percentage));
    double[][] thresholdBlue = computeDataWithThreshold(haarBlue,
            getThresholdValue(haarBlue, percentage));

    int[][] compressedRed = fromDoubleArray(invhaar(thresholdRed));
    int[][] compressedGreen = fromDoubleArray(invhaar(thresholdGreen));
    int[][] compressedBlue = fromDoubleArray(invhaar(thresholdBlue));
    int height = image.getHeight();
    int width = image.getWidth();
    Pixel[][] newPixelArray = new Pixel[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        newPixelArray[row][col] =
                Factory.createRGBPixel(compressedRed[row][col],
                        compressedGreen[row][col], compressedBlue[row][col]);
      }
    }
    return Factory.createImage(newPixelArray);
  }


  private double getThresholdValue(double[][] data, int percentage) {
    int totalLength = data.length * data[0].length;
    double[] flattenedData = new double[totalLength];
    int index = 0;

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        flattenedData[index] = data[i][j];
        index++;
      }
    }
    return getThresholdValue(flattenedData, percentage);
  }

  private double[][] computeDataWithThreshold(double[][] haarData,
                                              double thresholdValue) {
    int height = haarData.length;
    int width = haarData[0].length;
    double[][] dataWithThreshold = Arrays.copyOf(haarData, height);
    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        if (Math.abs(haarData[row][column]) < thresholdValue) {
          dataWithThreshold[row][column] = 0;
        }
      }
    }
    return dataWithThreshold;
  }

  /**
   * Gets the threshold value for the given data and percentage.
   *
   * @param data       the data to calculate the threshold value
   * @param percentage the percentage to calculate the threshold value
   * @return the threshold value
   */
  private double getThresholdValue(double[] data, int percentage) {
    double[] nonZeroData = Arrays.stream(data)
            .filter(value -> value != 0)
            .toArray();

    int length = nonZeroData.length;
    // Getting the index based on percentage
    double[] sortedData = Arrays.copyOf(nonZeroData, length);
    // Converting the data to absolute values
    for (int i = 0; i < length; i++) {
      sortedData[i] = Math.abs(sortedData[i]);
    }
    // Sorting the data
    Arrays.sort(sortedData);
    // Getting the value from index to reduce all the values less than this
    // to 0.
    sortedData = Arrays.stream(sortedData)
            .distinct()
            .filter(value -> value != 0)
            .toArray();
    int thresholdIndex =
            (int) Math.ceil((double) (sortedData.length * percentage) / 100);
    return sortedData[thresholdIndex];
  }

}
