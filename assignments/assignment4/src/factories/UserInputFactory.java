package factories;

import java.io.InputStream;

import view.input.ConsoleInput;
import view.input.UserInput;

/**
 * Factory class to create UserInput objects.
 */
public class UserInputFactory {

  private UserInputFactory() {
    // Private constructor to prevent instantiation.
  }


  /**
   * Creates a UserInput object to read user input.
   * Inputs are read from the given input stream.
   *
   * @param input the input stream to read the user input
   * @return the UserInput object
   */
  public static UserInput createUserInput(InputStream input) {
    return new ConsoleInput(input);
  }
}
