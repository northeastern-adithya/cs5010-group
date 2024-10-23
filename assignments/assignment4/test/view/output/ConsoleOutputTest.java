package view.output;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.StringWriter;
import exception.DisplayException;

import static org.junit.Assert.assertEquals;

public class ConsoleOutputTest {

  private StringWriter stringWriter;
  private ConsoleOutput consoleOutput;

  @Before
  public void setUp() {
    stringWriter = new StringWriter();
    consoleOutput = new ConsoleOutput(stringWriter);
  }

  @Test
  public void testDisplayMessage() throws DisplayException {
    String message = "Hello, World!";
    consoleOutput.displayMessage(message);
    assertEquals("Hello, World!\n", stringWriter.toString());
  }

  @Test(expected = NullPointerException.class)
  public void testConstructorWithNullOutput() {
    new ConsoleOutput(null);
  }

  @Test(expected = DisplayException.class)
  public void testDisplayMessageThrowsDisplayException() throws DisplayException {
    Appendable failingAppendable = new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException("Forced IOException");
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException("Forced IOException");
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException("Forced IOException");
      }
    };
    ConsoleOutput failingConsoleOutput = new ConsoleOutput(failingAppendable);
    failingConsoleOutput.displayMessage("This will fail");
  }
}