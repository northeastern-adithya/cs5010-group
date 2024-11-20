package view.input;

import java.util.Objects;
import java.util.Optional;
import java.util.function.IntConsumer;

import exception.ImageProcessorException;

import exception.ImageProcessorException;

/**
 * Represents all the inputs
 * taken from command line.
 */
public class ConsoleInput implements UserInput {

  /**
   * The input stream from which the user is communicating.
   */
  private final Readable input;

  /**
   * Constructs a ConsoleInput object with the given input stream.
   *
   * @param input the input stream from which the user is communicating.
   * @throws NullPointerException if the input stream is null.
   */
  public ConsoleInput(Readable input) {
    Objects.requireNonNull(input, "Input cannot be null");
    this.input = input;
  }

  /**
   * Returns the input stream associated with this ConsoleInput instance.
   *
   * @return the Readable input stream used for reading user input
   * @throws ImageProcessorException if there are any issues accessing the input stream
   */
  @Override
  public Readable getUserInput() throws ImageProcessorException{
    return this.input;
  }

  /**
   * This operation is not supported in console mode as it relates to GUI functionality.
   *
   * @param updateImageCallback callback function to handle image updates
   * @return never returns as it always throws an exception
   * @throws ImageProcessorException always, with message indicating the operation is not supported
   */
  @Override
  public boolean confirmSplitView(IntConsumer updateImageCallback) throws
          ImageProcessorException {
    throw new ImageProcessorException("Slider not supported in console input");
  }

  /**
   * Returns an empty Optional as slider functionality is not supported in console mode.
   *
   * @return an empty Optional
   */
  @Override
  public Optional<Integer> getSliderInput(){
    return Optional.empty();
  }

  /**
   * This operation is not supported in console mode as it relates to interactive file selection.
   *
   * @return never returns as it always throws an exception
   * @throws ImageProcessorException always, with message indicating the operation is not supported
   */
  @Override
  public String interactiveImageLoadPathInput() throws ImageProcessorException {
    throw new ImageProcessorException("Not supported for console input");
  }

  /**
   * This operation is not supported in console mode as it relates to interactive file selection.
   *
   * @return never returns as it always throws an exception
   * @throws ImageProcessorException always, with message indicating the operation is not supported
   */
  @Override
  public String interactiveImageSavePathInput() throws ImageProcessorException {
    throw new ImageProcessorException("Not supported for console input");
  }

  /**
   * This operation is not supported in console mode as it relates to interactive value selection.
   *
   * @return never returns as it always throws an exception
   * @throws ImageProcessorException always, with message indicating the operation is not supported
   */
  @Override
  public int[] interactiveThreeLevelInput() throws ImageProcessorException {
    throw new ImageProcessorException("Not supported for console input");
  }
}
