package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * LoadCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to load an image.
 */
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
