package model.pixels;

import model.LinearColorTransformationType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BlackPixelTest {
  private RGB blackPixel;
  private final int BLACK = 0;

  @Before
  public void setUp() {
    blackPixel = new RGB(BLACK, BLACK, BLACK);
  }

  @Test
  public void testConstructor() {
    assertEquals(BLACK, blackPixel.getRed());
    assertEquals(BLACK, blackPixel.getGreen());
    assertEquals(BLACK, blackPixel.getBlue());
  }

  @Test
  public void testCreatePixel() {
    Pixel newPixel = blackPixel.createPixel(0, 0, 0);
    assertTrue(newPixel instanceof RGB);
    assertEquals(BLACK, newPixel.getRed());
    assertEquals(BLACK, newPixel.getGreen());
    assertEquals(BLACK, newPixel.getBlue());
  }

  @Test
  public void testCreateRedComponent() {
    Pixel redComponent = blackPixel.createRedComponent();
    assertEquals(BLACK, redComponent.getRed());
    assertEquals(BLACK, redComponent.getGreen());
    assertEquals(BLACK, redComponent.getBlue());
  }

  @Test
  public void testCreateGreenComponent() {
    Pixel greenComponent = blackPixel.createGreenComponent();
    assertEquals(BLACK, greenComponent.getRed());
    assertEquals(BLACK, greenComponent.getGreen());
    assertEquals(BLACK, greenComponent.getBlue());
  }

  @Test
  public void testCreateBlueComponent() {
    Pixel blueComponent = blackPixel.createBlueComponent();
    assertEquals(BLACK, blueComponent.getRed());
    assertEquals(BLACK, blueComponent.getGreen());
    assertEquals(BLACK, blueComponent.getBlue());
  }

  @Test
  public void testEqualsReflexive() {
    RGB x = new RGB(BLACK, BLACK, BLACK);
    assertTrue("Reflexive property failed", x.equals(x));
  }

  @Test
  public void testEqualsSymmetric() {
    RGB x = new RGB(BLACK, BLACK, BLACK);
    RGB y = new RGB(BLACK, BLACK, BLACK);

    assertTrue("Forward symmetric test failed", x.equals(y));
    assertTrue("Backward symmetric test failed", y.equals(x));
  }

  @Test
  public void testEqualsTransitive() {
    RGB x = new RGB(BLACK, BLACK, BLACK);
    RGB y = new RGB(BLACK, BLACK, BLACK);
    RGB z = new RGB(BLACK, BLACK, BLACK);

    assertTrue("First transitive condition failed", x.equals(y));
    assertTrue("Second transitive condition failed", y.equals(z));
    assertTrue("Transitive property failed", x.equals(z));
  }

  @Test
  public void testEqualsConsistent() {
    RGB x = new RGB(BLACK, BLACK, BLACK);
    RGB y = new RGB(BLACK, BLACK, BLACK);

    boolean firstResult = x.equals(y);
    for (int i = 0; i < 5; i++) {
      assertEquals("Consistent property failed on iteration " + i,
              firstResult, x.equals(y));
    }
  }

  @Test
  public void testEqualsNullComparison() {
    RGB x = new RGB(BLACK, BLACK, BLACK);
    assertFalse("Null comparison failed", x.equals(null));
  }

  @Test
  public void testEqualsDifferentTypes() {
    RGB x = new RGB(BLACK, BLACK, BLACK);
    Object nonPixel = "Not a pixel";
    assertFalse("Different type comparison failed", x.equals(nonPixel));
  }

  @Test
  public void testAdjustBrightness() {
    Pixel brighterPixel = blackPixel.adjustBrightness(50);
    assertEquals(50, brighterPixel.getRed());
    assertEquals(50, brighterPixel.getGreen());
    assertEquals(50, brighterPixel.getBlue());

    // Adjusting brightness of black pixel by negative value should still be black
    Pixel darkerPixel = blackPixel.adjustBrightness(-50);
    assertEquals(BLACK, darkerPixel.getRed());
    assertEquals(BLACK, darkerPixel.getGreen());
    assertEquals(BLACK, darkerPixel.getBlue());
  }

  @Test
  public void testGetIntensity() {
    Pixel intensity = blackPixel.getIntensity();
    assertEquals(BLACK, intensity.getRed());
    assertEquals(BLACK, intensity.getGreen());
    assertEquals(BLACK, intensity.getBlue());
  }

  @Test
  public void testGetValue() {
    Pixel value = blackPixel.getValue();
    assertEquals(BLACK, value.getRed());
    assertEquals(BLACK, value.getGreen());
    assertEquals(BLACK, value.getBlue());
  }

  @Test
  public void testGetLuma() {
    Pixel luma = blackPixel.getLuma();
    assertEquals(BLACK, luma.getRed());
    assertEquals(BLACK, luma.getGreen());
    assertEquals(BLACK, luma.getBlue());
  }

  @Test
  public void testGetSepia() {
    // Black pixel should remain black even after sepia transformation
    Pixel sepia = blackPixel.getSepia();
    assertEquals(BLACK, sepia.getRed());
    assertEquals(BLACK, sepia.getGreen());
    assertEquals(BLACK, sepia.getBlue());
  }
}