package model.visual;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.pixels.Pixel;
import model.pixels.RGB;
import factories.ImageFactory;

public class RenderedImageTest {
  private RenderedImage image;
  private Pixel[][] pixels;

  @Before
  public void setUp() {
    // Create a 2x2 test image with different RGB values
    pixels = new Pixel[2][2];
    pixels[0][0] = new RGB(100, 150, 200); // top-left
    pixels[0][1] = new RGB(50, 100, 150);  // bottom-left
    pixels[1][0] = new RGB(200, 100, 50);  // top-right
    pixels[1][1] = new RGB(150, 200, 100); // bottom-right
    image = new RenderedImage(pixels);
  }

  @Test
  public void testConstructor() {
    assertNotNull("Image should be created successfully", image);
  }

  @Test(expected = NullPointerException.class)
  public void testConstructorWithNull() {
    new RenderedImage(null);
  }

  @Test
  public void testGetPixel() {
    assertEquals(new RGB(100, 150, 200), image.getPixel(0, 0));
    assertEquals(new RGB(50, 100, 150), image.getPixel(0, 1));
    assertEquals(new RGB(200, 100, 50), image.getPixel(1, 0));
    assertEquals(new RGB(150, 200, 100), image.getPixel(1, 1));
  }

  @Test
  public void testGetWidth() {
    assertEquals(2, image.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(2, image.getHeight());
  }

  @Test
  public void testCreateRedComponent() {
    Image redImage = image.createRedComponent();
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel original = image.getPixel(x, y);
        Pixel redPixel = redImage.getPixel(x, y);
        assertEquals(redPixel.getRed(), original.getRed());
        assertEquals(redPixel.getGreen(), original.getRed());
        assertEquals(redPixel.getBlue(), original.getRed());
      }
    }
  }

  @Test
  public void testCreateGreenComponent() {
    Image greenImage = image.createGreenComponent();
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel original = image.getPixel(x, y);
        Pixel green = greenImage.getPixel(x, y);
        assertEquals(green.getRed(), original.getGreen());
        assertEquals(green.getGreen(), original.getGreen());
        assertEquals(green.getBlue(), original.getGreen());
      }
    }
  }

  @Test
  public void testCreateBlueComponent() {
    Image blueImage = image.createBlueComponent();
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel original = image.getPixel(x, y);
        Pixel blue = blueImage.getPixel(x, y);
        assertEquals(blue.getRed(), original.getBlue());
        assertEquals(blue.getGreen(), original.getBlue());
        assertEquals(blue.getBlue(), original.getBlue());
      }
    }
  }

  @Test
  public void testAdjustImageBrightness() {
    // Test brightening
    Image brightenedImage = image.adjustImageBrightness(50);
    for( int x = 0; x < image.getWidth(); x++) {
      for( int y = 0; y < image.getHeight(); y++) {
        Pixel original = image.getPixel(x, y);
        Pixel brightened = brightenedImage.getPixel(x, y);
        assertEquals(Math.min(255, original.getRed() + 50), brightened.getRed());
        assertEquals(Math.min(255, original.getGreen() + 50), brightened.getGreen());
        assertEquals(Math.min(255, original.getBlue() + 50), brightened.getBlue());
      }
    }

    // Test darkening
    Image darkenedImage = image.adjustImageBrightness(-50);
    for( int x = 0; x < image.getWidth(); x++) {
      for( int y = 0; y < image.getHeight(); y++) {
        Pixel original = image.getPixel(x, y);
        Pixel darkened = darkenedImage.getPixel(x, y);
        assertEquals(Math.max(0, original.getRed() - 50), darkened.getRed());
        assertEquals(Math.max(0, original.getGreen() - 50), darkened.getGreen());
        assertEquals(Math.max(0, original.getBlue() - 50), darkened.getBlue());
      }
    }
  }

  @Test
  public void testGetLuma() {
    Image lumaImage = image.getLuma();

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel original = image.getPixel(x, y);
        int expectedLuma = (int)(0.2126 * original.getRed() +
                0.7152 * original.getGreen() +
                0.0722 * original.getBlue());
        Pixel lumaPixel = lumaImage.getPixel(x, y);

        assertEquals(new RGB(expectedLuma, expectedLuma, expectedLuma), lumaPixel);
      }
    }
  }

  @Test
  public void testGetSepia() {
    Image sepiaImage = image.getSepia();

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel original = image.getPixel(x, y);
        int originalRed = original.getRed();
        int originalGreen = original.getGreen();
        int originalBlue = original.getBlue();

        int expectedRed = Math.min(255, (int)(0.393 * originalRed +
                0.769 * originalGreen +
                0.189 * originalBlue));
        int expectedGreen = Math.min(255, (int)(0.349 * originalRed +
                0.686 * originalGreen +
                0.168 * originalBlue));
        int expectedBlue = Math.min(255, (int)(0.272 * originalRed +
                0.534 * originalGreen +
                0.131 * originalBlue));

        Pixel sepiaPixel = sepiaImage.getPixel(x, y);

        assertEquals(new RGB(expectedRed, expectedGreen, expectedBlue), sepiaPixel);
      }
    }
  }

  @Test
  public void testGetIntensity() {
    Image intensityImage = image.getIntensity();

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel original = image.getPixel(x, y);
        int expectedIntensity = (original.getRed() +
                original.getGreen() +
                original.getBlue()) / 3;

        Pixel intensityPixel = intensityImage.getPixel(x, y);

        assertEquals(new RGB(expectedIntensity, expectedIntensity, expectedIntensity),
                intensityPixel);
      }
    }
  }

  @Test
  public void testGetValue() {
    Image valueImage = image.getValue();

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel original = image.getPixel(x, y);
        int expectedValue = Math.max(Math.max(original.getRed(),
                        original.getGreen()),
                original.getBlue());

        Pixel valuePixel = valueImage.getPixel(x, y);

        assertEquals(new RGB(expectedValue, expectedValue, expectedValue),
                valuePixel);
      }
    }
  }

  @Test
  public void testHorizontalFlip() {
    Image flippedImage = image.horizontalFlip();
    // Verify pixels are flipped horizontally
    assertEquals(image.getPixel(0, 0), flippedImage.getPixel(1, 0));
    assertEquals(image.getPixel(1, 0), flippedImage.getPixel(0, 0));
    assertEquals(image.getPixel(0, 1), flippedImage.getPixel(1, 1));
    assertEquals(image.getPixel(1, 1), flippedImage.getPixel(0, 1));
  }

  @Test
  public void testVerticalFlip() {
    Image flippedImage = image.verticalFlip();
    // Verify pixels are flipped vertically
    assertEquals(image.getPixel(0, 0), flippedImage.getPixel(0, 1));
    assertEquals(image.getPixel(0, 1), flippedImage.getPixel(0, 0));
    assertEquals(image.getPixel(1, 0), flippedImage.getPixel(1, 1));
    assertEquals(image.getPixel(1, 1), flippedImage.getPixel(1, 0));
  }

  @Test
  public void testEdgeCases() {
    // Test 1x1 image
    Pixel[][] singlePixel = new Pixel[][]{{new RGB(100, 100, 100)}};
    RenderedImage smallImage = new RenderedImage(singlePixel);
    assertEquals(1, smallImage.getWidth());
    assertEquals(1, smallImage.getHeight());

    // Test brightness adjustment clamping
    Image overBrightened = image.adjustImageBrightness(300);
    assertTrue(overBrightened.getPixel(0, 0).getRed() <= 255);

    Image overDarkened = image.adjustImageBrightness(-300);
    assertTrue(overDarkened.getPixel(0, 0).getRed() >= 0);
  }

}