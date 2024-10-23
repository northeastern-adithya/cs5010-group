package exception;

/**
 * An exception that is thrown when there is an error displaying an image.
 */
public class DisplayException extends ImageProcessingRunTimeException {

  /**
   * Constructs a new DisplayException with the given message
   * and the cause of this exception.
   *
   * @param message the message of this DisplayException
   * @param cause   the cause of this DisplayException
   */
  public DisplayException(String message, Throwable cause) {
    super(message, cause);
  }
}
