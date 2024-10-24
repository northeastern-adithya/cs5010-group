# Image Processor Application

## Setup
### Prerequisites
- Java version 11.0.24

### Steps to Run Script
- Clone the repository.
- Open the project in an IDE.
- run `src/app/ImageProcessorApp.java`
- execute the command `run sample-script.txt` on the console (If you face any errors, make sure the project root is set to current working directory.).
- Output images will be saved in `res/` folder.
- To view input files go to `test_resources/input/`
- To test for a better image use `test_resources/input/dubai.png`

### Image Citation
- Testing image `test_resources/input/sample_input.png` was taken from [dummyimage.com](https://dummyimage.com/)
- Testing image `test_resources/input/dubai.png` is our own photo and is authorized to be used in this project.
- Testing images `test_resources/input/random.jpeg`,`test_resources/input/random.jpg`,`test_resources/input/random.png`,`test_resources/input/random.ppm` are generated from our own scripts and are authorized to be used in this project.

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
        - **Subclasses**:
          - DisplayException
              - **Purpose**: An exception that is thrown when there is an error displaying a message.
          - QuitException
            - **Purpose**: An exception that is thrown when the user wants to quit the application.
    - `ImageProcessorException`
      - **Purpose**: A generic exception that is thrown across the image processing application when there is an error processing an image.
      - **Subclasses**:
          - NotFoundException
              - **Purpose**: An exception that is thrown when an image is not found in the memory.
          - NotImplementedException
              - **Purpose**: An exception that is thrown when a method is not implemented.


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
    - `model/memory`: Manages image memory through interfaces like `ImageMemory` and classes like `HashMapImageMemory`.
    - `model/pixels`: Contains interfaces and classes for pixel representation, such as `Pixel`, `AbstractPixel`, and `RGB`.
    - `model/visual`: Contains interfaces and classes for image representation, such as `Image` and `RenderedImage`.
    - `services`: Contains interfaces and classes for image processing services, such as `ImageProcessingService` and `SimpleImageProcessingService`.

2. **View**:
- **Purpose**: Handles the presentation layer and user interface.
- **Packages**:
    - `view.input`: Contains interfaces and classes for user input, such as `UserInput` and `ConsoleInput`.
    - `view.output`: Contains interfaces and classes for user output, such as `UserOutput` and `ConsoleOutput`.

3. **Controller**:
- **Purpose**: Manages the flow of the application, processes user commands, and updates the model and view.
- **Packages**:
    - `controller`: Contains interfaces and classes for controlling the application, such as `ImageProcessorController`, `SimpleImageProcessorController`, and `ExecutionStatus`.