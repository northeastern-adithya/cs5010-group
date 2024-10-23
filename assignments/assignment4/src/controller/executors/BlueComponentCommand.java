package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * BlueComponentCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to create the blue component of an image.
 */
public class BlueComponentCommand extends AbstractCommand {

  /**
   * Constructor to initialize the BlueComponentCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public BlueComponentCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    validateScanner(scanner);
    String path = scanner.next();
    validateScanner(scanner);
    String destinationImageName = scanner.next();
    imageProcessor.createBlueComponent(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully created blue component.");
  }
}
