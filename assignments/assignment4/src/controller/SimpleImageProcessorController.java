package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;


import exception.ImageProcessingRunTimeException;
import exception.ImageProcessorException;
import factories.Factory;
import model.enumeration.UserCommand;
import services.ImageProcessingService;
import utility.StringUtils;
import view.input.UserInput;
import view.output.UserOutput;

/**
 * SimpleImageProcessorController class that implements the
 * ImageProcessorController interface and processes the commands entered by the user.
 * It handles communication with user along with controlling the model.
 */
public class SimpleImageProcessorController implements ImageProcessorController {

  /**
   * Comment prefix to ignore the comments in the script file.
   */
  private static final String COMMENT_PREFIX = "#";
  /**
   * UserInput object to get the user input.
   */
  private final UserInput userInput;
  /**
   * UserOutput object to display the output to the user.
   */
  private final UserOutput userOutput;
  /**
   * ImageProcessingService object to process the image.
   */
  private final ImageProcessingService imageProcessingService;

  /**
   * Constructor to initialize the SimpleImageProcessorController.
   * Initializes and displays the commands to the user.
   *
   * @param userInput              input coming from user.
   * @param userOutput             output to be displayed to user.
   * @param imageProcessingService ImageProcessingService object.
   * @throws NullPointerException if input, userOutput or imageProcessor is null
   */
  public SimpleImageProcessorController(UserInput userInput,
                                        UserOutput userOutput,
                                        ImageProcessingService imageProcessingService) {
    validateInput(userInput, userOutput, imageProcessingService);
    this.userInput = userInput;
    this.userOutput = userOutput;
    this.imageProcessingService = imageProcessingService;
    displayCommands();
  }

  /**
   * Validates the input given to the controller.
   *
   * @param input          UserInput object.
   * @param output         UserOutput object.
   * @param imageProcessor ImageProcessingService object.
   * @throws NullPointerException if input, output or imageProcessor is null.
   */
  private void validateInput(UserInput input, UserOutput output,
                             ImageProcessingService imageProcessor)
          throws ImageProcessingRunTimeException.QuitException {
    Objects.requireNonNull(input, "UserInput cannot be null");
    Objects.requireNonNull(output, "UserOutput cannot be null");
    Objects.requireNonNull(imageProcessor, "ImageProcessor cannot be null");
  }

  /**
   * Displays the commands to the user.
   */
  private void displayCommands() {
    displayMessage(execute(Factory.createUserInput(
            new StringReader(UserCommand.HELP.getCommand()))).getMessage()
    );
  }

  @Override
  public void processCommands() throws ImageProcessingRunTimeException.QuitException {
    ExecutionStatus executionStatus = execute(userInput);
    displayMessage(executionStatus.getMessage());
  }


  private ExecutionStatus execute(UserInput input) {
    try {
      Scanner scanner = new Scanner(input.getUserInput());
      String userInput = scanner.next();
      Optional<UserCommand> command = UserCommand.getCommand(userInput);
      if (command.isPresent()) {
        return executeCommand(command.get(), scanner);
      } else {
        return new ExecutionStatus(false, String.format("Invalid command: %s", input));
      }
    } catch (ImageProcessorException e) {
      return new ExecutionStatus(false, e.getMessage());
    }
  }

  /**
   * Executes the command based on the user input.
   *
   * @param command command to be executed.
   * @param scanner scanner to read the command arguments.
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException              if an error occurs while executing the command.
   * @throws ImageProcessingRunTimeException.QuitException if user wants to quit the application.
   */
  private ExecutionStatus executeCommand(UserCommand command, Scanner scanner)
          throws ImageProcessorException {
    switch (command) {
      case LOAD:
        return executeLoadCommand(scanner);
      case SAVE:
        return executeSaveCommand(scanner);
      case RED_COMPONENT:
        return executeRedComponentCommand(scanner);
      case BLUE_COMPONENT:
        return executeBlueComponentCommand(scanner);
      case GREEN_COMPONENT:
        return executeGreenComponentCommand(scanner);
      case VALUE_COMPONENT:
        return executeValueComponentCommand(scanner);
      case LUMA_COMPONENT:
        return executeLumaComponentCommand(scanner);
      case INTENSITY_COMPONENT:
        return executeIntensityComponentCommand(scanner);
      case HORIZONTAL_FLIP:
        return executeHorizontalFlipCommand(scanner);
      case VERTICAL_FLIP:
        return executeVerticalFlipCommand(scanner);
      case BRIGHTEN:
        return executeBrightenCommand(scanner);
      case RGB_SPLIT:
        return executeRgbSplitCommand(scanner);
      case RGB_COMBINE:
        return executeRgbCombineCommand(scanner);
      case BLUR:
        return executeBlurCommand(scanner);
      case SHARPEN:
        return executeSharpenCommand(scanner);
      case SEPIA:
        return executeSepiaCommand(scanner);
      case RUN:
        return executeRunCommand(scanner);
      case HELP:
        return executeHelpCommand();
      case QUIT:
        throw new ImageProcessingRunTimeException.QuitException("Shutting down application");
      default:
        return new ExecutionStatus(false, "Invalid command.");
    }
  }

  /**
   * Executes the load command.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeLoadCommand(Scanner scanner) throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.loadImage(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully loaded the image.");
  }

  /**
   * Executes the save command.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeSaveCommand(Scanner scanner) throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.saveImage(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully saved the image.");
  }

  /**
   * Executes to extract red component from image.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeRedComponentCommand(Scanner scanner)
          throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.createRedComponent(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully created red component.");
  }

  /**
   * Executes to extract blue component from image.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeBlueComponentCommand(Scanner scanner)
          throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.createBlueComponent(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully created blue component.");
  }

  /**
   * Executes to extract green component from image.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeGreenComponentCommand(Scanner scanner)
          throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.createGreenComponent(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully created green component.");
  }

  /**
   * Executes to extract value component from image.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeValueComponentCommand(Scanner scanner)
          throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.createValueComponent(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully created value component.");
  }

  /**
   * Executes to extract luma component from image.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeLumaComponentCommand(Scanner scanner)
          throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.createLumaComponent(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully created luma component.");
  }

  /**
   * Executes to extract intensity component from image.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeIntensityComponentCommand(Scanner scanner)
          throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.createIntensityComponent(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully created intensity component.");
  }

  /**
   * Executes to flip the image horizontally.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeHorizontalFlipCommand(Scanner scanner)
          throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.horizontalFlip(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully flipped the image horizontally.");
  }

  /**
   * Executes to flip the image vertically.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeVerticalFlipCommand(Scanner scanner)
          throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.verticalFlip(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully flipped the image vertically.");
  }

  /**
   * Executes to brighten the image.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeBrightenCommand(Scanner scanner)
          throws ImageProcessorException {
    if (!scanner.hasNextInt()) {
      throw new ImageProcessorException("Invalid factor provided for brightening the image.");
    }
    int brightness = scanner.nextInt();
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.brighten(arguments.get(0), arguments.get(1), brightness);
    return new ExecutionStatus(true,
            String.format("Successfully brightened the image at factor:%s", brightness));
  }

  /**
   * Executes to split the image into RGB components.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeRgbSplitCommand(Scanner scanner)
          throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 4);
    imageProcessingService.rgbSplit(arguments.get(0), arguments.get(1),
            arguments.get(2), arguments.get(3));
    return new ExecutionStatus(true,
            "Successfully split the image into RGB components.");
  }

  /**
   * Executes to combine the RGB components of the image.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeRgbCombineCommand(Scanner scanner)
          throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 4);
    imageProcessingService.rgbCombine(arguments.get(0), arguments.get(1),
            arguments.get(2), arguments.get(3));
    return new ExecutionStatus(true, "Successfully combined the RGB components.");
  }

  /**
   * Executes to blur the image.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeBlurCommand(Scanner scanner) throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.blurImage(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully blurred the image.");
  }

  /**
   * Executes to sharpen the image.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeSharpenCommand(Scanner scanner) throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.sharpenImage(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully sharpened the image.");
  }

  /**
   * Executes to apply sepia filter to the image.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution.
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeSepiaCommand(Scanner scanner) throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 2);
    imageProcessingService.sepiaImage(arguments.get(0), arguments.get(1));
    return new ExecutionStatus(true, "Successfully converted the image to sepia.");
  }

  /**
   * Executes the run command to execute a script file.
   *
   * @param scanner scanner to read the command arguments
   * @return ExecutionStatus information of the execution
   * @throws ImageProcessorException if an error occurs while executing the command
   */
  private ExecutionStatus executeRunCommand(Scanner scanner) throws ImageProcessorException {
    List<String> arguments = extractArguments(scanner, 1);
    String scriptFile = arguments.get(0);
    if (Objects.isNull(scriptFile)) {
      throw new ImageProcessorException("Script file path cannot be null.");
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (shouldSkipLine(line)) {
          continue;
        }
        ExecutionStatus status = execute(Factory.createUserInput(new StringReader(line)));
        if (status.isSuccess()) {
          displayMessage(status.getMessage());
        } else {
          return status;
        }
      }

    } catch (IOException e) {
      throw new ImageProcessorException(String.format("Error reading script file: %s",
              scriptFile), e);
    }
    return new ExecutionStatus(true, "Successfully executed the script file.");
  }

  /**
   * Checks if the line should be skipped.
   * A line should be skipped if it is a comment or empty.
   *
   * @param line line to be checked
   * @return true if the line should be skipped, false otherwise
   */
  private boolean shouldSkipLine(String line) {
    return line.trim().startsWith(COMMENT_PREFIX) || line.trim().isEmpty();
  }


  /**
   * Extracts the arguments from the scanner.
   *
   * @param scanner           scanner to read the arguments
   * @param numberOfArguments number of arguments to be read
   * @return list of arguments
   * @throws ImageProcessorException if the number of arguments is invalid.
   */
  private List<String> extractArguments(Scanner scanner, int numberOfArguments)
          throws ImageProcessorException {
    List<String> arguments = new ArrayList<>();
    while (numberOfArguments-- > 0) {
      if (!scanner.hasNext()) {
        throw new ImageProcessorException("Invalid command parameters.");
      }
      arguments.add(scanner.next());
    }
    return arguments;
  }


  /**
   * Executes the help command.
   *
   * @return ExecutionStatus information of the execution.
   */
  private ExecutionStatus executeHelpCommand() {
    return new ExecutionStatus(true, UserCommand.getUserCommands());
  }


  /**
   * Displays the message to the user.
   *
   * @param message message to be displayed
   */
  private void displayMessage(String message) {
    if (StringUtils.isNotNullOrEmpty(message)) {
      this.userOutput.displayMessage(message);
    }
  }
}
