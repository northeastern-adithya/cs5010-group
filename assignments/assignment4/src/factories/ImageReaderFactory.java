package factories;

import exception.NotImplementedException;
import model.ImageType;
import reader.ImageReader;
import reader.JPGImageReader;
import reader.PNGImageReader;
import reader.PPMImageReader;

/**
 * A factory that creates image readers.
 * Readers are responsible for reading images from files.
 */
public class ImageReaderFactory {

  private ImageReaderFactory() {
    // Empty private constructor to prevent instantiation.
  }

  /**
   * Creates an image reader based on the given image type.
   *
   * @param type the type of image to read
   * @return the image reader based on the given image type
   * @throws NotImplementedException if the image type is not implemented
   */
  public static ImageReader createImageReader(ImageType type) throws NotImplementedException {
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
