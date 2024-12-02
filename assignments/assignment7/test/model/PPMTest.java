package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.ImageUtil;
import org.junit.Test;


import java.io.File;

/**
 * A JUnit test class for the PPM Image testing.
 */
public class PPMTest {


  @Test
  public void testLoadPPMImage() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageModel imageModel = new ImageImplementation();
    ImageUtil.loadImage(inputFile);

    assertNotNull("Image should be loaded", imageModel.getImage());

    int expectedHeight = 100;
    int expectedWidth = 100;

    Pixel[][] image = imageModel.getImage();
    assertEquals("Image height should be correct", expectedHeight, image.length);
    assertEquals("Image width should be correct", expectedWidth, image[0].length);

    System.out.println("Test passed: PPM image loaded successfully.");
  }

  @Test
  public void testSavePPMImage() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageModel imageModel = new ImageImplementation();
    ImageUtil.loadImage(inputFile);

    String outputFile = "res/PPM/saved_image.ppm";
    imageModel.saveImage(outputFile, imageModel.getImage());

    File savedFile = new File(outputFile);
    assertTrue("Saved PPM image should exist", savedFile.exists());

    ImageModel savedImageModel = new ImageImplementation();
    ImageUtil.loadImage(outputFile);
    Pixel[][] savedImage = savedImageModel.getImage();

    Pixel[][] originalImage = imageModel.getImage();
    for (int row = 0; row < originalImage.length; row++) {
      for (int col = 0; col < originalImage[0].length; col++) {
        assertEquals("Red value mismatch at (" + row + ", " + col + ")",
            originalImage[row][col].get(0), savedImage[row][col].get(0));
        assertEquals("Green value mismatch at (" + row + ", " + col + ")",
            originalImage[row][col].get(1), savedImage[row][col].get(1));
        assertEquals("Blue value mismatch at (" + row + ", " + col + ")",
            originalImage[row][col].get(2), savedImage[row][col].get(2));
      }
    }

    System.out.println("Test passed: PPM image saved successfully.");
  }

  @Test
  public void testFlipHorizontally() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageModel imageModel = new ImageImplementation();
    ImageUtil.loadImage(inputFile);

    try {
      ImageUtil.loadImage(inputFile);
    } catch (Exception e) {
      fail("Failed to load input image: " + e.getMessage());
    }

    assertNotNull("Image should be loaded", imageModel.getImage());

    imageModel.flipHorizontally();

    String flippedFile = "res/PPM/flipped_output.ppm";
    imageModel.saveImage(flippedFile, imageModel.getImage());
    ImageUtil.loadImage(inputFile);

    String expectedFile = "res/PPM/dummy_horizontal.ppm";
    ImageModel expectedImageModel = new ImageImplementation();

    try {
      ImageUtil.loadImage(expectedFile);
    } catch (Exception e) {
      fail("Failed to load expected flipped image: " + e.getMessage());
    }

    Pixel[][] flippedImage = imageModel.getImage();
    Pixel[][] expectedFlippedImage = expectedImageModel.getImage();

    assertEquals("Image heights should be equal", flippedImage.length, expectedFlippedImage.length);
    assertEquals("Image widths should be equal", flippedImage[0].length,
        expectedFlippedImage[0].length);

    for (int row = 0; row < flippedImage.length; row++) {
      for (int col = 0; col < flippedImage[0].length; col++) {
        Pixel actualPixel = flippedImage[row][col];
        Pixel expectedPixel = expectedFlippedImage[row][col];

        assertEquals("Red value mismatch at (" + row + "," + col + ")",
            expectedPixel.get(0), actualPixel.get(0));
        assertEquals("Green value mismatch at (" + row + "," + col + ")",
            expectedPixel.get(1), actualPixel.get(1));
        assertEquals("Blue value mismatch at (" + row + "," + col + ")",
            expectedPixel.get(2), actualPixel.get(2));
      }
    }

    System.out.println("Test passed: Image flipped correctly.");
  }

  @Test
  public void testFlipVertically() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageModel imageModel = new ImageImplementation();
    ImageUtil.loadImage(inputFile);

    assertNotNull("Image should be loaded", imageModel.getImage());

    imageModel.flipVertically();
    ImageUtil.loadImage(inputFile);

    String outputFile = "res/PPM/flipped_vertically_image.ppm";
    imageModel.saveImage(outputFile, imageModel.getImage());

    File savedFile = new File(outputFile);
    assertTrue("Flipped vertically image should be saved", savedFile.exists());

    String expectedFile = "res/PPM/expected_flipped_vertically_image.ppm";
    ImageModel expectedImageModel = new ImageImplementation();
    ImageUtil.loadImage(expectedFile);

    Pixel[][] flippedImage = imageModel.getImage();
    Pixel[][] expectedFlippedImage = expectedImageModel.getImage();

    for (int row = 0; row < flippedImage.length; row++) {
      for (int col = 0; col < flippedImage[0].length; col++) {
        Pixel actualPixel = flippedImage[row][col];
        Pixel expectedPixel = expectedFlippedImage[row][col];

        assertEquals("Red value mismatch at (" + row + ", " + col + ")",
            expectedPixel.get(0), actualPixel.get(0));
        assertEquals("Green value mismatch at (" + row + ", " + col + ")",
            expectedPixel.get(1), actualPixel.get(1));
        assertEquals("Blue value mismatch at (" + row + ", " + col + ")",
            expectedPixel.get(2), actualPixel.get(2));
      }
    }

    System.out.println("Test passed: Image flipped vertically and saved successfully.");
  }

  @Test
  public void testGetRGBComponents() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageModel imageModel = new ImageImplementation();
    ImageUtil.loadImage(inputFile);

    int[][][] rgb = imageModel.getRGBComponents();
    assertNotNull("RGB components should not be null", rgb);

    for (int i = 0; i < rgb.length; i++) {
      for (int j = 0; j < rgb[0].length; j++) {
        assertEquals("Each pixel should have 3 color components", 3, rgb[i][j].length);
      }
    }
  }

  @Test
  public void testGetLuma() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageModel imageModel = new ImageImplementation();
    ImageUtil.loadImage(inputFile);

    Pixel[][] luma = imageModel.getLuma();
    assertNotNull("Luma array should not be null", luma);

    assertEquals("Height should match", imageModel.getImage().length, luma.length);
    assertEquals("Width should match", imageModel.getImage()[0].length, luma[0].length);
  }

  @Test
  public void testGetIntensity() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageModel imageModel = new ImageImplementation();
    ImageUtil.loadImage(inputFile);

    Pixel[][] intensity = imageModel.getIntensity();
    assertNotNull("Intensity array should not be null", intensity);

    assertEquals("Height should match", imageModel.getImage().length, intensity.length);
    assertEquals("Width should match", imageModel.getImage()[0].length, intensity[0].length);
  }

  @Test
  public void testGetValue() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageModel imageModel = new ImageImplementation();
    ImageUtil.loadImage(inputFile);

    Pixel[][] value = imageModel.getValue();
    assertNotNull("Value array should not be null", value);

    assertEquals("Height should match", imageModel.getImage().length, value.length);
    assertEquals("Width should match", imageModel.getImage()[0].length, value[0].length);
  }

  @Test
  public void testBrighten() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageModel imageModel = new ImageImplementation();
    ImageUtil.loadImage(inputFile);

    Pixel[][] originalImage = imageModel.getImage();
    imageModel.brighten(100);

    Pixel[][] brightenedImage = imageModel.getImage();
    String outputFile = "res/PPM/brightened_dummy.ppm";
    imageModel.saveImage(outputFile, imageModel.getImage());

    for (int i = 0; i < originalImage.length; i++) {
      for (int j = 0; j < originalImage[0].length; j++) {
        assertTrue("Brightened red value should be higher",
            brightenedImage[i][j].get(0) >= originalImage[i][j].get(0));
        assertTrue("Brightened green value should be higher",
            brightenedImage[i][j].get(1) >= originalImage[i][j].get(1));
        assertTrue("Brightened blue value should be higher",
            brightenedImage[i][j].get(2) >= originalImage[i][j].get(2));
      }
    }
  }

  @Test
  public void testDarken() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageModel imageModel = new ImageImplementation();
    ImageUtil.loadImage(inputFile);

    Pixel[][] originalImage = imageModel.getImage();
    imageModel.darken(100);

    Pixel[][] darkenedImage = imageModel.getImage();

    String outputFile = "res/PPM/darkened_dummy.ppm";
    imageModel.saveImage(outputFile, imageModel.getImage());
    for (int i = 0; i < originalImage.length; i++) {
      for (int j = 0; j < originalImage[0].length; j++) {
        assertTrue("Darkened red value should be lower",
            darkenedImage[i][j].get(0) <= originalImage[i][j].get(0));
        assertTrue("Darkened green value should be lower",
            darkenedImage[i][j].get(1) <= originalImage[i][j].get(1));
        assertTrue("Darkened blue value should be lower",
            darkenedImage[i][j].get(2) <= originalImage[i][j].get(2));
      }
    }
  }

  @Test
  public void testSplitChannels() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageModel imageModel = new ImageImplementation();
    ImageUtil.loadImage(inputFile);

    imageModel.splitChannels();

    Pixel[][] redChannelImage = imageModel.getRedChannelImage();
    Pixel[][] greenChannelImage = imageModel.getGreenChannelImage();
    Pixel[][] blueChannelImage = imageModel.getBlueChannelImage();

    Pixel[][] originalImage = imageModel.getImage();

    for (int row = 0; row < originalImage.length; row++) {
      for (int col = 0; col < originalImage[0].length; col++) {
        assertEquals("Red channel should preserve the red value",
            originalImage[row][col].get(0),
            redChannelImage[row][col].get(0));
        assertEquals("Red channel should have 0 for green",
            0, redChannelImage[row][col].get(1));
        assertEquals("Red channel should have 0 for blue",
            0, redChannelImage[row][col].get(2));

        assertEquals("Green channel should preserve the green value",
            originalImage[row][col].get(1),
            greenChannelImage[row][col].get(1));
        assertEquals("Green channel should have 0 for red",
            0, greenChannelImage[row][col].get(0));
        assertEquals("Green channel should have 0 for blue",
            0, greenChannelImage[row][col].get(2));

        assertEquals("Blue channel should preserve the blue value",
            originalImage[row][col].get(2),
            blueChannelImage[row][col].get(2));
        assertEquals("Blue channel should have 0 for red",
            0, blueChannelImage[row][col].get(0));
        assertEquals("Blue channel should have 0 for green",
            0, blueChannelImage[row][col].get(1));
      }
    }

    imageModel.saveImage("res/PPM/red_channel_image.ppm", redChannelImage);
    imageModel.saveImage("res/PPM/green_channel_image.ppm", greenChannelImage);
    imageModel.saveImage("res/PPM/blue_channel_image.ppm", blueChannelImage);
  }

  @Test
  public void testCombineGrayscaleImagesIntoColorImage() {
    ImageModel imageModel = new ImageImplementation();

    ImageUtil.loadImage("res/PPM/red_channel_image.ppm");
    int[][] red = imageModel.getRGBComponents()[0];

    ImageUtil.loadImage("res/PPM/green_channel_image.ppm");
    int[][] green = imageModel.getRGBComponents()[1];

    ImageUtil.loadImage("res/PPM/blue_channel_image.ppm");
    int[][] blue = imageModel.getRGBComponents()[2];

    imageModel.combineChannels(red, green, blue);

    Pixel[][] combinedImage = imageModel.getImage();

    assertNotNull("Combined image should not be null", combinedImage);
    assertEquals("Height of combined image should be the same as channel images",
        red.length, combinedImage.length);
    assertEquals("Width of combined image should be the same as channel images",
        red[0].length, combinedImage[0].length);

    imageModel.saveImage("res/PPM/combined2_channel_image.ppm", combinedImage);

    System.out.println("Test passed: Grayscale images combined into a color image.");
  }

  @Test
  public void testApplyBlur() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageImplementation imgImpl = new ImageImplementation();

    ImageUtil.loadImage(inputFile);
    imgImpl.applyBlur();
    Pixel[][] blurredImage = imgImpl.getImage();
    imgImpl.saveImage("res/PPM/dummy_blurred.ppm", blurredImage);

    assertNotNull(blurredImage);

    ImageImplementation expectedImgImpl = new ImageImplementation();
    Pixel[][] expectedBlurredImage = ImageUtil.loadImage(
        "res/PPM/expected_blurred_image.ppm");

    for (int row = 0; row < blurredImage.length; row++) {
      for (int col = 0; col < blurredImage[0].length; col++) {
        assertEquals("Red value mismatch at (" + row + "," + col + ")",
            expectedBlurredImage[row][col].get(0), blurredImage[row][col].get(0));
        assertEquals("Green value mismatch at (" + row + "," + col + ")",
            expectedBlurredImage[row][col].get(1), blurredImage[row][col].get(1));
        assertEquals("Blue value mismatch at (" + row + "," + col + ")",
            expectedBlurredImage[row][col].get(2), blurredImage[row][col].get(2));
      }
    }
  }

  @Test
  public void testApplySharpen() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageImplementation imgImpl = new ImageImplementation();

    ImageUtil.loadImage(inputFile);
    imgImpl.applySharpen();
    Pixel[][] sharpenedImage = imgImpl.getImage();
    imgImpl.saveImage("res/PPM/dummy_sharpened.ppm", sharpenedImage);
    assertNotNull(sharpenedImage);

    ImageImplementation expectedImgImpl = new ImageImplementation();
    Pixel[][] expectedSharpenedImage = ImageUtil.loadImage(
        "res/PPM/expected_sharpened_image.ppm");

    for (int row = 0; row < sharpenedImage.length; row++) {
      for (int col = 0; col < sharpenedImage[0].length; col++) {
        assertEquals("Red value mismatch at (" + row + "," + col + ")",
            expectedSharpenedImage[row][col].get(0), sharpenedImage[row][col].get(0));
        assertEquals("Green value mismatch at (" + row + "," + col + ")",
            expectedSharpenedImage[row][col].get(1), sharpenedImage[row][col].get(1));
        assertEquals("Blue value mismatch at (" + row + "," + col + ")",
            expectedSharpenedImage[row][col].get(2), sharpenedImage[row][col].get(2));
      }
    }
  }

  @Test
  public void testApplySepia() {
    String inputFile = "res/PPM/dummy_image.ppm";
    ImageImplementation imgImpl = new ImageImplementation();

    ImageUtil.loadImage(inputFile);
    imgImpl.applySepia();
    Pixel[][] sepiaImage = imgImpl.getImage();
    imgImpl.saveImage("res/PPM/dummy_sepia.ppm", sepiaImage);
    assertNotNull(sepiaImage);

    // Load expected sepia image for comparison
    ImageImplementation expectedImgImpl = new ImageImplementation();
    Pixel[][] expectedSepiaImage = ImageUtil.loadImage(
        "res/PPM/expected_sepia_image.ppm");

    // Compare pixel values
    for (int row = 0; row < sepiaImage.length; row++) {
      for (int col = 0; col < sepiaImage[0].length; col++) {
        assertEquals("Red value mismatch at (" + row + "," + col + ")",
            expectedSepiaImage[row][col].get(0), sepiaImage[row][col].get(0));
        assertEquals("Green value mismatch at (" + row + "," + col + ")",
            expectedSepiaImage[row][col].get(1), sepiaImage[row][col].get(1));
        assertEquals("Blue value mismatch at (" + row + "," + col + ")",
            expectedSepiaImage[row][col].get(2), sepiaImage[row][col].get(2));
      }
    }
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
  public void testFlipHorizontallyPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.flipHorizontally();
    Pixel[][] flippedImage = imgImpl.getImage();
    assertNotNull(flippedImage);
  }

  @Test
  public void testFlipVerticallyPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.flipVertically();
    Pixel[][] flippedImage = imgImpl.getImage();
    assertNotNull(flippedImage);
  }

  @Test
  public void testBrightenImagePPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.brighten(50);
    Pixel[][] brightenedImage = imgImpl.getImage();
    assertNotNull(brightenedImage);
  }

  @Test
  public void testDarkenImagePPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.darken(50);
    Pixel[][] darkenedImage = imgImpl.getImage();
    assertNotNull(darkenedImage);
  }

  @Test
  public void testSplitChannelsPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.splitChannels();
    assertNotNull(imgImpl.getRedChannelImage());
    assertNotNull(imgImpl.getGreenChannelImage());
    assertNotNull(imgImpl.getBlueChannelImage());
  }

  @Test
  public void testCombineChannelsPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.splitChannels();
    imgImpl.combineChannels(new int[2][2], new int[2][2], new int[2][2]);
    assertNotNull(imgImpl.getImage());
  }

  @Test
  public void testApplyGreyscalePPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.applyGreyScale();
    Pixel[][] greyImage = imgImpl.getImage();
    assertNotNull(greyImage);
  }

  @Test
  public void testApplySepiaPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.applySepia();
    Pixel[][] sepiaImage = imgImpl.getImage();
    assertNotNull(sepiaImage);
  }

  @Test
  public void testApplyBlurPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.applyBlur();
    Pixel[][] blurredImage = imgImpl.getImage();
    assertNotNull(blurredImage);
  }

  @Test
  public void testApplySharpenPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.applySharpen();
    Pixel[][] sharpenedImage = imgImpl.getImage();
    assertNotNull(sharpenedImage);
  }

  @Test
  public void testApplyBlurOnSmallImagePPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.applyBlur();
    Pixel[][] blurredImage = imgImpl.getImage();
    assertNotNull(blurredImage);
  }

  @Test
  public void testSplitChannelsOnSmallPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.splitChannels();
    assertNotNull(imgImpl.getRedChannelImage());
    assertNotNull(imgImpl.getGreenChannelImage());
    assertNotNull(imgImpl.getBlueChannelImage());
  }

  @Test
  public void testGetRedChannelAfterSplit() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    imgImpl.splitChannels();
    Pixel[][] redChannel = imgImpl.getRedChannelImage();
    assertNotNull(redChannel);
  }

  @Test
  public void testDarkenBeyondBounds() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
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
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
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
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
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

