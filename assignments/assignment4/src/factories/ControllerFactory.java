package factories;

import controller.ImageProcessorController;
import controller.SimpleImageProcessorController;
import services.ImageProcessingService;
import view.input.UserInput;
import view.output.UserOutput;

public class ControllerFactory {

  private ControllerFactory() {
  }

  public static ImageProcessorController createController(UserInput input, UserOutput output, ImageProcessingService processor) {
    return new SimpleImageProcessorController(input, output, processor);
  }
}
