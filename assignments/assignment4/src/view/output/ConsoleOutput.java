package view.output;

import java.io.IOException;

import exception.DisplayException;
import exception.ImageProcessorException;

public class ConsoleOutput implements UserOutput {

  private final Appendable output;

  public ConsoleOutput(Appendable output) {
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
