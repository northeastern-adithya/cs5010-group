package controller;

import java.util.Scanner;

import exception.ImageProcessingRunTimeException;
import exception.ImageProcessorException;
import controller.services.ImageProcessingService;
import view.DisplayMessageType;
import view.text.TextView;

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
   * @param textView             view used to interact with the user.
   * @param imageProcessingService ImageProcessingService object.
   * @throws NullPointerException if text view or imageProcessor is
   * null
   */
  public CommandLineImageProcessorController(TextView textView,
                                             ImageProcessingService imageProcessingService) {
    super(textView, imageProcessingService);
  }

  /**
   * Processes the commands from the user.
   * This controller reads from the command line and processes it.
   * If the command is not valid, it displays an error message.
   */
  @Override
  public void processCommands() {
    try {
      Scanner scanner = new Scanner(textView.getUserInput());
      executeRunCommand(scanner);
      displayMessage("Successfully executed the script file.",
              DisplayMessageType.INFO);
    } catch (ImageProcessorException e) {
      displayMessage(e.getMessage(), DisplayMessageType.ERROR);
    }
    throw new ImageProcessingRunTimeException.QuitException("Quitting the "
            + "program");
  }
}
