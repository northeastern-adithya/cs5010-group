package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * GreenComponentCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to create the green component of an image.
 */
public class GreenComponentCommand extends AbstractCommand {

  /**
   * Constructor to initialize the GreenComponentCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public GreenComponentCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    validateScanner(scanner);
    String path = scanner.next();
    validateScanner(scanner);
    String destinationImageName = scanner.next();
    imageProcessor.createGreenComponent(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully created green component.");
  }
}
