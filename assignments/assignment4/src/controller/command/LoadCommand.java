package controller.command;

import java.util.Scanner;

import exception.ImageProcessorException;
import services.ImageProcessingService;

public class LoadCommand extends AbstractCommand {

  public LoadCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String path = scanner.next();
    String imageName = scanner.next();
    imageProcessor.loadImage(path, imageName);
    return new ExecutionStatus(true, String.format("Successfully loaded: %s from path: %s", imageName, path));
  }
}
