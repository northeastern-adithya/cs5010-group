package controller.command;

import java.util.Scanner;

import exception.ImageProcessorException;
import services.ImageProcessingService;

public class LumaComponentCommand extends AbstractCommand {

  public LumaComponentCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String path = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.createLumaComponent(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully created luma component.");
  }
}
