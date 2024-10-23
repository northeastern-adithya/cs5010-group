package factories;

import exception.NotImplementedException;
import model.ImageType;
import reader.ImageReader;
import reader.JPGImageReader;
import reader.PNGImageReader;
import reader.PPMImageReader;

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
