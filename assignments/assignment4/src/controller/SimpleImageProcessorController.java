package controller;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import controller.model.ExecutionStatus;
import controller.executors.HelpCommand;
import exception.QuitException;
import factories.CommandFactory;
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
    displayMessage(new HelpCommand(imageProcessor).execute(new Scanner("")).getMessage());
  }

  @Override
  public void processCommands() throws QuitException {
    Scanner scan = new Scanner(input.getUserInput());
    processCommands(scan);
  }

  private void processCommands(Scanner scan) {
    Objects.requireNonNull(scan, "Scanner cannot be null");
    String userInput = scan.next();
    Optional<UserCommand> command = UserCommand.getCommand(userInput);
    command.ifPresentOrElse(
            userCommand -> {
              ExecutionStatus executionStatus = CommandFactory.createCommand(userCommand, this.imageProcessor).execute(scan);
              displayMessage(executionStatus.getMessage());
            },
            () -> handleInvalidCommand(userInput)

    );
  }

  /**
   * Handles the invalid command entered by the user.
   *
   * @param command command entered by the user
   */
  private void handleInvalidCommand(String command) {
    displayMessage(String.format("Invalid command: %s received.Please try again.", command));
    displayCommands();
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
