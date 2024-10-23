package filters;

import org.junit.Before;
import org.junit.Test;

import factories.ImageFactory;
import factories.PixelFactory;
import model.pixels.Pixel;
import model.visual.Image;

import static org.junit.Assert.assertEquals;

/**
 * Test class for applying Sharpen filter to white images.
 */
public class SharpenWhiteImageTest extends SharpenTestBase {
  private Image whiteImage;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    Pixel[][] pixels = new Pixel[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        pixels[i][j] = PixelFactory.createRGBPixel(255, 255, 255);
      }
    }
    whiteImage = ImageFactory.createImage(pixels);
  }

  @Test
  public void testSharpenWhiteImage() {
    Image sharpenedImage = sharpenFilter.applyFilter(whiteImage);

    for( int i = 0; i < 5; i++ ) {
      for( int j = 0; j < 5; j++ ) {
        assertEquals("Sharpened image should remain white (red)",
                255, sharpenedImage.getPixel(i, j).getRed());
        assertEquals("Sharpened image should remain white (green)",
                255, sharpenedImage.getPixel(i, j).getGreen());
        assertEquals("Sharpened image should remain white (blue)",
                255, sharpenedImage.getPixel(i, j).getBlue());
      }
    }
  }
}