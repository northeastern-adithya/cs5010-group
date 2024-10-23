package exception;

public class ImageProcessingRunTimeException extends RuntimeException {

  public ImageProcessingRunTimeException(String message) {
    super(message);
  }

  public ImageProcessingRunTimeException(String message, Throwable cause) {
    super(message, cause);
  }
}
