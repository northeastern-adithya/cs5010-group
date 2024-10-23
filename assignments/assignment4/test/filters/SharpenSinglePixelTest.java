package filters;

import org.junit.Before;
import org.junit.Test;

import factories.Factory;
import model.pixels.Pixel;
import model.visual.Image;

import static org.junit.Assert.assertEquals;

/**
 * Test class for applying Sharpen filter to single pixel images.
 */
public class SharpenSinglePixelTest extends SharpenTestBase {
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
  public void testSharpenSinglePixel() {
    Image sharpenedImage = sharpenFilter.applyFilter(singlePixelImage);
    Pixel resultPixel = sharpenedImage.getPixel(0, 0);

    // With sharpen kernel, center value is 1.0, so single pixel value will equal input
    assertEquals("Single pixel red value should be affected by kernel",
            100, resultPixel.getRed());
    assertEquals("Single pixel green value should be affected by kernel",
            100, resultPixel.getGreen());
    assertEquals("Single pixel blue value should be affected by kernel",
            100, resultPixel.getBlue());
  }

  @Test
  public void testSinglePixelDimensionsPreserved() {
    Image sharpenedImage = sharpenFilter.applyFilter(singlePixelImage);
    assertEquals("Image width should remain 1",
            1, sharpenedImage.getWidth());
    assertEquals("Image height should remain 1",
            1, sharpenedImage.getHeight());
  }
}