package filters;

import org.junit.Before;
import org.junit.Test;

import factories.ImageFactory;
import factories.PixelFactory;
import model.pixels.Pixel;
import model.visual.Image;

import static org.junit.Assert.assertEquals;

/**
 * Test class for applying Sharpen filter to black images.
 */
public class SharpenBlackImageTest extends SharpenTestBase {
  private Image blackImage;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    Pixel[][] pixels = new Pixel[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        pixels[i][j] = PixelFactory.createRGBPixel(0, 0, 0);
      }
    }
    blackImage = ImageFactory.createImage(pixels);
  }

  @Test
  public void testSharpenBlackImage() {
    Image sharpenedImage = sharpenFilter.applyFilter(blackImage);
    Pixel centerPixel = sharpenedImage.getPixel(2, 2);

    assertEquals("Black image should remain black (red)",
            0, centerPixel.getRed());
    assertEquals("Black image should remain black (green)",
            0, centerPixel.getGreen());
    assertEquals("Black image should remain black (blue)",
            0, centerPixel.getBlue());
  }
}
