import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.io.StringReader;
import java.util.Arrays;

import controller.Features;
import controller.GUIImageProcessorController;
import controller.services.FileImageProcessingService;
import exception.ImageProcessorException;
import model.memory.HashMapMemory;
import model.memory.ImageMemory;
import model.memory.StringMemory;
import model.pixels.Pixel;
import model.request.ImageProcessingRequest;
import model.visual.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Integration tests for the GUI controller.
 * Tests from feature class to the model along with interaction of controller
 * with different services.
 */
public class GUIControllerTests {

  private Features features;

  /**
   * Cleans up output directory after test is run.
   */
  @AfterClass
  public static void cleanUp() {
    TestUtils.cleanUp("test_resources/output");
  }


  @Test
  public void testLoadCommandWithInvalidExtension() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            "test_resources.txt",
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertTrue(
            output.toString()
                    .contains("Image type with extension txt not supported"));
  }

  private ImageMemory<Image> initialiseImageMemory() {
    return new HashMapMemory();
  }

  private ImageMemory<String> initialiseStringMemory() {
    return new StringMemory();
  }

  @Test
  public void testLoadWithInvalidPath() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            "invalidPath.png",
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertTrue(
            output.toString()
                    .contains("Error loading the image file"));
  }


  @Test
  public void loadFromPngToPng() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    Image randomImage = TestUtils.randomImage();
    StringBuilder output = new StringBuilder();

    initialiseController(
            "",
            false,
            null,
            "test_resources/input/random.png",
            "test_resources/output/random-png1.png",
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertEquals(randomImage, imageMemory.getImage("random.png"));
    assertTrue(output.toString().contains(
            randomImage.toString()));
    assertEquals("random.png", stringMemory.getImage(""));
    features.saveImage();

    assertTrue(new File("test_resources/output/random-png1.png").exists());
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      stringMemory.getImage("");
    });
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      imageMemory.getImage("");
    });


    initialiseController(
            "",
            false,
            null,
            "test_resources/output/random-png1.png",
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertEquals(randomImage, imageMemory.getImage(
            "random-png1.png"));
    assertEquals("random-png1.png", stringMemory.getImage(""));
  }

  @Test
  public void loadAndSaveFromPPMToPPM() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    Image randomImage = TestUtils.randomImage();
    StringBuilder output = new StringBuilder();

    initialiseController(
            "",
            false,
            null,
            "test_resources/input/random.ppm",
            "test_resources/output/random-ppm.ppm",
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertEquals(randomImage, imageMemory.getImage("random.ppm"));
    assertTrue(output.toString().contains(
            randomImage.toString()));
    assertEquals("random.ppm", stringMemory.getImage(""));
    features.saveImage();

    assertTrue(new File("test_resources/output/random-ppm.ppm").exists());
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      stringMemory.getImage("");
    });
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      imageMemory.getImage("");
    });


    initialiseController(
            "",
            false,
            null,
            "test_resources/output/random-ppm.ppm",
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertEquals(randomImage, imageMemory.getImage(
            "random-ppm.ppm"));
    assertEquals("random-ppm.ppm", stringMemory.getImage(""));
  }

  @Test
  public void loadAndSaveFromPPMToPNG() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    Image randomImage = TestUtils.randomImage();
    StringBuilder output = new StringBuilder();

    initialiseController(
            "",
            false,
            null,
            "test_resources/input/random.ppm",
            "test_resources/output/random-ppm.png",
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertEquals(randomImage, imageMemory.getImage("random.ppm"));
    assertTrue(output.toString().contains(
            randomImage.toString()));
    assertEquals("random.ppm", stringMemory.getImage(""));
    features.saveImage();

    assertTrue(new File("test_resources/output/random-ppm.png").exists());
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      stringMemory.getImage("");
    });
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      imageMemory.getImage("");
    });


    initialiseController(
            "",
            false,
            null,
            "test_resources/output/random-ppm.png",
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertEquals(randomImage, imageMemory.getImage(
            "random-ppm.png"));
    assertEquals("random-ppm.png", stringMemory.getImage(""));
  }

  @Test
  public void loadAndSaveFromPNGToPPM() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    Image randomImage = TestUtils.randomImage();
    StringBuilder output = new StringBuilder();

    initialiseController(
            "",
            false,
            null,
            "test_resources/input/random.png",
            "test_resources/output/random-png.ppm",
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertEquals(randomImage, imageMemory.getImage("random.png"));
    assertTrue(output.toString().contains(
            randomImage.toString()));
    assertEquals("random.png", stringMemory.getImage(""));
    features.saveImage();

    assertTrue(new File("test_resources/output/random-png.ppm").exists());
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      stringMemory.getImage("");
    });
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      imageMemory.getImage("");
    });


    initialiseController(
            "",
            false,
            null,
            "test_resources/output/random-png.ppm",
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertEquals(randomImage, imageMemory.getImage(
            "random-png.ppm"));
    assertEquals("random-png.ppm", stringMemory.getImage(""));
  }

  @Test
  public void loadAndSaveFromJPEG() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    StringBuilder output = new StringBuilder();

    initialiseController(
            "",
            false,
            null,
            "test_resources/input/random.jpeg",
            "test_resources/output/random-jpeg.jpeg",
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertEquals("random.jpeg", stringMemory.getImage(""));
    features.saveImage();

    assertTrue(new File("test_resources/output/random-jpeg.jpeg").exists());
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      stringMemory.getImage("");
    });
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      imageMemory.getImage("");
    });


    initialiseController(
            "",
            false,
            null,
            "test_resources/output/random-jpeg.jpeg",
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertEquals("random-jpeg.jpeg", stringMemory.getImage(""));
  }

  @Test
  public void loadAndSaveFromJPG() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    StringBuilder output = new StringBuilder();

    initialiseController(
            "",
            false,
            null,
            "test_resources/input/random.jpg",
            "test_resources/output/random-jpg.jpg",
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertEquals("random.jpg", stringMemory.getImage(""));
    features.saveImage();
    assertTrue(new File("test_resources/output/random-jpg.jpg").exists());
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      stringMemory.getImage("");
    });
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      imageMemory.getImage("");
    });

    initialiseController(
            "",
            false,
            null,
            "test_resources/output/random-jpg.jpg",
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage();
    assertEquals("random-jpg.jpg", stringMemory.getImage(""));
  }


  private void initialiseController(String input, boolean confirmSplitView,
                                    Integer sliderInput,
                                    String interactiveImageLoadPathInput,
                                    String interactiveImageSavePathInput,
                                    ImageProcessingRequest.Levels interactiveThreeLevelInput,
                                    ImageMemory<String> stringMemory,
                                    ImageMemory<Image> imageMemory,
                                    StringBuilder log) {
    features = new GUIImageProcessorController(
            new MockInput(
                    new StringReader(input),
                    confirmSplitView,
                    sliderInput,
                    interactiveImageLoadPathInput,
                    interactiveImageSavePathInput,
                    interactiveThreeLevelInput),
            new MockOutput(log),
            new FileImageProcessingService(imageMemory),
            stringMemory);
  }

  // TODO: remove it
  private void getExpectedImage(Image image) {
    int[][] array = new int[image.getHeight()][image.getWidth()];
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        Pixel pixel = image.getPixel(i, j);
        int red = pixel.getRed();
        int green = pixel.getGreen();
        int blue = pixel.getBlue();
        array[i][j] =
                (red << 16) | (green << 8) | blue;
      }
    }
    System.out.println(Arrays.deepToString(array));
  }
}
