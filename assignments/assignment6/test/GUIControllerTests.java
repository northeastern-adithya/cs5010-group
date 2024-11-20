import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import controller.Features;
import controller.GUIImageProcessorController;
import controller.services.FileImageProcessingService;
import exception.ImageProcessorException;
import factories.Factory;
import model.enumeration.UserCommand;
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

  private static final String INITIAL_IMAGE_NAME = "initialImage";
  private Features features;

  /**
   * Cleans up output directory after test is run.
   */
  @AfterClass
  public static void cleanUp() {
    TestUtils.cleanUp("test_resources/output");
  }

  // LOAD AND SAVE TESTS
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
    assertTrue(output.toString().contains(
            randomImage.histogram().toString()));
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
    assertTrue(output.toString().contains(
            randomImage.histogram().toString()));
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
    assertTrue(output.toString().contains(
            randomImage.histogram().toString()));
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
    assertTrue(output.toString().contains(
            randomImage.histogram().toString()));
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


  // RED COMPONENT TESTS
  @Test
  public void testCreateRedComponentWithPureRedImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.redImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.redComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16777215, 16777215},
                    {16777215, 16777215}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.RED_COMPONENT)));

    assertTrue(output.toString().contains(
            expectedImage.toString()
    ));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateRedComponentWithPureBlueImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.blueImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.redComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.RED_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateRedComponentWithPureGreenImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.greenImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.redComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.RED_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateRedComponentWithPureGreyImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.greyImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.redComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {8421504, 8421504},
                    {8421504, 8421504}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.RED_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateRedComponentWithPureWhiteImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.whiteImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.redComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16777215, 16777215},
                    {16777215, 16777215}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.RED_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateRedComponentWithPureBlackImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.blackImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.redComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.RED_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateRedComponentWithRandomImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.redComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16777215, 0},
                    {0, 8421504}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.RED_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  // GREEN COMPONENT TESTS
  @Test
  public void testCreateGreenComponentWithPureGreenImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.greenImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.greenComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16777215, 16777215},
                    {16777215, 16777215}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.GREEN_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateGreenComponentWithPureRedImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.redImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.greenComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.GREEN_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateGreenComponentWithPureBlueImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.blueImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.greenComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.GREEN_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateGreenComponentWithPureGreyImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.greyImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.greenComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {8421504, 8421504},
                    {8421504, 8421504}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.GREEN_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateGreenComponentWithPureWhiteImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.whiteImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.greenComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16777215, 16777215},
                    {16777215, 16777215}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.GREEN_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateGreenComponentWithPureBlackImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.blackImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.greenComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.GREEN_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateGreenComponentWithRandomImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.greenComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {16777215, 8421504}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.GREEN_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }


  // BLUE COMPONENT TESTS
  @Test
  public void testCreateBlueComponentWithPureBlueImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.blueImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blueComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16777215, 16777215},
                    {16777215, 16777215}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUE_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateBlueComponentWithPureRedImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.redImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blueComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUE_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateBlueComponentWithPureGreenImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.greenImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blueComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUE_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateBlueComponentWithPureGreyImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.greyImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blueComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {8421504, 8421504},
                    {8421504, 8421504}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUE_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateBlueComponentWithPureWhiteImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.whiteImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blueComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16777215, 16777215},
                    {16777215, 16777215}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUE_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateBlueComponentWithPureBlackImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.blackImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blueComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUE_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testCreateBlueComponentWithRandomImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blueComponent();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 16777215},
                    {0, 8421504}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUE_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
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

  // BLUR TESTS
  @Test
  public void testBlurWithPureRedImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.redImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blurImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {9371648, 9371648},
            {9371648, 9371648}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUR)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testBlurWithPureGreenImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.greenImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blurImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {36608, 36608},
            {36608, 36608}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUR)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testBlurWithPureBlueImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.blueImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blurImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {143, 143},
            {143, 143}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUR)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testBlurWithPureGreyImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.greyImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blurImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {4737096, 4737096},
            {4737096, 4737096}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUR)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testBlurWithPureWhiteImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.whiteImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blurImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {9408399, 9408399},
            {9408399, 9408399}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUR)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testBlurWithPureBlackImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.blackImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blurImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUR)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testBlurWithRandomImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blurImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {4663079, 3088207},
            {3100447, 3096383}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.BLUR)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }


  // SHARPEN TESTS
  @Test
  public void testSharpenWithPureRedImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.redImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {16711680, 16711680},
            {16711680, 16711680}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.SHARPEN)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testSharpenWithPureGreenImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.greenImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {65280, 65280},
            {65280, 65280}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.SHARPEN)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testSharpenWithPureBlueImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.blueImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {255, 255},
            {255, 255}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.SHARPEN)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testSharpenWithPureGreyImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.greyImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {14737632, 14737632},
            {14737632, 14737632}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.SHARPEN)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testSharpenWithPureWhiteImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.whiteImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.SHARPEN)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testSharpenWithPureBlackImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.blackImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.SHARPEN)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testSharpenWithRandomImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {16736095, 6250495},
            {6291295, 12566463}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.SHARPEN)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  // COMPRESSION TESTS
  @Test
  public void testCompressionWithZeroPercentage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            0,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {14618624, 2101487, 61200},
            {6328432, 16715776, 255}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.COMPRESS)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testCompressionWithTwentyFivePercentage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            25,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {16723968, 4206767, 61200},
            {4206767, 16723968, 255}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.COMPRESS)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testCompressionWithFiftyPercentage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            50,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {6225920, 175, 48976},
            {175, 6225920, 80}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.COMPRESS)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testCompressionWithSeventyFivePercentage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            75,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {0, 96, 0},
            {96, 0, 0}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.COMPRESS)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testCompressionWithHundredPercentage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            100,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
            {0, 0, 0},
            {0, 0, 0}
    }));
    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.COMPRESS)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
  }

  @Test
  public void testCompressionWithPNGToPNGAndPPMFormats() throws
          IOException {
    for (int compression = 0; compression <= 100; compression++) {
      ImageMemory<Image> imageMemory = initialiseImageMemory();
      ImageMemory<String> stringMemory = initialiseStringMemory();
      StringBuilder output = new StringBuilder();
      initialiseController(
              "",
              true,
              compression,
              "test_resources/input/random.png",
              String.format(
                      "test_resources/output/compressedImage%s.png",
                      compression),
              null,
              stringMemory,
              imageMemory,
              output);

      features.loadImage();
      features.compressImage();
      features.saveImage();

      initialiseController(
              "",
              true,
              compression,
              "test_resources/input/random.png",
              String.format(
                      "test_resources/output/compressedImage%s.ppm",
                      compression),
              null,
              stringMemory,
              imageMemory,
              output);

      features.loadImage();
      features.compressImage();
      features.saveImage();


      Path currentFilePath = Paths.get(
              "test_resources/input/random.png"
      );
      Path compressedPngPath = Paths.get(
              String.format("test_resources/output/compressedImage%s.png",
                      compression)
      );


      Path compressedPpmPath = Paths.get(
              String.format("test_resources/output/compressedImage%s.ppm",
                      compression)
      );

      assertTrue(Files.exists(currentFilePath));
      assertTrue(Files.exists(compressedPngPath));
      assertTrue(Files.exists(compressedPpmPath));

      assertTrue(Files.size(compressedPngPath) <= Files.size(currentFilePath));
      assertTrue(Files.size(compressedPpmPath) <= Files.size(currentFilePath));
    }
  }

  @Test
  public void testCompressionWithJPGToPNGAndPPMFormats() throws
          IOException {
    for (int compression = 0; compression <= 100; compression++) {
      ImageMemory<Image> imageMemory = initialiseImageMemory();
      ImageMemory<String> stringMemory = initialiseStringMemory();
      StringBuilder output = new StringBuilder();
      initialiseController(
              "",
              true,
              compression,
              "test_resources/input/random.jpg",
              String.format(
                      "test_resources/output/compressedImage%s.png",
                      compression),
              null,
              stringMemory,
              imageMemory,
              output);

      features.loadImage();
      features.compressImage();
      features.saveImage();

      initialiseController(
              "",
              true,
              compression,
              "test_resources/input/random.jpg",
              String.format(
                      "test_resources/output/compressedImage%s.ppm",
                      compression),
              null,
              stringMemory,
              imageMemory,
              output);

      features.loadImage();
      features.compressImage();
      features.saveImage();


      Path currentFilePath = Paths.get(
              "test_resources/input/random.jpg"
      );
      Path compressedPngPath = Paths.get(
              String.format("test_resources/output/compressedImage%s.png",
                      compression)
      );


      Path compressedPpmPath = Paths.get(
              String.format("test_resources/output/compressedImage%s.ppm",
                      compression)
      );

      assertTrue(Files.exists(currentFilePath));
      assertTrue(Files.exists(compressedPngPath));
      assertTrue(Files.exists(compressedPpmPath));

      assertTrue(Files.size(compressedPngPath) <= Files.size(currentFilePath));
      assertTrue(Files.size(compressedPpmPath) <= Files.size(currentFilePath));
    }
  }

  @Test
  public void testCompressionWithPPMToPPM() throws
          IOException {
    for (int compression = 0; compression <= 100; compression++) {
      ImageMemory<Image> imageMemory = initialiseImageMemory();
      ImageMemory<String> stringMemory = initialiseStringMemory();
      StringBuilder output = new StringBuilder();
      initialiseController(
              "",
              true,
              compression,
              "test_resources/input/random.ppm",
              String.format(
                      "test_resources/output/compressedImage%s.ppm",
                      compression),
              null,
              stringMemory,
              imageMemory,
              output);

      features.loadImage();
      features.compressImage();
      features.saveImage();

      Path currentFilePath = Paths.get(
              "test_resources/input/random.ppm"
      );
      Path compressedPpmPath = Paths.get(
              String.format("test_resources/output/compressedImage%s.ppm",
                      compression)
      );
      assertTrue(Files.exists(currentFilePath));
      assertTrue(Files.exists(compressedPpmPath));
      assertTrue(Files.size(compressedPpmPath) <= Files.size(currentFilePath));
    }
  }


  @Test
  public void compressionWithNegativeCompressionPercentage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            -1,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage();
    assertTrue(output.toString().contains(
            "Invalid compression percentage"));

  }

  @Test
  public void compressionWithGreaterThanHundredCompressionPercentage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            101,
            null,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage();
    assertTrue(output.toString().contains(
            "Invalid compression percentage"));
  }

  private ImageMemory<Image> initialiseImageMemory() {
    return new HashMapMemory();
  }

  private ImageMemory<String> initialiseStringMemory() {
    return new StringMemory();
  }

  private String createDestinationImageName(String imageName,
                                            UserCommand command) {
    return command.getCommand() + "_" + imageName;
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
