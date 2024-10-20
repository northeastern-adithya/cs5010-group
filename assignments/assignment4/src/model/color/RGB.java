package model.color;

public class RGB extends AbstractPixel {
  private final int red;
  private final int green;
  private final int blue;
  private final int maxValue;


  public RGB(int red, int green, int blue, int bits) {
    super(24);
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.maxValue = (1 << (this.getBits()/3)) - 1;
  }

  @Override
  public int getValue() {
    return Math.max(Math.max(red, green), blue);
  }

  @Override
  public double getLuma() {
    return 0.2126 * red + 0.7152 * green + 0.0722 * blue;
  }

  @Override
  public double getIntensity() {
    return (red + green + blue) / 3.0;
  }

  @Override
  public int getBits() {
    return bits;
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
  public Pixel adjustBrightness(int factor) {
    int newRed = red + factor;
    int newGreen = green + factor;
    int newBlue = blue + factor;
    newRed = Math.max(0, Math.min(newRed, maxValue));
    newGreen = Math.max(0, Math.min(newGreen, maxValue));
    newBlue = Math.max(0, Math.min(newBlue, maxValue));
    return new RGB(newRed, newGreen, newBlue, maxValue);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof AbstractPixel)) {
      return false;
    }
    AbstractPixel rgb = (AbstractPixel) obj;
    return rgb.equalsRGB(this);
  }

  @Override
  protected boolean equalsRGB(RGB rgb) {
    return red == rgb.getRed() && green == rgb.getGreen() && blue == rgb.getBlue();
  }
}
