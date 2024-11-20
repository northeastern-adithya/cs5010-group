package view.output;


import java.io.IOException;
import java.util.List;
import java.util.Objects;

import controller.Features;
import exception.ImageProcessingRunTimeException;
import model.enumeration.UserCommand;
import model.visual.Image;

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
  public void displayMessage(String message, DisplayMessageType messageType)
          throws ImageProcessingRunTimeException.DisplayException {
    try {
      this.output.append(message).append("\n");
    } catch (IOException e) {
      throw new ImageProcessingRunTimeException.DisplayException(
              String.format("Error displaying message:%s", message), e
      );
    }
  }


  @Override
  public void displayCommands(List<UserCommand> commands)
          throws ImageProcessingRunTimeException.DisplayException {
    for (UserCommand command : commands) {
      displayMessage(command.getDescription(), DisplayMessageType.INFO);
    }
  }

  @Override
  public void addFeatures(Features features) {
    // Do nothing as this is console output
  }

  @Override
  public void displayImage(Image image, Image histogram)
          throws ImageProcessingRunTimeException.DisplayException {
    throw new ImageProcessingRunTimeException.DisplayException("Displaying "
            + "image is not supported in console output");
  }

  @Override
  public void clearImage() throws ImageProcessingRunTimeException.DisplayException {
    throw new ImageProcessingRunTimeException.DisplayException(
            "Clearing image is not supported in console output"
    );
  }

  @Override
  public void closeWindow() {
    throw new ImageProcessingRunTimeException.QuitException(
            "Exiting the program."
    );
  }

  @Override
  public void doNotCloseWindow() {
    // Do nothing as console always stays open.
  }
}
