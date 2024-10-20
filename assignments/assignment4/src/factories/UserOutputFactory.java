package factories;

import view.output.ConsoleOutput;
import view.output.UserOutput;

public class UserOutputFactory {
  public static UserOutput createUserOutput(Appendable output) {
    return new ConsoleOutput(output);
  }
}
