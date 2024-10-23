package exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class NotFoundExceptionTest {

  @Test
  public void testNotFoundExceptionMessage() {
    String errorMessage = "Error processing image";
    ImageProcessorException.NotFoundException exception = new ImageProcessorException.NotFoundException(errorMessage);
    assertEquals(errorMessage, exception.getMessage());
  }

}