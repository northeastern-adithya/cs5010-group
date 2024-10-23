package exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImageProcessorExceptionTest {
  @Test
  public void testImageProcessorExceptionMessage() {
    String errorMessage = "Error processing image";
    ImageProcessorException exception = new ImageProcessorException(errorMessage);
    assertEquals(errorMessage, exception.getMessage());
  }

  @Test
  public void testImageProcessorExceptionMessageAndCause() {
    String errorMessage = "Error processing image";
    Throwable cause = new Throwable("Cause of the error");
    ImageProcessorException exception = new ImageProcessorException(errorMessage, cause);
    assertEquals(errorMessage, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}