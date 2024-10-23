package factories;

import controller.command.BlueComponentCommand;
import controller.command.BlurCommand;
import controller.command.BrightenCommand;
import controller.command.Command;
import controller.command.GreenComponentCommand;
import controller.command.HelpCommand;
import controller.command.HorizontalFlipCommand;
import controller.command.IntensityComponentCommand;
import controller.command.LoadCommand;
import controller.command.LumaComponentCommand;
import controller.command.QuitCommand;
import controller.command.RGBCombineCommand;
import controller.command.RGBSplitCommand;
import controller.command.RedComponentCommand;
import controller.command.RunCommand;
import controller.command.SaveCommand;
import controller.command.SepiaCommand;
import controller.command.SharpenCommand;
import controller.command.ValueComponentCommand;
import controller.command.VerticalFlipCommand;
import exception.ImageProcessorException;
import exception.NotImplementedException;
import exception.QuitException;
import model.UserCommand;
import services.ImageProcessingService;

public class CommandFactory {

  private CommandFactory() {

  }


  public static Command createCommand(UserCommand command, ImageProcessingService imageProcessingService) {
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
