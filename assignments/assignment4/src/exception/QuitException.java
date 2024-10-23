package exception;

/**
 * An exception that is thrown when the user wants to quit the program.
 */
public class QuitException extends ImageProcessingRunTimeException {

  /**
   * Constructs a new QuitException with the given message.
   *
   * @param message the message of this QuitException
   */
  public QuitException(String message) {
    super(message);
  }
}
