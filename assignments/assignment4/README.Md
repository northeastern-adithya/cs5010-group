# Image Processor Application

## Setup
### Prerequisites
- Java version 11.0.24 

### Steps to Run Script
- Clone the repository.
- run `src/app/ImageProcessorApp.java`
- execute the command `run <path-to-script-file>` on the console.

## Packages and MVC Structure

### `app` Package
- **Class**: `ImageProcessorApp`
  - **Purpose**: The entry point of the application.

### `controller` Package
- **Interfaces**:
  - `ImageProcessorController`
    - **Purpose**: Interface for the controller that processes user commands.
- **Classes**:
  - `SimpleImageProcessorController`
    - **Purpose**: A simple implementation of the `ImageProcessorController` interface that processes user commands.
- #### `command` Package
  - **Interfaces**:
    - `Command`
      - **Purpose**: Interface for user commands. 
  - **Classes**:
    - `AbstractCommand`
      - **Purpose**: Provides a common structure for all command classes to implement.
    - `BlueComponentCommand`
      - **Purpose**: Concrete command class that extracts the blue component of the image.
    - `BlurCommand`
      - **Purpose**: Concrete command class that applies a blur filter to an image.
    - `BrightenCommand`
      - **Purpose**: Concrete command class that increases the brightness of the image.
    - `GreenComponentCommand`
      - **Purpose**: Concrete command class that extracts the green component of the image.
    - `HelpCommand`
      - **Purpose**: Concrete command class that displays help or usage instructions for the available commands.
    - `HorizontalFlipCommand`
      - **Purpose**: Concrete command class that flips the image horizontally, creating a mirrored version of the image.
    - `IntensityComponentCommand`
      - **Purpose**: Concrete command class that extracts the intensity component of the image.
    - `LoadCommand`
      - **Purpose**: Concrete command class that loads an image from the specified path to memory.
    - `LumaComponentCommand`
      - **Purpose**: Concrete command class that extracts the Luma component of the image.
    - `QuitCommand`
      - **Purpose**: Concrete command class that exits the application safely.
    - `RedComponentCommand`
      - **Purpose**: Concrete command class that extracts the red component of the image.
    - `RGBCombineCommand`
      - **Purpose**: Concrete command class that combines the red, green, and blue components of separate images into one composite image.
    - `RGBSplitCommand`
      - **Purpose**: Concrete command class that splits an image into its red, green, and blue component images.
    - `RunCommand`
      - **Purpose**: Concrete command class that executes a predefined script image processing commands.
    - `SaveCommand`
      - **Purpose**: Concrete command class that saves the current state of the image to a file.
    - `SepiaCommand`
      - **Purpose**: Concrete command class that extracts the sepia value of an image.
    - `SharpenCommand`
      - **Purpose**: Concrete command class that sharpens the image, enhancing the edges and details.
    - `ValueComponentCommand`
      - **Purpose**: Concrete command class that extracts the value component of the image's pixels.
    - `VerticalFlipCommand`
      - **Purpose**: Concrete command class that flips the image vertically, creating an upside-down version of the image.
- #### `model` Package
  - **Classes**:
    - `ExecutionStatus`
      - **Purpose**: Represents the execution statistics of a command.

### `exception` Package
- **Classes**:
  - `DisplayException`
    - **Purpose**: Custom exception used to handle errors during display of images.
  - `ImageProcessingRuntimeException`
    - **Purpose**: Custom exception used to handle runtime errors during image processing.
  - `ImageProcessingException`
    - **Purpose**: Custom exception used to handle errors during image processing.
  - `NotFoundException`
    - **Purpose**: Custom exception used to handle errors when an image is not found.
  - `NotImplementedException`
    - **Purpose**: Custom exception used to handle errors when a method is not implemented.
  - `QuitException`
    - **Purpose**: Custom exception used to signal the termination of the command processing loop.

### `factories` Package
- **Classes**:
  - `CommandFactory`
    - **Purpose**: Factory class for creating instances of `Command`.
  - `ControllerFactory`
    - **Purpose**: Factory class for creating instances of `ImageProcessorController`.
  - `FilterFactory`
    - **Purpose**: Factory class for creating instances of image filters.
  - `ImageFactory`
    - **Purpose**: Factory class for creating instances of `Image`.
  - `ImageMemoryFactory`
    - **Purpose**: Factory class for managing image memory.
  - `ImageProcessingServiceFactory`
    - **Purpose**: Factory class for creating instances of the image processing service.
  - `ImageReaderFactory`
    - **Purpose**: Factory class for creating instances of image readers.
  - `ImageWriterFactory`
    - **Purpose**: Factory class for creating instances of image writers.
  - `PixelFactory`
    - **Purpose**: Factory class for creating instances of `Pixel`.
  - `UserInputFactory`
    - **Purpose**: Factory class for creating instances of user input.
  - `UserOutputFactory`
    - **Purpose**: Factory class for creating instances of user output.

### `filters` Package
- **Enumerable**:
  - `FilterOptions`
    - **Purpose**: Enum that represents different types of filters.
- **Interfaces**:
  - `Filter`
    - **Purpose**: Interface for image filters.
- **Classes**:
  - `AbstractFilter`
    - **Purpose**: Abstract class that implements the `Filter` interface and provides common functionality for all filters.
  - `Blur`
    - **Purpose**: Concrete filter class that applies a Gaussian blur to an image.
  - `Sharpen`
    - **Purpose**: Concrete filter class that sharpens an image.

### `model` Package
- **Enumerable**:
  - `ImageType`
    - **Purpose**: Enum that represents different types of images.
  - `PixelType`
    - **Purpose**: Enum that represents different types of pixels.
  - `UserCommand`
    - **Purpose**: Enum that represents different user commands.
- #### `visual` Package
  - **Interfaces**:
    - `Image`
      - **Purpose**: Interface to represent an Image.
  - **Classes**:
    - `RenderedImage`
      - **Purpose**: Concrete class that represents a rendered image.
- #### `pixels` Package
  - **Interfaces**:
    - `Pixel`
      - **Purpose**: Interface for to represent a Pixel.
  - **Classes**:
    - `AbstractPixel`
      - **Purpose**: Abstract class that provides common functionality for all pixel classes.
    - `Pixel`
      - **Purpose**: Represents a pixel in an image.
- #### `memory` Package
  - **Interfaces**:
    - `ImageMemory`
      - **Purpose**: Interface for managing image memory.
  - **Classes**:
    - `HashMapImageMemory`
      - **Purpose**: Concrete class that implements the `ImageMemory` interface using a HashMap to store images.

### `reader` Package
- **Interfaces**:
  - `ImageReader`
    - **Purpose**: Interface for reading images from a file.
- **Classes**:
  - `AbstractImageReader`
    - **Purpose**: Abstract class that provides common functionality for all image readers.
  - `JPGImageReader`
    - **Purpose**: Concrete class that reads JPG images from a file.
  - `PNGImageReader`
    - **Purpose**: Concrete class that reads PNG images from a file.
  - `PPMImageReader`
    - **Purpose**: Concrete class that reads PPM images from a file.

### `services` Package
- **Interfaces**:
  - `ImageProcessingService`
    - **Purpose**: Interface for image processing services.
- **Classes**:
  - `SimpleImageProcessingService`
    - **Purpose**: Concrete class that implements the `ImageProcessingService` interface and provides image processing services.

### `utility` Package
- **Classes**:
  - `ImageUtility`
    - **Purpose**: Utility class for image processing operations.
  - `PixelTranformUtility`
    - **Purpose**: Utility class for pixel transformation operations.
  - `StringUtils`
    - **Purpose**: Utility class for string manipulation operations.

### `view` Package
- #### `input` Package
  - **Interfaces**:
    - `UserInput`
      - **Purpose**: Interface for user input.
  - **Classes**:
    - `ConsoleInput`
      - **Purpose**: Concrete class that implements the `UserInput` interface and provides console-based user input.
- #### `output` Package
  - **Interfaces**:
    - `UserOutput`
      - **Purpose**: Interface for user output.
    - `ConsoleOutput`
      - **Purpose**: Concrete class that implements the `UserOutput` interface and provides console-based user output.

### `writer` Package
- **Interfaces**:
  - `ImageWriter`
    - **Purpose**: Interface for writing images to a file.
- **Classes**:
  - `AbstractImageWriter`
    - **Purpose**: Abstract class that provides common functionality for all image writers.
  - `JPGImageWriter`
    - **Purpose**: Concrete class that writes JPG images to a file.
  - `PNGImageWriter`
    - **Purpose**: Concrete class that writes PNG images to a file.
  - `PPMImageWriter`
    - **Purpose**: Concrete class that writes PPM images to a file.

## MVC Architecture

This project follows the Model-View-Controller (MVC) architecture, which helps in organizing the codebase into three main components:

1. **Model**:
  - **Purpose**: Represents the data and the business logic of the application.
  - **Packages**:
    - `model`: Contains classes like `ExecutionStatus` that represent the state and data of the application.
    - `memory`: Manages image memory through interfaces like `ImageMemory` and classes like `SimpleImageMemory`.
    - `utility`: Provides utility classes for image and pixel processing operations.

2. **View**:
  - **Purpose**: Handles the presentation layer and user interface.
  - **Packages**:
    - `view.input`: Contains interfaces and classes for user input, such as `UserInput` and `ConsoleInput`.
    - `view.output`: Contains interfaces and classes for user output, such as `UserOutput` and `ConsoleOutput`.

3. **Controller**:
  - **Purpose**: Manages the flow of the application, processes user commands, and updates the model and view.
  - **Packages**:
    - `controller`: Contains interfaces and classes for the controller logic, such as `ImageProcessorController` and `SimpleImageProcessorController`.
    - `controller.command`: Contains command classes that execute specific actions, such as `LoadCommand`, `BlurCommand`, and `SaveCommand`.
