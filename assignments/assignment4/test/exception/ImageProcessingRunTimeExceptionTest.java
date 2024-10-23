package exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImageProcessingRunTimeExceptionTest {
  @Test
  public void testConstructorWithMessage() {
    String message = "Test message";
    ImageProcessingRunTimeException exception = new ImageProcessingRunTimeException(message);
    assertEquals(message, exception.getMessage());
  }

  @Test
  public void testConstructorWithMessageAndCause() {
    String message = "Test message";
    Throwable cause = new Throwable("Cause message");
    ImageProcessingRunTimeException exception = new ImageProcessingRunTimeException(message, cause);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}