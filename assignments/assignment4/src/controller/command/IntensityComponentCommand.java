package controller.command;

import java.util.Scanner;

import exception.ImageProcessorException;
import services.ImageProcessingService;

public class IntensityComponentCommand extends AbstractCommand {

  public IntensityComponentCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String path = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.createIntensityComponent(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully created intensity component.");
  }
}
