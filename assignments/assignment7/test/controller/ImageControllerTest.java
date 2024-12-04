package controller;

import org.junit.Test;

import model.ImageImplementationV2;
import model.ImageModelV2;

/**
 * JUnit test class for ImageController to verify that the dither command processes correctly.
 */
public class ImageControllerTest {
  private ImageController controller;
  private ImageModelV2 model;

  private void setup() {
    model = new ImageImplementationV2();
    controller = new ImageController(model, null);
  }

  @Test
  public void testDither() {
    setup();
    controller.processCommand("load res/PNG/random_dither_test_image.png");
    controller.processCommand("dither building building_dithered");
  }
}