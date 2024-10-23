package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * RedComponentCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to create the red component of an image.
 */
public class RedComponentCommand extends AbstractCommand {

  /**
   * Constructor to initialize the RedComponentCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public RedComponentCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String path = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.createRedComponent(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully created red component.");
  }
}
