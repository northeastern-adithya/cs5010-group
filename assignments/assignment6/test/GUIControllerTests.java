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
import static org.junit.Assert.assertFalse;
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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources.txt");
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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("invalidPath.png");
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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources/input/random.png");
    assertEquals(randomImage, imageMemory.getImage("random.png"));
    assertTrue(output.toString().contains(
            randomImage.toString()));
    assertTrue(output.toString().contains(
            randomImage.histogram().toString()));
    assertEquals("random.png", stringMemory.getImage(""));
    features.saveImage("test_resources/output/random-png1.png");

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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources/output/random-png1.png");
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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources/input/random.ppm");
    assertEquals(randomImage, imageMemory.getImage("random.ppm"));
    assertTrue(output.toString().contains(
            randomImage.toString()));
    assertTrue(output.toString().contains(
            randomImage.histogram().toString()));
    assertEquals("random.ppm", stringMemory.getImage(""));
    features.saveImage("test_resources/output/random-ppm.ppm");

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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources/output/random-ppm.ppm");
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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources/input/random.ppm");
    assertEquals(randomImage, imageMemory.getImage("random.ppm"));
    assertTrue(output.toString().contains(
            randomImage.toString()));
    assertTrue(output.toString().contains(
            randomImage.histogram().toString()));
    assertEquals("random.ppm", stringMemory.getImage(""));
    features.saveImage("test_resources/output/random-ppm.png");

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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources/output/random-ppm.png");
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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources/input/random.png");
    assertEquals(randomImage, imageMemory.getImage("random.png"));
    assertTrue(output.toString().contains(
            randomImage.toString()));
    assertTrue(output.toString().contains(
            randomImage.histogram().toString()));
    assertEquals("random.png", stringMemory.getImage(""));
    features.saveImage("test_resources/output/random-png.ppm");

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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources/output/random-png.ppm");
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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources/input/random.jpeg");
    assertEquals("random.jpeg", stringMemory.getImage(""));
    features.saveImage("test_resources/output/random-jpeg.jpeg");

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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources/output/random-jpeg.jpeg");
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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources/input/random.jpg");
    assertEquals("random.jpg", stringMemory.getImage(""));
    features.saveImage("test_resources/output/random-jpg.jpg");
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
            null,
            stringMemory,
            imageMemory,
            output);
    features.loadImage("test_resources/output/random-jpg.jpg");
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

  @Test
  public void testVerticalFlipWithNoImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.verticalFlip();

    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
              UserCommand.VERTICAL_FLIP));
    });
    assertTrue(output.toString().contains(
            "No image loaded"));
  }

  @Test
  public void testVerticalFlipWithRandomImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.randomImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.verticalFlip();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {65280, 8421504},
                    {16711680, 255}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.VERTICAL_FLIP)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testVerticalFlipWithRandomImage_ConsistentImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.redImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.verticalFlip();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 16711680},
                    {16711680, 16711680}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.VERTICAL_FLIP)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testVerticalFlipWithRandomImage_BlackImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.blackImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.verticalFlip();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.VERTICAL_FLIP)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testVerticalFlipWithRandomImage_WhiteImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.whiteImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.verticalFlip();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16777215, 16777215},
                    {16777215, 16777215}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.VERTICAL_FLIP)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testHorizontalFlipWithNoImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.horizontalFlip();
    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
              UserCommand.HORIZONTAL_FLIP));
    });
    assertTrue(output.toString().contains(
            "No image loaded"));
  }

  @Test
  public void testHorizontalFlipWithRandomImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.randomImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.horizontalFlip();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {255, 16711680},
                    {8421504, 65280}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.HORIZONTAL_FLIP)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testHorizontalFlipWithRandomImage_ConsistentImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.blueImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.horizontalFlip();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {255, 255},
                    {255, 255}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.HORIZONTAL_FLIP)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testHorizontalFlipWithRandomImage_BlackImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.blackImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.horizontalFlip();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.HORIZONTAL_FLIP)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testHorizontalFlipWithRandomImage_WhiteImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.whiteImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.horizontalFlip();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16777215, 16777215},
                    {16777215, 16777215}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.HORIZONTAL_FLIP)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testGetLumaWithNoImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.getLuma();

    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
              UserCommand.LUMA_COMPONENT));
    });
    assertTrue(output.toString().contains(
            "No image loaded"));
  }

  @Test
  public void testGetLumaWithRandomImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.randomImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.getLuma();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {3552822, 1184274},
                    {11974326, 8421504}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.LUMA_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testGetLumaWithRandomImage_BlackImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.blackImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.getLuma();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.LUMA_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testGetLumaWithRandomImage_WhiteImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.whiteImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.getLuma();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711422, 16711422},
                    {16711422, 16711422}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.LUMA_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testGetLumaWithRandomImage_RedImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.redImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.getLuma();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {3552822, 3552822},
                    {3552822, 3552822}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.LUMA_COMPONENT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testGetSepiaWithNomImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.applySepia();

    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
              UserCommand.SEPIA));
    });
    assertTrue(output.toString().contains(
            "No image loaded"));
  }

  @Test
  public void testGetSepiaWithRandomImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.randomImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.applySepia();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {6576197, 3156513},
                    {12889736, 11311479}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.SEPIA)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testGetSepiaWithBlackImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.blackImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.applySepia();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.SEPIA)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testGetSepiaWithWhiteImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.whiteImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.applySepia();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16777198, 16777198},
                    {16777198, 16777198}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.SEPIA)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testGetSepiaWithBlueImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.blueImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.applySepia();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {3156513, 3156513},
                    {3156513, 3156513}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.SEPIA)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testColorCorrectionWithNoImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.colorCorrect();

    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
              UserCommand.COLOR_CORRECT));
    });
    assertTrue(output.toString().contains(
            "No image loaded"));
  }

  @Test
  public void testColorCorrectionWithRandomImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.randomImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.colorCorrect();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255},
                    {65280, 8421504}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.COLOR_CORRECT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testColorCorrectionWithBlackImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.blackImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.colorCorrect();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {0, 0}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.COLOR_CORRECT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testColorCorrectionWithRedImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.redImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.colorCorrect();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 16711680},
                    {16711680, 16711680}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.COLOR_CORRECT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testColorCorrectionWithUniformImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.uniformOffsetImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.colorCorrect();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {6645093, 9934743},
                    {9934743, 16514043}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.COLOR_CORRECT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testColorCorrectionPreservesNaturalGreyImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.randomGreyscaleImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.colorCorrect();

    // color correction does not impact natural greys
    Image expectedImage = TestUtils.randomGreyscaleImage();

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.COLOR_CORRECT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testColorCorrectionWithEdgeDistribution() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.randomImageWithEdgeDistributedPixels();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.colorCorrect();

    // color correction does not impact natural greys
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {688382, 8421504, 688382}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.COLOR_CORRECT)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }


  @Test
  public void testLevelsAdjustWithNoImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    ImageMemory<String> stringMemory = initialiseStringMemory();
    StringBuilder output = new StringBuilder();
    ImageProcessingRequest.Levels levels =
            new ImageProcessingRequest.Levels(20, 100, 255);
    initialiseController(
            "",
            true,
            null,
            levels,
            stringMemory,
            imageMemory,
            output);
    features.levelsAdjust();

    assertThrows(ImageProcessorException.NotFoundException.class, () -> {
      imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
              UserCommand.LEVELS_ADJUST));
    });
    assertTrue(output.toString().contains(
            "No image loaded"));
  }

  @Test
  public void testLevelsAdjustWithRandomImage() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    Image randomImage = TestUtils.randomImage();
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    ImageProcessingRequest.Levels levels =
            new ImageProcessingRequest.Levels(20, 100, 255);
    initialiseController(
            "",
            true,
            null,
            levels,
            stringMemory,
            imageMemory,
            output);
    features.levelsAdjust();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255},
                    {65280, 10724259}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.LEVELS_ADJUST)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testLevelsAdjustWithRandomImage_ClampingAt0() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();

    Image randomImage = Factory.createImage(TestUtils.createPixels(new int[][]{
            {657930, 16777215},
            {1315860, 8421504}
    }));

    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    ImageProcessingRequest.Levels levels =
            new ImageProcessingRequest.Levels(10, 20, 50);
    initialiseController(
            "",
            true,
            null,
            levels,
            stringMemory,
            imageMemory,
            output);
    features.levelsAdjust();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 0},
                    {8421504, 0}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.LEVELS_ADJUST)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testLevelsAdjustWithRandomImage_ClampingAt255() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();

    Image randomImage = Factory.createImage(TestUtils.createPixels(new int[][]{
            {657930, 13816530},
            {6645093, 8421504}
    }));
    ;

    getExpectedImage(randomImage);

    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    ImageProcessingRequest.Levels levels =
            new ImageProcessingRequest.Levels(100, 200, 210);
    initialiseController(
            "",
            true,
            null,
            levels,
            stringMemory,
            imageMemory,
            output);
    features.levelsAdjust();

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16777215, 16777215},
                    {0, 0}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.LEVELS_ADJUST)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testLevelsAdjustWithRandomImage_AdjacentPoints() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();

    Image randomImage = Factory.createImage(TestUtils.createPixels(new int[][]{
            {657930, 13816530},
            {6645093, 8421504}
    }));
    ;
    ;
    getExpectedImage(randomImage);
    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    ImageProcessingRequest.Levels levels =
            new ImageProcessingRequest.Levels(128, 129, 130);
    initialiseController(
            "",
            true,
            null,
            levels,
            stringMemory,
            imageMemory,
            output);
    features.levelsAdjust();

    getExpectedImage(imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
            UserCommand.LEVELS_ADJUST)));

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 16777215},
                    {0, 0}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.LEVELS_ADJUST)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  @Test
  public void testLevelsAdjustWithRandomImage_PointsMatchCurve() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();

    Image randomImage = Factory.createImage(TestUtils.createPixels(new int[][]{
            {657930, 8421504},
            {15461355, 16777215}
    }));

    imageMemory.addImage(INITIAL_IMAGE_NAME, randomImage);
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    ImageProcessingRequest.Levels levels =
            new ImageProcessingRequest.Levels(10, 128, 235);
    initialiseController(
            "",
            true,
            null,
            levels,
            stringMemory,
            imageMemory,
            output);
    features.levelsAdjust();

    getExpectedImage(imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
            UserCommand.LEVELS_ADJUST)));

    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {0, 8421504},
                    {16777215, 16777215}
            }));

    assertEquals(expectedImage,
            imageMemory.getImage(createDestinationImageName(INITIAL_IMAGE_NAME,
                    UserCommand.LEVELS_ADJUST)));
    assertTrue(output.toString().contains(
            expectedImage.toString()));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));
  }

  private void initialiseController(String input, boolean confirmSplitView,
                                    Integer sliderInput,
                                    ImageProcessingRequest.Levels interactiveThreeLevelInput,
                                    ImageMemory<String> stringMemory,
                                    ImageMemory<Image> imageMemory,
                                    StringBuilder log) {
    features = new GUIImageProcessorController(
            new MockGUIView(
                    new StringReader(input),
                    confirmSplitView,
                    sliderInput,
                    interactiveThreeLevelInput, log),
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
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage(0);
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
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage(25);
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
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage(50);
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
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage(75);
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
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage(100);
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
              null,
              null,
              stringMemory,
              imageMemory,
              output);

      features.loadImage("test_resources/input/random.png");
      features.compressImage(compression);
      features.saveImage(String.format(
              "test_resources/output/compressedImage%s.png",
              compression));

      initialiseController(
              "",
              true,
              null,
              null,
              stringMemory,
              imageMemory,
              output);

      features.loadImage("test_resources/input/random.png");
      features.compressImage(compression);
      features.saveImage(String.format(
              "test_resources/output/compressedImage%s.ppm",
              compression));


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
              null,
              null,
              stringMemory,
              imageMemory,
              output);

      features.loadImage("test_resources/input/random.jpg");
      features.compressImage(compression);
      features.saveImage(String.format(
              "test_resources/output/compressedImage%s.png",
              compression));

      initialiseController(
              "",
              true,
              null,
              null,
              stringMemory,
              imageMemory,
              output);

      features.loadImage("test_resources/input/random.jpg");
      features.compressImage(compression);
      features.saveImage(String.format(
              "test_resources/output/compressedImage%s.ppm",
              compression));


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
              null,
              null,
              stringMemory,
              imageMemory,
              output);

      features.loadImage("test_resources/input/random.ppm");
      features.compressImage(compression);
      features.saveImage(String.format(
              "test_resources/output/compressedImage%s.ppm",
              compression));

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
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage(-1);
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
            null,
            null,
            stringMemory,
            imageMemory,
            output);
    features.compressImage(101);
    assertTrue(output.toString().contains(
            "Invalid compression percentage"));
  }


  // SPLIT VIEW TESTS
  @Test
  public void testBlurWithZeroPercentageSplitView() throws
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
            stringMemory,
            imageMemory,
            output);
    features.blurImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {6230063, 4663127, 999231},
                    {6234159, 6233935, 2039631}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedImage.toString()));

    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255, 65280},
                    {8421504, 16711680, 255}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
  }

  @Test
  public void testBlurWithNegativePercentageSplitView() throws
          ImageProcessorException {

    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            -1,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blurImage();
    assertTrue(output.toString().contains(
            "The percentage must be between 0 and 100"));
  }

  @Test
  public void testBlurWithHundredPercentageSplitView() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            100,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blurImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255, 65280},
                    {8421504, 16711680, 255}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));

    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {6230063, 4663127, 999231},
                    {6234159, 6233935, 2039631}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
  }

  @Test
  public void testBlurWithGreaterThanHundredPercentageSplitView() throws
          ImageProcessorException {

    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            101,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blurImage();
    assertTrue(output.toString().contains(
            "The percentage must be between 0 and 100"));
  }

  @Test
  public void testBlurWithThirtyPercentageSplitView() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            30,
            null,
            stringMemory,
            imageMemory,
            output);
    features.blurImage();
    Image expectedFinalImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {6230063, 4663127, 999231},
                    {6234159, 6233935, 2039631}
            }));
    assertEquals(expectedFinalImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedFinalImage.toString()));

    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255, 65280},
                    {8421504, 16711680, 255}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
  }

  @Test
  public void testBlurWithFiftyPercentageSplitView() throws
          ImageProcessorException {
    {
      ImageMemory<Image> imageMemory = initialiseImageMemory();
      imageMemory.addImage(INITIAL_IMAGE_NAME,
              TestUtils.randomRectangleImage());
      ImageMemory<String> stringMemory = initialiseStringMemory();
      stringMemory.addImage(INITIAL_IMAGE_NAME, null);
      StringBuilder output = new StringBuilder();
      initialiseController(
              "",
              false,
              50,
              null,
              stringMemory,
              imageMemory,
              output);
      features.blurImage();
      Image expectedFinalImage =
              Factory.createImage(TestUtils.createPixels(new int[][]{
                      {16711680, 255, 65280},
                      {8421504, 16711680, 255}
              }));
      assertEquals(expectedFinalImage,
              imageMemory.getImage(stringMemory.getImage("")));
      assertTrue(output.toString().contains(
              expectedFinalImage.toString()));
      Image expectedSplitViewImage =
              Factory.createImage(TestUtils.createPixels(new int[][]{
                      {6230063, 255, 65280},
                      {6234159, 16711680, 255}
              }));
      assertTrue(output.toString().contains(
              expectedSplitViewImage.toString()));
      assertFalse(output.toString().contains(
              expectedSplitViewImage.histogram().toString()));
    }
  }

  @Test
  public void testBlurWithSeventyFivePercentageSplitView() throws
          ImageProcessorException {
    {
      ImageMemory<Image> imageMemory = initialiseImageMemory();
      imageMemory.addImage(INITIAL_IMAGE_NAME,
              TestUtils.randomRectangleImage());
      ImageMemory<String> stringMemory = initialiseStringMemory();
      stringMemory.addImage(INITIAL_IMAGE_NAME, null);
      StringBuilder output = new StringBuilder();
      initialiseController(
              "",
              true,
              75,
              null,
              stringMemory,
              imageMemory,
              output);
      features.blurImage();
      Image expectedFinalImage =
              Factory.createImage(TestUtils.createPixels(new int[][]{
                      {6230063, 4663127, 999231},
                      {6234159, 6233935, 2039631}
              }));
      assertEquals(expectedFinalImage,
              imageMemory.getImage(stringMemory.getImage("")));
      assertTrue(output.toString().contains(
              expectedFinalImage.toString()));

      Image expectedSplitViewImage =
              Factory.createImage(TestUtils.createPixels(new int[][]{
                      {6230063, 4663127, 65280},
                      {6234159, 6233935, 255}
              }));
      assertTrue(output.toString().contains(
              expectedSplitViewImage.toString()));
      assertFalse(output.toString().contains(
              expectedSplitViewImage.histogram().toString()));
    }
  }

  // SHARPEN SPLIT VIEW TESTS
  @Test
  public void testSharpenWithZeroPercentageSplitView() throws
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
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711743, 10444799, 1044335},
                    {16736415, 16736159, 995327}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedImage.toString()));

    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255, 65280},
                    {8421504, 16711680, 255}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
  }

  @Test
  public void testSharpenWithNegativePercentageSplitView() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            -1,
            null,
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    assertTrue(output.toString().contains(
            "The percentage must be between 0 and 100"));
  }

  @Test
  public void testSharpenWithHundredPercentageSplitView() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            100,
            null,
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255, 65280},
                    {8421504, 16711680, 255}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));

    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711743, 10444799, 1044335},
                    {16736415, 16736159, 995327}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
  }

  @Test
  public void testSharpenWithGreaterThanHundredPercentageSplitView() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            101,
            null,
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    assertTrue(output.toString().contains(
            "The percentage must be between 0 and 100"));
  }

  @Test
  public void testSharpenWithThirtyPercentageSplitView() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            30,
            null,
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    Image expectedFinalImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711743, 10444799, 1044335},
                    {16736415, 16736159, 995327}
            }));
    assertEquals(expectedFinalImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedFinalImage.toString()));

    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255, 65280},
                    {8421504, 16711680, 255}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
  }

  @Test
  public void testSharpenWithFiftyPercentageSplitView() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            50,
            null,
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    Image expectedFinalImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255, 65280},
                    {8421504, 16711680, 255}
            }));
    assertEquals(expectedFinalImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedFinalImage.toString()));
    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711743, 255, 65280},
                    {16736415, 16711680, 255}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
  }

  @Test
  public void testSharpenWithSeventyFivePercentageSplitView() throws
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
            stringMemory,
            imageMemory,
            output);
    features.sharpenImage();
    Image expectedFinalImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711743, 10444799, 1044335},
                    {16736415, 16736159, 995327}
            }));
    assertEquals(expectedFinalImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedFinalImage.toString()));

    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711743, 10444799, 65280},
                    {16736415, 16736159, 255}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
  }


  // LUMA COMPONENT SPLIT VIEW TESTS
  @Test
  public void testLumaWithZeroPercentageSplitView() throws
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
            stringMemory,
            imageMemory,
            output);
    features.getLuma();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {3552822, 1184274, 11974326}, {8421504, 3552822, 1184274}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedImage.toString()));

    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255, 65280}, {8421504, 16711680, 255}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
  }

  @Test
  public void testLumaWithNegativePercentageSplitView() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            -1,
            null,
            stringMemory,
            imageMemory,
            output);
    features.getLuma();
    assertTrue(output.toString().contains(
            "The percentage must be between 0 and 100"));
  }

  @Test
  public void testLumaWithHundredPercentageSplitView() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            100,
            null,
            stringMemory,
            imageMemory,
            output);
    features.getLuma();
    Image expectedImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255, 65280}, {8421504, 16711680, 255}
            }));
    assertEquals(expectedImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedImage.histogram().toString()));

    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {3552822, 1184274, 11974326}, {8421504, 3552822, 1184274}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
  }

  @Test
  public void testLumaWithGreaterThanHundredPercentageSplitView() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            101,
            null,
            stringMemory,
            imageMemory,
            output);
    features.getLuma();
    assertTrue(output.toString().contains(
            "The percentage must be between 0 and 100"));
  }

  @Test
  public void testLumaWithThirtyPercentageSplitView() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            true,
            30,
            null,
            stringMemory,
            imageMemory,
            output);
    features.getLuma();
    Image expectedFinalImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {3552822, 1184274, 11974326}, {8421504, 3552822, 1184274}
            }));
    assertEquals(expectedFinalImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedFinalImage.toString()));

    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255, 65280}, {8421504, 16711680, 255}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
  }

  @Test
  public void testLumaWithFiftyPercentageSplitView() throws
          ImageProcessorException {
    ImageMemory<Image> imageMemory = initialiseImageMemory();
    imageMemory.addImage(INITIAL_IMAGE_NAME, TestUtils.randomRectangleImage());
    ImageMemory<String> stringMemory = initialiseStringMemory();
    stringMemory.addImage(INITIAL_IMAGE_NAME, null);
    StringBuilder output = new StringBuilder();
    initialiseController(
            "",
            false,
            50,
            null,
            stringMemory,
            imageMemory,
            output);
    features.getLuma();
    Image expectedFinalImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {16711680, 255, 65280}, {8421504, 16711680, 255}
            }));
    assertEquals(expectedFinalImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedFinalImage.toString()));
    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {3552822, 255, 65280}, {8421504, 16711680, 255}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
  }

  @Test
  public void testLumaWithSeventyFivePercentageSplitView() throws
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
            stringMemory,
            imageMemory,
            output);
    features.getLuma();
    Image expectedFinalImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {3552822, 1184274, 11974326}, {8421504, 3552822, 1184274}
            }));
    assertEquals(expectedFinalImage,
            imageMemory.getImage(stringMemory.getImage("")));
    assertTrue(output.toString().contains(
            expectedFinalImage.toString()));

    Image expectedSplitViewImage =
            Factory.createImage(TestUtils.createPixels(new int[][]{
                    {3552822, 1184274, 65280}, {8421504, 3552822, 255}
            }));
    assertTrue(output.toString().contains(
            expectedSplitViewImage.toString()));
    assertFalse(output.toString().contains(
            expectedSplitViewImage.histogram().toString()));
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
