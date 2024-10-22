package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import exception.ImageProcessorException;
import factories.ImageFactory;
import factories.PixelFactory;
import model.PixelType;
import model.color.Pixel;
import model.color.RGB;
import model.visual.Image;

public class ImageUtility {

  private ImageUtility() {
  }

  public static Image loadImage(String imagePath) throws ImageProcessorException {
    try {
      File file = new File(imagePath);
      BufferedImage image = ImageIO.read(file);
      int width = image.getWidth();
      int height = image.getHeight();
      Pixel[][] pixelArray = new Pixel[width][height];
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          pixelArray[x][y] = PixelFactory.createPixel(image.getRGB(x, y), PixelType.fromBufferedImageType(image.getType()));
        }
      }
      return ImageFactory.createImage(pixelArray);
    } catch (IOException e) {
      throw new ImageProcessorException("Error loading the image file", e);
    }
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

  public static void saveImage(Image image, String imagePath) {
    try {
      // Determine the format from the file extension
      String formatName = imagePath.substring(imagePath.lastIndexOf('.') + 1);


      // Create a BufferedImage from the Image object
      BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
      for (int y = 0; y < image.getHeight(); y++) {
        for (int x = 0; x < image.getWidth(); x++) {
          Pixel pixel = image.getPixel(x, y);
          int rgb = (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
          bufferedImage.setRGB(x, y, rgb);
        }
      }

      // Write the BufferedImage to the specified file
      File outputFile = new File(imagePath);
      if (!ImageIO.write(bufferedImage, formatName, outputFile)) {
        throw new IOException("No appropriate writer found for format: " + formatName);
      }
    } catch (IOException e) {
      System.out.println("Error saving the image file: " + e.getMessage());
    }
  }
}
