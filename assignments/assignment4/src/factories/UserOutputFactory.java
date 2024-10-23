package factories;

import view.output.ConsoleOutput;
import view.output.UserOutput;

/**
 * Factory class to create UserOutput objects.
 */
public class UserOutputFactory {


  private UserOutputFactory() {
    // Private constructor to prevent instantiation.
  }

  /**
   * Creates a UserOutput object to write user output.
   * Outputs are written to the given output stream.
   *
   * @param output the output stream to write the user output
   * @return the UserOutput object
   */
  public static UserOutput createUserOutput(Appendable output) {
    return new ConsoleOutput(output);
  }
}
