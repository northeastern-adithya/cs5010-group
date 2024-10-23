package controller.command;

import java.util.Scanner;

import exception.ImageProcessorException;
import services.ImageProcessingService;

public class RGBSplitCommand extends AbstractCommand {

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
