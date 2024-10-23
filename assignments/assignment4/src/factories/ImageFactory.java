package factories;

import java.util.Objects;

import exception.ImageProcessorException;
import model.pixels.Pixel;
import model.visual.Image;
import model.visual.RenderedImage;

public class ImageFactory {

  private ImageFactory() {
  }

  public static Image createImage(Pixel[][] pixels) {
    return new RenderedImage(pixels);
  }


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

  private static void validateRGBComponents(Image redComponent, Image greenComponent, Image blueComponent) throws ImageProcessorException {
    Objects.requireNonNull(redComponent, "Red component cannot be null");
    Objects.requireNonNull(greenComponent, "Green component cannot be null");
    Objects.requireNonNull(blueComponent, "Blue component cannot be null");
    if (redComponent.getWidth() != greenComponent.getWidth() || redComponent.getWidth() != blueComponent.getWidth() || redComponent.getHeight() != greenComponent.getHeight() || redComponent.getHeight() != blueComponent.getHeight()) {
      throw new ImageProcessorException("The RGB components must have the same dimensions");
    }
  }
}
