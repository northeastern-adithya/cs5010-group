import java.util.List;

import controller.Features;
import exception.ImageProcessingRunTimeException;
import model.enumeration.UserCommand;
import model.visual.Image;
import view.output.DisplayMessageType;
import view.output.UserOutput;

/**
 * A mock output class that logs the output to a StringBuilder.
 */
public class MockOutput implements UserOutput {
  private final StringBuilder log;

  /**
   * Constructor for the MockOutput class.
   *
   * @param log the log to append the output to
   */
  public MockOutput(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void displayMessage(String message, DisplayMessageType messageType) throws
          ImageProcessingRunTimeException.DisplayException {
    log(message);
    log(messageType.toString());
  }

  @Override
  public void displayCommands(List<UserCommand> commands) throws
          ImageProcessingRunTimeException.DisplayException {
    log(commands.toString());
  }

  @Override
  public void addFeatures(Features features) {

  }

  @Override
  public void displayImage(Image image, Image histogram) throws
          ImageProcessingRunTimeException.DisplayException {
    log(image.toString());
    log(histogram.toString());
  }

  @Override
  public void clearImage() throws
          ImageProcessingRunTimeException.DisplayException {
    log("Image cleared");
  }

  @Override
  public void closeWindow() throws
          ImageProcessingRunTimeException.QuitException {
    log("Window closed");

  }

  @Override
  public void doNotCloseWindow() {
    log("Window not closed");
  }

  private void log(String message) {
    log.append(message).append("\n");
  }
}
