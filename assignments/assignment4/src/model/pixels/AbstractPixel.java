package model.pixels;

public abstract class AbstractPixel implements Pixel {
  protected final int bits;

  protected AbstractPixel(int bits) {
    this.bits = bits;
  }

  protected int computeMaxValue() {
    return (1 << (this.bits / 3)) - 1;
  }
}
