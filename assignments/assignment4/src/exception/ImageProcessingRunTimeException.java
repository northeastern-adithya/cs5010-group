package exception;

/**
 * A generic runtime exception that is
 * used across the image processing application.
 */
public class ImageProcessingRunTimeException extends RuntimeException {

  /**
   * Constructs a new ImageProcessingRunTimeException with the given message.
   *
   * @param message the message of this ImageProcessingRunTimeException
   */
  public ImageProcessingRunTimeException(String message) {
    super(message);
  }

  /**
   * Constructs a new ImageProcessingRunTimeException with the given message
   * and the cause of this exception.
   *
   * @param message the message of this ImageProcessingRunTimeException
   * @param cause   the cause of this ImageProcessingRunTimeException
   */
  public ImageProcessingRunTimeException(String message, Throwable cause) {
    super(message, cause);
  }
}
