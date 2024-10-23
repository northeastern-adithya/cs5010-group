package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * SaveCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to save an image.
 */
public class SaveCommand extends AbstractCommand {

  /**
   * Constructor to initialize the SaveCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public SaveCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    validateScanner(scanner);
    String path = scanner.next();
    validateScanner(scanner);
    String imageName = scanner.next();
    imageProcessor.saveImage(path, imageName);
    return new ExecutionStatus(true, String.format("Successfully saved: %s to path: %s", imageName, path));
  }
}
