package controller.command;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

public class VerticalFlipCommand extends AbstractCommand {

  public VerticalFlipCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String path = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.verticalFlip(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully flipped the image vertically.");
  }
}
