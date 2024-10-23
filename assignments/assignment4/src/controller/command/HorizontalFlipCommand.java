package controller.command;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

public class HorizontalFlipCommand extends AbstractCommand {

  public HorizontalFlipCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String path = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.horizontalFlip(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully flipped the image horizontally.");
  }
}
