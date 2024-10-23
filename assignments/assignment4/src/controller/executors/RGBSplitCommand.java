package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * RGBSplitCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to split the image into RGB components.
 */
public class RGBSplitCommand extends AbstractCommand {

  /**
   * Constructor to initialize the RGBSplitCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public RGBSplitCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String imageName = scanner.next();
    String destinationImageNameRed = scanner.next();
    String destinationImageNameGreen = scanner.next();
    String destinationImageNameBlue = scanner.next();
    imageProcessor.rgbSplit(imageName, destinationImageNameRed, destinationImageNameGreen, destinationImageNameBlue);
    return new ExecutionStatus(true, "Successfully split the image into RGB components.");
  }
}
