package controller.command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

import controller.model.ExecutionStatus;
import exception.ImageProcessorException;
import factories.CommandFactory;
import model.UserCommand;
import services.ImageProcessingService;

public class RunCommand extends AbstractCommand {

  private static final String COMMAND_PREFIX = "#";


  public RunCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    String scriptFile = scanner.next();
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

  boolean shouldSkipLine(String line) {
    return line.trim().startsWith(COMMAND_PREFIX) || line.trim().isEmpty();
  }

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
