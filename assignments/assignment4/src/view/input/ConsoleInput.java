package view.input;

import java.io.InputStream;
import java.util.Objects;

public class ConsoleInput implements UserInput {

  private final InputStream input;

  public ConsoleInput(InputStream input) {
    Objects.requireNonNull(input, "Input cannot be null");
    this.input = input;
  }

  public InputStream getUserInput() {
    return this.input;
  }


}
