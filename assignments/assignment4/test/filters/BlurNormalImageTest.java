package filters;

import org.junit.Before;
import org.junit.Test;

import factories.Factory;
import model.pixels.Pixel;
import model.visual.Image;

import static org.junit.Assert.assertEquals;

/**
 * Test class for applying Blur filter to normal RGB images.
 */
public class BlurNormalImageTest extends BlurTestBase {
  private Image testImage;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    Pixel[][] pixels = new Pixel[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels[i][j] = Factory.createRGBPixel(100, 150, 200);
      }
    }
    testImage = Factory.createImage(pixels);
  }

  @Test
  public void testBlurUniformImage() {
    Image blurredImage = blurFilter.applyFilter(testImage);
    Pixel[][] expectedPixels = {
            {Factory.createRGBPixel(56, 84, 112), Factory.createRGBPixel(75, 112, 150), Factory.createRGBPixel(56, 84, 112)},
            {Factory.createRGBPixel(75, 112, 150), Factory.createRGBPixel(100, 150, 200), Factory.createRGBPixel(75, 112, 150)},
            {Factory.createRGBPixel(56, 84, 112), Factory.createRGBPixel(75, 112, 150), Factory.createRGBPixel(56, 84, 112)}
    };

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Pixel resultPixel = blurredImage.getPixel(i, j);
        Pixel expectedPixel = expectedPixels[i][j];
        assertEquals("Pixel red value should be as expected", expectedPixel.getRed(), resultPixel.getRed());
        assertEquals("Pixel green value should be as expected", expectedPixel.getGreen(), resultPixel.getGreen());
        assertEquals("Pixel blue value should be as expected", expectedPixel.getBlue(), resultPixel.getBlue());
      }
    }
  }

  @Test
  public void testImageDimensionsPreserved() {
    Image blurredImage = blurFilter.applyFilter(testImage);
    assertEquals("Image width should be preserved",
            testImage.getWidth(), blurredImage.getWidth());
    assertEquals("Image height should be preserved",
            testImage.getHeight(), blurredImage.getHeight());
  }
}
