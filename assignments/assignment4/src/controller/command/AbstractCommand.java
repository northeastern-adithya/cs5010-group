package controller.command;

import java.util.Objects;
import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

public abstract class AbstractCommand implements Command {
  protected final ImageProcessingService imageProcessor;
  protected AbstractCommand(ImageProcessingService imageProcessor) {
    Objects.requireNonNull(imageProcessor, "ImageProcessor cannot be null");
    this.imageProcessor = imageProcessor;
  }


  public ExecutionStatus execute(Scanner scanner) {
    Objects.requireNonNull(scanner, "Scanner cannot be null");
    try {
      return executeCommand(scanner);
    } catch (ImageProcessorException e) {
      return new ExecutionStatus(false, e.getMessage());
    }
  }
  protected abstract ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException;
}
