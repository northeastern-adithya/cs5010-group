package controller;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit test class for MockImageController to verify that runScript and processCommand log the
 * expected actions.
 */

public class MockImageControllerTest {

  @Test
  public void testRunScript() {
    MockImageController mockController = new MockImageController();
    mockController.runScript("res/PNG/PNGScript.txt");

    // Assert that the log contains the expected entry
    assertTrue(mockController.getLog().contains("Running the script: res/PNG/PNGScript.txt"));
  }

  @Test
  public void testProcessCommand() {
    MockImageController mockController = new MockImageController();
    mockController.processCommand("load res/JPG/building.jpg");

    // Assert that the log contains the expected command
    assertTrue(mockController.getLog().contains("Processing command: load res/JPG/building.jpg"));
  }

  @Test
  public void testMultipleCommands_logsCorrectly() {
    // Arrange: prepare multiple commands
    MockImageController mockController = new MockImageController();
    String command1 = "load res/JPG/building.jpg building";
    String command2 = "sepia building building_sepia";

    // Act: call processCommand for both commands
    mockController.processCommand(command1);
    mockController.processCommand(command2);

    // Debugging: Print the log to see what has been added
    System.out.println("Log contents: " + mockController.getLog());

    // Assert: check if both commands were logged correctly
    String logContents = String.join(" ", mockController.getLog()).trim();

    assertTrue(logContents.contains("Processing command: load res/JPG/building.jpg"));
    assertTrue(logContents.contains("Processing command: sepia building building_sepia"));
  }



}