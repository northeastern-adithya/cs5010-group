package utility;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import exception.ImageProcessorException;
import factories.Factory;
import model.enumeration.ImageType;
import model.enumeration.PixelType;
import model.pixels.Pixel;
import model.visual.Image;

/**
 * Utility class for IO operations.
 */
public class IOUtils {

  /**
   * Private constructor to prevent instantiation.
   */
  private IOUtils() {
  }


  /**
   * Reads an image from a file.
   * Uses the ImageIO class to read the image.
   *
   * @param path      the path to the file.
   * @param imageType the type of the image.
   * @return the image read from the file.
   * @throws ImageProcessorException if the image cannot be read.
   */
  public static Image read(String path, ImageType imageType) throws ImageProcessorException {
    if (ImageType.PPM.equals(imageType)) {
      return readImageForPPM(path);
    } else {
      return readImageUsingIOImage(path);
    }
  }

  /**
   * Reads a PPM image from a file.
   * Custom implementation for reading PPM images.
   *
   * @param path the path to the file.
   * @return the image read from the file.
   * @throws ImageProcessorException if the image cannot be read.
   */
  private static Image readImageForPPM(String path) throws ImageProcessorException {
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String format = reader.readLine();
      if (!"P3".equals(format)) {
        throw new ImageProcessorException(String.format("Unsupported PPM " +
                "format: %s", format));
      }
      String[] dimensions = reader.readLine().split(" ");
      int width = Integer.parseInt(dimensions[0]);
      int height = Integer.parseInt(dimensions[1]);
      Pixel[][] pixelArray = new Pixel[height][width];
      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          int red = Integer.parseInt(reader.readLine());
          int green = Integer.parseInt(reader.readLine());
          int blue = Integer.parseInt(reader.readLine());
          pixelArray[row][col] = Factory.createRGBPixel(red, green, blue);
        }
      }

      return Factory.createImage(pixelArray);
    } catch (IOException e) {
      throw new ImageProcessorException(String.format("Error reading PPM " +
              "file: %s", path), e);
    }
  }

  /**
   * Reads an image from a file using the ImageIO class.
   *
   * @param path the path to the file.
   * @return the image read from the file.
   * @throws ImageProcessorException if the image cannot be read.
   */
  private static Image readImageUsingIOImage(String path) throws ImageProcessorException {
    try {
      File file = new File(path);
      BufferedImage image = ImageIO.read(file);
      int width = image.getWidth();
      int height = image.getHeight();
      Pixel[][] pixelArray = new Pixel[height][width];
      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          pixelArray[row][col] = Factory.createPixel(
                  image.getRGB(row, col),
                  PixelType.fromBufferedImageType(image.getType())
          );
        }
      }
      return Factory.createImage(pixelArray);
    } catch (IOException e) {
      throw new ImageProcessorException("Error loading the image file", e);
    }
  }


  /**
   * Write an image from a file.
   * Uses the ImageIO class to write the image.
   *
   * @param path      the path to the file.
   * @param image     the image to write.
   * @param imageType the type of the image.
   * @throws ImageProcessorException if the image cannot be read.
   */
  public static void write(Image image, String path, ImageType imageType)
          throws ImageProcessorException {
    createDirectoryIfNotPresent(path);
    if (ImageType.PPM.equals(imageType)) {
      writeImageForPPM(image, path);
    } else {
      writeImageUsingImageIO(image, path);
    }
  }


  private static void writeImageUsingImageIO(Image image, String path)
          throws ImageProcessorException {
    try {
      String extension = getExtensionFromPath(path);
      BufferedImage bufferedImage = new BufferedImage(
              image.getWidth(),
              image.getHeight(),
              BufferedImage.TYPE_INT_RGB
      );
      int width = image.getWidth();
      int height = image.getHeight();

      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          Pixel pixel = image.getPixel(row, col);
          int rgb =
                  (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
          bufferedImage.setRGB(row, col, rgb);
        }
      }
      File outputFile = new File(path);
      if (!ImageIO.write(bufferedImage, extension, outputFile)) {
        throw new ImageProcessorException(String.format(
                "No appropriate writer found for format: %s",
                extension)
        );
      }
    } catch (IOException e) {
      throw new ImageProcessorException(String.format(
              "Error saving the image file to path: %s", path), e);
    }
  }

  private static void writeImageForPPM(Image image, String path)
          throws ImageProcessorException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
      writer.write("P3\n");
      writer.write(image.getWidth() + " " + image.getHeight() + "\n");
      writer.write("255\n");
      for (int row = 0; row < image.getHeight(); row++) {
        for (int col = 0; col < image.getWidth(); col++) {
          Pixel pixel = image.getPixel(row, col);
          writer.write(pixel.getRed() + "\n");
          writer.write(pixel.getGreen() + "\n");
          writer.write(pixel.getBlue() + "\n");
        }
      }
    } catch (IOException e) {
      throw new ImageProcessorException("Error writing PPM file: " + path, e);
    }

  }

  /**
   * Creates the directory for the given path if it does not exist.
   *
   * @param path the path to the file.
   * @throws ImageProcessorException if the directory cannot be created.
   */
  private static void createDirectoryIfNotPresent(String path) throws ImageProcessorException {
    File file = new File(path);
    if (file.getParentFile() != null && !file.getParentFile().exists()) {
      if (!file.getParentFile().mkdirs()) {
        throw new ImageProcessorException("Error creating directory for path:" +
                " " + path);
      }
    }
  }

  /**
   * Get the extension of the image from the path.
   *
   * @param imagePath the path of the image.
   * @return the extension of the image.
   */
  public static String getExtensionFromPath(String imagePath) {
    return imagePath.substring(imagePath.lastIndexOf('.') + 1);
  }

}
