package controller.executors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import factories.CommandFactory;
import model.UserCommand;
import services.ImageProcessingService;

/**
 * RunCommand class that extends the AbstractCommand class
 * and implements the executeCommand method to run a script file.
 */
public class RunCommand extends AbstractCommand {

  /**
   * Prefix for the command in the script file.
   */
  private static final String COMMAND_PREFIX = "#";


  /**
   * Constructor to initialize the RunCommand.
   *
   * @param imageProcessingService ImageProcessingService object
   * @throws NullPointerException if imageProcessingService is null
   */
  public RunCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String scriptFile = scanner.next();
    if (Objects.isNull(scriptFile)) {
      throw new ImageProcessorException("Script file path cannot be null.");
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (shouldSkipLine(line)) {
          continue;
        }
        ExecutionStatus status = processCommands(new Scanner(line));
        if (!status.isSuccess()) {
          return status;
        }
      }
    } catch (IOException e) {
      throw new ImageProcessorException(String.format("Error reading script file: %s", scriptFile), e);
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
    return line.trim().startsWith(COMMAND_PREFIX) || line.trim().isEmpty();
  }

  /**
   * Processes the commands in the script file.
   *
   * @param scan Scanner object
   * @return ExecutionStatus object
   */
  private ExecutionStatus processCommands(Scanner scan) {
    String userInput = scan.next();
    Optional<UserCommand> command = UserCommand.getCommand(userInput);
    if (command.isPresent()) {
      return CommandFactory.createCommand(command.get(), imageProcessor).execute(scan);
    } else {
      return new ExecutionStatus(false, String.format("Invalid command: %s", userInput));
    }
  }
}
