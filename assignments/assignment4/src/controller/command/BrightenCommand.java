package controller.command;

import java.util.Scanner;

import exception.ImageProcessorException;
import services.ImageProcessingService;

public class BrightenCommand extends AbstractCommand {

  public BrightenCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    if (!scanner.hasNextInt()) {
      throw new ImageProcessorException("Invalid factor provided for brightening the image.");
    }
    int factor = scanner.nextInt();
    String imageName = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.brighten(imageName, destinationImageName, factor);
    return new ExecutionStatus(true, String.format("Successfully brightened the image at factor:%s", factor));
  }
}
