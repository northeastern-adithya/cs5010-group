package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * BrightenCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to brighten an image.
 */
public class BrightenCommand extends AbstractCommand {

  /**
   * Constructor to initialize the BrightenCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public BrightenCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    if (!scanner.hasNextInt()) {
      throw new ImageProcessorException("Invalid factor provided for brightening the image.");
    }
    int factor = scanner.nextInt();
    String imageName = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.brighten(imageName, destinationImageName, factor);
    return new ExecutionStatus(true, String.format("Successfully brightened the image at factor:%s", factor));
  }
}
