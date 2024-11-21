package view.text;


import java.io.IOException;
import java.util.List;
import java.util.Objects;

import exception.ImageProcessingRunTimeException;
import exception.ImageProcessorException;
import model.enumeration.UserCommand;
import view.DisplayMessageType;

/**
 * Represents the console view where user interacts
 * with the program through console input and output.
 */
public class ConsoleView implements TextView {

  /**
   * The output stream to which is used
   * to communicate with user.
   */
  private final Appendable output;

  /**
   * The input stream from which the user is communicating.
   */
  private final Readable input;

  /**
   * Constructs a Console View object with the given input and output stream.
   *
   * @param input  the input stream from which the user is communicating.
   * @param output the output stream to which is used to communicate with user.
   * @throws NullPointerException if the output stream is null.
   */
  public ConsoleView(Readable input, Appendable output) {
    Objects.requireNonNull(input, "Input cannot be null");
    Objects.requireNonNull(output, "Output cannot be null");
    this.input = input;
    this.output = output;
  }

  @Override
  public Readable getUserInput() {
    return this.input;
  }

  @Override
  public void displayMessage(String message, DisplayMessageType messageType)
          throws
          ImageProcessingRunTimeException.DisplayException {
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
          throws
          ImageProcessingRunTimeException.DisplayException {
    for (UserCommand command : commands) {
      displayMessage(command.getDescription(), DisplayMessageType.INFO);
    }
  }
}
