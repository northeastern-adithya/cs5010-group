package controller.command;

import java.util.Scanner;

import exception.ImageProcessorException;
import services.ImageProcessingService;

public abstract class AbstractCommand implements Command {
  protected ImageProcessingService imageProcessor;
  protected AbstractCommand(ImageProcessingService imageProcessor) {
    this.imageProcessor = imageProcessor;
  }

  public ExecutionStatus execute(Scanner scanner) {
    try {
      return executeCommand(scanner);
    } catch (ImageProcessorException e) {
      return new ExecutionStatus(false, e.getMessage());
    }
  }
  protected abstract ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException;




}
