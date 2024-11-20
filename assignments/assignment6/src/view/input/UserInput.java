package view.input;


import java.util.Optional;
import java.util.function.IntConsumer;
import exception.ImageProcessorException;
import model.request.ImageProcessingRequest;

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

  /**
   * Prompts the user for the path to load an image interactively.
   *
   * @return the path to load the image
   * @throws ImageProcessorException if there is an error getting the input
   */
  String interactiveImageLoadPathInput() throws ImageProcessorException;

  /**
   * Prompts the user for the path to save an image interactively.
   *
   * @return the path to save the image
   * @throws ImageProcessorException if there is an error getting the input
   */
  String interactiveImageSavePathInput() throws ImageProcessorException;

  /**
   * Prompts the user for the levels to apply interactively.
   *
   * @return the levels to apply
   * @throws ImageProcessorException if there is an error getting the input
   */
  ImageProcessingRequest.Levels interactiveThreeLevelInput() throws ImageProcessorException;
}
