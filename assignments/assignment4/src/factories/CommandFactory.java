package factories;

import controller.executors.BlueComponentCommand;
import controller.executors.BlurCommand;
import controller.executors.BrightenCommand;
import controller.executors.Command;
import controller.executors.GreenComponentCommand;
import controller.executors.HelpCommand;
import controller.executors.HorizontalFlipCommand;
import controller.executors.IntensityComponentCommand;
import controller.executors.LoadCommand;
import controller.executors.LumaComponentCommand;
import controller.executors.QuitCommand;
import controller.executors.RGBCombineCommand;
import controller.executors.RGBSplitCommand;
import controller.executors.RedComponentCommand;
import controller.executors.RunCommand;
import controller.executors.SaveCommand;
import controller.executors.SepiaCommand;
import controller.executors.SharpenCommand;
import controller.executors.ValueComponentCommand;
import controller.executors.VerticalFlipCommand;
import exception.NotImplementedException;
import model.UserCommand;
import services.ImageProcessingService;

/**
 * CommandFactory class that creates the command
 * object based on the user command.
 */
public class CommandFactory {

  private CommandFactory() {
    // private constructor to prevent instantiation
  }

  /**
   * Creates the command object based on the user command.
   * @param command command type to be created
   * @param imageProcessingService service to process the image.
   * @throws NotImplementedException if the command is not implemented
   */
  public static Command createCommand(UserCommand command, ImageProcessingService imageProcessingService) throws NotImplementedException{
    switch (command) {
      case LOAD:
        return new LoadCommand(imageProcessingService);
      case SAVE:
        return new SaveCommand(imageProcessingService);
      case RED_COMPONENT:
        return new RedComponentCommand(imageProcessingService);
      case BLUE_COMPONENT:
        return new BlueComponentCommand(imageProcessingService);
      case GREEN_COMPONENT:
        return new GreenComponentCommand(imageProcessingService);
      case VALUE_COMPONENT:
        return new ValueComponentCommand(imageProcessingService);
      case LUMA_COMPONENT:
        return new LumaComponentCommand(imageProcessingService);
      case INTENSITY_COMPONENT:
        return new IntensityComponentCommand(imageProcessingService);
      case HORIZONTAL_FLIP:
        return new HorizontalFlipCommand(imageProcessingService);
      case VERTICAL_FLIP:
        return new VerticalFlipCommand(imageProcessingService);
      case BRIGHTEN:
        return new BrightenCommand(imageProcessingService);
      case RGB_SPLIT:
        return new RGBSplitCommand(imageProcessingService);
      case RGB_COMBINE:
        return new RGBCombineCommand(imageProcessingService);
      case BLUR:
        return new BlurCommand(imageProcessingService);
      case SHARPEN:
        return new SharpenCommand(imageProcessingService);
      case SEPIA:
        return new SepiaCommand(imageProcessingService);
      case RUN:
        return new RunCommand(imageProcessingService);
      case QUIT:
        return new QuitCommand(imageProcessingService);
      case HELP:
        return new HelpCommand(imageProcessingService);
      default:
        throw new NotImplementedException(String.format("Command %s not implemented", command));
    }
  }

}
