package model.color;

public interface Pixel {
  /**
   * Gets the value of the pixel.
   *
   * @return the value of the pixel
   */
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

}
