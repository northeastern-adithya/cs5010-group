package controller.command;

import java.util.Scanner;

import exception.ImageProcessorException;
import exception.QuitException;
import model.UserCommand;
import services.ImageProcessingService;

public class HelpCommand extends AbstractCommand {

  public HelpCommand(ImageProcessingService imageProcessingService) {
    super(imageProcessingService);
  }

  @Override
  protected ExecutionStatus executeCommand(Scanner scanner) throws ImageProcessorException {
    return new ExecutionStatus(true, UserCommand.getUserCommands());
  }
}
