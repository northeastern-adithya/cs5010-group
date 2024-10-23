package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * SepiaCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to convert an image to sepia.
 */
public class SepiaCommand extends AbstractCommand {

  /**
   * Constructor to initialize the SepiaCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public SepiaCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    validateScanner(scanner);
    String imageName = scanner.next();
    validateScanner(scanner);
    String destinationImageName = scanner.next();
    imageProcessor.sepiaImage(imageName, destinationImageName);
    return new ExecutionStatus(true, "Successfully converted the image to sepia.");
  }
}
