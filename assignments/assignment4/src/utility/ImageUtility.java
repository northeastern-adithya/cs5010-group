package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import exception.ImageProcessorException;
import factories.ImageFactory;
import factories.ImageReaderFactory;
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

  private static ImageType getExtensionFromPath(String path) {
    String extension = path.substring(path.lastIndexOf('.') + 1);
    return ImageType.fromExtension(extension);
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

  public static void saveImage(Image image, String imagePath) throws ImageProcessorException {
    try {
      String formatName = imagePath.substring(imagePath.lastIndexOf('.') + 1);
      BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
      for (int y = 0; y < image.getHeight(); y++) {
        for (int x = 0; x < image.getWidth(); x++) {
          Pixel pixel = image.getPixel(x, y);
          int rgb = (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
          bufferedImage.setRGB(x, y, rgb);
        }
      }
      File outputFile = new File(imagePath);
      if (!ImageIO.write(bufferedImage, formatName, outputFile)) {
        throw new ImageProcessorException("No appropriate writer found for format: " + formatName);
      }
    } catch (IOException e) {
      throw new ImageProcessorException(String.format("Error saving the image file to path: %s", imagePath), e);
    }
  }
}
