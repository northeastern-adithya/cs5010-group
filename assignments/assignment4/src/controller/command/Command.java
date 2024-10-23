package controller.command;

import java.util.Scanner;

import controller.model.ExecutionStatus;


public interface Command {

  ExecutionStatus execute(Scanner scanner);
}
