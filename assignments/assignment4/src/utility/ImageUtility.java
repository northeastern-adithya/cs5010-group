package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import exception.ImageProcessorException;
import factories.ImageFactory;
import factories.ImageReaderFactory;
import factories.ImageWriterFactory;
import factories.PixelFactory;
import model.ImageType;
import model.color.Pixel;
import model.visual.Image;

public class ImageUtility {

  private ImageUtility() {
  }

  public static Image loadImage(String imagePath) throws ImageProcessorException {
    ImageType imageType = getExtensionFromPath(imagePath);
    return ImageReaderFactory.createImageReader(imageType).read(imagePath);
  }

  public static void saveImage(Image image, String imagePath) throws ImageProcessorException {
    ImageType imageType = getExtensionFromPath(imagePath);
    ImageWriterFactory.createImageWriter(imageType).write(image, imagePath);
  }

  private static ImageType getExtensionFromPath(String path) {
    return ImageType.fromExtension(getExtension(path));
  }

  public static String getExtension(String imagePath) {
    return imagePath.substring(imagePath.lastIndexOf('.') + 1);
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
    if (redComponent.getWidth() != greenComponent.getWidth() || redComponent.getWidth() != blueComponent.getWidth() || redComponent.getHeight() != greenComponent.getHeight() || redComponent.getHeight() != blueComponent.getHeight()) {
      throw new ImageProcessorException("The RGB components must have the same dimensions");
    }
  }

  public static Image horizontalFlip(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = image.getPixel(width - x - 1, y);
      }
    }
    return ImageFactory.createImage(newPixelArray);
  }

  public static Image verticalFlip(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = image.getPixel(x, height - y - 1);
      }
    }
    return ImageFactory.createImage(newPixelArray);
  }
}
