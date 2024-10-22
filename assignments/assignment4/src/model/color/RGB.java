package model.color;

import utility.PixelTransformUtility;

public class RGB extends AbstractPixel {
  private final int red;
  private final int green;
  private final int blue;

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
  public Pixel createRedComponent() {
    return new RGB(red, red, red);
  }

  @Override
  public Pixel createGreenComponent() {
    return new RGB(green, green, green);
  }

  @Override
  public Pixel createBlueComponent() {
    return new RGB(blue, blue, blue);
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
    return new RGB(newRed, newGreen, newBlue);
  }

  @Override
  public Pixel getIntensity() {
    int intensity = (getRed() + getGreen() + getBlue()) / 3;
    return new RGB(intensity, intensity, intensity);
  }

  @Override
  public Pixel getValue() {
    int value = Math.max(Math.max(getRed(), getGreen()), getBlue());
    return new RGB(value, value, value);
  }

  @Override
  public Pixel getLuma() {
    int channelMatrix[] = {getRed(), getGreen(), getBlue()};
    int multiplicationResult[] = PixelTransformUtility.getLuma(channelMatrix);
    return new RGB(multiplicationResult[0], multiplicationResult[1], multiplicationResult[2]);
  }

  @Override
  public Pixel getSepia() {
    int channelMatrix[] = {getRed(), getGreen(), getBlue()};
    int multiplicationResult[] = PixelTransformUtility.getSepia(channelMatrix);
    return new RGB(multiplicationResult[0], multiplicationResult[1], multiplicationResult[2]);
  }
}