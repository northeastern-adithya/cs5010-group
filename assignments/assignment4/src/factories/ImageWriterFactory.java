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

public class ImageWriterFactory {

  private ImageWriterFactory() {
  }

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
