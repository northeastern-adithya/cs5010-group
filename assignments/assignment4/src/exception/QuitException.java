package exception;

public class QuitException extends ImageProcessingRunTimeException {

  public QuitException(String message) {
    super(message);
  }

  public QuitException(String message, Throwable cause) {
    super(message, cause);
  }
}
