package view.output;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

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
   * @param message the message to be displayed
   * @throws ImageProcessingRunTimeException.DisplayException if there is an
   * error displaying the message
   */
  void displayMessage(String message) throws ImageProcessingRunTimeException.DisplayException;


  void displayCommands(List<UserCommand> commands)
          throws ImageProcessingRunTimeException.DisplayException;


  void addActionListener(ActionListener listener);

  void displayImage(Image image, Image histogram) throws ImageProcessingRunTimeException.DisplayException;
}
