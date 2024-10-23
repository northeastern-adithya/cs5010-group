package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import exception.ImageProcessorException;
import factories.Factory;
import model.pixels.Pixel;
import model.visual.Image;

/**
 * Class for reading a PPM image from a file.
 */
public class PPMImageReader extends AbstractImageReader {

  public PPMImageReader() {
    super();
  }


  /**
   * Reads a PPM image from a file.
   * Custom implementation for reading PPM images.
   * @param path the path to the file.
   * @return the image read from the file.
   * @throws ImageProcessorException if the image cannot be read.
   */
  @Override
  public Image read(String path) throws ImageProcessorException {
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String format = reader.readLine();
      if (!"P3".equals(format)) {
        throw new ImageProcessorException(String.format("Unsupported PPM format: %s", format));
      }
      String[] dimensions = reader.readLine().split(" ");
      int width = Integer.parseInt(dimensions[0]);
      int height = Integer.parseInt(dimensions[1]);
      int maxColorValue = Integer.parseInt(reader.readLine());
      Pixel[][] pixelArray = new Pixel[width][height];
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int red = Integer.parseInt(reader.readLine());
          int green = Integer.parseInt(reader.readLine());
          int blue = Integer.parseInt(reader.readLine());
          pixelArray[x][y] = Factory.createRGBPixel(red, green, blue);
        }
      }

      return Factory.createImage(pixelArray);
    } catch (IOException e) {
      throw new ImageProcessorException(String.format("Error reading PPM file: %s", path), e);
    }
  }

}
