package view.output;

import exception.ImageProcessingRunTimeException;

/**
 * Represents the output to the user.
 * This output is displayed through the console.
 */
public interface UserOutput {

  /**
   * Displays the message to the user.
   *
   * @param message the message to be displayed
   * @throws ImageProcessingRunTimeException.DisplayException if there is an error displaying the message
   */
  void displayMessage(String message) throws ImageProcessingRunTimeException.DisplayException;
}
