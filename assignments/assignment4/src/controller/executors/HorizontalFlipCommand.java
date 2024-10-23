package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * HorizontalFlipCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to flip an image horizontally.
 */
public class HorizontalFlipCommand extends AbstractCommand {

  /**
   * Constructor to initialize the HorizontalFlipCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public HorizontalFlipCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String path = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.horizontalFlip(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully flipped the image horizontally.");
  }
}
