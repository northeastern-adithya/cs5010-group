package exception;

public class DisplayException extends ImageProcessorException{
  public DisplayException(String message) {
    super(message);
  }

  public DisplayException(String message, Throwable cause) {
    super(message, cause);
  }
}
