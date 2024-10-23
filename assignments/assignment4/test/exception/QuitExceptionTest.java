package exception;

import static org.junit.Assert.*;
import org.junit.Test;

public class QuitExceptionTest {

  @Test
  public void testConstructorWithMessage() {
    String message = "Test message";
    QuitException exception = new QuitException(message);
    assertEquals(message, exception.getMessage());
  }
}