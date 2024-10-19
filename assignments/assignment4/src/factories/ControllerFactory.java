package factories;

import controller.ImageProcessorController;
import controller.SimpleImageProcessorController;
import services.ImageProcessor;
import view.input.UserInput;
import view.output.UserOutput;

public class ControllerFactory {

  public static ImageProcessorController createController(UserInput input, UserOutput output, ImageProcessor processor) {
    return new SimpleImageProcessorController(input, output, processor);
  }
}
