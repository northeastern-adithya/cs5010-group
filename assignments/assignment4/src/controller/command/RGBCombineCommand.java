package controller.command;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

public class RGBCombineCommand extends AbstractCommand {

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
    return new ExecutionStatus(true,"Successfully combined the RGB components.");
  }
}
