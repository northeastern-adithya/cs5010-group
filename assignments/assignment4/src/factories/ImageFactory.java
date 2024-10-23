package factories;

import java.util.Objects;

import exception.ImageProcessorException;
import model.pixels.Pixel;
import model.visual.Image;
import model.visual.RenderedImage;

/**
 * Factory class to create Image objects.
 */
public class ImageFactory {

  private ImageFactory() {
    //Private constructor to prevent instantiation
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
  public static Image combineRGBComponents(Image redComponent, Image greenComponent, Image blueComponent) throws ImageProcessorException {
    validateRGBComponents(redComponent, greenComponent, blueComponent);
    int height = redComponent.getHeight();
    int width = redComponent.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = PixelFactory.createRGBPixel(redComponent.getPixel(x, y).getRed(), greenComponent.getPixel(x, y).getGreen(), blueComponent.getPixel(x, y).getBlue());
      }
    }
    return ImageFactory.createImage(newPixelArray);
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
  private static void validateRGBComponents(Image redComponent, Image greenComponent, Image blueComponent) throws ImageProcessorException, NullPointerException {
    Objects.requireNonNull(redComponent, "Red component cannot be null");
    Objects.requireNonNull(greenComponent, "Green component cannot be null");
    Objects.requireNonNull(blueComponent, "Blue component cannot be null");
    if (redComponent.getWidth() != greenComponent.getWidth() || redComponent.getWidth() != blueComponent.getWidth() || redComponent.getHeight() != greenComponent.getHeight() || redComponent.getHeight() != blueComponent.getHeight()) {
      throw new ImageProcessorException("The RGB components must have the same dimensions");
    }
  }
}
