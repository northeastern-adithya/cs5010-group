package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * VerticalFlipCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to flip an image vertically.
 */
public class VerticalFlipCommand extends AbstractCommand {

  /**
   * Constructor to initialize the VerticalFlipCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public VerticalFlipCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    validateScanner(scanner);
    String path = scanner.next();
    validateScanner(scanner);
    String destinationImageName = scanner.next();
    imageProcessor.verticalFlip(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully flipped the image vertically.");
  }
}
