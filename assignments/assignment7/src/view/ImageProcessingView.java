package view;

/**
 * This interface extends ViewInterface to add functionality for interacting with the controller and
 * handling image processing commands in a GUI.
 */
public interface ImageProcessingView extends ViewInterface {

  /**
   * Loads an image from the file system.
   *
   * @return the path to the loaded image.
   */
  String load();

  /**
   * Saves the current image to the selected path on the system.
   *
   * @return the path where the image was saved.
   */
  String save();


  /**
   * Displays the specified image on the GUI.
   *
   * @param imageName the name of the image to display.
   */
  void displayImage(String imageName);

  /**
   * Displays the error message in the GUI when an error occurs.
   *
   * @param error the error message to display.
   */
  void displayError(String error);
}
