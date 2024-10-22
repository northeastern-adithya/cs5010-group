package factories;

import exception.NotImplementedException;
import model.ImageType;
import model.reader.ImageReader;
import model.reader.JPGImageReader;
import model.reader.PNGImageReader;
import model.reader.PPMImageReader;

public class ImageReaderFactory {

  private ImageReaderFactory() {
  }

  public static ImageReader createImageReader(ImageType type) {
    switch (type) {
      case PPM:
        return new PPMImageReader();
      case JPEG:
      case JPG:
        return new JPGImageReader();
      case PNG:
        return new PNGImageReader();
      default:
        throw new NotImplementedException(String.format("Received an unsupported image type: %s", type));
    }
  }
}
