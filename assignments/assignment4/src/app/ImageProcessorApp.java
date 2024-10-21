package app;


import controller.ImageProcessorController;
import exception.QuitException;
import factories.ControllerFactory;
import factories.ImageMemoryFactory;
import factories.ImageProcessingServiceFactory;
import factories.UserInputFactory;
import factories.UserOutputFactory;

public class ImageProcessorApp {
  public static void main(String[] args) {
    ImageProcessorController controller = ControllerFactory.createController(
            UserInputFactory.createUserInput(System.in),
            UserOutputFactory.createUserOutput(System.out),
            ImageProcessingServiceFactory.createImageProcessor(ImageMemoryFactory.getImageMemory())
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
