package model.pixels;

import static org.junit.Assert.*;


import model.LinearColorTransformationType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RGBTest {
  private RGB pixel;
  private final int RED = 100;
  private final int GREEN = 150;
  private final int BLUE = 200;

  @Before
  public void setUp() {
    pixel = new RGB(RED, GREEN, BLUE);
  }

  @Test
  public void testConstructor() {
    assertEquals(RED, pixel.getRed());
    assertEquals(GREEN, pixel.getGreen());
    assertEquals(BLUE, pixel.getBlue());
  }

  @Test
  public void testConstructorWithNegativeValues() {
    RGB pixel = new RGB(-10, -20, -30);
    assertEquals(0, pixel.getRed());
    assertEquals(0, pixel.getGreen());
    assertEquals(0, pixel.getBlue());
  }

  @Test
  public void testConstructorWithOverflowValues() {
    RGB pixel = new RGB(300, 400, 500);
    assertEquals(255, pixel.getRed());
    assertEquals(255, pixel.getGreen());
    assertEquals(255, pixel.getBlue());
  }

  @Test
  public void testCreatePixel() {
    Pixel newPixel = pixel.createPixel(50, 100, 150);
    assertTrue(newPixel instanceof RGB);
    assertEquals(50, newPixel.getRed());
    assertEquals(100, newPixel.getGreen());
    assertEquals(150, newPixel.getBlue());
  }

  @Test
  public void testCreateRedComponent() {
    Pixel redComponent = pixel.createRedComponent();
    assertEquals(RED, redComponent.getRed());
    assertEquals(RED, redComponent.getGreen());
    assertEquals(RED, redComponent.getBlue());
  }

  @Test
  public void testCreateGreenComponent() {
    Pixel greenComponent = pixel.createGreenComponent();
    assertEquals(GREEN, greenComponent.getRed());
    assertEquals(GREEN, greenComponent.getGreen());
    assertEquals(GREEN, greenComponent.getBlue());
  }

  @Test
  public void testCreateBlueComponent() {
    Pixel blueComponent = pixel.createBlueComponent();
    assertEquals(BLUE, blueComponent.getRed());
    assertEquals(BLUE, blueComponent.getGreen());
    assertEquals(BLUE, blueComponent.getBlue());
  }

  @Test
  public void testEquals() {
    RGB samePixel = new RGB(RED, GREEN, BLUE);
    RGB differentPixel = new RGB(RED + 1, GREEN, BLUE);

    assertTrue(pixel.equals(pixel)); // Same object
    assertTrue(pixel.equals(samePixel)); // Equal values
    assertFalse(pixel.equals(differentPixel)); // Different values
    assertFalse(pixel.equals(null)); // Null comparison
    assertFalse(pixel.equals("Not a pixel")); // Different type
  }

  @Test
  public void testEqualsReflexive() {
    // Reflexive: x.equals(x) should return true
    RGB x = new RGB(100, 150, 200);
    assertTrue(x.equals(x));
  }

  @Test
  public void testEqualsSymmetric() {
    // Symmetric: x.equals(y) should return true if and only if y.equals(x) returns true
    RGB x = new RGB(100, 150, 200);
    RGB y = new RGB(100, 150, 200);

    assertTrue(x.equals(y));
    assertTrue(y.equals(x));
  }

  @Test
  public void testEqualsTransitive() {
    // Transitive: if x.equals(y) and y.equals(z), then x.equals(z)
    RGB x = new RGB(100, 150, 200);
    RGB y = new RGB(100, 150, 200);
    RGB z = new RGB(100, 150, 200);

    assertTrue(x.equals(y));
    assertTrue(y.equals(z));
    assertTrue(x.equals(z));
  }

  @Test
  public void testAdjustBrightness() {
    Pixel brighterPixel = pixel.adjustBrightness(50);
    assertEquals(RED + 50, brighterPixel.getRed());
    assertEquals(GREEN + 50, brighterPixel.getGreen());
    assertEquals(BLUE + 50, brighterPixel.getBlue());

    Pixel darkerPixel = pixel.adjustBrightness(-50);
    assertEquals(RED - 50, darkerPixel.getRed());
    assertEquals(GREEN - 50, darkerPixel.getGreen());
    assertEquals(BLUE - 50, darkerPixel.getBlue());
  }

  @Test
  public void testGetIntensity() {
    int expectedIntensity = (RED + GREEN + BLUE) / 3;
    Pixel intensity = pixel.getIntensity();
    assertEquals(expectedIntensity, intensity.getRed());
    assertEquals(expectedIntensity, intensity.getGreen());
    assertEquals(expectedIntensity, intensity.getBlue());
  }

  @Test
  public void testGetValue() {
    int expectedValue = Math.max(Math.max(RED, GREEN), BLUE);
    Pixel value = pixel.getValue();
    assertEquals(expectedValue, value.getRed());
    assertEquals(expectedValue, value.getGreen());
    assertEquals(expectedValue, value.getBlue());
  }

  @Test
  public void testGetLuma() {
    Pixel luma = pixel.getLuma();
    double[][] lumaKernel = LinearColorTransformationType.LUMA.getKernel();

    int expectedRed = (int)(lumaKernel[0][0] * RED + lumaKernel[0][1] * GREEN + lumaKernel[0][2] * BLUE);
    int expectedGreen = (int)(lumaKernel[1][0] * RED + lumaKernel[1][1] * GREEN + lumaKernel[1][2] * BLUE);
    int expectedBlue = (int)(lumaKernel[2][0] * RED + lumaKernel[2][1] * GREEN + lumaKernel[2][2] * BLUE);

    assertEquals(expectedRed, luma.getRed());
    assertEquals(expectedGreen, luma.getGreen());
    assertEquals(expectedBlue, luma.getBlue());
  }

  @Test
  public void testGetSepia() {
    Pixel sepia = pixel.getSepia();
    double[][] sepiaKernel = LinearColorTransformationType.SEPIA.getKernel();

    int expectedRed = (int)(sepiaKernel[0][0] * RED + sepiaKernel[0][1] * GREEN + sepiaKernel[0][2] * BLUE);
    int expectedGreen = (int)(sepiaKernel[1][0] * RED + sepiaKernel[1][1] * GREEN + sepiaKernel[1][2] * BLUE);
    int expectedBlue = (int)(sepiaKernel[2][0] * RED + sepiaKernel[2][1] * GREEN + sepiaKernel[2][2] * BLUE);

    assertEquals(expectedRed, sepia.getRed());
    assertEquals(expectedGreen, sepia.getGreen());
    assertEquals(expectedBlue, sepia.getBlue());
  }

  @Test
  public void testBrightnessAdjustmentClamping() {
    // Test upper bound clamping
    Pixel veryBright = pixel.adjustBrightness(1000);
    assertEquals(255, veryBright.getRed());
    assertEquals(255, veryBright.getGreen());
    assertEquals(255, veryBright.getBlue());

    // Test lower bound clamping
    Pixel veryDark = pixel.adjustBrightness(-1000);
    assertEquals(0, veryDark.getRed());
    assertEquals(0, veryDark.getGreen());
    assertEquals(0, veryDark.getBlue());
  }
}