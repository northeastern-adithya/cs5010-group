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
