package factories;

import controller.ImageProcessorController;
import controller.SimpleImageProcessorController;
import services.ImageProcessingService;
import view.input.UserInput;
import view.output.UserOutput;

/**
 * A factory class for creating an ImageProcessorController.
 */
public class ControllerFactory {

  private ControllerFactory() {
    // Empty private constructor to prevent instantiation.
  }

  /**
   * Creates an ImageProcessorController to control view and model.
   *
   * @param input     The input collector from user.
   * @param output    The output collector from user.
   * @param processor The processor service to perform operations on the image.
   * @return the controller to control the view and model
   */
  public static ImageProcessorController createController(UserInput input, UserOutput output, ImageProcessingService processor) {
    return new SimpleImageProcessorController(input, output, processor);
  }
}
