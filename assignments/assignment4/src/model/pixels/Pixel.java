package model.pixels;

public interface Pixel {
  Pixel getValue();

  Pixel getLuma();

  Pixel getSepia();

  Pixel getIntensity();

  Pixel adjustBrightness(int factor);


  Pixel createRedComponent();

  Pixel createGreenComponent();

  Pixel createBlueComponent();

  int getRed();

  int getGreen();

  int getBlue();

  Pixel createPixel(int red, int green, int blue);

}
