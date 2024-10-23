package utility;

import model.ImageType;


/**
 * Utility class to help in image operations.
 */
public class ImageUtility {

  /**
   * Private constructor to prevent instantiation.
   */
  private ImageUtility() {
  }


  /**
   * Get the type of the image from the path.
   *
   * @param path the path of the image.
   * @return the type of the image.
   */
  public static ImageType getExtensionFromPath(String path) {
    return ImageType.fromExtension(getExtension(path));
  }

  /**
   * Get the extension of the image from the path.
   *
   * @param imagePath the path of the image.
   * @return the extension of the image.
   */
  public static String getExtension(String imagePath) {
    return imagePath.substring(imagePath.lastIndexOf('.') + 1);
  }
}
