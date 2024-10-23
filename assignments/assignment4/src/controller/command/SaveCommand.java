package controller.command;

import java.util.Scanner;

import exception.ImageProcessorException;
import services.ImageProcessingService;

public class SaveCommand extends AbstractCommand {

  public SaveCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String path = scanner.next();
    String imageName = scanner.next();
    imageProcessor.saveImage(path, imageName);
    return new ExecutionStatus(true, String.format("Successfully saved: %s to path: %s", imageName, path));
  }
}
