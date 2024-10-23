package filters;

import org.junit.Before;
import org.junit.Test;

import factories.ImageFactory;
import factories.PixelFactory;
import filters.BlurTestBase;
import model.pixels.Pixel;
import model.visual.Image;

import static org.junit.Assert.assertEquals;

/**
 * Test class for applying Blur filter to black images.
 */
public class BlurBlackImageTest extends BlurTestBase {
  private Image blackImage;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    Pixel[][] pixels = new Pixel[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels[i][j] = PixelFactory.createRGBPixel(0, 0, 0);
      }
    }
    blackImage = ImageFactory.createImage(pixels);
  }

  @Test
  public void testBlurBlackImage() {
    Image blurredImage = blurFilter.applyFilter(blackImage);
    Pixel centerPixel = blurredImage.getPixel(1, 1);

    Pixel[][] expectedPixels = new Pixel[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        expectedPixels[i][j] = PixelFactory.createRGBPixel(0, 0, 0);
      }
    }

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Pixel resultPixel = blurredImage.getPixel(i, j);
        Pixel expectedPixel = expectedPixels[i][j];
        assertEquals("Blurred image red value should be as expected",
                expectedPixel.getRed(), resultPixel.getRed());
        assertEquals("Blurred image green value should be as expected",
                expectedPixel.getGreen(), resultPixel.getGreen());
        assertEquals("Blurred image blue value should be as expected",
                expectedPixel.getBlue(), resultPixel.getBlue());
      }
    }
  }
}
