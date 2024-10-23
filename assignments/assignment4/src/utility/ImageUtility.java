package utility;

import exception.ImageProcessorException;
import factories.ImageFactory;
import factories.ImageReaderFactory;
import factories.ImageWriterFactory;
import factories.PixelFactory;
import model.ImageType;
import model.pixels.Pixel;
import model.visual.Image;

public class ImageUtility {

  private ImageUtility() {
  }


  public static ImageType getExtensionFromPath(String path) {
    return ImageType.fromExtension(getExtension(path));
  }

  public static String getExtension(String imagePath) {
    return imagePath.substring(imagePath.lastIndexOf('.') + 1);
  }
}
