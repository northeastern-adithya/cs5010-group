package controller.command;

import java.util.Scanner;


public interface Command {

  ExecutionStatus execute(Scanner scanner);
}
