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
 * Test class for applying Blur filter to white images.
 */
public class BlurWhiteImageTest extends BlurTestBase {
  private Image whiteImage;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    Pixel[][] pixels = new Pixel[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels[i][j] = PixelFactory.createRGBPixel(255, 255, 255);
      }
    }
    whiteImage = ImageFactory.createImage(pixels);
  }

  @Test
  public void testBlurWhiteImage() {
    Image blurredImage = blurFilter.applyFilter(whiteImage);
    Pixel centerPixel = blurredImage.getPixel(1, 1);

    Pixel[][] expectedPixels = {
            {PixelFactory.createRGBPixel(143, 143, 143), PixelFactory.createRGBPixel(191, 191, 191), PixelFactory.createRGBPixel(143, 143, 143)},
            {PixelFactory.createRGBPixel(191, 191, 191), PixelFactory.createRGBPixel(255, 255, 255), PixelFactory.createRGBPixel(191, 191, 191)},
            {PixelFactory.createRGBPixel(143, 143, 143), PixelFactory.createRGBPixel(191, 191, 191), PixelFactory.createRGBPixel(143, 143, 143)}
    };

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedPixels[i][j], blurredImage.getPixel(i, j));
      }
    }
  }
}