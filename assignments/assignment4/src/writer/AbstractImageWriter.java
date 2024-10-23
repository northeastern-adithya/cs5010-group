package writer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import exception.ImageProcessorException;
import model.pixels.Pixel;
import model.visual.Image;
import utility.ImageUtility;

/**
 * Abstract class for writing an image to a file.
 */
public abstract class AbstractImageWriter implements ImageWriter {

  /**
   * Constructs an instance of the writer.
   */
  protected AbstractImageWriter() {
  }


  /**
   * Write an image from a file.
   * Uses the ImageIO class to write the image.
   *
   * @param path the path to the file.
   * @throws ImageProcessorException if the image cannot be read.
   */
  @Override
  public void write(Image image, String path) throws ImageProcessorException {
    try {
      String extension = ImageUtility.getExtension(path);
      BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
      for (int y = 0; y < image.getHeight(); y++) {
        for (int x = 0; x < image.getWidth(); x++) {
          Pixel pixel = image.getPixel(x, y);
          int rgb = (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
          bufferedImage.setRGB(x, y, rgb);
        }
      }
      File outputFile = new File(path);
      if (!ImageIO.write(bufferedImage, extension, outputFile)) {
        throw new ImageProcessorException(String.format("No appropriate writer found for format: %s", extension));
      }
    } catch (IOException e) {
      throw new ImageProcessorException(String.format("Error saving the image file to path: %s", path), e);
    }
  }
}
