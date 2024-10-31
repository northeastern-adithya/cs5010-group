package factories;

import java.util.Objects;

import controller.ImageProcessorController;
import controller.SimpleImageProcessorController;
import exception.ImageProcessorException;
import model.enumeration.PixelType;
import model.memory.HashMapMemory;
import model.memory.ImageMemory;
import model.pixels.Pixel;
import model.pixels.RGB;
import model.visual.Image;
import model.visual.RenderedImage;
import services.FileImageProcessingService;
import services.ImageProcessingService;
import view.input.ConsoleInput;
import view.input.UserInput;
import view.output.ConsoleOutput;
import view.output.UserOutput;

/**
 * Factory class to create objects for the Image Processor application.
 */
public class Factory {
  private Factory() {
    //Empty private constructor to prevent instantiation.
  }

  /**
   * Creates an ImageProcessorController to control view and model.
   *
   * @param input     The input collector from user.
   * @param output    The output collector from user.
   * @param processor The processor service to perform operations on the image.
   * @return the controller to control the view and model
   */
  public static ImageProcessorController createController(UserInput input, UserOutput output,
                                                          ImageProcessingService processor) {
    return new SimpleImageProcessorController(input, output, processor);
  }


  /**
   * Creates an Image object with the given pixel array.
   *
   * @param pixels the pixel array to create the image
   * @return the image object with the given pixel array
   */
  public static Image createImage(Pixel[][] pixels) {
    return new RenderedImage(pixels);
  }


  /**
   * Combines the red, green, and blue components of an image to create a new image.
   *
   * @param redComponent   the red component of the image
   * @param greenComponent the green component of the image
   * @param blueComponent  the blue component of the image
   * @return the new image with the combined RGB components
   * @throws ImageProcessorException if the RGB components do not have the same dimensions
   */
  public static Image combineRGBComponents(Image redComponent, Image greenComponent,
                                           Image blueComponent)
          throws ImageProcessorException {
    validateRGBComponents(redComponent, greenComponent, blueComponent);
    int height = redComponent.getHeight();
    int width = redComponent.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = createRGBPixel(redComponent.getPixel(x, y).getRed(),
                greenComponent.getPixel(x, y).getGreen(),
                blueComponent.getPixel(x, y).getBlue());
      }
    }
    return createImage(newPixelArray);
  }

  /**
   * Validates the given RGB components of an image.
   *
   * @param redComponent   the red component of the image
   * @param greenComponent the green component of the image
   * @param blueComponent  the blue component of the image
   * @throws ImageProcessorException if the RGB components do not have the same dimensions
   * @throws NullPointerException    if any of the RGB components are null
   */
  private static void validateRGBComponents(Image redComponent, Image greenComponent,
                                            Image blueComponent)
          throws ImageProcessorException, NullPointerException {
    Objects.requireNonNull(redComponent, "Red component cannot be null");
    Objects.requireNonNull(greenComponent, "Green component cannot be null");
    Objects.requireNonNull(blueComponent, "Blue component cannot be null");
    if (redComponent.getWidth() != greenComponent.getWidth()
            || redComponent.getWidth() != blueComponent.getWidth()
            || redComponent.getHeight() != greenComponent.getHeight()
            || redComponent.getHeight() != blueComponent.getHeight()) {
      throw new ImageProcessorException("The RGB components must have the same dimensions");
    }
  }

  /**
   * Creates an ImageMemory object to store the
   * images in memory.
   * Images are stored in memory based on their implementation.
   *
   * @return the ImageMemory object
   */
  public static ImageMemory getImageMemory() {
    return new HashMapMemory();
  }

  /**
   * Creates an ImageProcessingService object to process images.
   *
   * @param memory the memory to store images
   * @return the ImageProcessingService object
   */
  public static ImageProcessingService createImageProcessor(ImageMemory memory) {
    return new FileImageProcessingService(memory);
  }


  /**
   * Creates a pixel based on the given pixel and type.
   *
   * @param pixel the pixel to create the pixel
   * @param type  the type of the pixel
   * @return the pixel based on the given pixel and type
   * @throws ImageProcessorException.NotImplementedException if the pixel is not implemented
   */
  public static Pixel createPixel(int pixel, PixelType type)
          throws ImageProcessorException.NotImplementedException {
    if (type == PixelType.RGB) {
      return createRGBPixel(pixel);
    } else {
      throw new ImageProcessorException.NotImplementedException(
              String.format("Received an unsupported image type: %s", type)
      );
    }
  }


  /**
   * Creates an RGB pixel based on the given pixel.
   *
   * @param pixel the pixel to create the RGB pixel
   * @return the RGB pixel based on the given pixel
   */
  private static Pixel createRGBPixel(int pixel) {
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = pixel & 0xff;
    return createRGBPixel(red, green, blue);
  }

  /**
   * Creates an RGB pixel based on the given red, green, and blue components.
   *
   * @param red   the red component of the pixel
   * @param green the green component of the pixel
   * @param blue  the blue component of the pixel
   * @return the RGB pixel based on the given red, green, and blue components
   */
  public static Pixel createRGBPixel(int red, int green, int blue) {
    return new RGB(red, green, blue);
  }

  /**
   * Creates a UserInput object to read user input.
   * Inputs are read from the given input stream.
   *
   * @param input the readable object to read the user input
   * @return the UserInput object
   */
  public static UserInput createUserInput(Readable input) {
    return new ConsoleInput(input);
  }


  /**
   * Creates a UserOutput object to write user output.
   * Outputs are written to the given output stream.
   *
   * @param output the output stream to write the user output
   * @return the UserOutput object
   */
  public static UserOutput createUserOutput(Appendable output) {
    return new ConsoleOutput(output);
  }
}
