package model.visual;

import model.color.Pixel;

public interface Image {
  Pixel getPixel(int x, int y);

  Image adjustImageBrightness(int factor);

  int getWidth();

  int getHeight();

  Image getLuma();

  Image getSepia();

  Image getIntensity();

  Image getValue();
}
