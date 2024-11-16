package app.parsers;

import controller.GUIImageProcessorController;
import controller.ImageProcessorController;
import exception.ImageProcessingRunTimeException;
import view.input.GUIInput;
import view.output.GUIOutput;

/**
 * A class that represents a parser for GUI arguments.
 * This class is responsible for creating a controller for GUI
 * based on the arguments required for GUI mode.
 */
public class GUIArgumentParser extends AbstractArgumentParser {
  @Override
  public ImageProcessorController createController(String[] args) throws
          ImageProcessingRunTimeException.QuitException {
    return new GUIImageProcessorController(
            new GUIInput(),
            new GUIOutput(),
            createService()

    );
  }
}
