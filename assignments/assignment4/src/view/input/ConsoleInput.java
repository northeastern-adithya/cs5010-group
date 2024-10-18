package view.input;

public class ConsoleInput implements UserInput {

  private final Readable input;
  public ConsoleInput(Readable input) {
    this.input = input;
  }

  public Readable getUserInput(){
    return this.input;
  }


}
