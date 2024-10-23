package exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class NotImplementedExceptionTest {

  @Test
  public void testConstructorWithMessage() {
    String message = "Test message";
    NotImplementedException exception = new NotImplementedException(message);
    assertEquals(message, exception.getMessage());
  }
}