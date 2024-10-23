package model.pixels;

import model.LinearColorTransformationType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WhitePixelTest {
  private RGB whitePixel;
  private final int WHITE = 255;

  @Before
  public void setUp() {
    whitePixel = new RGB(WHITE, WHITE, WHITE);
  }

  @Test
  public void testConstructor() {
    assertEquals(WHITE, whitePixel.getRed());
    assertEquals(WHITE, whitePixel.getGreen());
    assertEquals(WHITE, whitePixel.getBlue());
  }

  @Test
  public void testCreatePixel() {
    Pixel newPixel = whitePixel.createPixel(255, 255, 255);
    assertTrue(newPixel instanceof RGB);
    assertEquals(WHITE, newPixel.getRed());
    assertEquals(WHITE, newPixel.getGreen());
    assertEquals(WHITE, newPixel.getBlue());
  }

  @Test
  public void testCreateRedComponent() {
    Pixel redComponent = whitePixel.createRedComponent();
    assertEquals(WHITE, redComponent.getRed());
    assertEquals(WHITE, redComponent.getGreen());
    assertEquals(WHITE, redComponent.getBlue());
  }

  @Test
  public void testCreateGreenComponent() {
    Pixel greenComponent = whitePixel.createGreenComponent();
    assertEquals(WHITE, greenComponent.getRed());
    assertEquals(WHITE, greenComponent.getGreen());
    assertEquals(WHITE, greenComponent.getBlue());
  }

  @Test
  public void testCreateBlueComponent() {
    Pixel blueComponent = whitePixel.createBlueComponent();
    assertEquals(WHITE, blueComponent.getRed());
    assertEquals(WHITE, blueComponent.getGreen());
    assertEquals(WHITE, blueComponent.getBlue());
  }

  @Test
  public void testEqualsReflexive() {
    RGB x = new RGB(WHITE, WHITE, WHITE);
    assertTrue("Reflexive property failed", x.equals(x));
  }

  @Test
  public void testEqualsSymmetric() {
    RGB x = new RGB(WHITE, WHITE, WHITE);
    RGB y = new RGB(WHITE, WHITE, WHITE);

    assertTrue("Forward symmetric test failed", x.equals(y));
    assertTrue("Backward symmetric test failed", y.equals(x));
  }

  @Test
  public void testEqualsTransitive() {
    RGB x = new RGB(WHITE, WHITE, WHITE);
    RGB y = new RGB(WHITE, WHITE, WHITE);
    RGB z = new RGB(WHITE, WHITE, WHITE);

    assertTrue("First transitive condition failed", x.equals(y));
    assertTrue("Second transitive condition failed", y.equals(z));
    assertTrue("Transitive property failed", x.equals(z));
  }

  @Test
  public void testEqualsConsistent() {
    RGB x = new RGB(WHITE, WHITE, WHITE);
    RGB y = new RGB(WHITE, WHITE, WHITE);

    boolean firstResult = x.equals(y);
    for (int i = 0; i < 5; i++) {
      assertEquals("Consistent property failed on iteration " + i,
              firstResult, x.equals(y));
    }
  }

  @Test
  public void testEqualsNullComparison() {
    RGB x = new RGB(WHITE, WHITE, WHITE);
    assertFalse("Null comparison failed", x.equals(null));
  }

  @Test
  public void testEqualsDifferentTypes() {
    RGB x = new RGB(WHITE, WHITE, WHITE);
    Object nonPixel = "Not a pixel";
    assertFalse("Different type comparison failed", x.equals(nonPixel));
  }

  @Test
  public void testAdjustBrightness() {
    // White pixel with increased brightness should remain white
    Pixel brighterPixel = whitePixel.adjustBrightness(50);
    assertEquals(WHITE, brighterPixel.getRed());
    assertEquals(WHITE, brighterPixel.getGreen());
    assertEquals(WHITE, brighterPixel.getBlue());

    Pixel darkerPixel = whitePixel.adjustBrightness(-50);
    assertEquals(WHITE - 50, darkerPixel.getRed());
    assertEquals(WHITE - 50, darkerPixel.getGreen());
    assertEquals(WHITE - 50, darkerPixel.getBlue());
  }

  @Test
  public void testGetIntensity() {
    Pixel intensity = whitePixel.getIntensity();
    assertEquals(WHITE, intensity.getRed());
    assertEquals(WHITE, intensity.getGreen());
    assertEquals(WHITE, intensity.getBlue());
  }

  @Test
  public void testGetValue() {
    Pixel value = whitePixel.getValue();
    assertEquals(WHITE, value.getRed());
    assertEquals(WHITE, value.getGreen());
    assertEquals(WHITE, value.getBlue());
  }

  @Test
  public void testGetLuma() {
    Pixel luma = whitePixel.getLuma();
    // White pixel should remain white after luma transformation
    // Because 0.2126 + 0.7152 + 0.0722 = 1.0
    assertEquals(254, luma.getRed());
    assertEquals(254, luma.getGreen());
    assertEquals(254, luma.getBlue());
  }

  @Test
  public void testGetSepia() {
    Pixel sepia = whitePixel.getSepia();
    double[][] sepiaKernel = LinearColorTransformationType.SEPIA.getKernel();

    // For white pixel, all channels are 255, so we can calculate expected values
    int expectedRed = (int)((sepiaKernel[0][0] + sepiaKernel[0][1] + sepiaKernel[0][2]) * WHITE);
    int expectedGreen = (int)((sepiaKernel[1][0] + sepiaKernel[1][1] + sepiaKernel[1][2]) * WHITE);
    int expectedBlue = (int)((sepiaKernel[2][0] + sepiaKernel[2][1] + sepiaKernel[2][2]) * WHITE);

    // Values should be clamped to 255 if they exceed it
    expectedRed = Math.min(255, expectedRed);
    expectedGreen = Math.min(255, expectedGreen);
    expectedBlue = Math.min(255, expectedBlue);

    assertEquals(expectedRed, sepia.getRed());
    assertEquals(expectedGreen, sepia.getGreen());
    assertEquals(expectedBlue, sepia.getBlue());
  }
}