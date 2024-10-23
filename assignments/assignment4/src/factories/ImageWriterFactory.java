package factories;

import exception.NotImplementedException;
import model.ImageType;
import reader.ImageReader;
import reader.JPGImageReader;
import reader.PNGImageReader;
import reader.PPMImageReader;
import writer.ImageWriter;
import writer.JPGImageWriter;
import writer.PNGImageWriter;
import writer.PPMImageWriter;

/**
 * A factory that creates image writers.
 * Writers are responsible for writing images to files.
 */
public class ImageWriterFactory {

  private ImageWriterFactory() {
    // Empty private constructor to prevent instantiation.
  }

  /**
   * Creates an image writer based on the given image type.
   *
   * @param type the type of image to write
   * @return the image writer based on the given image type
   * @throws NotImplementedException if the image type is not implemented
   */
  public static ImageWriter createImageWriter(ImageType type) {
    switch (type) {
      case PPM:
        return new PPMImageWriter();
      case JPEG:
      case JPG:
        return new JPGImageWriter();
      case PNG:
        return new PNGImageWriter();
      default:
        throw new NotImplementedException(String.format("Received an unsupported image type: %s", type));
    }
  }
}
