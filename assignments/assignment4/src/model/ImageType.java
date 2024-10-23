package model;

import java.util.Arrays;

import exception.NotImplementedException;

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
   * @throws NotImplementedException if the image type is not supported.
   */
  public static ImageType fromExtension(String extension) {
    return Arrays.stream(ImageType.values()).filter(imageType -> imageType.getExtension().equals(extension)).findFirst()
            .orElseThrow(() -> new NotImplementedException(String.format("Image type with extension %s not supported", extension)));
  }

  /**
   * Get the extension of the image type.
   *
   * @return extension of the image type.
   */
  public String getExtension() {
    return extension;
  }
}
