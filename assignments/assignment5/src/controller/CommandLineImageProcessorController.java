package controller;

import java.util.Scanner;

import exception.ImageProcessingRunTimeException;
import exception.ImageProcessorException;
import services.ImageProcessingService;
import view.input.UserInput;
import view.output.UserOutput;

/**
 * The controller class for the command line image processor.
 * This class is responsible for handling the image service
 * and communication with the view.
 */
public class CommandLineImageProcessorController extends InteractiveImageProcessorController {

  /**
   * Constructor to initialize the CommandLineImageProcessorController.
   * Initializes and displays the commands to the user.
   *
   * @param userInput              input coming from user from command line.
   * @param userOutput             output to be displayed to user.
   * @param imageProcessingService ImageProcessingService object.
   * @throws NullPointerException if input, userOutput or imageProcessor is null
   */
  public CommandLineImageProcessorController(UserInput userInput,
                                             UserOutput userOutput,
                                             ImageProcessingService imageProcessingService) {
    super(userInput, userOutput, imageProcessingService);
  }

  /**
   * Processes the commands from the user.
   * This controller reads from the command line and processes it.
   * If the command is not valid, it displays an error message.
   */
  @Override
  public void processCommands() {
    try {
      Scanner scanner = new Scanner(userInput.getUserInput());
      executeRunCommand(scanner);
      displayMessage("Successfully executed the script file.");
    } catch (ImageProcessorException e) {
      displayMessage(e.getMessage());
    }
    throw new ImageProcessingRunTimeException.QuitException("Quitting the "
            + "program");
  }
}
