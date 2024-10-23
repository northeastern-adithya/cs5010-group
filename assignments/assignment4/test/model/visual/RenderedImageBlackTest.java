package model.visual;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.pixels.Pixel;
import model.pixels.RGB;
import factories.ImageFactory;

public class RenderedImageBlackTest {
  private RenderedImage blackImage;
  private Pixel[][] blackPixels;

  @Before
  public void setUp() {
    // Create a 2x2 completely black image
    blackPixels = new Pixel[2][2];
    blackPixels[0][0] = new RGB(0, 0, 0);
    blackPixels[0][1] = new RGB(0, 0, 0);
    blackPixels[1][0] = new RGB(0, 0, 0);
    blackPixels[1][1] = new RGB(0, 0, 0);
    blackImage = new RenderedImage(blackPixels);
  }

  @Test
  public void testConstructor() {
    assertNotNull("Black image should be created successfully", blackImage);
  }

  @Test(expected = NullPointerException.class)
  public void testConstructorWithNull() {
    new RenderedImage(null);
  }

  @Test
  public void testGetPixel() {
    assertEquals(new RGB(0, 0, 0), blackImage.getPixel(0, 0));
    assertEquals(new RGB(0, 0, 0), blackImage.getPixel(0, 1));
    assertEquals(new RGB(0, 0, 0), blackImage.getPixel(1, 0));
    assertEquals(new RGB(0, 0, 0), blackImage.getPixel(1, 1));
  }

  @Test
  public void testGetWidth() {
    assertEquals(2, blackImage.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(2, blackImage.getHeight());
  }

  @Test
  public void testCreateRedComponent() {
    Image redImage = blackImage.createRedComponent();
    // Red component of a black image should remain black
    assertEquals(new RGB(0, 0, 0), redImage.getPixel(0, 0));
    assertEquals(new RGB(0, 0, 0), redImage.getPixel(0, 1));
  }

  @Test
  public void testCreateGreenComponent() {
    Image greenImage = blackImage.createGreenComponent();
    // Green component of a black image should remain black
    assertEquals(new RGB(0, 0, 0), greenImage.getPixel(0, 0));
    assertEquals(new RGB(0, 0, 0), greenImage.getPixel(0, 1));
  }

  @Test
  public void testCreateBlueComponent() {
    Image blueImage = blackImage.createBlueComponent();
    // Blue component of a black image should remain black
    assertEquals(new RGB(0, 0, 0), blueImage.getPixel(0, 0));
    assertEquals(new RGB(0, 0, 0), blueImage.getPixel(0, 1));
  }

  @Test
  public void testAdjustImageBrightness() {
    // Test brightening a black image
    Image brightenedImage = blackImage.adjustImageBrightness(50);
    assertEquals(new RGB(50, 50, 50), brightenedImage.getPixel(0, 0));

    // Test darkening a black image (it should remain black)
    Image darkenedImage = blackImage.adjustImageBrightness(-50);
    assertEquals(new RGB(0, 0, 0), darkenedImage.getPixel(0, 0));
  }

  @Test
  public void testGetLuma() {
    Image lumaImage = blackImage.getLuma();
    // Luma of a black image should remain black
    for (int x = 0; x < blackImage.getWidth(); x++) {
      for (int y = 0; y < blackImage.getHeight(); y++) {
        assertEquals(new RGB(0, 0, 0), lumaImage.getPixel(x, y));
      }
    }
  }

  @Test
  public void testGetSepia() {
    Image sepiaImage = blackImage.getSepia();
    // Sepia transformation of a black image should remain black
    for (int x = 0; x < blackImage.getWidth(); x++) {
      for (int y = 0; y < blackImage.getHeight(); y++) {
        assertEquals(new RGB(0, 0, 0), sepiaImage.getPixel(x, y));
      }
    }
  }

  @Test
  public void testGetIntensity() {
    Image intensityImage = blackImage.getIntensity();
    // Intensity of a black image should remain black
    for (int x = 0; x < blackImage.getWidth(); x++) {
      for (int y = 0; y < blackImage.getHeight(); y++) {
        assertEquals(new RGB(0, 0, 0), intensityImage.getPixel(x, y));
      }
    }
  }

  @Test
  public void testGetValue() {
    Image valueImage = blackImage.getValue();
    // Value of a black image should remain black
    for (int x = 0; x < blackImage.getWidth(); x++) {
      for (int y = 0; y < blackImage.getHeight(); y++) {
        assertEquals(new RGB(0, 0, 0), valueImage.getPixel(x, y));
      }
    }
  }

  @Test
  public void testHorizontalFlip() {
    Image flippedImage = blackImage.horizontalFlip();
    // Black image flipped horizontally should remain the same
    assertEquals(blackImage.getPixel(0, 0), flippedImage.getPixel(1, 0));
    assertEquals(blackImage.getPixel(1, 0), flippedImage.getPixel(0, 0));
    assertEquals(blackImage.getPixel(0, 1), flippedImage.getPixel(1, 1));
    assertEquals(blackImage.getPixel(1, 1), flippedImage.getPixel(0, 1));
  }

  @Test
  public void testVerticalFlip() {
    Image flippedImage = blackImage.verticalFlip();
    // Black image flipped vertically should remain the same
    assertEquals(blackImage.getPixel(0, 0), flippedImage.getPixel(0, 1));
    assertEquals(blackImage.getPixel(0, 1), flippedImage.getPixel(0, 0));
    assertEquals(blackImage.getPixel(1, 0), flippedImage.getPixel(1, 1));
    assertEquals(blackImage.getPixel(1, 1), flippedImage.getPixel(1, 0));
  }

  @Test
  public void testEdgeCases() {
    // Test brightness adjustment clamping on a black image
    Image overBrightened = blackImage.adjustImageBrightness(300);
    assertTrue(overBrightened.getPixel(0, 0).getRed() <= 255);

    Image overDarkened = blackImage.adjustImageBrightness(-300);
    assertTrue(overDarkened.getPixel(0, 0).getRed() >= 0);
  }
}
