package view.gui;

import java.util.Optional;
import java.util.function.IntConsumer;

import controller.Features;
import exception.ImageProcessingRunTimeException;
import exception.ImageProcessorException;
import model.request.ImageProcessingRequest;
import model.visual.Image;
import view.UserView;

/**
 * Represents the graphic view which is used to interact with the user.
 * User can use this view and input commands through a graphical user interface.
 */
public interface GUIView extends UserView {

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
          throws
          ImageProcessingRunTimeException.DisplayException;

  /**
   * Clears the image from the output.
   *
   * @throws ImageProcessingRunTimeException.DisplayException if there is an
   *                                                          error clearing
   *                                                          the image
   */
  void clearImage() throws
          ImageProcessingRunTimeException.DisplayException;


  /**
   * Closes the window.
   */
  void closeWindow() throws
          ImageProcessingRunTimeException.QuitException;

  /**
   * Does not close the window.
   */
  void doNotCloseWindow();


  /**
   * Confirm the split view.
   *
   * @param updateImageCallback the callback function to update the image.
   * @return true if the user confirms the split view false otherwise.
   * @throws ImageProcessorException if there is any exception when getting
   *                                 inputs from the user.
   */
  boolean confirmSplitView(IntConsumer updateImageCallback) throws
          ImageProcessorException;

  /**
   * Prompts the user for the levels to apply interactively.
   *
   * @return the levels to apply
   * @throws ImageProcessorException if there is an error getting the input
   */
  ImageProcessingRequest.Levels interactiveThreeLevelInput() throws ImageProcessorException;

  /**
   * Prompts the user for the scaling factors to apply interactively.
   *
   * @return the scaling factors to apply
   * @throws ImageProcessorException if there is an error getting the input
   */
  ImageProcessingRequest.ScalingFactors interactiveScalingFactorsInput() throws ImageProcessorException;

}