package app;


import java.io.InputStreamReader;

import controller.ImageProcessorController;
import exception.QuitException;
import factories.Factory;

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
            Factory.createUserInput(new InputStreamReader(System.in)),
            Factory.createUserOutput(System.out),
            Factory.createImageProcessor(Factory.getImageMemory())
    );
    while (true) {
      try {
        controller.processCommands();
      } catch (QuitException e) {
        break;
      }
    }
  }
}
