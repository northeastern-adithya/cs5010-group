package filters;

import org.junit.Before;
import org.junit.Test;

import factories.Factory;
import model.pixels.Pixel;
import model.visual.Image;

import static org.junit.Assert.assertEquals;

/**
 * Test class for applying Sharpen filter to normal RGB images.
 */
public class SharpenNormalImageTest extends SharpenTestBase {
  private Image testImage;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    Pixel[][] pixels = new Pixel[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        pixels[i][j] = Factory.createRGBPixel(100, 150, 200);
      }
    }
    testImage = Factory.createImage(pixels);
  }

  @Test
  public void testSharpenUniformImage() {
    Image sharpenedImage = sharpenFilter.applyFilter(testImage);
    Pixel[][] expectedPixels = {
            {Factory.createRGBPixel(112, 168, 225), Factory.createRGBPixel(150, 225, 255), Factory.createRGBPixel(112, 168, 225), Factory.createRGBPixel(150, 225, 255), Factory.createRGBPixel(112, 168, 225)},
            {Factory.createRGBPixel(150, 225, 255), Factory.createRGBPixel(212, 255, 255), Factory.createRGBPixel(162, 243, 255), Factory.createRGBPixel(212, 255, 255), Factory.createRGBPixel(150, 225, 255)},
            {Factory.createRGBPixel(112, 168, 225), Factory.createRGBPixel(162, 243, 255), Factory.createRGBPixel(100, 150, 200), Factory.createRGBPixel(162, 243, 255), Factory.createRGBPixel(112, 168, 225)},
            {Factory.createRGBPixel(150, 225, 255), Factory.createRGBPixel(212, 255, 255), Factory.createRGBPixel(162, 243, 255), Factory.createRGBPixel(212, 255, 255), Factory.createRGBPixel(150, 225, 255)},
            {Factory.createRGBPixel(112, 168, 225), Factory.createRGBPixel(150, 225, 255), Factory.createRGBPixel(112, 168, 225), Factory.createRGBPixel(150, 225, 255), Factory.createRGBPixel(112, 168, 225)}
    };

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        assertEquals(expectedPixels[i][j], sharpenedImage.getPixel(i, j));
      }
    }
  }

  @Test
  public void testImageDimensionsPreserved() {
    Image sharpenedImage = sharpenFilter.applyFilter(testImage);
    assertEquals("Image width should be preserved",
            testImage.getWidth(), sharpenedImage.getWidth());
    assertEquals("Image height should be preserved",
            testImage.getHeight(), sharpenedImage.getHeight());
  }
}