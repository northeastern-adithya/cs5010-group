package controller.command;

import java.util.Scanner;

import exception.ImageProcessorException;
import services.ImageProcessingService;

public class RedComponentCommand extends AbstractCommand {

  public RedComponentCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String path = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.createRedComponent(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully created red component.");
  }
}
