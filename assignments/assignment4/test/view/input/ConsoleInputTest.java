package view.input;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;

import static org.junit.Assert.*;

public class ConsoleInputTest {

  @Test
  public void testConstructorWithValidInputStream() {
    ConsoleInput consoleInput = new ConsoleInput(new StringReader("test input"));
    assertNotNull(consoleInput);
  }

  @Test(expected = NullPointerException.class)
  public void testConstructorWithNullInputStream() {
    new ConsoleInput(null);
  }

  @Test
  public void testGetUserInput() {
    StringReader reader = new StringReader("test input");
    ConsoleInput consoleInput = new ConsoleInput(reader);
    assertEquals(reader, consoleInput.getUserInput());
  }
}