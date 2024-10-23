package view.input;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class ConsoleInputTest {

  @Test
  public void testConstructorWithValidInputStream() {
    InputStream inputStream = new ByteArrayInputStream("test input".getBytes());
    ConsoleInput consoleInput = new ConsoleInput(inputStream);
    assertNotNull(consoleInput);
  }

  @Test(expected = NullPointerException.class)
  public void testConstructorWithNullInputStream() {
    new ConsoleInput(null);
  }

  @Test
  public void testGetUserInput() {
    InputStream inputStream = new ByteArrayInputStream("test input".getBytes());
    ConsoleInput consoleInput = new ConsoleInput(inputStream);
    assertEquals(inputStream, consoleInput.getUserInput());
  }
}