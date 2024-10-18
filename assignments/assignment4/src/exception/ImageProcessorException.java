package exception;

public class ImageProcessorException extends Exception{
  public ImageProcessorException(String message) {
    super(message);
  }

  public ImageProcessorException(String message, Throwable cause) {
    super(message, cause);
  }

}
