package utility;

public class PixelTransformUtility {
  private static final double[][] LUMA_MATRIX = {
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722}
  };

  private static final double[][] SEPIA_MATRIX = {
          {0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168},
          {0.272, 0.534, 0.131}
  };

  public static int[] getLuma(int[] pixel) {
    return matrixOperation(pixel, LUMA_MATRIX);
  }

  public static int[] getSepia(int[] pixel) {
    return matrixOperation(pixel, SEPIA_MATRIX);
  }

  private static int[] matrixOperation(int[] pixel, double[][] matrix) {
    int r = pixel[0];
    int g = pixel[1];
    int b = pixel[2];
    double rPrime = matrix[0][0] * r + matrix[0][1] * g + matrix[0][2] * b;
    double gPrime = matrix[1][0] * r + matrix[1][1] * g + matrix[1][2] * b;
    double bPrime = matrix[2][0] * r + matrix[2][1] * g + matrix[2][2] * b;
    return new int[] {(int) rPrime, (int) gPrime, (int) bPrime};
  }
}