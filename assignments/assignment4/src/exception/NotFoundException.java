package exception;

/**
 * An exception that is thrown when an image is not found.
 */
public class NotFoundException extends ImageProcessorException {
  /**
   * Constructs a new NotFoundException with the given message.
   *
   * @param message the message of this NotFoundException
   */
  public NotFoundException(String message) {
    super(message);
  }
}
