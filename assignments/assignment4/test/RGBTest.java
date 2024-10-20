import static org.junit.Assert.*;

import org.junit.Test;

import model.color.Pixel;
import model.color.RGB;

public class RGBTest {

  @Test
  public void testGetValue() {
    RGB pixel = new RGB(100, 150, 200, 24);
    assertEquals(200, pixel.getValue());
  }

  @Test
  public void testGetLuma() {
    RGB pixel = new RGB(100, 150, 200, 24);
    assertEquals(142.98, pixel.getLuma(), 0.1);
  }

  @Test
  public void testGetIntensity() {
    RGB pixel = new RGB(100, 150, 200, 24);
    assertEquals(150.0, pixel.getIntensity(), 0.1);
  }

  @Test
  public void testGetBits() {
    RGB pixel = new RGB(100, 150, 200, 24);
    assertEquals(24, pixel.getBits());
  }

  @Test
  public void testGetRed() {
    RGB pixel = new RGB(100, 150, 200, 24);
    assertEquals(100, pixel.getRed());
  }

  @Test
  public void testGetGreen() {
    RGB pixel = new RGB(100, 150, 200, 24);
    assertEquals(150, pixel.getGreen());
  }

  @Test
  public void testGetBlue() {
    RGB pixel = new RGB(100, 150, 200, 24);
    assertEquals(200, pixel.getBlue());
  }

  @Test
public void testAdjustBrightness() {
    RGB pixel = new RGB(100, 150, 200, 24);
    Pixel adjustedPixel = pixel.adjustBrightness(50);
    assertEquals(150, adjustedPixel.getRed());
    assertEquals(200, adjustedPixel.getGreen());
    assertEquals(250, adjustedPixel.getBlue());
}

@Test
public void testAdjustBrightnessClamping() {
    RGB pixel = new RGB(10, 20, 30, 24);
    Pixel adjustedPixel = pixel.adjustBrightness(-50);
    assertEquals(0, adjustedPixel.getRed());
    assertEquals(0, adjustedPixel.getGreen());
    assertEquals(0, adjustedPixel.getBlue());

    pixel = new RGB(250, 250, 250, 24);
    adjustedPixel = pixel.adjustBrightness(50);
    assertEquals(255, adjustedPixel.getRed());
    assertEquals(255, adjustedPixel.getGreen());
    assertEquals(255, adjustedPixel.getBlue());
}
}