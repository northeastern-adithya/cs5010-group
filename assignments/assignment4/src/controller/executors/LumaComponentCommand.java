package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * LumaComponentCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to create a luma component of an image.
 */
public class LumaComponentCommand extends AbstractCommand {

  /**
   * Constructor to initialize the LumaComponentCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public LumaComponentCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    validateScanner(scanner);
    String path = scanner.next();
    validateScanner(scanner);
    String destinationImageName = scanner.next();
    imageProcessor.createLumaComponent(path, destinationImageName);
    return new ExecutionStatus(true, "Successfully created luma component.");
  }
}
