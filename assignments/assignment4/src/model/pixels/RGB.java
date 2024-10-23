package model.pixels;

import utility.PixelTransformUtility;

/**
 * Represents a pixel in the RGB color space.
 * It has three channels: red, green, and blue.
 * Each channel has a value between 0 and 255(8*3 bits).
 */
public class RGB extends AbstractPixel {
  /**
   * The red channel of the pixel.
   */
  private final int red;
  /**
   * The green channel of the pixel.
   */
  private final int green;
  /**
   * The blue channel of the pixel.
   */
  private final int blue;

  /**
   * Constructs an RGB pixel with the given red, green, and blue values.
   *
   * @param red   the red channel of the pixel
   * @param green the green channel of the pixel
   * @param blue  the blue channel of the pixel
   */
  public RGB(int red, int green, int blue) {
    super(24);
    this.red = Math.max(0, Math.min(red, computeMaxValue()));
    this.green = Math.max(0, Math.min(green, computeMaxValue()));
    this.blue = Math.max(0, Math.min(blue, computeMaxValue()));
  }

  @Override
  public int getRed() {
    return red;
  }

  @Override
  public int getGreen() {
    return green;
  }

  @Override
  public int getBlue() {
    return blue;
  }


  @Override
  public Pixel createPixel(int red, int green, int blue) {
    return new RGB(red, green, blue);
  }

  @Override
  public Pixel createRedComponent() {
    return this.createPixel(this.red, this.red, this.red);
  }

  @Override
  public Pixel createGreenComponent() {
    return this.createPixel(this.green, this.green, this.green);
  }

  @Override
  public Pixel createBlueComponent() {
    return this.createPixel(this.blue, this.blue, this.blue);
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof RGB)) {
      return false;
    }
    RGB rgb = (RGB) obj;
    return rgb.equalsRGB(this);
  }

  protected boolean equalsRGB(RGB rgb) {
    return red == rgb.getRed() && green == rgb.getGreen() && blue == rgb.getBlue();
  }

  @Override
  public Pixel adjustBrightness(int factor) {
    int newRed = getRed() + factor;
    int newGreen = getGreen() + factor;
    int newBlue = getBlue() + factor;
    return this.createPixel(newRed, newGreen, newBlue);
  }

  @Override
  public Pixel getIntensity() {
    int intensity = (getRed() + getGreen() + getBlue()) / 3;
    return this.createPixel(intensity, intensity, intensity);
  }

  @Override
  public Pixel getValue() {
    int value = Math.max(Math.max(getRed(), getGreen()), getBlue());
    return this.createPixel(value, value, value);
  }

  @Override
  public Pixel getLuma() {
    int[] channelMatrix = {getRed(), getGreen(), getBlue()};
    int[] multiplicationResult = PixelTransformUtility.getLuma(channelMatrix);
    return this.createPixel(multiplicationResult[0], multiplicationResult[1], multiplicationResult[2]);
  }

  @Override
  public Pixel getSepia() {
    int[] channelMatrix = {getRed(), getGreen(), getBlue()};
    int[] multiplicationResult = PixelTransformUtility.getSepia(channelMatrix);
    return this.createPixel(multiplicationResult[0], multiplicationResult[1], multiplicationResult[2]);
  }
}