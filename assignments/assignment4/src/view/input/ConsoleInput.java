package view.input;

import java.io.InputStream;
import java.util.Objects;

/**
 * Console input represents all the inputs
 * taken from command line .
 */
public class ConsoleInput implements UserInput {

  /**
   * The input stream from which the user is communicating.
   */
  private final InputStream input;

  /**
   * Constructs a ConsoleInput object with the given input stream.
   *
   * @param input the input stream from which the user is communicating.
   * @throws NullPointerException if the input stream is null.
   */
  public ConsoleInput(InputStream input) {
    Objects.requireNonNull(input, "Input cannot be null");
    this.input = input;
  }


  public InputStream getUserInput() {
    return this.input;
  }


}
