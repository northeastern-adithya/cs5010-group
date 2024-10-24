# Image Processor Application

## Setup
### Prerequisites
- Java version 11.0.24

### Steps to Run Script
- Clone the repository.
- run `src/app/ImageProcessorApp.java`
- execute the command `run sample-script.txt` on the console.

### Image Citation
- Testing image was taken from [dummyimage.com](https://dummyimage.com/

## Packages and MVC Structure

### `app` Package
- **Class**: `ImageProcessorApp`
    - **Purpose**: The main class for the Image Processor application. This class is responsible for creating the controller and running the application.

### `controller` Package
- **Interfaces**:
    - `ImageProcessorController`
        - **Purpose**: The controller interface for the image processor. This is responsible for handling the image module and communication with the view.
- **Classes**:
    - `SimpleImageProcessorController`
        - **Purpose**: This class Implements the `ImageProcessorController` interface and processes the commands entered by the user. It handles communication with user along with controlling the model.
    - `ExecutionStatus`
        - **Purpose**: ExecutionStatus class that represents the status of the execution of a command. It contains a boolean value to indicate the success of the execution and a message.

### `exception` Package
- **Classes**:
    - `ImageProcessingRunTimeException`
        - **Purpose**: A generic runtime exception that is used across the image processing application.
    - `ImageProcessorException`
        - **Purpose**: A generic exception that is thrown across the image processing application when there is an error processing an image.

### `factories` Package
- **Classes**:
    - `Factory`
      - **Purpose**: Factory class to create objects for the Image Processor application.

### `model` Package
- **Enumerable**:
  - `FilterOption`
    - **Purpose**: Enum representing the different filter options available.
  - `ImageType`
      - **Purpose**: Enum that represents different types of images.
  - `LinearColorTransformationType`
    - **Purpose**: Represents the type of linear transformation that can be applied to an image.
  - `PixelType`
      - **Purpose**: Enum that represents different types of pixels.
  - `UserCommand`
      - **Purpose**: Enum representing the user commands. These are possible commands that the user can give to the program.
- #### `memory` Package
    - **Interfaces**:
        - `ImageMemory`
            - **Purpose**: A class that represents a memory that stores images. The memory can store images and retrieve them by their name. Memory is stored depending on the implementation.
    - **Classes**:
        - `HashMapImageMemory`
            - **Purpose**: Concrete class that implements the `ImageMemory` interface using a HashMap to store images.
- #### `pixels` Package
    - **Interfaces**:
        - `Pixel`
            - **Purpose**: Interface for to represent a Pixel.
    - **Classes**:
        - `AbstractPixel`
            - **Purpose**: Abstract class that provides common functionality for all pixel classes.
        - `RGB`
            - **Purpose**: Concrete class that represents an RGB pixel. 
- #### `visual` Package
    - **Interfaces**:
        - `Image`
            - **Purpose**: Interface to represent an Image.
    - **Classes**:
        - `RenderedImage`
            - **Purpose**: Concrete class that represents a rendered image. An image is a sequence of pixels.

### `services` Package
- **Interfaces**:
    - `ImageProcessingService`
        - **Purpose**: Interface for image processing services. Service class to help with all image processing operations.
- **Classes**:
    - `SimpleImageProcessingService`
        - **Purpose**: FileImageProcessingService class that implements the `ImageProcessingService` interface and provides the implementation for the methods to process images. It uses the ImageMemory object to store and retrieve images.

### `utility` Package
- **Classes**:
    - `FilterUtils`
        - **Purpose**: Utility class for applying filters to an image. Provides a method to apply a filter to an image.
    - `IOUtils`
        - **Purpose**: Utility class for IO operations.
    - `StringUtils`
        - **Purpose**: Utility class for String operations.

### `view` Package
- #### `input` Package
    - **Interfaces**:
        - `UserInput`
            - **Purpose**: Interface for user input. This input can be from any source like console, file etc.
    - **Classes**:
        - `ConsoleInput`
            - **Purpose**: Concrete class that implements the `UserInput` interface and provides console-based user input.
- #### `output` Package
    - **Interfaces**:
        - `UserOutput`
            - **Purpose**: Interface for user output. Represents the output to the user.
    - **Classes**:
        - `ConsoleOutput`
            - **Purpose**: Concrete class that implements the `UserOutput` interface and provides console-based user output.


## MVC Architecture

This project follows the Model-View-Controller (MVC) architecture, which helps in organizing the codebase into three main components:

1. **Model**:
- **Purpose**: Represents the data and the business logic of the application.
- **Packages**:
    - `memory`: Manages image memory through interfaces like `ImageMemory` and classes like `HashMapImageMemory`.
    - `pixels`: Contains interfaces and classes for pixel representation, such as `Pixel`, `AbstractPixel`, and `RGB`.
    - `visual`: Contains interfaces and classes for image representation, such as `Image` and `RenderedImage`.

2. **View**:
- **Purpose**: Handles the presentation layer and user interface.
- **Packages**:
    - `view.input`: Contains interfaces and classes for user input, such as `UserInput` and `ConsoleInput`.
    - `view.output`: Contains interfaces and classes for user output, such as `UserOutput` and `ConsoleOutput`.

3. **Controller**:
- **Purpose**: Manages the flow of the application, processes user commands, and updates the model and view.
- **Packages**:
    - `controller`: Contains interfaces and classes for controlling the application, such as `ImageProcessorController`, `SimpleImageProcessorController`, and `ExecutionStatus`.