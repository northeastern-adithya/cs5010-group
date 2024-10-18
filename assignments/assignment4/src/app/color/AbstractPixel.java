package app.color;

public abstract class AbstractPixel implements Pixel {
  protected int bits;

  public AbstractPixel(int bits) {
    this.bits = bits;
  }

  protected abstract boolean equalsRGB(RGB rgb);
}
