package view;

/**
 * This class represents the view for the image processing application. It outputs messages to the
 * console.
 */
public class ImageView implements ViewInterface {

  /**
   * Prints a message to the console.
   *
   * @param message the message to be printed
   */
  @Override
  public void displayMessage(String message) {
    System.out.println(message);
  }

  /**
   * Prints an error message to the console.
   *
   * @param errorMessage the error message to be printed
   */
  @Override
  public void displayError(String errorMessage) {
    System.err.println(errorMessage);
  }
}