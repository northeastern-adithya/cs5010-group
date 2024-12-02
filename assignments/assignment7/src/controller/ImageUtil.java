package controller;

import java.io.FileWriter;
import java.io.PrintWriter;
import model.Pixel;  // Import Pixel from the model package

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class contains utility methods to read and write images in different formats, including PPM
 * and other common image formats like JPG and PNG.
 */
public class ImageUtil {

  /**
   * Loads an image file (PPM, JPG, PNG) and returns its pixel data as a 2D array of {@link Pixel}
   * objects.
   *
   * @param filepath the path of the image file to be loaded.
   * @return a 2D array of Pixel objects representing the image, or {@code null} if the file is not
   *         found or unsupported.
   */
  public static Pixel[][] loadImage(String filepath) {
    if (filepath.endsWith(".ppm")) {
      return loadPPM(filepath);  // Load PPM image
    } else {
      return loadOtherFormats(filepath);  // Load other formats (JPG, PNG)
    }
  }

  /**
   * Loads a PPM image file and returns its pixel data as a 2D array of {@link Pixel} objects.
   *
   * @param filename the path to the PPM file to be loaded.
   * @return a 2D array of Pixel objects representing the image.
   */
  public static Pixel[][] loadPPM(String filename) {
    try (Scanner scanner = new Scanner(new FileInputStream(filename))) {
      String format = scanner.next();
      if (!format.equals("P3")) {
        throw new IllegalArgumentException("Invalid PPM format: " + format);
      }

      int width = scanner.nextInt();
      int height = scanner.nextInt();
      int maxColorValue = scanner.nextInt();

      Pixel[][] image = new Pixel[height][width];

      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          int red = scanner.nextInt();
          int green = scanner.nextInt();
          int blue = scanner.nextInt();
          image[row][col] = new Pixel(red, green, blue);
        }
      }
      return image;
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + filename);
      return null;
    }
  }

  /**
   * Loads other image formats like JPG, PNG, and returns the pixel data as a 2D array of
   * {@link Pixel} objects.
   *
   * @param filepath the path of the image file to be loaded.
   * @return a 2D array of Pixel objects representing the image, or {@code null} if the image is
   *         unsupported.
   */
  private static Pixel[][] loadOtherFormats(String filepath) {
    try {
      File file = new File(filepath);
      BufferedImage img = ImageIO.read(file);
      if (img == null) {
        System.out.println("Unsupported image format or corrupted file: " + filepath);
        return null;
      }

      int width = img.getWidth();
      int height = img.getHeight();
      Pixel[][] image = new Pixel[height][width];

      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          int rgb = img.getRGB(col, row);
          int red = (rgb >> 16) & 0xFF;
          int green = (rgb >> 8) & 0xFF;
          int blue = rgb & 0xFF;
          image[row][col] = new Pixel(red, green, blue);
        }
      }

      System.out.println("Image loaded successfully: " + filepath);
      return image;
    } catch (IOException e) {
      System.err.println("Error loading image: " + filepath);
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Saves a 2D array of {@link Pixel} objects as a PPM file. The file will be in the "P3" format
   * with the maximum color value of 255.
   *
   * @param filename the name of the file where the image will be saved.
   * @param image    the 2D array of {@link Pixel} objects to save.
   * @return the saved 2D array of {@link Pixel} objects.
   */
  public static Pixel[][] savePPM(String filename, Pixel[][] image) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
      writer.println("P3");
      writer.println(image[0].length + " " + image.length);
      writer.println(255);

      for (int row = 0; row < image.length; row++) {
        for (int col = 0; col < image[0].length; col++) {
          Pixel pixel = image[row][col];
          writer.println(pixel.get(0) + " " + pixel.get(1) + " " + pixel.get(2));
        }
      }
    } catch (IOException e) {
      System.err.println("Error saving file: " + filename);
    }
    return image;
  }
}
