package app;

import java.io.InputStreamReader;

import controller.ImageProcessorController;
import exception.QuitException;
import factories.ControllerFactory;
import factories.ImageProcessorFactory;
import factories.UserInputFactory;
import factories.UserOutputFactory;

public class ImageProcessorApp {
  public static void main(String[] args) {
    ImageProcessorController controller = ControllerFactory.createController(
            UserInputFactory.createUserInput(System.in),
            UserOutputFactory.createUserOutput(System.out),
            ImageProcessorFactory.createImageProcessor()
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
