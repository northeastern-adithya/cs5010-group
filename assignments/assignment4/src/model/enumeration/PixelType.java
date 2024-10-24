package model.enumeration;

import java.awt.image.BufferedImage;


import exception.ImageProcessingRunTimeException;

/**
 * Enum representing the type of pixel.
 */
public enum PixelType {

  RGB;


  /**
   * Get the pixel type from the BufferedImage type.
   *
   * @param type BufferedImage type.
   * @return PixelType object.
   * @throws ImageProcessingRunTimeException.NotImplementedException if the pixel type is not supported.
   */
  public static PixelType fromBufferedImageType(int type) {
    switch (type) {
      case BufferedImage.TYPE_INT_RGB:
      case BufferedImage.TYPE_INT_ARGB:
      case BufferedImage.TYPE_INT_ARGB_PRE:
      case BufferedImage.TYPE_INT_BGR:
      case BufferedImage.TYPE_3BYTE_BGR:
      case BufferedImage.TYPE_4BYTE_ABGR:
      case BufferedImage.TYPE_4BYTE_ABGR_PRE:
      case BufferedImage.TYPE_USHORT_565_RGB:
      case BufferedImage.TYPE_USHORT_555_RGB:
        return RGB;
      default:
        throw new ImageProcessingRunTimeException.NotImplementedException(String.format("Received an unsupported image type: %s", type));
    }
  }
}
