package view.output;

import java.util.List;

import controller.Features;
import exception.ImageProcessingRunTimeException;
import model.enumeration.UserCommand;
import model.visual.Image;

/**
 * Represents the output to the user.
 * This output is displayed through the console.
 */
public interface UserOutput {

  /**
   * Displays the message to the user.
   *
   * @param message     the message to be displayed
   * @param messageType the type of message to be displayed
   * @throws ImageProcessingRunTimeException.DisplayException if there is an
   *                                                          error
   *                                                          displaying the
   *                                                          message
   */
  void displayMessage(String message, DisplayMessageType messageType)
          throws ImageProcessingRunTimeException.DisplayException;


  /**
   * Displays the commands to the user.
   *
   * @param commands the list of commands to be displayed
   * @throws ImageProcessingRunTimeException.DisplayException if there is an
   *                                                          error
   *                                                          displaying the
   *                                                          commands
   */
  void displayCommands(List<UserCommand> commands)
          throws ImageProcessingRunTimeException.DisplayException;


  /**
   * Adds the features that can be executed by user to the output.
   *
   * @param features the features to be added
   */
  void addFeatures(Features features);

  /**
   * Displays the image to the user.
   * If image is null then output will stay the same as before.
   *
   * @param image     the image to be displayed
   * @param histogram the histogram of the image to be displayed
   * @throws ImageProcessingRunTimeException.DisplayException if there is an
   *                                                          error
   *                                                          displaying the
   *                                                          image
   */
  void displayImage(Image image, Image histogram)
          throws ImageProcessingRunTimeException.DisplayException;

  /**
   * Clears the image from the output.
   *
   * @throws ImageProcessingRunTimeException.DisplayException if there is an
   *                                                          error clearing
   *                                                          the image
   */
  void clearImage() throws ImageProcessingRunTimeException.DisplayException;
}
