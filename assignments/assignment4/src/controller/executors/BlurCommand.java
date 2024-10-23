package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * BlurCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to blur an image.
 */
public class BlurCommand extends AbstractCommand {

  /**
   * Constructor to initialize the BlurCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public BlurCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    validateScanner(scanner);
    String imageName = scanner.next();
    validateScanner(scanner);
    String destinationImageName = scanner.next();
    imageProcessor.blurImage(imageName, destinationImageName);
    return new ExecutionStatus(true, "Successfully blurred the image.");
  }
}
