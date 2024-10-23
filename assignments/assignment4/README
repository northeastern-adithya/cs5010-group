To segregate the provided code into packages and MVC structure, we can organize the classes as follows:

### Packages and MVC Structure

#### `app` Package
- **Class**: `ImageProcessorApp`
  - **Purpose**: The entry point of the application.

#### `controller` Package
- **Class**: `ImageProcessorController`
  - **Purpose**: Handles user commands and coordinates the image processing tasks.

#### `exception` Package
- **Class**: `QuitException`
  - **Purpose**: Custom exception used to signal the termination of the command processing loop.

#### `factories` Package
- **Classes**:
  - `ControllerFactory`
    - **Purpose**: Factory class for creating instances of `ImageProcessorController`.
  - `ImageMemoryFactory`
    - **Purpose**: Factory class for managing image memory.
  - `ImageProcessingServiceFactory`
    - **Purpose**: Factory class for creating instances of the image processing service.
  - `UserInputFactory`
    - **Purpose**: Factory class for creating instances of user input.
  - `UserOutputFactory`
    - **Purpose**: Factory class for creating instances of user output.
  - `ImageFactory`
    - **Purpose**: Factory class for creating instances of `Image`.

#### `filters` Package
- **Classes**:
  - `Filter`
    - **Purpose**: Interface for image filters.
  - `AbstractFilter`
    - **Purpose**: Abstract class that implements the `Filter` interface and provides common functionality for all filters.
  - `Blur`
    - **Purpose**: Concrete filter class that applies a Gaussian blur to an image.
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