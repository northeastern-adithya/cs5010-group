package model.visual;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.pixels.Pixel;
import model.pixels.RGB;
public class RenderedImageWhiteTest {
  private RenderedImage whiteImage;
  private Pixel[][] whitePixels;

  @Before
  public void setUp() {
    // Create a 2x2 completely white image
    whitePixels = new Pixel[2][2];
    whitePixels[0][0] = new RGB(255, 255, 255);
    whitePixels[0][1] = new RGB(255, 255, 255);
    whitePixels[1][0] = new RGB(255, 255, 255);
    whitePixels[1][1] = new RGB(255, 255, 255);
    whiteImage = new RenderedImage(whitePixels);
  }

  @Test
  public void testConstructor() {
    assertNotNull("White image should be created successfully", whiteImage);
  }

  @Test(expected = NullPointerException.class)
  public void testConstructorWithNull() {
    new RenderedImage(null);
  }

  @Test
  public void testGetPixel() {
    assertEquals(new RGB(255, 255, 255), whiteImage.getPixel(0, 0));
    assertEquals(new RGB(255, 255, 255), whiteImage.getPixel(0, 1));
    assertEquals(new RGB(255, 255, 255), whiteImage.getPixel(1, 0));
    assertEquals(new RGB(255, 255, 255), whiteImage.getPixel(1, 1));
  }

  @Test
  public void testGetWidth() {
    assertEquals(2, whiteImage.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(2, whiteImage.getHeight());
  }

  @Test
  public void testCreateRedComponent() {
    Image redImage = whiteImage.createRedComponent();
    // Red component of a white image should remain white
    assertEquals(new RGB(255, 255, 255), redImage.getPixel(0, 0));
    assertEquals(new RGB(255, 255, 255), redImage.getPixel(0, 1));
  }

  @Test
  public void testCreateGreenComponent() {
    Image greenImage = whiteImage.createGreenComponent();
    // Green component of a white image should remain white
    assertEquals(new RGB(255, 255, 255), greenImage.getPixel(0, 0));
    assertEquals(new RGB(255, 255, 255), greenImage.getPixel(0, 1));
  }

  @Test
  public void testCreateBlueComponent() {
    Image blueImage = whiteImage.createBlueComponent();
    // Blue component of a white image should remain white
    assertEquals(new RGB(255, 255, 255), blueImage.getPixel(0, 0));
    assertEquals(new RGB(255, 255, 255), blueImage.getPixel(0, 1));
  }

  @Test
  public void testAdjustImageBrightness() {
    // Test brightening a white image (it should remain white as it can't go beyond 255)
    Image brightenedImage = whiteImage.adjustImageBrightness(50);
    assertEquals(new RGB(255, 255, 255), brightenedImage.getPixel(0, 0));

    // Test darkening a white image
    Image darkenedImage = whiteImage.adjustImageBrightness(-50);
    assertEquals(new RGB(205, 205, 205), darkenedImage.getPixel(0, 0));
  }

  @Test
  public void testGetLuma() {
    Image lumaImage = whiteImage.getLuma();
    // Luma of a white image should remain white
    for (int x = 0; x < whiteImage.getWidth(); x++) {
      for (int y = 0; y < whiteImage.getHeight(); y++) {
        assertEquals(new RGB(254, 254, 254), lumaImage.getPixel(x, y));
      }
    }
  }

  @Test
  public void testGetSepia() {
    Image sepiaImage = whiteImage.getSepia();
    // Sepia transformation of a white image should result in light sepia tones
    for (int x = 0; x < whiteImage.getWidth(); x++) {
      for (int y = 0; y < whiteImage.getHeight(); y++) {
        assertEquals(new RGB(255, 255, 238), sepiaImage.getPixel(x, y));
      }
    }
  }

  @Test
  public void testGetIntensity() {
    Image intensityImage = whiteImage.getIntensity();
    // Intensity of a white image should remain white
    for (int x = 0; x < whiteImage.getWidth(); x++) {
      for (int y = 0; y < whiteImage.getHeight(); y++) {
        assertEquals(new RGB(255, 255, 255), intensityImage.getPixel(x, y));
      }
    }
  }

  @Test
  public void testGetValue() {
    Image valueImage = whiteImage.getValue();
    // Value of a white image should remain white
    for (int x = 0; x < whiteImage.getWidth(); x++) {
      for (int y = 0; y < whiteImage.getHeight(); y++) {
        assertEquals(new RGB(255, 255, 255), valueImage.getPixel(x, y));
      }
    }
  }

  @Test
  public void testHorizontalFlip() {
    Image flippedImage = whiteImage.horizontalFlip();
    // White image flipped horizontally should remain the same
    assertEquals(whiteImage.getPixel(0, 0), flippedImage.getPixel(1, 0));
    assertEquals(whiteImage.getPixel(1, 0), flippedImage.getPixel(0, 0));
    assertEquals(whiteImage.getPixel(0, 1), flippedImage.getPixel(1, 1));
    assertEquals(whiteImage.getPixel(1, 1), flippedImage.getPixel(0, 1));
  }

  @Test
  public void testVerticalFlip() {
    Image flippedImage = whiteImage.verticalFlip();
    // White image flipped vertically should remain the same
    assertEquals(whiteImage.getPixel(0, 0), flippedImage.getPixel(0, 1));
    assertEquals(whiteImage.getPixel(0, 1), flippedImage.getPixel(0, 0));
    assertEquals(whiteImage.getPixel(1, 0), flippedImage.getPixel(1, 1));
    assertEquals(whiteImage.getPixel(1, 1), flippedImage.getPixel(1, 0));
  }

  @Test
  public void testEdgeCases() {
    // Test brightness adjustment clamping on a white image
    Image overBrightened = whiteImage.adjustImageBrightness(300);
    assertTrue(overBrightened.getPixel(0, 0).getRed() == 255);

    Image overDarkened = whiteImage.adjustImageBrightness(-300);
    assertTrue(overDarkened.getPixel(0, 0).getRed() == 0);
  }
}
