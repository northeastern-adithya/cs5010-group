import org.junit.Test;
import static org.junit.Assert.*;

import model.enumeration.ImageType;
import model.pixels.Pixel;
import model.visual.Image;
import utility.IOUtils;

public class NewTests {

  @Test
  public void testHistogram() {
    try {
      Image image = IOUtils.read("test_resources/input/manhattan-small.png", ImageType.PNG);
      System.out.println("width: " + image.getWidth());
      System.out.println("height: " + image.getHeight());
      IOUtils.write(image.createHistogram(), "test_resources/output/histogram.png", ImageType.PNG);

      Image expected = IOUtils.read("test_resources/expected/manhattan-small-histogram.png", ImageType.PNG);
      Image actual = IOUtils.read("test_resources/output/histogram.png", ImageType.PNG);
      if (actual.getWidth() != expected.getWidth() || actual.getHeight() != expected.getHeight()) {
        fail("Images have different dimensions");
      }

    } catch (Exception e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  @Test
  public void testColorCorrection() {
    try {
      Image image = IOUtils.read("test_resources/input/galaxy.png", ImageType.PNG);
      IOUtils.write(image.colorCorrect(), "test_resources/output/color-corrected.png", ImageType.PNG);

      Image expected = IOUtils.read("test_resources/expected/galaxy-corrected.png", ImageType.PNG);
      Image actual = image.colorCorrect();
      if (actual.getWidth() != expected.getWidth() || actual.getHeight() != expected.getHeight()) {
        fail("Images have different dimensions");
      }
      for (int x = 0; x < expected.getWidth(); x++) {
        for (int y = 0; y < expected.getHeight(); y++) {
          Pixel p1 = expected.getPixel(x, y);
          Pixel p2 = actual.getPixel(x, y);
          assertEquals(p1.getRed(), p2.getRed());
          assertEquals(p1.getGreen(), p2.getGreen());
          assertEquals(p1.getBlue(), p2.getBlue());
        }
      }
    } catch (Exception e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }
}