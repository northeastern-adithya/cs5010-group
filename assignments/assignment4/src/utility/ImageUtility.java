package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import exception.ImageProcessorException;
import factories.ImageFactory;
import factories.PixelFactory;
import model.color.Pixel;
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
          pixelArray[x][y] = PixelFactory.createPixel(image.getRGB(x, y), image.getType());
        }
      }
      return ImageFactory.createImage(pixelArray);
    } catch (IOException e) {
      throw new ImageProcessorException("Error loading the image file", e);
    }
  }

  public static Image createRedComponent(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = image.getPixel(x, y).createRedComponent();
      }
    }
    return ImageFactory.createImage(newPixelArray);
  }

  public static Image createGreenComponent(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = image.getPixel(x, y).createGreenComponent();
      }
    }
    return ImageFactory.createImage(newPixelArray);
  }

  public static Image createBlueComponent(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = image.getPixel(x, y).createBlueComponent();
      }
    }
    return ImageFactory.createImage(newPixelArray);
  }

//  public static void saveImage(java.awt.Image image, String imagePath) {
//    try {
//      // Determine the format from the file extension
//      String formatName = imagePath.substring(imagePath.lastIndexOf('.') + 1);
//
//
//      // Create a BufferedImage from the Image object
//      BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
//      for (int y = 0; y < image.getHeight(); y++) {
//        for (int x = 0; x < image.getWidth(); x++) {
//          Pixel pixel = image.getPixel(x, y);
//          int rgb = (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
//          bufferedImage.setRGB(x, y, rgb);
//        }
//      }
//
//      // Write the BufferedImage to the specified file
//      File outputFile = new File(imagePath);
//      if (!ImageIO.write(bufferedImage, formatName, outputFile)) {
//        throw new IOException("No appropriate writer found for format: " + formatName);
//      }
//    } catch (IOException e) {
//      System.out.println("Error saving the image file: " + e.getMessage());
//    }
//  }
}
