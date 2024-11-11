package app;


import java.io.InputStreamReader;
import java.io.StringReader;

import controller.ImageProcessorController;
import exception.ImageProcessingRunTimeException;
import factories.Factory;
import controller.ControllerType;
import utility.StringUtils;

/**
 * The main class for the Image Processor application.
 * This class is responsible for creating
 * the controller and running the application.
 */
public class ImageProcessorApp {

  /**
   * Entry point of the application.
   * Initializes the controller and starts processing commands.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    ImageProcessorController controller = Factory.createController(
            Factory.createUserInput(createReadableFromArgs(args)),
            Factory.createUserOutput(System.out),
            Factory.createImageProcessor(Factory.getImageMemory()),
            getControllerType(args)
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
   * If command line arguments are not provided, it reads from the system
   * input.
   * If command line is args are provided, it reads from the string provided.
   *
   * @param args The command line arguments.
   * @return a readable object to read inputs from.
   */
  private static Readable createReadableFromArgs(String[] args) {
    if (containsCommandLineArgs(args)) {
      return new StringReader(String.join(" ",args));
    } else {
      return new InputStreamReader(System.in);
    }
  }

  /**
   * Checks if the command line arguments are provided.
   *
   * @param args The command line arguments.
   * @return true if the command line arguments are provided, false otherwise.
   */
  private static boolean containsCommandLineArgs(String[] args) {
    return args.length > 0 && StringUtils.isNotNullOrEmpty(args[0]);
  }

  /**
   * Returns the controller type based on the command line arguments.
   * If command line arguments are provided, it returns the command line
   * controller type. Otherwise, it returns the interactive controller type.
   *
   * @param args The command line arguments.
   * @return the controller type.
   */
  private static ControllerType getControllerType(String[] args) {
    if (containsCommandLineArgs(args)) {
      return ControllerType.COMMAND_LINE;
    } else {
      return ControllerType.INTERACTIVE;
    }
  }
}
