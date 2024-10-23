import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
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
    userInput = UserInputFactory.createUserInput(new StringReader(input));
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

  @Test
  public void testCreateRedComponentWithoutImageName() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController("red-component", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
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

  @Test
  public void testCreateGreenComponentWithoutImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("green-component", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
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

  @Test
  public void testCreateBlueComponentWithoutImageName() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController("blue-component", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  // VALUE COMPONENT TESTS
  @Test
  public void testCreateValueComponentWithPureRedImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("value-component %s valueComponent", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created value component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("valueComponent"));
  }

  @Test
  public void testCreateValueComponentWithPureBlueImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("value-component %s valueComponent", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created value component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("valueComponent"));
  }

  @Test
  public void testCreateValueComponentWithPureGreenImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("value-component %s valueComponent", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created value component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("valueComponent"));
  }

  @Test
  public void testCreateValueComponentWithPureGreyImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("value-component %s valueComponent", INITIAL_IMAGE_NAME), output, greyImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created value component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {8421504, 8421504},
            {8421504, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("valueComponent"));
  }

  @Test
  public void testCreateValueComponentWithPureWhiteImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("value-component %s valueComponent", INITIAL_IMAGE_NAME), output, whiteImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created value component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("valueComponent"));
  }

  @Test
  public void testCreateValueComponentWithPureBlackImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("value-component %s valueComponent", INITIAL_IMAGE_NAME), output, blackImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created value component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("valueComponent"));
  }

  @Test
  public void testValueComponentCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("value-component invalidImageName valueComponent", output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testValueComponentCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("value-component %s", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testCreateValueComponentWithRandomImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("value-component %s valueComponent", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created value component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("valueComponent"));
  }

  @Test
  public void testCreateValueComponentWithoutImageName() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController("value-component", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  // INTENSITY COMPONENT TESTS
  @Test
  public void testCreateIntensityComponentWithPureRedImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("intensity-component %s intensityComponent", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created intensity component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {5592405, 5592405},
            {5592405, 5592405}
    }));
    assertEquals(expectedImage, imageMemory.getImage("intensityComponent"));
  }

  @Test
  public void testCreateIntensityComponentWithPureBlueImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("intensity-component %s intensityComponent", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created intensity component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {5592405, 5592405},
            {5592405, 5592405}
    }));
    assertEquals(expectedImage, imageMemory.getImage("intensityComponent"));
  }

  @Test
  public void testCreateIntensityComponentWithPureGreenImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("intensity-component %s intensityComponent", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created intensity component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {5592405, 5592405},
            {5592405, 5592405}
    }));
    assertEquals(expectedImage, imageMemory.getImage("intensityComponent"));
  }

  @Test
  public void testCreateIntensityComponentWithPureGreyImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("intensity-component %s intensityComponent", INITIAL_IMAGE_NAME), output, greyImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created intensity component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {8421504, 8421504},
            {8421504, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("intensityComponent"));
  }

  @Test
  public void testCreateIntensityComponentWithPureWhiteImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("intensity-component %s intensityComponent", INITIAL_IMAGE_NAME), output, whiteImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created intensity component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("intensityComponent"));
  }

  @Test
  public void testCreateIntensityComponentWithPureBlackImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("intensity-component %s intensityComponent", INITIAL_IMAGE_NAME), output, blackImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created intensity component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("intensityComponent"));
  }

  @Test
  public void testIntensityComponentCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("intensity-component invalidImageName intensityComponent", output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testIntensityComponentCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("intensity-component %s", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testCreateIntensityComponentWithRandomImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("intensity-component %s intensityComponent", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created intensity component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {5592405, 5592405},
            {5592405, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("intensityComponent"));
  }

  @Test
  public void testCreateIntensityComponentWithoutImageName() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController("intensity-component", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }


  // LUMA COMPONENT TESTS
  @Test
  public void testCreateLumaComponentWithPureRedImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("luma-component %s lumaComponent", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created luma component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {3552822, 3552822},
            {3552822, 3552822}
    }));
    assertEquals(expectedImage, imageMemory.getImage("lumaComponent"));
  }

  @Test
  public void testCreateLumaComponentWithPureBlueImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("luma-component %s lumaComponent", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created luma component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {1184274, 1184274},
            {1184274, 1184274}
    }));
    assertEquals(expectedImage, imageMemory.getImage("lumaComponent"));
  }

  @Test
  public void testCreateLumaComponentWithPureGreenImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("luma-component %s lumaComponent", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created luma component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {11974326, 11974326},
            {11974326, 11974326}
    }));
    assertEquals(expectedImage, imageMemory.getImage("lumaComponent"));
  }

  @Test
  public void testCreateLumaComponentWithPureGreyImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("luma-component %s lumaComponent", INITIAL_IMAGE_NAME), output, greyImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created luma component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {8421504, 8421504},
            {8421504, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("lumaComponent"));
  }

  @Test
  public void testCreateLumaComponentWithPureWhiteImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("luma-component %s lumaComponent", INITIAL_IMAGE_NAME), output, whiteImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created luma component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16711422, 16711422},
            {16711422, 16711422}
    }));
    assertEquals(expectedImage, imageMemory.getImage("lumaComponent"));
  }

  @Test
  public void testCreateLumaComponentWithPureBlackImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("luma-component %s lumaComponent", INITIAL_IMAGE_NAME), output, blackImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created luma component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("lumaComponent"));
  }

  @Test
  public void testLumaComponentCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("luma-component invalidImageName lumaComponent", output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testLumaComponentCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("luma-component %s", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testCreateLumaComponentWithRandomImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("luma-component %s lumaComponent", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully created luma component."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {3552822, 1184274},
            {11974326, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("lumaComponent"));
  }

  @Test
  public void testCreateLumaComponentWithoutImageName() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController("luma-component", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }


 // HORIZONTAL FLIP TESTS
  @Test
  public void testHorizontalFlipWithPureRedImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("horizontal-flip %s horizontalFlip", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image horizontally."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16711680, 16711680},
            {16711680, 16711680}
    }));
    assertEquals(expectedImage, imageMemory.getImage("horizontalFlip"));
  }

  @Test
  public void testHorizontalFlipWithPureBlueImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("horizontal-flip %s horizontalFlip", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image horizontally."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {255, 255},
            {255, 255}
    }));
    assertEquals(expectedImage, imageMemory.getImage("horizontalFlip"));
  }

  @Test
  public void testHorizontalFlipWithPureGreenImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("horizontal-flip %s horizontalFlip", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image horizontally."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {65280, 65280},
            {65280, 65280}
    }));
    assertEquals(expectedImage, imageMemory.getImage("horizontalFlip"));
  }

  @Test
  public void testHorizontalFlipWithPureGreyImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("horizontal-flip %s horizontalFlip", INITIAL_IMAGE_NAME), output, greyImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image horizontally."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {8421504, 8421504},
            {8421504, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("horizontalFlip"));
  }

  @Test
  public void testHorizontalFlipWithPureWhiteImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("horizontal-flip %s horizontalFlip", INITIAL_IMAGE_NAME), output, whiteImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image horizontally."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("horizontalFlip"));
  }

  @Test
  public void testHorizontalFlipWithPureBlackImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("horizontal-flip %s horizontalFlip", INITIAL_IMAGE_NAME), output, blackImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image horizontally."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("horizontalFlip"));
  }

  @Test
  public void testHorizontalFlipCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("horizontal-flip invalidImageName horizontalFlip", output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testHorizontalFlipCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("horizontal-flip %s", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testHorizontalFlipWithRandomImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("horizontal-flip %s horizontalFlip", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image horizontally."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {65280, 8421504},
            {16711680,255}
    }));
    assertEquals(expectedImage, imageMemory.getImage("horizontalFlip"));
  }

  @Test
  public void testHorizontalFlipWithoutImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("horizontal-flip", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  // VERTICAL FLIP TESTS
  @Test
  public void testVerticalFlipWithPureRedImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("vertical-flip %s verticalFlip", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image vertically."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16711680, 16711680},
            {16711680, 16711680}
    }));
    assertEquals(expectedImage, imageMemory.getImage("verticalFlip"));
  }

  @Test
  public void testVerticalFlipWithPureBlueImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("vertical-flip %s verticalFlip", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image vertically."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {255, 255},
            {255, 255}
    }));
    assertEquals(expectedImage, imageMemory.getImage("verticalFlip"));
  }

  @Test
  public void testVerticalFlipWithPureGreenImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("vertical-flip %s verticalFlip", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image vertically."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {65280, 65280},
            {65280, 65280}
    }));
    assertEquals(expectedImage, imageMemory.getImage("verticalFlip"));
  }

  @Test
  public void testVerticalFlipWithPureGreyImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("vertical-flip %s verticalFlip", INITIAL_IMAGE_NAME), output, greyImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image vertically."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {8421504, 8421504},
            {8421504, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("verticalFlip"));
  }

  @Test
  public void testVerticalFlipWithPureWhiteImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("vertical-flip %s verticalFlip", INITIAL_IMAGE_NAME), output, whiteImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image vertically."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("verticalFlip"));
  }

  @Test
  public void testVerticalFlipWithPureBlackImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("vertical-flip %s verticalFlip", INITIAL_IMAGE_NAME), output, blackImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image vertically."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("verticalFlip"));
  }

  @Test
  public void testVerticalFlipCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("vertical-flip invalidImageName verticalFlip", output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testVerticalFlipCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("vertical-flip %s", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testVerticalFlipWithRandomImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("vertical-flip %s verticalFlip", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully flipped the image vertically."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {255,16711680},
            {8421504,65280}
    }));
    assertEquals(expectedImage, imageMemory.getImage("verticalFlip"));
  }

  @Test
  public void testVerticalFlipWithoutImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("vertical-flip", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  // Brighten tests
  @Test
  public void testBrightenWithPositiveFactor() throws ImageProcessorException{
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("brighten 1 %s brighten", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully brightened the image at factor:1"));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16711937, 66047},
            {130817, 8487297}
    }));
    assertEquals(expectedImage, imageMemory.getImage("brighten"));
  }

  @Test
  public void testBrightenWithNegativeFactor() throws ImageProcessorException{
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("brighten -1 %s brighten", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully brightened the image at factor:-1"));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16646144, 254},
            {65024, 8355711}
    }));
    assertEquals(expectedImage, imageMemory.getImage("brighten"));
  }

  @Test
  public void testBrightenWithZeroFactor() throws ImageProcessorException{
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("brighten 0 %s brighten", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully brightened the image at factor:0"));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16711680, 255},
            {65280, 8421504}
    }));
    assertEquals(expectedImage, imageMemory.getImage("brighten"));
  }

  @Test
  public  void testBrightenWithInvalidFactor(){
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("brighten invalidFactor %s brighten", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid factor provided for brightening the image."));
  }

  @Test
  public void testBrightenCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("brighten 1 invalidImageName brighten", output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testBrightenCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("brighten 1 %s", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testBrightenCommandWithoutImageName(){
    StringBuilder output = new StringBuilder();
    initialiseController("brighten 1", output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }


  // RGB Split tests
  @Test
  public void testRGBSplitOfPureRedImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("rgb-split %s redImage greenImage blueImage", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully split the image into RGB components."));
    Image redImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    Image greenImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    Image blueImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(redImage, imageMemory.getImage("redImage"));
    assertEquals(greenImage, imageMemory.getImage("greenImage"));
    assertEquals(blueImage, imageMemory.getImage("blueImage"));
  }

  @Test
  public void testRGBSplitOfPureBlueImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("rgb-split %s redImage greenImage blueImage", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully split the image into RGB components."));
    Image redImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    Image greenImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    Image blueImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(redImage, imageMemory.getImage("redImage"));
    assertEquals(greenImage, imageMemory.getImage("greenImage"));
    assertEquals(blueImage, imageMemory.getImage("blueImage"));
  }

  @Test
  public void testRGBSplitOfPureGreenImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("rgb-split %s redImage greenImage blueImage", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully split the image into RGB components."));
    Image redImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    Image greenImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    Image blueImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(redImage, imageMemory.getImage("redImage"));
    assertEquals(greenImage, imageMemory.getImage("greenImage"));
    assertEquals(blueImage, imageMemory.getImage("blueImage"));
  }

  @Test
  public void testRGBSplitOfPureGreyImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("rgb-split %s redImage greenImage blueImage", INITIAL_IMAGE_NAME), output, greyImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully split the image into RGB components."));
    Image redImage = ImageFactory.createImage(createPixels(new int[][]{
            {8421504, 8421504},
            {8421504, 8421504}
    }));
    Image greenImage = ImageFactory.createImage(createPixels(new int[][]{
            {8421504, 8421504},
            {8421504, 8421504}
    }));
    Image blueImage = ImageFactory.createImage(createPixels(new int[][]{
            {8421504, 8421504},
            {8421504, 8421504}
    }));
    assertEquals(redImage, imageMemory.getImage("redImage"));
    assertEquals(greenImage, imageMemory.getImage("greenImage"));
    assertEquals(blueImage, imageMemory.getImage("blueImage"));
  }

  @Test
  public void testRGBSplitOfPureWhiteImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("rgb-split %s redImage greenImage blueImage", INITIAL_IMAGE_NAME), output, whiteImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully split the image into RGB components."));
    Image redImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    Image greenImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    Image blueImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(redImage, imageMemory.getImage("redImage"));
    assertEquals(greenImage, imageMemory.getImage("greenImage"));
    assertEquals(blueImage, imageMemory.getImage("blueImage"));
  }

  @Test
  public void testRGBSplitOfPureBlackImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("rgb-split %s redImage greenImage blueImage", INITIAL_IMAGE_NAME), output, blackImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully split the image into RGB components."));
    Image redImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    Image greenImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    Image blueImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(redImage, imageMemory.getImage("redImage"));
    assertEquals(greenImage, imageMemory.getImage("greenImage"));
    assertEquals(blueImage, imageMemory.getImage("blueImage"));
  }

  @Test
  public void testRGBSplitCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("rgb-split invalidImageName redImage greenImage blueImage", output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testRGBSplitCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("rgb-split %s", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testRGBSplitWithoutImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("rgb-split", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }


  // Blur tests
  @Test
  public void testBlurImageWithPureRedImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blur %s blurImage", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully blurred the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {9371648,9371648},
            {9371648,9371648}
    }));
    assertEquals(expectedImage, imageMemory.getImage("blurImage"));
  }

  @Test
  public void testBlurImageWithPureBlueImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blur %s blurImage", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully blurred the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {143,143},
            {143,143}
  }));
    assertEquals(expectedImage, imageMemory.getImage("blurImage"));
  }

  @Test
  public void testBlurImageWithPureGreenImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blur %s blurImage", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully blurred the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {36608,36608},
            {36608,36608}
  }));
    assertEquals(expectedImage, imageMemory.getImage("blurImage"));
  }

  @Test
  public void testBlurImageWithPureGreyImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blur %s blurImage", INITIAL_IMAGE_NAME), output, greyImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully blurred the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {4737096,4737096},
            {4737096,4737096}
  }));
    assertEquals(expectedImage, imageMemory.getImage("blurImage"));
  }

  @Test
  public void testBlurImageWithPureWhiteImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blur %s blurImage", INITIAL_IMAGE_NAME), output, whiteImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully blurred the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {9408399,9408399},
            {9408399,9408399}
  }));
    assertEquals(expectedImage, imageMemory.getImage("blurImage"));
  }

  @Test
  public void testBlurImageWithPureBlackImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blur %s blurImage", INITIAL_IMAGE_NAME), output, blackImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully blurred the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0,0},
            {0,0}
  }));
    assertEquals(expectedImage, imageMemory.getImage("blurImage"));
  }

  @Test
  public void testBlurCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("blur invalidImageName blurImage", output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testBlurCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blur %s", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testBlurImageWithRandomImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("blur %s blurImage", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully blurred the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {4663079, 3088207},
            {3100447, 3096383}

  }));
    assertEquals(expectedImage, imageMemory.getImage("blurImage"));
  }

  @Test
  public void testBlurImageWithoutImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("blur", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }


  // Sharpen tests

  @Test
  public void testSharpenImageWithPureRedImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sharpen %s sharpenImage", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully sharpened the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16711680,16711680},
            {16711680,16711680}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sharpenImage"));
  }

  @Test
  public void testSharpenImageWithPureBlueImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sharpen %s sharpenImage", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully sharpened the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {255,255},
            {255,255}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sharpenImage"));
  }

  @Test
  public void testSharpenImageWithPureGreenImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sharpen %s sharpenImage", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully sharpened the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {65280,65280},
            {65280,65280}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sharpenImage"));
  }

  @Test
  public void testSharpenImageWithPureGreyImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sharpen %s sharpenImage", INITIAL_IMAGE_NAME), output, greyImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully sharpened the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {14737632,14737632},
            {14737632,14737632}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sharpenImage"));
  }

  @Test
  public void testSharpenImageWithPureWhiteImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sharpen %s sharpenImage", INITIAL_IMAGE_NAME), output, whiteImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully sharpened the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215,16777215},
            {16777215,16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sharpenImage"));
  }

  @Test
  public void testSharpenImageWithPureBlackImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sharpen %s sharpenImage", INITIAL_IMAGE_NAME), output, blackImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully sharpened the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0,0},
            {0,0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sharpenImage"));
  }

  @Test
  public void testSharpenCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("sharpen invalidImageName sharpenImage", output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testSharpenCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sharpen %s", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testSharpenImageWithRandomImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sharpen %s sharpenImage", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully sharpened the image."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16736095,6250495},
            {6291295,12566463}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sharpenImage"));
  }

  @Test
  public void testSharpenImageWithoutImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("sharpen", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  // Sepia tests
  @Test
  public void testSepiaImageWithPureRedImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sepia %s sepiaImage", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully converted the image to sepia."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {6576197, 6576197},
            {6576197, 6576197}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sepiaImage"));
  }

  @Test
  public void testSepiaImageWithPureBlueImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sepia %s sepiaImage", INITIAL_IMAGE_NAME), output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully converted the image to sepia."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sepiaImage"));
  }

  @Test
  public void testSepiaImageWithPureGreenImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sepia %s sepiaImage", INITIAL_IMAGE_NAME), output, greenImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully converted the image to sepia."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sepiaImage"));
  }

  @Test
  public void testSepiaImageWithPureGreyImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sepia %s sepiaImage", INITIAL_IMAGE_NAME), output, greyImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully converted the image to sepia."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sepiaImage"));
  }

  @Test
  public void testSepiaImageWithPureWhiteImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sepia %s sepiaImage", INITIAL_IMAGE_NAME), output, whiteImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully converted the image to sepia."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777198, 16777198},
            {16777198, 16777198}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sepiaImage"));
  }

  @Test
  public void testSepiaImageWithPureBlackImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sepia %s sepiaImage", INITIAL_IMAGE_NAME), output, blackImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully converted the image to sepia."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {0, 0},
            {0, 0}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sepiaImage"));
  }

  @Test
  public void testSepiaCommandWithInvalidImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("sepia invalidImageName sepiaImage", output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Image with name invalidImageName not found in memory"));
  }

  @Test
  public void testSepiaCommandWithInvalidDestinationImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sepia %s", INITIAL_IMAGE_NAME), output, redImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
  }

  @Test
  public void testSepiaImageWithRandomImage() throws ImageProcessorException {
    StringBuilder output = new StringBuilder();
    initialiseController(String.format("sepia %s sepiaImage", INITIAL_IMAGE_NAME), output, randomImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Successfully converted the image to sepia."));
    Image expectedImage = ImageFactory.createImage(createPixels(new int[][]{
            {16777215, 16777215},
            {16777215, 16777215}
    }));
    assertEquals(expectedImage, imageMemory.getImage("sepiaImage"));
  }

  @Test
  public void testSepiaImageWithoutImageName() {
    StringBuilder output = new StringBuilder();
    initialiseController("sepia", output, blueImage());
    controller.processCommands();
    assertTrue(output.toString().contains("Invalid command parameters."));
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
