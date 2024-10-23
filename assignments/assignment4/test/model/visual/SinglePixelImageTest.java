package model.visual;

import org.junit.Before;
import org.junit.Test;

import model.pixels.Pixel;
import model.pixels.RGB;

import static org.junit.Assert.assertEquals;

public class SinglePixelImageTest {
  private RenderedImage singlePixelImage;
  private Pixel originalPixel;

  @Before
  public void setUp() {
    // Create a single pixel with distinct RGB values for clear testing
    originalPixel = new RGB(120, 180, 240);
    Pixel[][] pixels = new Pixel[][]{{originalPixel}};
    singlePixelImage = new RenderedImage(pixels);
  }

  @Test
  public void testBasicProperties() {
    // Verify dimensions
    assertEquals("Width should be 1", 1, singlePixelImage.getWidth());
    assertEquals("Height should be 1", 1, singlePixelImage.getHeight());

    // Verify pixel retrieval
    Pixel retrievedPixel = singlePixelImage.getPixel(0, 0);
    assertEquals("Original pixel should match retrieved pixel", originalPixel, retrievedPixel);
  }

  @Test
  public void testLuma() {
    Image lumaImage = singlePixelImage.getLuma();

    // Calculate expected luma value
    int expectedLuma = (int)(0.2126 * originalPixel.getRed() +
            0.7152 * originalPixel.getGreen() +
            0.0722 * originalPixel.getBlue());

    Pixel lumaPixel = lumaImage.getPixel(0, 0);

    assertEquals("Luma transformation should match expected value",
            new RGB(expectedLuma, expectedLuma, expectedLuma), lumaPixel);
  }

  @Test
  public void testSepia() {
    Image sepiaImage = singlePixelImage.getSepia();

    // Calculate expected sepia values
    int originalRed = originalPixel.getRed();
    int originalGreen = originalPixel.getGreen();
    int originalBlue = originalPixel.getBlue();

    int expectedRed = Math.min(255, (int)(0.393 * originalRed +
            0.769 * originalGreen +
            0.189 * originalBlue));
    int expectedGreen = Math.min(255, (int)(0.349 * originalRed +
            0.686 * originalGreen +
            0.168 * originalBlue));
    int expectedBlue = Math.min(255, (int)(0.272 * originalRed +
            0.534 * originalGreen +
            0.131 * originalBlue));

    Pixel sepiaPixel = sepiaImage.getPixel(0, 0);

    assertEquals("Sepia transformation should match expected values",
            new RGB(expectedRed, expectedGreen, expectedBlue), sepiaPixel);
  }

  @Test
  public void testIntensity() {
    Image intensityImage = singlePixelImage.getIntensity();

    // Calculate expected intensity
    int expectedIntensity = (originalPixel.getRed() +
            originalPixel.getGreen() +
            originalPixel.getBlue()) / 3;

    Pixel intensityPixel = intensityImage.getPixel(0, 0);

    assertEquals("Intensity transformation should match expected value",
            new RGB(expectedIntensity, expectedIntensity, expectedIntensity), intensityPixel);
  }

  @Test
  public void testValue() {
    Image valueImage = singlePixelImage.getValue();

    // Calculate expected value (maximum of RGB)
    int expectedValue = Math.max(Math.max(originalPixel.getRed(),
                    originalPixel.getGreen()),
            originalPixel.getBlue());

    Pixel valuePixel = valueImage.getPixel(0, 0);

    assertEquals("Value transformation should match expected value",
            new RGB(expectedValue, expectedValue, expectedValue), valuePixel);
  }

  @Test
  public void testColorComponents() {
    // Test Red Component
    Image redImage = singlePixelImage.createRedComponent();
    Pixel redPixel = redImage.getPixel(0, 0);
    assertEquals("Red component should preserve red value only",
            new RGB(originalPixel.getRed(), originalPixel.getRed(), originalPixel.getRed()), redPixel);

    // Test Green Component
    Image greenImage = singlePixelImage.createGreenComponent();
    Pixel greenPixel = greenImage.getPixel(0, 0);
    assertEquals("Green component should preserve green value only",
            new RGB(originalPixel.getGreen(), originalPixel.getGreen(), originalPixel.getGreen()), greenPixel);

    // Test Blue Component
    Image blueImage = singlePixelImage.createBlueComponent();
    Pixel bluePixel = blueImage.getPixel(0, 0);
    assertEquals("Blue component should preserve blue value only",
            new RGB(originalPixel.getBlue(), originalPixel.getBlue(), originalPixel.getBlue()), bluePixel);
  }

  @Test
  public void testBrightnessAdjustment() {
    // Test brightening
    int brightenFactor = 50;
    Image brightenedImage = singlePixelImage.adjustImageBrightness(brightenFactor);
    Pixel brightenedPixel = brightenedImage.getPixel(0, 0);

    assertEquals("Brightened pixel should have increased RGB values",
            new RGB(originalPixel.getRed() + brightenFactor,
                    originalPixel.getGreen() + brightenFactor,
                    originalPixel.getBlue() + brightenFactor), brightenedPixel);

    // Test darkening
    int darkenFactor = -30;
    Image darkenedImage = singlePixelImage.adjustImageBrightness(darkenFactor);
    Pixel darkenedPixel = darkenedImage.getPixel(0, 0);

    assertEquals("Darkened pixel should have decreased RGB values",
            new RGB(originalPixel.getRed() + darkenFactor,
                    originalPixel.getGreen() + darkenFactor,
                    originalPixel.getBlue() + darkenFactor), darkenedPixel);

  }
}