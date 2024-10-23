package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * IntensityComponentCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to create an intensity component of an image.
 */
public class IntensityComponentCommand extends AbstractCommand {

  /**
   * Constructor to initialize the IntensityComponentCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public IntensityComponentCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    validateScanner(scanner);
    String path = scanner.next();
    validateScanner(scanner);
    String destinationImageName = scanner.next();
    imageProcessor.createIntensityComponent(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully created intensity component.");
  }
}
