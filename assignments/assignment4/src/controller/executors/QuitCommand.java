package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import exception.QuitException;
import services.ImageProcessingService;

/**
 * QuitCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to quit the application.
 */
public class QuitCommand extends AbstractCommand {

  /**
   * Constructor to initialize the QuitCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public QuitCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    throw new QuitException("Quitting the application.");
  }
}
