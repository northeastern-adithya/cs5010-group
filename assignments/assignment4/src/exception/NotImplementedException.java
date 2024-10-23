package exception;

/**
 * An exception that is thrown when a method is not implemented.
 */
public class NotImplementedException extends ImageProcessingRunTimeException {

  /**
   * Constructs a new NotImplementedException with the given message.
   *
   * @param message the message of this NotImplementedException
   */
  public NotImplementedException(String message) {
    super(message);
  }
}
