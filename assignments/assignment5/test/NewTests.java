import org.junit.Test;
import static org.junit.Assert.*;

import model.enumeration.ImageType;
import model.visual.Image;
import utility.IOUtils;

public class NewTests {

  @Test
  public void testHistogram() {
    try {
      Image image = IOUtils.read("test_resources/input/manhattan-small.png", ImageType.PNG);
      IOUtils.write(image.createHistogram(), "test_resources/output/dubai-histogram.png", ImageType.PNG);
    } catch (Exception e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }
}