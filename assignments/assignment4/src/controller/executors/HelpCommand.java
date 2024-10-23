package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import model.UserCommand;
import services.ImageProcessingService;

/**
 * HelpCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to display the list of available commands.
 */
public class HelpCommand extends AbstractCommand {

  /**
   * Constructor to initialize the HelpCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public HelpCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    return new ExecutionStatus(true, UserCommand.getUserCommands());
  }
}
