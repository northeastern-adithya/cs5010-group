package view;

/**
 * This interface represents the view component of the image processing application.
 * It provides methods for displaying messages and errors.
 */
public interface ViewInterface {

  /**
   * Displays a message to the user.
   *
   * @param message the message to be displayed
   */
  void displayMessage(String message);

  /**
   * Displays an error message to the user.
   *
   * @param errorMessage the error message to be displayed
   */
  void displayError(String errorMessage);
}
