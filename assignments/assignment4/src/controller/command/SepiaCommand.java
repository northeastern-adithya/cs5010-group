package controller.command;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

public class SepiaCommand extends AbstractCommand {

  public SepiaCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String imageName = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.sepiaImage(imageName, destinationImageName);
    return new ExecutionStatus(true, "Successfully converted the image to sepia.");
  }
}
