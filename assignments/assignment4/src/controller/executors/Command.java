package controller.executors;

import java.util.Scanner;

import controller.model.ExecutionStatus;


/**
 * Command interface that provides a method to
 * execute the command.
 */
public interface Command {

  /**
   * Executes the command given by user and does the required operations.
   *
   * @param scanner Scanner object to read the input
   * @return execution status of the command containing if
   * the command was successful and the message
   */
  ExecutionStatus execute(Scanner scanner);
}
