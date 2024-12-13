package controller;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import model.ImageImplementation;
import model.ImageModel;
import model.Pixel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * JUnit test class for ImageController to see if controller is working as
 * expected.
 */
public class ControllerTests {

  @Test
  public void testImageDitheringWithLessThanZero() {
    ImageModel model = new ImageImplementation();
    Map<String,Pixel[][]> imageStore = new HashMap<>();
    ControllerInterface controller = initialiseController(
            model,
            imageStore
    );
    controller.processCommand("dither testImage dithering split -1");
    // Equals to the original image without dithering since the split
    // percentage is less than 0
    assertEquals(null, imageStore.get("dithering"));
  }

  @Test
  public void testImageDitheringWithGreaterThanHundred() {
    ImageModel model = new ImageImplementation();
    Map<String,Pixel[][]> imageStore = new HashMap<>();
    ControllerInterface controller = initialiseController(
            model,
            imageStore
    );
    controller.processCommand("dither testImage dithering split 101");
    // Equals to the original image without dithering since the split
    // percentage is greater than 100
    assertEquals(null, imageStore.get("dithering"));
  }

  @Test
  public void testImageDitheringWithZeroPercentage() {
    ImageModel model = new ImageImplementation();
    Map<String,Pixel[][]> imageStore = new HashMap<>();
    ControllerInterface controller = initialiseController(
            model,
            imageStore
    );
    controller.processCommand("dither testImage dithering split 0");
    assertArrayEquals(new Pixel[][]{
            {new Pixel(0, 0, 0), new Pixel(0, 0, 255)},
            {new Pixel(0, 0, 0), new Pixel(128, 128, 128)}
    }, imageStore.get("dithering"));
  }

  @Test
  public void testImageDitheringWithHundredPercentage() {
    ImageModel model = new ImageImplementation();
    Map<String,Pixel[][]> imageStore = new HashMap<>();
    ControllerInterface controller = initialiseController(
            model,
            imageStore
    );
    controller.processCommand("dither testImage dithering split 100");
    assertArrayEquals(new Pixel[][]{
            {new Pixel(0, 0, 0), new Pixel(0, 0, 0)},
            {new Pixel(255, 255, 255), new Pixel(0, 0, 0)}
    }, imageStore.get("dithering"));
  }

  @Test
  public void testImageDitheringWithFiftyPercentage() {
    ImageModel model = new ImageImplementation();
    Map<String,Pixel[][]> imageStore = new HashMap<>();
    ControllerInterface controller = initialiseController(
            model,
            imageStore
    );
    controller.processCommand("dither testImage dithering split 50");
    assertArrayEquals(new Pixel[][]{
            {new Pixel(0, 0, 0), new Pixel(0, 0, 0)},
            {new Pixel(255, 255, 255), new Pixel(0, 0, 0)}
    }, imageStore.get("dithering"));
  }

  @Test
  public void testImageDitheringWithInvalidImageName() {
    ImageModel model = new ImageImplementation();
    Map<String,Pixel[][]> imageStore = new HashMap<>();
    ControllerInterface controller = initialiseController(
            model,
            imageStore
    );
    controller.processCommand("dither invalidImage dithering split 25");
    assertEquals(null, imageStore.get("dithering"));
  }

  @Test
  public void testImageDitheringWithoutSplitPercentage() {
    ImageModel model = new ImageImplementation();
    Map<String,Pixel[][]> imageStore = new HashMap<>();
    ControllerInterface controller = initialiseController(
            model,
            imageStore
    );
    controller.processCommand(
            "dither testImage dithering split");
    assertEquals(null, imageStore.get("dithering"));
  }

  @Test
  public void testImageDitheringWithInvalidSplitPercentage() {
    ImageModel model = new ImageImplementation();
    Map<String,Pixel[][]> imageStore = new HashMap<>();
    ControllerInterface controller = initialiseController(
            model,
            imageStore
    );
    controller.processCommand(
            "dither testImage dithering split split");
    assertEquals(null, imageStore.get("dithering"));
  }

  @Test
  public void testImageDitheringWithInvalidSplitCommand() {
    ImageModel model = new ImageImplementation();
    Map<String,Pixel[][]> imageStore = new HashMap<>();
    ControllerInterface controller = initialiseController(
            model,
            imageStore
    );
    controller.processCommand(
            "dither testImage dithering dont-split 25");
    assertEquals(null, imageStore.get("dithering"));
  }

  @Test
  public void testImageDitheringWithoutSplitCommand() {
    ImageModel model = new ImageImplementation();
    Map<String,Pixel[][]> imageStore = new HashMap<>();
    ControllerInterface controller = initialiseController(
            model,
            imageStore
    );
    controller.processCommand(
            "dither testImage dithering");
    assertArrayEquals(new Pixel[][]{
            {new Pixel(0, 0, 0), new Pixel(0, 0, 0)},
            {new Pixel(255, 255, 255), new Pixel(0, 0, 0)}
    }, imageStore.get("dithering"));
  }


  private ControllerInterface initialiseController(ImageModel model,
                                                   Map<String,Pixel[][]> imageStore) {
    ControllerInterface controllerInterface = new ImageController(model,
            null,imageStore);
    controllerInterface.processCommand("load test_resources/random.png "
            + "testImage");
    return controllerInterface;
  }
}
