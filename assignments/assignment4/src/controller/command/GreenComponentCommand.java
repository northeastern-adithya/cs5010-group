package controller.command;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

public class GreenComponentCommand extends AbstractCommand {

  public GreenComponentCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String path = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.createGreenComponent(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully created green component.");
  }
}
