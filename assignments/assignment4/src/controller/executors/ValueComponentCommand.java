package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * ValueComponentCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to create the value component of an image.
 */
public class ValueComponentCommand extends AbstractCommand {

  /**
   * Constructor to initialize the ValueComponentCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public ValueComponentCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    validateScanner(scanner);
    String path = scanner.next();
    validateScanner(scanner);
    String destinationImageName = scanner.next();
    imageProcessor.createValueComponent(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully created value component.");
  }
}
