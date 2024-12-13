package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import controller.ImageUtil;
import org.junit.Test;


import java.io.File;

/**
 * A JUnit test class for the PPM Image testing.
 */
public class PPMTest {


  @Test
  public void testLoadPPMImage() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);

    assertNotNull("Image should be loaded", imgImpl.getImage());

    int expectedHeight = 100;
    int expectedWidth = 100;

    Pixel[][] image = imgImpl.getImage();
    assertEquals("Image height should be correct", expectedHeight, image.length);
    assertEquals("Image width should be correct", expectedWidth, image[0].length);

    System.out.println("Test passed: PPM image loaded successfully.");
  }


  @Test
  public void testFlipHorizontally() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.flipHorizontally();

    Pixel[][] horizontalImage = imgImpl.getImage();
    imgImpl.saveImage("res/PPM/output.ppm", horizontalImage);  // Save the flipped image
    assertNotNull("Flipped image should not be null", horizontalImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/PPM/output.ppm");
    assertTrue("Flipped image file should exist", outputFile.exists());
  }


  @Test
  public void testFlipVertically() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.flipVertically();

    Pixel[][] horizontalImage = imgImpl.getImage();
    imgImpl.saveImage("res/PPM/output.ppm", horizontalImage);  // Save the flipped image
    assertNotNull("Flipped image should not be null", horizontalImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/PPM/output.ppm");
    assertTrue("Flipped image file should exist", outputFile.exists());
  }


  @Test
  public void testGetLuma() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);

    Pixel[][] luma = imgImpl.getLuma();
    assertNotNull("Luma array should not be null", luma);

    assertEquals("Height should match", imgImpl.getImage().length, luma.length);
    assertEquals("Width should match", imgImpl.getImage()[0].length, luma[0].length);
  }

  @Test
  public void testGetIntensity() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);

    Pixel[][] intensity = imgImpl.getIntensity();
    assertNotNull("Intensity array should not be null", intensity);

    assertEquals("Height should match", imgImpl.getImage().length, intensity.length);
    assertEquals("Width should match", imgImpl.getImage()[0].length, intensity[0].length);
  }

  @Test
  public void testGetValue() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);

    Pixel[][] value = imgImpl.getValue();
    assertNotNull("Value array should not be null", value);

    assertEquals("Height should match", imgImpl.getImage().length, value.length);
    assertEquals("Width should match", imgImpl.getImage()[0].length, value[0].length);
  }

  @Test
  public void testBrighten() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.brighten(50);

    Pixel[][] brightenImage = imgImpl.getImage();
    imgImpl.saveImage("res/PPM/output.ppm", brightenImage);  // Save the flipped image
    assertNotNull("brighten image should not be null", brightenImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/PPM/output.ppm");
    assertTrue("brighten image file should exist", outputFile.exists());
  }

  @Test
  public void testDarken() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.darken(-50);

    Pixel[][] darkenImage = imgImpl.getImage();
    imgImpl.saveImage("res/PPM/output.ppm", darkenImage);  // Save the flipped image
    assertNotNull("darken image should not be null", darkenImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/PPM/output.ppm");
    assertTrue("darken image file should exist", outputFile.exists());
  }

  @Test
  public void testSplitChannels() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);
    imgImpl.splitChannels();
    assertNotNull(imgImpl.getRedChannelImage());
    assertNotNull(imgImpl.getGreenChannelImage());
    assertNotNull(imgImpl.getBlueChannelImage());
  }

  @Test
  public void testApplyBlur() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.applyBlur();

    Pixel[][] blurImage = imgImpl.getImage();
    imgImpl.saveImage("res/PPM/output.ppm", blurImage);  // Save the flipped image
    assertNotNull("blur image should not be null", blurImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/PPM/output.ppm");
    assertTrue("blur image file should exist", outputFile.exists());
  }

  @Test
  public void testApplySharpen() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.applySharpen();

    Pixel[][] sharpenImage = imgImpl.getImage();
    imgImpl.saveImage("res/PPM/output.ppm", sharpenImage);  // Save the flipped image
    assertNotNull("sharpen image should not be null", sharpenImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/PPM/output.ppm");
    assertTrue("sharpen image file should exist", outputFile.exists());
  }

  @Test
  public void testApplySepia() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.applySepia();

    Pixel[][] sepiaImage = imgImpl.getImage();
    imgImpl.saveImage("res/PPM/output.ppm", sepiaImage);  // Save the flipped image
    assertNotNull("sepia image should not be null", sepiaImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/PPM/output.ppm");
    assertTrue("sepia image file should exist", outputFile.exists());
  }

  @Test
  public void testLoadCorruptedImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] image = ImageUtil.loadImage("res/PPM/corrupted.ppm");
    assertNull(image);
  }

  @Test
  public void testSavePPMImageSuccess() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] image = new Pixel[2][2];
    image[0][0] = new Pixel(255, 0, 0);
    image[0][1] = new Pixel(0, 255, 0);
    image[1][0] = new Pixel(0, 0, 255);
    image[1][1] = new Pixel(255, 255, 0);
    imgImpl.saveImage("res/PPM/output.ppm", image);
    assertTrue(new File("res/PPM/output.ppm").exists());
  }


  @Test
  public void testCombineChannelsPPM() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);
    imgImpl.splitChannels();
    imgImpl.combineChannels(new int[2][2], new int[2][2], new int[2][2]);
    assertNotNull(imgImpl.getImage());
  }

  @Test
  public void testApplyGreyscalePPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);
    imgImpl.applyGreyScale();
    Pixel[][] greyImage = imgImpl.getImage();
    assertNotNull(greyImage);
  }


  @Test
  public void testApplyBlurOnSmallImagePPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);
    imgImpl.applyBlur();
    Pixel[][] blurredImage = imgImpl.getImage();
    assertNotNull(blurredImage);
  }

  @Test
  public void testSplitChannelsOnSmallPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);
    imgImpl.splitChannels();
    assertNotNull(imgImpl.getRedChannelImage());
    assertNotNull(imgImpl.getGreenChannelImage());
    assertNotNull(imgImpl.getBlueChannelImage());
  }

  @Test
  public void testGetRedChannelAfterSplit() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);
    imgImpl.splitChannels();
    Pixel[][] redChannel = imgImpl.getRedChannelImage();
    assertNotNull(redChannel);
  }

  @Test
  public void testDarkenBeyondBounds() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);
    imgImpl.darken(300);
    Pixel[][] darkenedImage = imgImpl.getImage();
    for (Pixel[] row : darkenedImage) {
      for (Pixel pixel : row) {
        assertTrue(pixel.get(0) >= 0 && pixel.get(1) >= 0 && pixel.get(2) >= 0);
      }
    }
  }

  @Test
  public void testApplyEdgeDetectionPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);
    double[][] edgeDetectionKernel = {
        {-1, -1, -1},
        {-1, 8, -1},
        {-1, -1, -1}
    };
    imgImpl.applyFilter(edgeDetectionKernel);
    Pixel[][] edgeDetectedImage = imgImpl.getImage();
    assertNotNull(edgeDetectedImage);
  }

  @Test
  public void testApplyLargeKernelBlurPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.setImage(loadedImage);
    double[][] largeKernel = {
        {1 / 25.0, 1 / 25.0, 1 / 25.0, 1 / 25.0, 1 / 25.0},
        {1 / 25.0, 1 / 25.0, 1 / 25.0, 1 / 25.0, 1 / 25.0},
        {1 / 25.0, 1 / 25.0, 1 / 25.0, 1 / 25.0, 1 / 25.0},
        {1 / 25.0, 1 / 25.0, 1 / 25.0, 1 / 25.0, 1 / 25.0},
        {1 / 25.0, 1 / 25.0, 1 / 25.0, 1 / 25.0, 1 / 25.0}
    };
    imgImpl.applyFilter(largeKernel);
    Pixel[][] blurredImage = imgImpl.getImage();
    assertNotNull(blurredImage);
  }
}

