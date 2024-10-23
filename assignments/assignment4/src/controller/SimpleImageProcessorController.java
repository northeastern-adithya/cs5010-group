package controller;

import java.io.StringReader;
import java.util.Objects;

import controller.executors.ImageOperationCommand;
import controller.model.ExecutionStatus;
import exception.ImageProcessingRunTimeException;
import factories.Factory;
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


  private final ImageOperationCommand imageOperationCommand;

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
    this.imageOperationCommand = new ImageOperationCommand(imageProcessor);
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
  private void validateInput(UserInput input, UserOutput output, ImageProcessingService imageProcessor) throws ImageProcessingRunTimeException.QuitException {
    Objects.requireNonNull(input, "UserInput cannot be null");
    Objects.requireNonNull(output, "UserOutput cannot be null");
    Objects.requireNonNull(imageProcessor, "ImageProcessor cannot be null");
  }

  /**
   * Displays the commands to the user.
   */
  private void displayCommands() {
    displayMessage(this.imageOperationCommand.execute(Factory.createUserInput(new StringReader(UserCommand.HELP.getCommand()))).getMessage());
  }

  @Override
  public void processCommands() throws ImageProcessingRunTimeException.QuitException {
    ExecutionStatus executionStatus = this.imageOperationCommand.execute(input);
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
