package controller;

import java.util.ArrayList;
import java.util.List;

/**
 * MockImageController is a mock implementation of the ControllerInterface. It logs method calls
 * instead of performing image processing, useful for testing to verify that the correct methods are
 * called.
 */

public class MockImageController implements ControllerInterface {

  private List<String> log = new ArrayList<>();  // List to log the actions

  @Override
  public void runScript(String filepath) {
    // Simulating script execution
    log.add("Running the script: " + filepath);
  }

  @Override
  public void processCommand(String command) {
    // Log the command that is being processed
    log.add("Processing command: " + command);
  }

  // Additional method to retrieve logs for assertions in your tests
  public List<String> getLog() {
    return log;
  }
}
