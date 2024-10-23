package controller.executors;


import controller.model.ExecutionStatus;
import view.input.UserInput;


/**
 * Command interface that provides a method to
 * execute the command.
 */
public interface Command {

  /**
   * Executes the command given by user and does the required operations.
   *
   * @param input input given by the user
   * @return execution status of the command containing if
   * the command was successful and the message
   */
  ExecutionStatus execute(UserInput input);
}
