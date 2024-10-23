package exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class DisplayExceptionTest {

  @Test
  public void testDisplayExceptionMessage() {
    String message = "Error displaying image";
    Throwable cause = new Throwable("Cause of the error");
    DisplayException exception = new DisplayException(message, cause);
    assertEquals(message, exception.getMessage());
  }

  @Test
  public void testDisplayExceptionMessageAndCause() {
    String message = "Error displaying image";
    Throwable cause = new Throwable("Cause of the error");
    DisplayException exception = new DisplayException(message, cause);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}