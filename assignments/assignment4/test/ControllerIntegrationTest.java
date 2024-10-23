import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import controller.ImageProcessorController;
import exception.ImageProcessorException;
import factories.ControllerFactory;
import factories.ImageFactory;
import factories.ImageMemoryFactory;
import factories.ImageProcessingServiceFactory;
import factories.PixelFactory;
import factories.UserInputFactory;
import factories.UserOutputFactory;
import model.PixelType;
import model.memory.ImageMemory;
import model.pixels.Pixel;
import model.visual.Image;
import services.ImageProcessingService;
import view.input.UserInput;
import view.output.UserOutput;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ControllerIntegrationTest {

  private static final String INITIAL_IMAGE_NAME = "initialImage";
  private ImageProcessorController controller;
  private UserInput userInput;
  private UserOutput userOutput;
  private ImageProcessingService processingService;
  private ImageMemory imageMemory;

  private void initialiseController(String input, StringBuilder output, Image initialImage) {
    userInput = UserInputFactory.createUserInput(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    userOutput = UserOutputFactory.createUserOutput(output);
    imageMemory = ImageMemoryFactory.getImageMemory();
    processingService = ImageProcessingServiceFactory.createImageProcessor(imageMemory);
    controller = ControllerFactory.createController(userInput, userOutput, processingService);
    imageMemory.addImage(INITIAL_IMAGE_NAME, initialImage);
  }

  // RED COMPONENT TESTS
  @Test
  public void testCreateRedComponentWithPureRedImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("red-component %s redComponent", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created red component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("redComponent"));
  }

  @Test
  public void testCreateRedComponentWithPureBlueImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("red-component %s redComponent", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created red component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("redComponent"));
  }

  @Test
  public void testCreateRedComponentWithPureGreenImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("red-component %s redComponent", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created red component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("redComponent"));
  }

  @Test
  public void testCreateRedComponentWithPureGreyImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("red-component %s redComponent", INITIAL_IMAGE_NAME), output, greyImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created red component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {8421504, 8421504},
            {8421504, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("redComponent"));
  }

  @Test
  public void testCreateRedComponentWithPureWhiteImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("red-component %s redComponent", INITIAL_IMAGE_NAME), output, whiteImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created red component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("redComponent"));
  }

  @Test
  public void testCreateRedComponentWithPureBlackImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("red-component %s redComponent", INITIAL_IMAGE_NAME), output, blackImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created red component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("redComponent"));
  }

  @Test
  public void testRedComponentCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("red-component invalidImageName redComponent", output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testRedComponentCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("red-component %s", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testCreateRedComponentWithRandomImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("red-component %s redComponent", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created red component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 0},
            {0, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("redComponent"));
  }


  // GREEN COMPONENT TESTS
  @Test
  public void testCreateGreenComponentWithPureRedImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("green-component %s greenComponent", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created green component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("greenComponent"));
  }

  @Test
  public void testCreateGreenComponentWithPureBlueImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("green-component %s greenComponent", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created green component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("greenComponent"));
  }

  @Test
  public void testCreateGreenComponentWithPureGreenImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("green-component %s greenComponent", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created green component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("greenComponent"));
  }

  @Test
  public void testCreateGreenComponentWithPureGreyImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("green-component %s greenComponent", INITIAL_IMAGE_NAME), output, greyImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created green component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {8421504, 8421504},
            {8421504, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("greenComponent"));
  }

  @Test
  public void testCreateGreenComponentWithPureWhiteImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("green-component %s greenComponent", INITIAL_IMAGE_NAME), output, whiteImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created green component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("greenComponent"));
  }

  @Test
  public void testCreateGreenComponentWithPureBlackImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("green-component %s greenComponent", INITIAL_IMAGE_NAME), output, blackImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created green component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("greenComponent"));
  }

  @Test
  public void testGreenComponentCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("green-component invalidImageName greenComponent", output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testGreenComponentCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("green-component %s", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testCreateGreenComponentWithRandomImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("green-component %s greenComponent", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created green component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {16777215, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("greenComponent"));
  }


  // BLUE COMPONENT TESTS
  @Test
  public void testCreateBlueComponentWithPureRedImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blue-component %s blueComponent", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created blue component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("blueComponent"));
  }

  @Test
  public void testCreateBlueComponentWithPureBlueImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blue-component %s blueComponent", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created blue component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("blueComponent"));
  }

  @Test
  public void testCreateBlueComponentWithPureGreenImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blue-component %s blueComponent", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created blue component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("blueComponent"));
  }

  @Test
  public void testCreateBlueComponentWithPureGreyImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blue-component %s blueComponent", INITIAL_IMAGE_NAME), output, greyImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created blue component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {8421504, 8421504},
            {8421504, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("blueComponent"));
  }

  @Test
  public void testCreateBlueComponentWithPureWhiteImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blue-component %s blueComponent", INITIAL_IMAGE_NAME), output, whiteImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created blue component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("blueComponent"));
  }

  @Test
  public void testCreateBlueComponentWithPureBlackImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blue-component %s blueComponent", INITIAL_IMAGE_NAME), output, blackImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created blue component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("blueComponent"));
  }

  @Test
  public void testBlueComponentCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("blue-component invalidImageName blueComponent", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testBlueComponentCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blue-component %s", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testCreateBlueComponentWithRandomImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blue-component %s blueComponent", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created blue component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 16777215},
            {0, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("blueComponent"));
  }



  private Image redImage() {
    int[][] redArray = new int[][]{
            {16711680, 16711680},
            {16711680, 16711680}
    };
    return ImageFactory.createImage(createPixels(redArray));
  }

  private Image blueImage() {
    int[][] blueArray = new int[][]{
            {255, 255},
            {255, 255}
    };
    return ImageFactory.createImage(createPixels(blueArray));
  }

  private Image greenImage() {
    int[][] greenArray = new int[][]{
            {65280, 65280},
            {65280, 65280}
    };
    return ImageFactory.createImage(createPixels(greenArray));
  }

  private Image greyImage() {
    int[][] greyArray = new int[][]{
            {8421504, 8421504},
            {8421504, 8421504}
    };
    return ImageFactory.createImage(createPixels(greyArray));
  }

  private Image whiteImage() {
    int[][] whiteArray = new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    };
    return ImageFactory.createImage(createPixels(whiteArray));
  }

  private Image blackImage() {
    int[][] blackArray = new int[][]{
            {0, 0},
            {0, 0}
    };
    return ImageFactory.createImage(createPixels(blackArray));
  }

  private Image yellowImage() {
    int[][] yellowArray = new int[][]{
            {16776960, 16776960},
            {16776960, 16776960}
    };
    return ImageFactory.createImage(createPixels(yellowArray));
  }

  private Image randomImage() {
    int[][] randomArray = new int[][]{
            {16711680, 255},
            {65280, 8421504}
    };
    return ImageFactory.createImage(createPixels(randomArray));
  }

  private Pixel[][] createPixels(int[][] array) {
    Pixel[][] pixels = new Pixel[array.length][array[0].length];
    for (int i = 0; i < array.length; i++) {
      for (int j = 0; j < array[0].length; j++) {
        pixels[i][j] = PixelFactory.createPixel(array[i][j], PixelType.RGB);
      }
    }
    return pixels;
  }
}
