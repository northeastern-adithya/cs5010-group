package controller;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import controller.model.ExecutionStatus;
import controller.command.HelpCommand;
import exception.QuitException;
import factories.CommandFactory;
import model.UserCommand;
import services.ImageProcessingService;
import utility.StringUtils;
import view.input.UserInput;
import view.output.UserOutput;

public class SimpleImageProcessorController implements ImageProcessorController {

  private final UserInput input;
  private final UserOutput output;
  private final ImageProcessingService imageProcessor;

  public SimpleImageProcessorController(UserInput input, UserOutput userOutput, ImageProcessingService imageProcessor) {

    validateInput(input, userOutput, imageProcessor);
    this.input = input;
    this.output = userOutput;
    this.imageProcessor = imageProcessor;
    displayCommands();
  }

  void validateInput(UserInput input, UserOutput output, ImageProcessingService imageProcessor) throws QuitException {
    Objects.requireNonNull(input, "UserInput cannot be null");
    Objects.requireNonNull(output, "UserOutput cannot be null");
    Objects.requireNonNull(imageProcessor, "ImageProcessor cannot be null");
  }

  private void displayCommands() {
    displayMessage(new HelpCommand(imageProcessor).execute(new Scanner("")).getMessage());
  }

  @Override
  public void processCommands() throws QuitException {
    Scanner scan = new Scanner(input.getUserInput());
    processCommands(scan);
  }

  private void processCommands(Scanner scan) {
    Objects.requireNonNull(scan, "Scanner cannot be null");
    String userInput = scan.next();
    Optional<UserCommand> command = UserCommand.getCommand(userInput);
    command.ifPresentOrElse(
            userCommand -> {
              ExecutionStatus executionStatus = CommandFactory.createCommand(userCommand, this.imageProcessor).execute(scan);
              displayMessage(executionStatus.getMessage());
            },
            () -> handleInvalidCommand(userInput)

    );
  }

  private void handleInvalidCommand(String command) {
    displayMessage(String.format("Invalid command: %s received.Please try again.", command));
    displayCommands();
  }


  private void displayMessage(String message) {
    if (StringUtils.isNotNullOrEmpty(message)) {
      this.output.displayMessage(message);
    }
  }

}
