package writer;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import exception.ImageProcessorException;
import model.pixels.Pixel;
import model.visual.Image;

/**
 * Class for reading a PPM image from a file.
 */
public class PPMImageWriter extends AbstractImageWriter {

  /**
   * Constructs a PPMImageWriter.
   */
  public PPMImageWriter() {
    super();
  }

  /**
   * Write a PPM image from a file.
   * Custom implementation for writing PPM images.
   * @param path the path to the file.
   * @throws ImageProcessorException if the image cannot be written.
   */
  @Override
  public void write(Image image, String path) throws ImageProcessorException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
      File outputFile = new File(path);
      createDirectoryIfNotPresent(path);
      writer.write("P3\n");
      writer.write(image.getWidth() + " " + image.getHeight() + "\n");
      writer.write("255\n");

      for (int y = 0; y < image.getHeight(); y++) {
        for (int x = 0; x < image.getWidth(); x++) {
          Pixel pixel = image.getPixel(x, y);
          writer.write(pixel.getRed() + "\n");
          writer.write(pixel.getGreen() + "\n");
          writer.write(pixel.getBlue() + "\n");
        }
      }
    } catch (IOException e) {
      throw new ImageProcessorException("Error writing PPM file: " + path, e);
    }
  }

}
