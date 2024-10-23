package controller.command;

import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

public class SharpenCommand extends AbstractCommand {

  public SharpenCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String imageName = scanner.next();
    String destinationImageName = scanner.next();
    imageProcessor.sharpenImage(imageName, destinationImageName);
    return new ExecutionStatus(true, "Successfully sharpened the image."); }
}
