package controller.executors;

import java.util.Objects;
import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import services.ImageProcessingService;

/**
 * AbstractCommand class that implements the Command interface
 * and provides a constructor to initialize the ImageProcessingService.
 * The child classes of AbstractCommand will implement the executeCommand method.
 */
public abstract class AbstractCommand implements Command {
  /**
   * ImageProcessingService object to perform
   * image processing operations.
   */
  protected final ImageProcessingService imageProcessor;

  /**
   * Constructor to initialize the AbstractCommand.
   *
   * @param imageProcessor ImageProcessingService object
   * @throws NullPointerException if imageProcessor is null
   */
  protected AbstractCommand(ImageProcessingService imageProcessor) {
    Objects.requireNonNull(imageProcessor, "ImageProcessor cannot be null");
    this.imageProcessor = imageProcessor;
  }


  /**
   * Executes the command given by user and does the required operations.
   *
   * @param scanner Scanner object to read the input
   * @return execution status of the command containing if
   * the command was successful and the message
   * @throws NullPointerException if scanner is null
   */
  public ExecutionStatus execute(Scanner scanner) {
    Objects.requireNonNull(scanner, "Scanner cannot be null");
    try {
      return executeCommand(scanner);
    } catch (ImageProcessorException e) {
      return new ExecutionStatus(false, e.getMessage());
    }
  }



  /**
   * Executes the command given by user and does the required operations.
   *
   * @param scanner Scanner object to read the input
   * @return execution status of the command containing if
   * the command was successful and the message
   * @throws ImageProcessorException if any error occurs during image processing
   */
  protected abstract ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException;


  /**
   * Validates the scanner object to check if it has the required number of arguments.
   *
   * @param scanner Scanner object to read the input
   * @throws ImageProcessorException if the scanner does not have the required number of arguments
   */
  protected void validateScanner(Scanner scanner) throws ImageProcessorException{
    if(!scanner.hasNext()){
      throw new ImageProcessorException("Invalid command parameters.");
    }
  }


}
