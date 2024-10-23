package filters;

import org.junit.Before;
import org.junit.Test;

import factories.Factory;
import model.pixels.Pixel;
import model.visual.Image;

import static org.junit.Assert.assertEquals;

/**
 * Test class for applying Blur filter to single pixel images.
 */
public class BlurSinglePixelTest extends BlurTestBase {
  private Image singlePixelImage;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    Pixel[][] pixel = new Pixel[1][1];
    pixel[0][0] = Factory.createRGBPixel(100, 100, 100);
    singlePixelImage = Factory.createImage(pixel);
  }

  @Test
  public void testBlurSinglePixel() {
    Image blurredImage = blurFilter.applyFilter(singlePixelImage);
    Pixel resultPixel = blurredImage.getPixel(0, 0);

    assertEquals("Single pixel red value should be affected by kernel",
            25, resultPixel.getRed());
    assertEquals("Single pixel green value should be affected by kernel",
            25, resultPixel.getGreen());
    assertEquals("Single pixel blue value should be affected by kernel",
            25, resultPixel.getBlue());
  }

  @Test
  public void testSinglePixelDimensionsPreserved() {
    Image blurredImage = blurFilter.applyFilter(singlePixelImage);
    assertEquals("Image width should remain 1",
            1, blurredImage.getWidth());
    assertEquals("Image height should remain 1",
            1, blurredImage.getHeight());
  }
}