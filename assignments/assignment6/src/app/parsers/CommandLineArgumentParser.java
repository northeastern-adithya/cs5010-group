package app.parsers;


import java.io.StringReader;

import controller.CommandLineImageProcessorController;
import controller.ImageProcessorController;
import exception.ImageProcessingRunTimeException;
import utility.StringUtils;
import view.text.ConsoleView;

/**
 * A class that represents a parser for command line arguments.
 * This class is responsible for creating a command line controller
 * based on the arguments required for command line mode.
 */
public class CommandLineArgumentParser extends AbstractArgumentParser {

  @Override
  public ImageProcessorController createController(String[] args) throws
          ImageProcessingRunTimeException.QuitException {
    return new CommandLineImageProcessorController(
            new ConsoleView(new StringReader(getFileNameFromArgs(args)),
                    System.out),
            createService()
    );
  }

  /**
   * Gets the file name from the command line arguments.
   *
   * @param args The command line arguments.
   * @return The file name.
   * @throws ImageProcessingRunTimeException If there is no
   *                                         file name provided.
   */
  private String getFileNameFromArgs(String[] args) throws
          ImageProcessingRunTimeException.QuitException {
    if (args.length > 1) {
      String fileName = args[1];
      if (StringUtils.isNotNullOrEmpty(fileName)) {
        return fileName;
      }
    }
    throw new ImageProcessingRunTimeException("No file name "
            + "provided");
  }
}
