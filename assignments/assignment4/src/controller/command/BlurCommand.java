package controller.command;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

public class BlurCommand extends AbstractCommand {

  public BlurCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String imageName = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.blurImage(imageName, destinationImageName);
    return new ExecutionStatus(true, "Successfully blurred the image.");
  }
}
