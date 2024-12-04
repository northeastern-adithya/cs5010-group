import controller.ImageController;
import model.ImageImplementation;
import model.ImageModel;
import view.ImageProcessingGUI;
import controller.ImageProcessingGUIController;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Entry point for the image processing app. Initializes model, view, and controller, and runs the
 * app in GUI, Script, or Text mode based on command-line arguments.
 */
public class ImageProcessingApp {

  /**
   * The entry point for the image processing application. Initializes the model, view, and
   * controller, and runs the image processing script based on the command-line arguments.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    // Initialize the model, view, and controller
    ImageModel model = new ImageImplementation(); // Model for image processing
    ImageProcessingGUI view = new ImageProcessingGUI(); // View for displaying the UI
    ImageController controller = new ImageController(model,
        view,new HashMap<>()); // ImageController handles image processing

    // Initialize the new ImageProcessingGUIController to link the view and controller
    ImageProcessingGUIController guiController = new ImageProcessingGUIController(view, controller,
        model);

    // Set the GUI controller in the view
    view.setController(guiController);

    // Check the command-line arguments to decide the mode
    if (args.length == 0) {
      // GUI Mode: No arguments passed, open GUI
      System.out.println("Launching in GUI mode...");
      // The GUI will open automatically when the program is run
      view.showGUI();

    } else if (args.length == 2 && "-file".equals(args[0])) {
      // Script Mode: Execute the script file provided as the second argument
      String scriptPath = args[1];
      File scriptFile = new File(scriptPath);
      if (scriptFile.exists() && scriptFile.isFile()) {
        System.out.println("Running script: " + scriptPath);
        controller.runScript(scriptPath);  // Execute the script and shut down
      } else {
        System.out.println("Error: The specified script file does not exist.");
        System.exit(1);
      }

    } else if (args.length == 1 && "-text".equals(args[0])) {
      // Text Mode: Interactive mode where the user types one command at a time
      System.out.println("Launching in text mode...");

      // Use Scanner for interactive command-line input
      Scanner scanner = new Scanner(System.in);
      String command;

      // Start a loop to read commands from the user
      while (true) {
        System.out.print("Enter command: ");
        command = scanner.nextLine();

        // If the user types 'exit', break the loop
        if ("exit".equalsIgnoreCase(command)) {
          System.out.println("Exiting text mode.");
          break;  // Exit the loop and terminate the program
        }

        // Process the command here
        if (!command.isEmpty()) {
          controller.processCommand(command);  // Process the command with the controller
        } else {
          System.out.println("Invalid command. Please try again.");
        }
      }

      scanner.close();  // Close the scanner after use

    } else {
      // Invalid arguments
      System.out.println("Invalid arguments. Please use one of the following:");
      System.out.println("  java -jar Program.jar               # GUI Mode");
      System.out.println("  java -jar Program.jar -file <path>   # Script Mode");
      System.out.println("  java -jar Program.jar -text         # Text Mode");
      System.exit(1); // Exit the application with an error code
    }
  }
}
