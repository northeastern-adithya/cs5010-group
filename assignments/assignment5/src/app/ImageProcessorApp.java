package app;


import java.io.InputStreamReader;
import java.io.StringReader;

import controller.ImageProcessorController;
import exception.ImageProcessingRunTimeException;
import factories.Factory;
import model.enumeration.UserCommand;
import utility.StringUtils;

/**
 * The main class for the Image Processor application.
 * This class is responsible for creating
 * the controller and running the application.
 */
public class ImageProcessorApp {
  /**
   * The main method for the Image Processor application.
   * This method creates the controller and runs the application.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    ImageProcessorController controller = Factory.createController(
            Factory.createUserInput(createReadableFromArgs(args)),
            Factory.createUserOutput(System.out),
            Factory.createImageProcessor(Factory.getImageMemory())
    );
    while (true) {
      try {
        controller.processCommands();
      } catch (ImageProcessingRunTimeException.QuitException e) {
        break;
      }
    }
  }

  /**
   * Creates a Readable object from the command line arguments.
   * If command line arguments are not provided, it reads from the standard
   * input.
   * File name is expected as the first argument.
   *
   * @param args The command line arguments.
   * @return a readable object to read inputs from.
   */
  private static Readable createReadableFromArgs(String[] args) {
    if (args.length == 0 || StringUtils.isNullOrEmpty(args[0])) {
      return new InputStreamReader(System.in);
    } else {
      return formRunScriptCommand(args[0]);
    }
  }

  /**
   * Forms a command to run a script file.
   * Has a default input as run fileName quit.
   *
   * @param fileName The name of the file to run with its location.
   * @return a readable object to read inputs from.
   */
  private static Readable formRunScriptCommand(String fileName) {
    return new StringReader(String.format("%s %s %s",
            UserCommand.RUN.getCommand(),
            fileName, UserCommand.QUIT.getCommand()));
  }
}
