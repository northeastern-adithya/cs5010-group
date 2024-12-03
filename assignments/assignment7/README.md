----------------------------------------------------------------------------------------------------
Image Manipulation & Enhancement Application
----------------------------------------------------------------------------------------------------

Citation: All images used in this project are original photographs taken by Khushee Vakil and
Rida Sameer.
----------------------------------------------------------------------------------------------------
Description:
----------------------------------------------------------------------------------------------------

This is a Java-based application with the provision to manipulate and enhance images through various
operations such as color transformation, blurring, sharpening, flipping, and much more.
The application supports several image formats, including PPM, JPG, and PNG. It has the facility for
manual and scripting modes in which most scripts are provided beforehand.

An image processing application with a graphical user interface is further developed in this project.
The user will be able to load, process, and save photos interactively as a result. This iteration's
output will be a software that allows users to interact with it.

----------------------------------------------------------------------------------------------------
Design Overview:
----------------------------------------------------------------------------------------------------

The application is designed by following the Model-View-Controller architecture; hence, there is a
proper separation of concerns between the image processing logic, the handling of user input, and
the presentation of the output.

1. Model: Handles the logic related to image processing, such as loading, saving, and manipulating
   images.

2. View: It would be responsible for displaying the outputs to the user either via a command-line
   interface or a future graphical user interface.

3. Controller: Connects model and view, interpreting user commands into suitable model operations.

4. JAR file: The JAR file bundles the entire application, allowing it to be executed seamlessly in
   GUI, script, or text mode by using appropriate command-line arguments.

----------------------------------------------------------------------------------------------------
STRUCTURE:
----------------------------------------------------------------------------------------------------

The assignment is organized into the following main components: Model, View & Controller.

MODEL: Contains classes that handle core image processing logic.

- The ImageModel Interface: It is the interface that contains the contract for image
  processing, specifying all the basic methods for manipulating, analyzing, and transforming pictures.
  would include functions like flipHorizontally, colorCorrect, applyFilter, saveImage, among others.
  It guarantees that all classes that implement it follow uniform functionality for image processing
  activities.

- ImageImplementation Class: Implementing the ImageModel interface and offering tangible
  implementations for all defined methods is the responsibility of the ImageImplementation Class.
  Ensures a constant internal structure by representing and manipulating images using a 2D array of
  Pixel objects. Supports a number of functions, including brightness tweaks, grayscale conversions,
  and filtering. Offers the ability to apply filters and modifications to particular areas of an image,
  such as the left side up to a certain percentage.

- Pixel: One pixel in an RGB image is represented by the Pixel Class. Contains the channel values
  for red, green, and blue. Guarantees the immutability of the pixel state unless specifically
  changed and offers methods to retrieve or set channel values.

- MaskInterface Interface : Specifies methods for applying transformations and filters to images
  selectively, based on a masking mechanism. Methods include applyRedComponentWithMask,
  applyBlurWithMask, applySepiaWithMask and others.Defines a mechanism (shouldApplyMask) to determine
  where the mask should be applied.

- ImageMaskProcessor Class: Implements the MaskInterface, extending image processing to handle
  masked operations. Introduces logic to selectively apply transformations to masked regions while
  preserving the rest of the image. Facilitates complex image processing scenarios like masked
  blurring or grayscale conversion.

Design Justifications for the Model.

1. Image Representation and Design Choices :
- Keeping the image as a 2D array of pixel objects allows for fine-grained control over each
  individual pixel.
  -Encapsulation of Pixel Logic: Following object-oriented guidelines, the Pixel class encapsulates
  a pixel's attributes and actions.
  -ImageImplementation and ImageMaskProcessor's processing logic is made simpler by this abstraction.

2. Modular Image Processing via Interfaces

3. Techniques for Image Transformation
- Application in ImageImplementation: Matrix operations are used to effectively implement techniques
  such as applyFilter and applyGreyScale.
  -Contains helper methods to aid readability and cut down on redundancy, such
  as convertSingleChannelToGrayscaleImage.
  -Operations Based on Splits: Partial transformations are possible using methods like
  applyBlur(double p), which increases flexibility. For complex picture altering situations,
  such as focusing effects, these features are essential.
- Generating Histograms: For activities like color correction, the calculateHistogram method offers
  visual insights into the distribution of color intensity.

----------------------------------------------------------------------------------------------------
VIEW: Our project's view layer is in charge of managing user interactions and creating the
graphical user interface (GUI). Java Swing components and interfaces are used in combination to
construct this layer.


View Components
1. ViewInterface : This is the view layer's main interface, which defines methods for: Informational
   message display (displayMessage). Error messages are displayed (displayError).

Design Justification: Adheres to the Interface Segregation Principle by focusing solely on general
view-related methods. Provides flexibility to create multiple view implementations.
(e.g., console-based or GUI-based).

2. ImageProcessingView : Extends ViewInterface and adds methods specific to GUI-based
   image processing:
   -load and save for file input/output through a graphical file chooser.
   -displayImage for rendering images on the GUI.
   -displayError for showing error messages via dialogs.

Design Justification:
Provides a specialized contract for GUI views without polluting the general-purpose ViewInterface.
Ensures separation of concerns, isolating GUI-specific functionality from other view types.

3. ImageView : Implements ViewInterface as a console-based view: Outputs messages and errors to the
   console.

4. ImageProcessingGUI : Implements ImageProcessingView, serving as the main GUI for the application.

Key Features:

Layout and Components:
- Uses a JFrame to create the main application window.
- Includes a JSplitPane for displaying the image on one side and a histogram panel on the other.
- Adds a JPanel for arranging buttons like "Load Image," "Save Image," "Sepia," "Blur," etc., using
  a grid layout.

File Operations: Implements load and save using JFileChooser for interactive file selection.

Image Display: Displays images using JLabel with an ImageIcon. Supports rendering from both PPM and
standard formats (PNG, JPG).

Histogram Visualization: Displays histograms in a dedicated panel, refreshing dynamically.

User Interaction: Uses JOptionPane dialogs for input (e.g., split percentage) and confirmations.
Associates buttons with actions by setting ActionListener instances.

Design Justification:

1. Separation of Concerns: The GUI layout and user interactions are encapsulated in the
   ImageProcessingGUI class, keeping the controller and model logic separate.
2. Extensibility: Easily extendable by adding new buttons or features to the GUI.
3. User-Centric Design: Provides an intuitive layout with clear labels and a scrollable image
   display for large images.
4. Error Handling: Displays error messages using dialog boxes, ensuring clarity and user feedback.

Design Justification:
Offers a lightweight alternative for text-based interaction or debugging.
Useful in scenarios where a GUI is not necessary, adhering to modularity and reusability principles.

----------------------------------------------------------------------------------------------------
CONTROLLER: Manages the workflow between the view and model.
----------------------------------------------------------------------------------------------------

1. ControllerInterface : Provides a contract for controllers handling image processing tasks.

Methods:
runScript(String filepath): Reads and executes commands from a script file.
processCommand(String command): Processes a single command for the ImageModel.

Design Justification:
Facilitates extensibility by allowing different controller implementations for varied use cases
(e.g., GUI-based or script-based). Enforces uniformity across controllers.

2. ImageController : Implements ControllerInterface, serving as the primary controller for the
   application.

3. ImageProcessingGUIController : Implements Features and acts as the controller for the GUI view.

Responsibilities:
Bridges interactions between the GUI (ImageProcessingGUI) and the model.
Handles user actions like button clicks and translates them into model operations.
Parses script commands and invokes corresponding model methods.
Manages a centralized store (imageStore) to hold multiple image states for transformations and processing.
Handles tasks like loading, saving, applying filters, and splitting/combining RGB channels.

Key Features:
Interactive User Prompts: Uses dialogs to gather user input (e.g., split percentages, brightness
levels).

Image Operations:
Applies transformations like blur, sepia, and flip using model methods.
Updates the view with processed images and histograms.

Design Justification:
Integration: Ensures smooth communication between the view and model layers.
User Experience: Provides feedback to users through dialogs and real-time updates.
Reusability: Implements Features for consistent operation handling across different controllers.

4. Features : Defines user-triggered operations for the GUI.

Methods: loadImage, saveImage, applySepiaEffect, applyBlurEffect, etc.

Design Justification:
Simplification: Provides a clear set of actions the GUI controller must support.
Consistency: Ensures all GUI controllers adhere to the same interface.

5. ImageUtil : Contains utility methods for loading and saving images.

Key Features: Image Loading, Supports PPM and other formats (e.g., JPG, PNG).
Converts image data into a 2D array of Pixel objects.

6. MockImageController : Simulates the behavior of ImageController for testing.
   Logs executed commands for validation in unit tests.

Design Justification: Facilitates testing without relying on the actual implementation.

7. MockGUIController : Simulates the behavior of ImageProcessingGUIController.
   Logs user actions (e.g., applyBlurEffect, loadImage) for testing.

Design Justification:
Simplifies testing by validating user-triggered operations independently of the view and model.

----------------------------------------------------------------------------------------------------
Main Application: `ImageProcessingApp.java` is the entry point of the application. It initializes
the model, view, and controller and runs the specified script.
----------------------------------------------------------------------------------------------------

Core Responsibilities
- Initialization: Creates instances of the model (ImageImplementation), view (ImageProcessingGUI),
  and primary controllers (ImageController and ImageProcessingGUIController). Links the GUI controller
  to the view using view.setController(guiController).

- Modes of Operation: The application supports three modes:
  1.GUI Mode: If no arguments are provided, the application launches in GUI mode. The graphical user
  interface is displayed using view.showGUI().

2. Script Mode: If -file <path> is provided as arguments, the application executes the commands from
   the specified script file. The ImageController processes the script commands using
   controller.runScript(scriptPath). Provides feedback if the script file is invalid.

3. Text Mode: If -text is passed as an argument, the application runs in interactive text mode.
   Accepts commands one at a time from the user via the console and processes them using
   controller.processCommand(command). Allows the user to exit by typing exit.

- Error Handling:
  Validates the script file path in script mode and displays appropriate error messages for invalid files.
  Provides feedback for invalid command-line arguments.

- User Guidance:
  Displays usage instructions for invalid arguments or when users need help with command-line options.

----------------------------------------------------------------------------------------------------
Design Patterns and Principles
----------------------------------------------------------------------------------------------------

Model-View-Controller (MVC) Architecture:

1. Model: Performs image processing logic and stores application data (ImageImplementation).
2. View: Handles rendering the GUI and receiving user input (ImageProcessingGUI).
3. Controller: Processes user commands and coordinates between the view and model
   (ImageProcessingGUIController).

Justification:
- Ensures a clean separation of concerns, allowing each component to focus on its specific responsibility.
- Facilitates future modifications (e.g., changing the GUI framework without affecting the model logic).
- Single Responsibility Principle: Each class in the view layer has a single responsibility:
* ViewInterface provides a basic contract for displaying messages.
* ImageProcessingView extends this for GUI-specific functionalities.
* ImageProcessingGUI implements the GUI with Swing components.
- Dependency Inversion Principle: The view depends on abstractions (ImageProcessingView and
  ViewInterface), not concrete implementations.
- Extensibility: Adding new features like buttons or additional panels is straightforward,
  given the modular design.
- Error Handling: Errors are displayed interactively (dialogs) or logged to the console, ensuring
  robust user feedback mechanisms.

----------------------------------------------------------------------------------------------------
Features
----------------------------------------------------------------------------------------------------

The application supports various image processing operations, including:

1. Color Transformations
    - Apply grayscale and sepia effects to portions or entire images.
    - Adjust brightness and color levels.

2. Filtering
    - Apply Gaussian blur and sharpening filters to portions or entire images.

3. Compression
    - Perform Haar wavelet-based compression on images, thresholding based on a specified
      compression percentage.

4. Image Manipulations
    - Flip images vertically and horizontally.
    - Extract and combine color channels.

5. Image downscaling

6. Partial Image Manipulation

----------------------------------------------------------------------------------------------------
Advanced Features - Extra Credit
----------------------------------------------------------------------------------------------------

LEVEL 1: Image downscaling is the process of reducing the dimensions of an image (width, height, or both)
while preserving its essential content. This operation is commonly used for creating thumbnails,
optimizing image storage, or preparing images for specific resolutions.

Implementation Overview:
The downscaling operation involves reducing the number of pixels in an image while maintaining the
overall visual appearance.
For every new pixel in the downscaled image, its color is calculated by averaging the colors of a
group of pixels from the original image that correspond to that pixel.
This ensures the visual fidelity of the downscaled image while efficiently reducing its size.

LEVEL 2: Partial Image Manipulation

The partial image manipulation feature enables the application of transformations (e.g., blur,
grayscale, sepia) to selected parts of an image using a mask image.

Implementation Overview:

-Mask Image: A mask image (MI) is a black-and-white image of the same dimensions as the original
image (I).

- Pixels in MI determine whether a transformation is applied:
  Black Pixel: The transformation is applied to the corresponding pixel in I.
  White Pixel: The pixel remains unaltered.

- Operations Supported:
  Blur, sharpen, grayscale, sepia, and others can be applied selectively to regions defined by the
  mask image.

----------------------------------------------------------------------------------------------------
Key Components
----------------------------------------------------------------------------------------------------

1. Pixel Class:
   Stores and performs basic operations on the RGB values of each individual pixel.

Methods:
int get(int channel): Returns the value of a specified RGB channel (0 for red, 1 for green,
2 for blue).
void set(int channel, int value): Sets the value of a specified RGB channel.

2. ImageUtil Class:
   Reads and writes image files. Right now, images are stored in PPM format, but this will change.

Methods:
Pixel[][] readPPM(String filename): This method reads a PPM file and returns it as a 2-D array of
Pixel objects.
void savePPM(String filename, Pixel[][] pixels): This method takes a 2-D pixel array and saves it
to a PPM image file.

3. ImageModel Interface:
   Defines the basic operations that may be performed on images, including loading and saving, and
   the
   manipulation of pixel data.

Methods:
loadImage(String fileName, String imageName) - This method loads the image from a given file.
void saveImage(String imageName, String fileName): Saves the manipulated image to a file.
Various transformation methods like blur, sharpen, flipHorizontal, flipVertical, greyscale,
compression, histogram, color correction, levels adjust, split and many others.

4. ImageImplementation class (implements ImageModel)
   Provides an implementation for the operations in the ImageModel interface.

Key functionality includes
Green Scaling using various color components
Horizonatal and/or vertical flip
Blurry, sharpening and sepia toning of the image
Maintain pictures using 2D array of Pixel objects to allow manipulation
Compression, histogram, color correction, levels adjust and split operation for an image.

5. ImageController Class
   This is the controller of the application. It takes user provided inputs/commands and calls the
   corresponding model operation.

Methods:
void executeCommand(String command): Parses and executes a command entered by the user.
Contains logic to handle command line inputs as well as scripted commands.

6. ImageProcessingApp Class
   The main point of the application.

Responsibilities
The class instantiates a model and controller, takes user inputs or a script file to automate
operations, and invokes image manipulations.

7. Command Classes:
   Provide methods for performing different image manipulation operations. The following, but not
   limited to, is performed by these methods.

ColorTransformations: This class implements operations like red, green, blue, luma, intensity,
and value transformations.
Blurring and Sharpening: An operation to change the pixel values of an image in order to blur or
sharpen the image.
Flipping: An operation that changes an image by flipping horizontally or vertically.
Sepia and Grayscale: Applies sepia tone or transforms the image to grayscale based on the luma
value.

8. Scripted Operations
   The following application supports script operations in order to automate the image manipulation
   process.

Example Scripts:
JPGScript.txt: This operates on loading of a JPG image, applies color transformation, flips, blurs,
sharpens, and saves the output as a JPG file(JPGScript).
PNGScript.txt: The same for the image in PNG format; doing some operations and storing the results
in the form of PNG files.
PPMScript.txt: for PPM format supporting the same set of manipulations,
save results as PPM files: PPMScript.

9. Implemented Features
   Image Operations:
   -Extract Red, Green, and Blue channels.
   -Greyscale Conversions: luma, intensity, value.
   -Brighten and darken images.
   -Blur and sharpen filters.
   -Flip horizontally and vertically.
   -Sepia tone transformation.
   -Split and combine RGB.
   -Image Formats: Program supports JPG, PNG, and PPM formats.
   -Command-Based Interaction: User can create or load commands and execute scripts that detail in
   full
   or in part how the manipulation process is to be carried out.

10. Setting up:
    -Open the project in IntelliJ or any Java IDE.
    -Make sure res folder containing input images should be present
    -Compile and run the ImageProcessingApp class.
    -Running the Application:
    -You will be able to run the application interactively typing commands i n terminal or load a
    script
    file with predefined set of commands.

Example Commands:
-load res/JPG/sample.jpg sample: You load a JPG image.
-blur sample: Then you apply blur on the image.
-save res/JPG/sample_blur.jpg sample: Saves the blurred image out to a file as a JPG.

Running from a Script:
-Use scripts such as JPGScript.txt, PNGScript.txt, or PPMScript.txt to drive the automation.
Example:
run-script res/scripts/JPGScript.txt: Runs a series of operations as specified in the script file.

11. File Structure

Model: Contains the main logic behind the manipulation of images (Pixel, ImageImplementation,
ImageModel).

Controller: This class handles the user input and then sends commands for execution.
(ImageController)

Utilities to deal with image files - reading/writing, by class ImageUtil.

View: It provides scope for future implementation of GUI, whereas presently it is based on CLI.

----------------------------------------------------------------------------------------------------
Image Processing Application
----------------------------------------------------------------------------------------------------

This README documents the current state of the Image Processing Application, including completed
functionalities, design choices, and justifications for design decisions.

----------------------------------------------------------------------------------------------------
Project Structure
----------------------------------------------------------------------------------------------------

The project is organized into the following directories:

- src: Contains the source code for the application, including model, view, and controller
  components.
- res: Contains resources such as sample images and scripts for testing.
- test: Contains test cases that verify the functionality of the application.
- jar application

----------------------------------------------------------------------------------------------------
How to Run the Application:
----------------------------------------------------------------------------------------------------

Three command-line inputs are valid:

1. java -jar Program.jar -file path-of-script-file : when invoked in this manner the program should
   open the script file, execute it and then shut down.

2. java -jar Program.jar -text : when invoked in this manner the program should open in an
   interactive text mode, allowing the user to type the script and execute it one line at a time.
   This is how the program worked in the last assignment.

3. java -jar Program.jar : when invoked in this manner the program should open the graphical user
   interface. This is what will happen if you simply double-click on the jar file.

Any other command-line arguments are invalid: in these cases the program should display an error
message suitably and quit.

----------------------------------------------------------------------------------------------------