package controller;


/**
 * The ImageController interface defines the operations for controlling image processing tasks using
 * an underlying ImageModel. It is responsible for managing images and executing commands from a
 * script.
 */
public interface ControllerInterface {

  /**
   * Reads and executes commands from a specified script file. The script can include commands such
   * as loading, saving, and processing images.
   *
   * @param filepath the path to the script file
   */
  void runScript(String filepath);

  /**
   * Processes individual commands from the script and performs the appropriate action on the
   * ImageModel.
   *
   * @param command the command to be processed
   */
  void processCommand(String command);

}
