package view.text;

import exception.ImageProcessorException;
import view.UserView;

/**
 * Represents the text based view where user interacts
 * with the program.
 */
public interface TextView extends UserView {

  /**
   * Get the user input
   * which the user is communicating.
   *
   * @return the readable
   * which the user has communicating.
   */
  Readable getUserInput();

}
