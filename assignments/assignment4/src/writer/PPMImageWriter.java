package writer;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import exception.ImageProcessorException;
import model.color.Pixel;
import model.visual.Image;

public class PPMImageWriter extends AbstractImageWriter {

  public PPMImageWriter() {
    super();
  }


  @Override
  public void write(Image image, String path) throws ImageProcessorException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
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
