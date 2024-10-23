package controller.command;

import java.util.Scanner;

import exception.ImageProcessorException;
import exception.QuitException;
import services.ImageProcessingService;

public class QuitCommand extends AbstractCommand {

  public QuitCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    throw new QuitException("Quitting the application.");
  }
}
