package controller.command;

import java.util.Scanner;

import exception.ImageProcessorException;
import services.ImageProcessingService;

public class BlueComponentCommand extends AbstractCommand {

  public BlueComponentCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String path = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.createBlueComponent(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully created blue component.");
  }
}
