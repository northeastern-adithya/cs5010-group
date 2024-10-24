package model.enumeration;

import java.util.Arrays;

import exception.ImageProcessingRunTimeException;
import utility.IOUtils;

/**
 * Enum representing the type of image.
 */
public enum ImageType {

  PNG("png"),
  PPM("ppm"),
  JPG("jpg"),
  JPEG("jpeg");

  /**
   * Extension of the image type.
   */
  private final String extension;

  /**
   * Constructor for the image type.
   *
   * @param extension extension of the image type.
   */
  ImageType(String extension) {
    this.extension = extension;
  }

  /**
   * Get the image type from the extension.
   *
   * @param extension extension of the image type.
   * @return ImageType object.
   * @throws ImageProcessingRunTimeException.NotImplementedException if image type is unsupported.
   */
  public static ImageType fromExtension(String extension) {
    return Arrays.stream(ImageType.values()).filter(
        imageType -> imageType.getExtension().equals(extension)).findFirst()
            .orElseThrow(() -> new ImageProcessingRunTimeException.NotImplementedException(
                    String.format("Image type with extension %s not supported", extension))
            );
  }

  /**
   * Get the extension of the image type.
   *
   * @return extension of the image type.
   */
  public String getExtension() {
    return extension;
  }

  /**
   * Get the type of the image from the path.
   *
   * @param path the path of the image.
   * @return the type of the image.
   */
  public static ImageType getImageTypeFromPath(String path) {
    return ImageType.fromExtension(IOUtils.getExtensionFromPath(path));
  }
}
