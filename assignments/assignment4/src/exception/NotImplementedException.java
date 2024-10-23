package exception;

public class NotImplementedException extends ImageProcessingRunTimeException {

  public NotImplementedException(String message) {
    super(message);
  }

  public NotImplementedException(String message, Throwable cause) {
    super(message, cause);
  }
}
