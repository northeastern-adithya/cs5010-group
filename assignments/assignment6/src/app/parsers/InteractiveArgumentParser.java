package app.parsers;

import java.io.InputStreamReader;

import controller.ImageProcessorController;
import controller.InteractiveImageProcessorController;
import exception.ImageProcessingRunTimeException;
import view.text.ConsoleView;

/**
 * A class that represents a parser for interactive arguments.
 * This class is responsible for creating a controller based on the
 * arguments required for interactive mode.
 */
public class InteractiveArgumentParser extends AbstractArgumentParser {

  @Override
  public ImageProcessorController createController(String[] args) throws
          ImageProcessingRunTimeException.QuitException {
    return new InteractiveImageProcessorController(
            new ConsoleView(
                    new InputStreamReader(System.in),
                    System.out
            ),
            createService()
    );
  }
}
