package model;

import controller.ImageController;
import controller.ImageUtil;
import java.io.BufferedReader;
import java.io.StringReader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * A JUnit test class for the PNG Image testing.
 */
public class PNGTest {

  @Test
  public void testLoadPNGImageSuccess() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] image = ImageUtil.loadImage("flower.png");
    assertNotNull(image);
  }

  @Test
  public void testFlipHorizontallyPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    imgImpl.flipHorizontally();
    Pixel[][] flippedImage = imgImpl.getImage();
    assertNotNull(flippedImage);
    Pixel[][] originalImage = ImageUtil.loadImage("flower.png");
    assertEquals(originalImage[0][0], flippedImage[0][flippedImage[0].length - 1]);
  }

  @Test
  public void testFlipVerticallyPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    imgImpl.flipVertically();
    Pixel[][] flippedImage = imgImpl.getImage();
    assertNotNull(flippedImage);

    Pixel[][] originalImage = ImageUtil.loadImage("flower.png");
    assertEquals(originalImage[0][0], flippedImage[flippedImage.length - 1][0]);
  }

  @Test
  public void testBrightenImagePNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    imgImpl.brighten(50);
    Pixel[][] brightenedImage = imgImpl.getImage();
    assertNotNull(brightenedImage);

    Pixel pixel = brightenedImage[0][0];
    assertTrue(pixel.get(0) <= 255 && pixel.get(1) <= 255 && pixel.get(2) <= 255);
  }


  @Test
  public void testDarkenImagePNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    imgImpl.darken(50);
    Pixel[][] darkenedImage = imgImpl.getImage();
    assertNotNull(darkenedImage);

    Pixel pixel = darkenedImage[0][0];
    assertTrue(pixel.get(0) <= 255 && pixel.get(1) <= 255 && pixel.get(2) <= 255);
  }

  @Test
  public void testSplitChannelsPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    imgImpl.splitChannels();
    assertNotNull(imgImpl.getRedChannelImage());
    assertNotNull(imgImpl.getGreenChannelImage());
    assertNotNull(imgImpl.getBlueChannelImage());
  }

  @Test
  public void testApplyGreyscaleOnPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    imgImpl.applyGreyScale();
    Pixel[][] greyImage = imgImpl.getImage();
    assertNotNull(greyImage);
    assertEquals(greyImage[0][0].get(0), greyImage[0][0].get(1));
    assertEquals(greyImage[0][0].get(1), greyImage[0][0].get(2));
  }

  @Test
  public void testApplySepiaOnPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    imgImpl.applySepia();
    Pixel[][] sepiaImage = imgImpl.getImage();
    assertNotNull(sepiaImage);

    Pixel pixel = sepiaImage[0][0];
    assertTrue(pixel.get(0) <= 255 && pixel.get(1) <= 255 && pixel.get(2) <= 255);
  }

  @Test
  public void testBrightenBeyondBoundsPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    imgImpl.brighten(300);
    Pixel[][] brightenedImage = imgImpl.getImage();
    for (Pixel[] row : brightenedImage) {
      for (Pixel pixel : row) {
        assertTrue(pixel.get(0) <= 255 && pixel.get(1) <= 255 && pixel.get(2) <= 255);
      }
    }
  }

  @Test
  public void testDarkenBeyondBoundsPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    imgImpl.darken(300);
    Pixel[][] darkenedImage = imgImpl.getImage();
    for (Pixel[] row : darkenedImage) {
      for (Pixel pixel : row) {
        assertTrue(pixel.get(0) <= 255 && pixel.get(1) <= 255 && pixel.get(2) <= 255);
      }
    }
  }

  @Test
  public void testVisualizeRedChannelPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    imgImpl.splitChannels();
    Pixel[][] redChannel = imgImpl.getRedChannelImage();
    assertNotNull(redChannel);
  }

  @Test
  public void testVisualizeGreenChannelPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    imgImpl.splitChannels();
    Pixel[][] greenChannel = imgImpl.getGreenChannelImage();
    assertNotNull(greenChannel);
  }

  @Test
  public void testVisualizeBlueChannelPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    imgImpl.splitChannels();
    Pixel[][] blueChannel = imgImpl.getBlueChannelImage();
    assertNotNull(blueChannel);
  }

  @Test
  public void testVisualizeValuePNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    Pixel[][] valueImage = imgImpl.getValue();
    assertNotNull(valueImage);
  }

  @Test
  public void testVisualizeIntensityPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    Pixel[][] intensityImage = imgImpl.getIntensity();
    assertNotNull(intensityImage);
  }

  @Test
  public void testVisualizeLumaPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("flower.png");
    Pixel[][] lumaImage = imgImpl.getLuma();
    assertNotNull(lumaImage);
  }

  @Test
  public void testApplyBlurPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.applyBlur();
    Pixel[][] blurredImage = imgImpl.getImage();
    assertNotNull(blurredImage);
  }

  @Test
  public void testApplySharpenPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.applySharpen();
    Pixel[][] sharpenedImage = imgImpl.getImage();
    assertNotNull(sharpenedImage);
  }

  @Test
  public void testDownscaleValidPercentage() {
    // Assume we have an image of size 200x200 and we downscale it by 50%
    Pixel[][] image = new Pixel[200][200];  // Mocking a 200x200 image
    ImageImplementation imgImpl = new ImageImplementation();
    // Apply downscale with 50%
    imgImpl.downscaleImage(50, 50);  // Assuming controller handles this logic

    // Verify that the image dimensions are halved (100x100)
    Pixel[][] downscaledImage = imgImpl.getImage();  // After downscale operation
    assertNotNull(downscaledImage);
    assertEquals(50, downscaledImage.length);  // Height should be 100
    assertEquals(50, downscaledImage[0].length);  // Width should be 100
  }

  @Test
  public void testDownscale100Percent() {
    // Test downscaling to 100% (no change)
    Pixel[][] image = new Pixel[200][200];  // Mocking a 200x200 image
    ImageImplementation imgImpl = new ImageImplementation();

    // Apply downscale with 100%
    imgImpl.downscaleImage(200, 200);  // No change expected

    // Verify that the image dimensions remain the same (200x200)
    Pixel[][] downscaledImage = imgImpl.getImage();
    assertNotNull(downscaledImage);
    assertEquals(200, downscaledImage.length);  // Height should remain 200
    assertEquals(200, downscaledImage[0].length);  // Width should remain 200
  }

  @Test
  public void testDownscaleZeroPercent() {
    // Test downscaling to 0% (empty image)
    Pixel[][] image = new Pixel[200][200];  // Mocking a 200x200 image
    ImageImplementation imgImpl = new ImageImplementation();

    // Apply downscale with 0%
    imgImpl.downscaleImage(0, 0);  // Image should be empty

    // Verify that the image is downscaled to 0x0
    Pixel[][] downscaledImage = imgImpl.getImage();
    assertNotNull(downscaledImage);
    assertEquals(0, downscaledImage.length);  // Height should be 0
    assertEquals(0, downscaledImage[0].length);  // Width should be 0
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleInvalidPercentageNegative() {
    // Test invalid downscale with negative percentage
    Pixel[][] image = new Pixel[200][200];  // Mocking a 200x200 image
    ImageImplementation imgImpl = new ImageImplementation();

    // Apply downscale with negative percentage
    imgImpl.downscaleImage(-50, -50);  // Invalid downscale percentage
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplySepiaInvalidSplitPercentageGreaterThan100() {
    // Setup mock ImageModel
    ImageImplementation imgImpl = new ImageImplementation(); // assuming null for the view

    double invalidPercentage = 150.0;  // Invalid percentage greater than 100

    // This should throw IllegalArgumentException
    imgImpl.applySepia(invalidPercentage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplySepiaInvalidSplitPercentageLessThan0() {
    // Setup mock ImageModel
    ImageImplementation imgImpl = new ImageImplementation(); // assuming null for the view

    double invalidPercentage = -60;  // Invalid percentage greater than 100

    // This should throw IllegalArgumentException
    imgImpl.applySepia(invalidPercentage);
  }

  @Test
  public void testScriptParsing() throws Exception {
    // Initialize the model and controller
    ImageModel model = new ImageImplementation();
    ImageController controller = new ImageController(model, null);  // Assuming null for the view

    // Simulate a script
    String script = "sepia flower flower_sepia\n" +
        "sepia flower mask-image sepia-masked-image\n" +
        "sepia flower flower_sepia split 50";

    // Create a BufferedReader to simulate reading a script file
    StringReader stringReader = new StringReader(script);
    BufferedReader reader = new BufferedReader(stringReader);

    // Process each line of the script
    String line;
    while ((line = reader.readLine()) != null) {
      String[] tokens = line.split(" ");
      controller.processCommand(script);  // Assuming handleSepiaCommand processes each line
    }

    Pixel[][] image = model.getImage();
    assertNotNull(image);  // Ensure an image was set
    assertEquals(100, image.length);  // Assuming sepia changed the size (just an example)

    String expectedImageName = "flower_sepia";
    Pixel[][] sepiaImage = model.getImage();
    assertNotNull(sepiaImage);
  }

  @Test
  public void testApplyLevelsAdjustShadowMidHighlight() {
    // Create a mock image for the test
    Pixel[][] image = new Pixel[200][200];  // Mocking a 200x200 image
    ImageImplementation imgImpl = new ImageImplementation();

    // Define shadow, midtone, highlight values
    int shadow = 50;    // Darken shadows
    int mid = 100;  // Neutralize midtones
    int highlight = 200;  // Brighten highlights

    // Apply levels adjustment with shadow < midtone < highlight
    imgImpl.levelsAdjust(shadow, mid, highlight);

    // Assertions to check if the image was adjusted correctly
    // After applying the levels adjustment, check if the model has been updated
    Pixel[][] adjustedImage = imgImpl.getImage();

    // Assuming the applyLevelsAdjust method modifies the image directly
    assertNotNull(adjustedImage);  // Ensure the image has been adjusted
    assertEquals(200, adjustedImage.length);  // Ensure image dimensions are unchanged
    assertEquals(200, adjustedImage[0].length);  // Ensure image dimensions are unchanged
  }

  @Test
  public void testApplyLevelsAdjustInvalid() {
    // Test invalid levels (e.g., negative values or values above 255)
    Pixel[][] image = new Pixel[200][200];  // Mocking a 200x200 image
    ImageImplementation imgImpl = new ImageImplementation();

    // Invalid levels values: negative shadow, midtone above 255, highlight above 255
    int shadow = -10;    // Invalid shadow value
    int mid = 300;   // Invalid midtone value (greater than 255)
    int highlight = 256; // Invalid highlight value (greater than 255)

    try {
      imgImpl.levelsAdjust(shadow, mid, highlight);  // Should throw exception
      fail("Expected IllegalArgumentException for invalid levels values");
    } catch (IllegalArgumentException e) {
      // Expected exception for invalid levels
      assertEquals("Levels values must be between 0 and 255.", e.getMessage());
    }
  }

  @Test
  public void testApplyLevelsAdjustNoChanges() {
    // Test case where shadow, midtone, and highlight are all equal (no adjustment)
    Pixel[][] image = new Pixel[200][200];  // Mocking a 200x200 image
    ImageImplementation imgImpl = new ImageImplementation();

    int shadow = 128;  // No shadow adjustment
    int midtone = 128; // No midtone adjustment
    int highlight = 128; // No highlight adjustment

    // Apply levels adjust with no change to the image
    imgImpl.levelsAdjust(shadow, midtone, highlight);

    // Check if the image remains unchanged (still same size and pixel values)
    Pixel[][] adjustedImage = imgImpl.getImage();

    assertNotNull(adjustedImage);  // Ensure image is still there
    assertEquals(200, adjustedImage.length);  // Ensure image dimensions are unchanged
    assertEquals(200, adjustedImage[0].length);  // Ensure image dimensions are unchanged

    // Here, you would further assert if the pixel values have remained the same
  }


}