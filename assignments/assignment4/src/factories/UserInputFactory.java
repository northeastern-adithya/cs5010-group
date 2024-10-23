package factories;

import java.io.InputStream;

import view.input.ConsoleInput;
import view.input.UserInput;

public class UserInputFactory {

  private UserInputFactory() {
  }

  public static UserInput createUserInput(InputStream input) {
    return new ConsoleInput(input);
  }
}
