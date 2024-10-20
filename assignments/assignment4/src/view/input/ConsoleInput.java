package view.input;

import java.io.InputStream;

public class ConsoleInput implements UserInput {

  private final InputStream input;

  public ConsoleInput(InputStream input) {
    this.input = input;
  }

  public InputStream getUserInput() {
    return this.input;
  }


}
