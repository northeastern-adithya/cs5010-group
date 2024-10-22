package factories;

import java.awt.image.BufferedImage;

import exception.ImageProcessorException;
import exception.NotImplementedException;
import model.color.Pixel;
import model.color.RGB;

public class PixelFactory {

  private PixelFactory() {

  }

  public static Pixel createPixel(int pixel, int type) throws ImageProcessorException {
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
        return createRGBPixel(pixel);
      default:
        throw new NotImplementedException(String.format("Received an unsupported image type: %s", type));
    }
  }


  private static RGB createRGBPixel(int pixel) {
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = pixel & 0xff;
    return new RGB(red, green, blue);
  }

  public static Pixel createRGBPixel(int red, int green, int blue) {
    return new RGB(red, green, blue);
  }
}
