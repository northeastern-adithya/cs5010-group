package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * RGBCombineCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to combine the RGB components of an image.
 */
public class RGBCombineCommand extends AbstractCommand {

  /**
   * Constructor to initialize the RGBCombineCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public RGBCombineCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String imageName = scanner.next();
    String redImageName = scanner.next();
    String greenImageName = scanner.next();
    String blueImageName = scanner.next();
    imageProcessor.rgbCombine(imageName, redImageName, greenImageName, blueImageName);
    return new ExecutionStatus(true, "Successfully combined the RGB components.");
  }
}
