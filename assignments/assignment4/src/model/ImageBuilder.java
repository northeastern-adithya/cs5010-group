package model;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import model.color.Pixel;
import model.color.RGB;
import model.visual.Image;

public class ImageBuilder {

  public ImageBuilder() {
  }

  public Image buildImageFromPath(String imagePath) {
    try {
      // Load the image file (PNG or JPEG)
      File file = new File(imagePath); // Provide the correct path to the image file
      BufferedImage image = ImageIO.read(file);

      // Get image dimensions
      int width = image.getWidth();
      int height = image.getHeight();

      Pixel[][] pixelArray = new Pixel[width][height];

      // Loop through each pixel in the image
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          // Get RGB value of the pixel (in integer form)
          int pixel = image.getRGB(x, y);

          // Get red, green, and blue values
          int red = (pixel >> 16) & 0xff;
          int green = (pixel >> 8) & 0xff;
          int blue = pixel & 0xff;

          // Create a new RGB pixel
          pixelArray[x][y] = new RGB(red, green, blue, 24);
        }
      }
      return new Image(width, height, pixelArray);
    } catch (IOException e) {
      System.out.println("Error reading the image file: " + e.getMessage());
      return null;
    }
  }
}
