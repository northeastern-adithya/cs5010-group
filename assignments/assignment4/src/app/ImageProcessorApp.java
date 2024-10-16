package app;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ImageProcessorApp {

  public static void main(String[] args) {
    String imagePath = "/Users/ishanrai/Desktop/Repos/CS5010/Dump/Assignment4/src/resource/manhattan-small-blue.png"; // Update with your image path

    try {
      File imageFile = new File(imagePath);
      ImageInputStream input = ImageIO.createImageInputStream(imageFile);
      Iterator<ImageReader> readers = ImageIO.getImageReaders(input);

      if (!readers.hasNext()) {
        System.out.println("No suitable image reader found.");
        return;
      }

      ImageReader reader = readers.next();
      reader.setInput(input);

      int width = reader.getWidth(0);
      int height = reader.getHeight(0);
      int[] pixels = new int[width * height];

      // Read pixel data
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int pixel = reader.read(0).getRGB(x, y); // Getting RGB value directly
          int red = (pixel >> 16) & 0xFF;
          int green = (pixel >> 8) & 0xFF;
          int blue = pixel & 0xFF;

          System.out.printf("Pixel at (%d, %d): RGB(%d, %d, %d)%n", x, y, red, green, blue);
          System.out.println("Height of image: " + height);
          System.out.println("Width of image: " + width);
        }
      }

      input.close();
      reader.dispose();
    } catch (IOException e) {
      System.out.println("Error reading the image: " + e.getMessage());
    }
  }
}
