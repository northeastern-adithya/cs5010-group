import org.junit.Test;

import model.visual.Image;
import model.color.Pixel;
import model.color.RGB;

import static org.junit.Assert.*;

public class ImageTest {
  @Test
  public void testGetPixel() {
    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new RGB(255, 0, 0, 24);
    pixels[0][1] = new RGB(0, 255, 0, 24);
    pixels[1][0] = new RGB(0, 0, 255, 24);
    pixels[1][1] = new RGB(255, 255, 0, 24);

    Image image = new Image(2, 2, pixels);

    assertEquals(pixels[0][0], image.getPixel(0, 0));
    assertEquals(pixels[0][1], image.getPixel(0, 1));
    assertEquals(pixels[1][0], image.getPixel(1, 0));
    assertEquals(pixels[1][1], image.getPixel(1, 1));
  }

  @Test
  public void testBrightenImage() {
    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new RGB(100, 100, 100, 24);
    pixels[0][1] = new RGB(150, 150, 150, 24);
    pixels[1][0] = new RGB(200, 200, 200, 24);
    pixels[1][1] = new RGB(250, 250, 250, 24);

    Image image = new Image(2, 2, pixels);
    Image brightenedImage = (Image) image.adjustImageBrightness(50);

    assertEquals(new RGB(150, 150, 150, 24), brightenedImage.getPixel(0, 0));
  }

  @Test
  public void testDarkenImage() {
    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new RGB(100, 100, 100, 24);
    pixels[0][1] = new RGB(150, 150, 150, 24);
    pixels[1][0] = new RGB(200, 200, 200, 24);
    pixels[1][1] = new RGB(250, 250, 250, 24);

    Image image = new Image(2, 2, pixels);
    Image darkenedImage = (Image) image.adjustImageBrightness(-50);

    assertEquals(new RGB(50, 50, 50, 24), darkenedImage.getPixel(0, 0));
    assertEquals(new RGB(100, 100, 100, 24), darkenedImage.getPixel(0, 1));
    assertEquals(new RGB(150, 150, 150, 24), darkenedImage.getPixel(1, 0));
    assertEquals(new RGB(200, 200, 200, 24), darkenedImage.getPixel(1, 1));
  }
}