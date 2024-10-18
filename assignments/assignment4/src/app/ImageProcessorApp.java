package app;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessorApp {

  public static void main(String[] args) {
    String imagePath = "/Users/ishanrai/Desktop/Repos/CS5010/cs5010-group/assignments/assignment4/src/resource/manhattan-small.png"; // Update with your image path

    try {
      // Load the image file (PNG or JPEG)
      File file = new File(imagePath); // Provide the correct path to the image file
      BufferedImage image = ImageIO.read(file);

      // Get image dimensions
      int width = image.getWidth();
      int height = image.getHeight();

      // Loop through each pixel in the image
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          // Get RGB value of the pixel (in integer form)
          int pixel = image.getRGB(x, y);

          // Extract the red, green, and blue values
          int red = (pixel >> 16) & 0xff;
          int green = (pixel >> 8) & 0xff;
          int blue = pixel & 0xff;

          // Print pixel information
          System.out.printf("Pixel at (%d, %d): R=%d, G=%d, B=%d%n", x, y, red, green, blue);
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading the image file: " + e.getMessage());
    }
  }
}
