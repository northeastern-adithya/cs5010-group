package factories;



import exception.NotImplementedException;
import model.PixelType;
import model.color.Pixel;
import model.color.RGB;

public class PixelFactory {

  private PixelFactory() {

  }

  public static Pixel createPixel(int pixel, PixelType type){
    switch (type) {
      case RGB:
        return createRGBPixel(pixel);
      default:
        throw new NotImplementedException(String.format("Received an unsupported image type: %s", type));
    }
  }


  private static Pixel createRGBPixel(int pixel) {
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = pixel & 0xff;
    return createRGBPixel(red, green, blue);
  }

  public static Pixel createRGBPixel(int red, int green, int blue) {
    return new RGB(red, green, blue);
  }
}
