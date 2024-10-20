package exception;

public class NotImplementedException extends ImageProcessorException {

  public NotImplementedException(String message) {
    super(message);
  }

  public NotImplementedException(String message, Throwable cause) {
    super(message, cause);
  }
}
