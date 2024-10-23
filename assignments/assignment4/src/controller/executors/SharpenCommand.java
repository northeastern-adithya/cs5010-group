package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * SharpenCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to sharpen an image.
 */
public class SharpenCommand extends AbstractCommand {

  /**
   * Constructor to initialize the SharpenCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public SharpenCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    validateScanner(scanner);
    String imageName = scanner.next();
    validateScanner(scanner);
    String destinationImageName = scanner.next();
    imageProcessor.sharpenImage(imageName, destinationImageName);
    return new ExecutionStatus(true, "Successfully sharpened the image.");
  }
}
