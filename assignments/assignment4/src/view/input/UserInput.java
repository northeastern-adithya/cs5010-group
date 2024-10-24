package view.input;


/**
 * Represents the user input.
 * This input can be from any source like console, file etc.
 */
public interface UserInput {

  /**
   * Get the user input
   * which the user is communicating.
   * @return the readable
   * which the user has communicating.
   */
  Readable getUserInput();
}
