package controller;

import model.Pixel;

/**
 * A utility class to provide a method to generate a random image for testing purposes.
 */
public class TestUtils {

  private TestUtils(){
    // private constructor to prevent instantiation
  }

  public static Pixel[][] getRandomImage() {
    return new Pixel[][]{
            {new Pixel(255, 0, 0), new Pixel(0, 0, 255)},
            {new Pixel(0, 255, 0), new Pixel(128, 128, 128)}
    };
  }
}
