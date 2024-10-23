package controller;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import controller.executors.ConsoleCommand;
import controller.model.ExecutionStatus;
import exception.QuitException;
import model.UserCommand;
import services.ImageProcessingService;
import utility.StringUtils;
import view.input.UserInput;
import view.output.UserOutput;

/**
 * SimpleImageProcessorController class that implements the
 * ImageProcessorController interface and processes the commands entered by the user.
 * It handles communication with user along with controlling the model.
 */
public class SimpleImageProcessorController implements ImageProcessorController {

  /**
   * UserInput object to get the user input.
   */
  private final UserInput input;
  /**
   * UserOutput object to display the output to the user.
   */
  private final UserOutput output;
  /**
   * ImageProcessingService object to process the image.
   */
  private final ImageProcessingService imageProcessor;


  private final ConsoleCommand consoleCommand;

  /**
   * Constructor to initialize the SimpleImageProcessorController.
   *
   * @param input          UserInput object
   * @param userOutput     UserOutput object
   * @param imageProcessor ImageProcessingService object
   * @throws NullPointerException if input, userOutput or imageProcessor is null
   */
  public SimpleImageProcessorController(UserInput input, UserOutput userOutput, ImageProcessingService imageProcessor) {
    validateInput(input, userOutput, imageProcessor);
    this.input = input;
    this.output = userOutput;
    this.imageProcessor = imageProcessor;
    this.consoleCommand = new ConsoleCommand(imageProcessor);
    displayCommands();
  }

  /**
   * Constructor to initialize the SimpleImageProcessorController.
   *
   * @param input          UserInput object
   * @param output         UserOutput object
   * @param imageProcessor ImageProcessingService object
   * @throws NullPointerException if input, output or imageProcessor is null
   */
  private void validateInput(UserInput input, UserOutput output, ImageProcessingService imageProcessor) throws QuitException {
    Objects.requireNonNull(input, "UserInput cannot be null");
    Objects.requireNonNull(output, "UserOutput cannot be null");
    Objects.requireNonNull(imageProcessor, "ImageProcessor cannot be null");
  }

  /**
   * Displays the commands to the user.
   */
  private void displayCommands() {
    displayMessage(this.consoleCommand.execute(new Scanner(UserCommand.HELP.getCommand())).getMessage());
  }

  @Override
  public void processCommands() throws QuitException {
    Scanner scan = new Scanner(input.getUserInput());
    processCommands(scan);
  }

  /**
   * Processes the commands entered by the user
   * using executors.
   *
   * @param scan Scanner object
   */
  private void processCommands(Scanner scan) {
    Objects.requireNonNull(scan, "Scanner cannot be null");
    ExecutionStatus executionStatus = this.consoleCommand.execute(scan);
    displayMessage(executionStatus.getMessage());
  }


  /**
   * Displays the message to the user.
   *
   * @param message message to be displayed
   */
  private void displayMessage(String message) {
    if (StringUtils.isNotNullOrEmpty(message)) {
      this.output.displayMessage(message);
    }
  }

}
