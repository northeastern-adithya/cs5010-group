package view.input;


import java.util.Optional;
import java.util.function.IntConsumer;

import exception.ImageProcessingRunTimeException;
import exception.ImageProcessorException;

import exception.ImageProcessingRunTimeException;

/**
 * Represents the user input.
 * This input can be from any source like console, file etc.
 */
public interface UserInput {

  /**
   * Get the user input
   * which the user is communicating.
   *
   * @return the readable
   * which the user has communicating.
   * @throws ImageProcessorException if there is any exception when returning
   *                                 inputstream.
   */
  Readable getUserInput() throws
          ImageProcessorException;


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
   * Returns the slider input.
   * Is present if the user has entered a value.
   * Otherwise, empty.
   * @return the slider input
   */
  Optional<Integer> getSliderInput();

  String interactiveImageLoadPathInput() throws ImageProcessingRunTimeException.DisplayException;

  String interactiveImageSavePathInput() throws ImageProcessingRunTimeException.DisplayException;
}
