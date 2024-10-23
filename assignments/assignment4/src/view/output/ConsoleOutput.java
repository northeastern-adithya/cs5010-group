package view.output;

import java.io.IOException;
import java.util.Objects;

import exception.DisplayException;

public class ConsoleOutput implements UserOutput {

  private final Appendable output;

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
