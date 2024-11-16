package view.input;

import java.util.Objects;
import java.util.function.IntConsumer;

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


  @Override
  public Readable getUserInput() throws ImageProcessorException{
    return this.input;
  }

  @Override
  public boolean confirmSplitView(IntConsumer updateImageCallback) throws
          ImageProcessorException {
    throw new ImageProcessorException("Slider not supported in console input");
  }


}
