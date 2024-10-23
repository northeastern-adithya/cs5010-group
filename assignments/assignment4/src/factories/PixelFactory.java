package factories;


import exception.NotImplementedException;
import model.PixelType;
import model.pixels.Pixel;
import model.pixels.RGB;

/**
 * Factory class to create Pixel objects.
 * Pixel represents a single pixel in an image.
 */
public class PixelFactory {


  private PixelFactory() {
    //Private constructor to prevent instantiation
  }

  /**
   * Creates a pixel based on the given pixel and type.
   *
   * @param pixel the pixel to create the pixel
   * @param type  the type of the pixel
   * @return the pixel based on the given pixel and type
   * @throws NotImplementedException if the pixel is not implemented
   */
  public static Pixel createPixel(int pixel, PixelType type) {
    switch (type) {
      case RGB:
        return createRGBPixel(pixel);
      default:
        throw new NotImplementedException(String.format("Received an unsupported image type: %s", type));
    }
  }


  /**
   * Creates an RGB pixel based on the given pixel.
   *
   * @param pixel the pixel to create the RGB pixel
   * @return the RGB pixel based on the given pixel
   */
  private static Pixel createRGBPixel(int pixel) {
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = pixel & 0xff;
    return createRGBPixel(red, green, blue);
  }

  /**
   * Creates an RGB pixel based on the given red, green, and blue components.
   *
   * @param red   the red component of the pixel
   * @param green the green component of the pixel
   * @param blue  the blue component of the pixel
   * @return the RGB pixel based on the given red, green, and blue components
   */
  public static Pixel createRGBPixel(int red, int green, int blue) {
    return new RGB(red, green, blue);
  }
}
