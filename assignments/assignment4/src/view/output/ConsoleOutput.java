package view.output;

import java.io.IOException;
import java.util.Objects;

import exception.DisplayException;

/**
 * Represents the output to the user.
 * This output can be to any source like console, file etc.
 */
public class ConsoleOutput implements UserOutput {

  /**
   * The output stream to which is used
   * to communicate with user.
   */
  private final Appendable output;

  /**
   * Constructs a ConsoleOutput object with the given output stream.
   *
   * @param output the output stream to which is used to communicate with user.
   * @throws NullPointerException if the output stream is null.
   */
  public ConsoleOutput(Appendable output) {
    Objects.requireNonNull(output, "Output cannot be null");
    this.output = output;
  }

  @Override
  public void displayMessage(String message) throws DisplayException {
    try {
      this.output.append(message).append("\n");
    } catch (IOException e) {
      throw new DisplayException(String.format("Error displaying message:%s", message), e);
    }
  }
}
