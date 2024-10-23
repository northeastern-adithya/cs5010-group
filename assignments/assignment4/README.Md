To segregate the provided code into packages and MVC structure, we can organize the classes as follows:

### Packages and MVC Structure

#### `app` Package
- **Class**: `ImageProcessorApp`
  - **Purpose**: The entry point of the application.

#### `controller` Package
- **Classes**: 
  - `ImageProcessorController`
    - **Purpose**: Handles user commands and coordinates the image processing tasks.
  - `SimpleImageProcessorController`
    - **Purpose**: A simple implementation of the `ImageProcessorController` interface that processes user commands.
- ### `command` Package
  - **Classes**:
    - `Command`
      - **Purpose**: Interface for user commands.
    - `AbstractCommand`
      - **Purpose**: Provides a common structure for all command classes to implement.
    - `BlueComponentCommand`
      - **Purpose**: Concrete command class that applies the blue component filter to an image, isolating and enhancing the blue tones.
    - `BlurCommand`
      - **Purpose**: Concrete command class that applies a blur filter to an image.
    - `BrightenCommand`
      - **Purpose**: Concrete command class that increases the brightness of the image by adjusting the light intensity.
    - `GreenComponentCommand`
      - **Purpose**: Concrete command class that applies the green component filter, emphasizing the green tones in the image.
    - `HelpCommand`
      - **Purpose**: Concrete command class that displays help or usage instructions for the available commands.
    - `HorizontalFlipCommand`
      - **Purpose**: Concrete command class that flips the image horizontally, creating a mirrored version of the image.
    - `IntensityComponentCommand`
      - **Purpose**: Concrete command class that adjusts the intensity component of the image's color, altering brightness and contrast.
    - `LoadCommand`
      - **Purpose**: Concrete command class that loads an image from the specified source.
    - `LumaComponentCommand`
      - **Purpose**: Concrete command class that adjusts the luminance or brightness component of the image, affecting the grayscale intensity.
    - `QuitCommand`
      - **Purpose**: Concrete command class that exits the application safely.
    - `RedComponentCommand`
      - **Purpose**: Concrete command class that applies the red component filter, isolating and enhancing red tones in the image.
    - `RGBCombineCommand`
      - **Purpose**: Concrete command class that combines the red, green, and blue components of separate images into one composite image.
    - `RGBSplitCommand`
      - **Purpose**: Concrete command class that splits an image into its red, green, and blue component images.
    - `RunCommand`
      - **Purpose**: Concrete command class that executes a batch of predefined image processing commands.
    - `SaveCommand`
      - **Purpose**: Concrete command class that saves the current state of the image to a file.
    - `SepiaCommand`
      - **Purpose**: Concrete command class that applies a sepia filter to the image, giving it a warm, antique look.
    - `SharpenCommand`
      - **Purpose**: Concrete command class that sharpens the image, enhancing the edges and details.
    - `ValueComponentCommand`
      - **Purpose**: Concrete command class that adjusts the value (or brightness) component of the image's colors.
    - `VerticalFlipCommand`
      - **Purpose**: Concrete command class that flips the image vertically, creating an upside-down version of the image.
- ### `model` Package
  - **Classes**:
    - `ExecutionStatus`
      - **Purpose**: Represents the execution statistics of a command.

  
#### `exception` Package
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

#### `factories` Package
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

#### `filters` Package
- **Classes**:
  - `Filter`
    - **Purpose**: Interface for image filters.
  - `AbstractFilter`
    - **Purpose**: Abstract class that implements the `Filter` interface and provides common functionality for all filters.
  - `Blur`
    - **Purpose**: Concrete filter class that applies a Gaussian blur to an image.
  - `Sharpen`
    - **Purpose**: Concrete filter class that sharpens an image.
  - `FilterOptions`
    - **Purpose**: Enum or class that provides various filter options and their corresponding convolution kernels.

#### `model.visual` Package
- **Class**: `Image`
  - **Purpose**: Represents an image.

#### `model.pixels` Package
- **Class**: `Pixel`
  - **Purpose**: Represents a pixel in an image.

### Example Directory Structure

```
src/
├── app/
│   └── ImageProcessorApp.java
├── controller/
│   └── ImageProcessorController.java
├── exception/
│   └── QuitException.java
├── factories/
│   ├── ControllerFactory.java
│   ├── ImageMemoryFactory.java
│   ├── ImageProcessingServiceFactory.java
│   ├── UserInputFactory.java
│   ├── UserOutputFactory.java
│   └── ImageFactory.java
├── filters/
│   ├── Filter.java
│   ├── AbstractFilter.java
│   ├── Blur.java
│   └── FilterOptions.java
├── model/
│   ├── visual/
│   │   └── Image.java
│   └── pixels/
│       └── Pixel.java
```

This structure organizes the code into appropriate packages and follows the MVC pattern. The `app` package contains the main application class, the `controller` package contains the controller class, the `exception` package contains custom exceptions, the `factories` package contains factory classes, the `filters` package contains filter classes, and the `model` package contains classes related to the data model. This structure helps in maintaining a clean and organized codebase.