package model;

import controller.ImageUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

/**
 * A JUnit test class for the JPG Image testing.
 */
public class JPGTest {

  @Test
  public void testLoadJPGImageSuccess() {
    Pixel[][] image = ImageUtil.loadImage("res/JPG/building.jpg");
    assertNotNull(image);
  }

  @Test
  public void testSaveJPGImageSuccess() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] image = new Pixel[2][2];
    image[0][0] = new Pixel(255, 0, 0);
    image[0][1] = new Pixel(0, 255, 0);
    image[1][0] = new Pixel(0, 0, 255);
    image[1][1] = new Pixel(255, 255, 0);
    imgImpl.saveImage("res/JPG/output.jpg", image);
    assertTrue(new File("res/JPG/output.jpg").exists());
  }


  @Test
  public void testFlipHorizontallyJPG() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.flipHorizontally();

    Pixel[][] horizontalImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", horizontalImage);  // Save the flipped image
    assertNotNull("Flipped image should not be null", horizontalImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("Flipped image file should exist", outputFile.exists());
  }


  @Test
  public void testBrightenJpgImage() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.brighten(50);

    Pixel[][] brightenImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", brightenImage);  // Save the flipped image
    assertNotNull("brighten image should not be null", brightenImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("brighten image file should exist", outputFile.exists());
  }


  @Test
  public void testDarkenJpgImage() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.darken(-50);

    Pixel[][] darkenImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", darkenImage);  // Save the flipped image
    assertNotNull("darken image should not be null", darkenImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("darken image file should exist", outputFile.exists());
  }

  @Test
  public void testRedComponentJpg() {

    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.getRedChannelImage();

    Pixel[][] redImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", redImage);  // Save the flipped image
    assertNotNull("red image should not be null", redImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("red image file should exist", outputFile.exists());
  }


  @Test
  public void testGreenComponentJpg() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.getGreenChannelImage();

    Pixel[][] greenImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", greenImage);  // Save the flipped image
    assertNotNull("green image should not be null", greenImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("green image file should exist", outputFile.exists());
  }

  @Test
  public void testBlueComponentJpg() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.getBlueChannelImage();

    Pixel[][] blueImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", blueImage);  // Save the flipped image
    assertNotNull("blue image should not be null", blueImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("blue image file should exist", outputFile.exists());
  }

  @Test
  public void testSplitJpgImageIntoRGB() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    imgImpl.splitChannels();
    assertNotNull(imgImpl.getRedChannelImage());
    assertNotNull(imgImpl.getGreenChannelImage());
    assertNotNull(imgImpl.getBlueChannelImage());
  }

  @Test
  public void testApplyBlurJpg() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.applyBlur();

    Pixel[][] blurImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", blurImage);  // Save the flipped image
    assertNotNull("blur image should not be null", blurImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("blur image file should exist", outputFile.exists());
  }

  @Test
  public void testApplySharpenJpg() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.applySharpen();

    Pixel[][] sharpenImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", sharpenImage);  // Save the flipped image
    assertNotNull("sharpen image should not be null", sharpenImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("sharpen image file should exist", outputFile.exists());
  }

  @Test
  public void testApplySepiaJpg() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.applySepia();

    Pixel[][] sepiaImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", sepiaImage);  // Save the flipped image
    assertNotNull("sepia image should not be null", sepiaImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("sepia image file should exist", outputFile.exists());
  }


  @Test
  public void testGrayscaleLumaJpg() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.getLuma();

    Pixel[][] lumaImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", lumaImage);  // Save the flipped image
    assertNotNull("luma image should not be null", lumaImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("luma image file should exist", outputFile.exists());
  }

  @Test
  public void testGrayscaleIntensityJpg() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.getIntensity();

    Pixel[][] intensityImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", intensityImage);  // Save the flipped image
    assertNotNull("intensity image should not be null", intensityImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("intensity image file should exist", outputFile.exists());
  }

  @Test
  public void testGrayscaleValueJpg() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Now apply flip horizontally
    imgImpl.getValue();

    Pixel[][] valueImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", valueImage);  // Save the flipped image
    assertNotNull("value image should not be null", valueImage);

    // Optionally, you could check if the file exists after saving (additional check)
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("value image file should exist", outputFile.exists());
  }

  @Test
  public void testLoadAndApplyMultipleFilters() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Apply multiple filters: flip horizontally and brighten
    imgImpl.flipHorizontally();
    imgImpl.brighten(20);

    // Get the resulting image
    Pixel[][] resultImage = imgImpl.getImage();

    // Save the processed image
    imgImpl.saveImage("res/JPG/output.jpg", resultImage);

    // Ensure that the result image is not null
    assertNotNull("Result image should not be null", resultImage);

    // Optionally, you could check if the output file exists after saving
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("Output image file should exist", outputFile.exists());
  }


  @Test
  public void testBrightenByZeroJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    imgImpl.brighten(0);
    Pixel[][] brightenedImage = imgImpl.getImage();
    assertNotNull(brightenedImage);
  }

  @Test
  public void testDarkenByZeroJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    imgImpl.darken(0);
    Pixel[][] darkenedImage = imgImpl.getImage();
    assertNotNull(darkenedImage);
  }

  @Test
  public void testApplyGreyScaleAndBlur() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Apply grayscale and blur filters
    imgImpl.applyGreyScale();
    imgImpl.applyBlur();

    // Get the resulting image
    Pixel[][] resultImage = imgImpl.getImage();

    // Save the processed image
    imgImpl.saveImage("res/JPG/output.jpg", resultImage);

    // Ensure that the result image is not null
    assertNotNull("Result image should not be null", resultImage);

    // Check if the output file exists after saving
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("Output image file should exist", outputFile.exists());
  }


  @Test
  public void testDoubleVerticalFlip() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Apply the vertical flip twice (should return the image to the original state)
    imgImpl.flipVertically();
    imgImpl.flipVertically();

    // Get the resulting image
    Pixel[][] verticalImage = imgImpl.getImage();

    // Save the processed image
    imgImpl.saveImage("res/JPG/output.jpg", verticalImage);

    // Ensure that the result image is not null
    assertNotNull("Result image should not be null", verticalImage);

    // Check if the output file exists after saving
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("Output image file should exist", outputFile.exists());
  }


  @Test
  public void testDoubleHorizontalFlip() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Load image and set it to the ImageImplementation instance
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);  // This assigns the loaded image to imgImpl

    // Apply the vertical flip twice (should return the image to the original state)
    imgImpl.flipHorizontally();
    imgImpl.flipHorizontally();

    // Get the resulting image
    Pixel[][] horizontalImage = imgImpl.getImage();

    // Save the processed image
    imgImpl.saveImage("res/JPG/output.jpg", horizontalImage);

    // Ensure that the result image is not null
    assertNotNull("Result image should not be null", horizontalImage);

    // Check if the output file exists after saving
    File outputFile = new File("res/JPG/output.jpg");
    assertTrue("Output image file should exist", outputFile.exists());

  }


  @Test
  public void testRedChannelAfterBrighten() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    imgImpl.brighten(50);
    imgImpl.splitChannels();
    Pixel[][] redChannel = imgImpl.getRedChannelImage();
    assertNotNull(redChannel);
  }

  @Test
  public void testBlurAndSepiaJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    imgImpl.applyBlur();
    imgImpl.applySepia();
    Pixel[][] resultImage = imgImpl.getImage();
    assertNotNull(resultImage);
  }

  @Test
  public void testFlipVerticalAndSharpenJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    imgImpl.flipVertically();
    imgImpl.applySharpen();
    Pixel[][] resultImage = imgImpl.getImage();
    assertNotNull(resultImage);
  }

  @Test
  public void testMultipleJpgLoadAndOperations() {
    ImageImplementation imgImpl1 = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    ImageImplementation imgImpl2 = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/Output/combined_building.jpg");
    imgImpl1.flipHorizontally();
    imgImpl2.brighten(20);
    assertNotNull(imgImpl1);
    assertNotNull(imgImpl2);
  }

  @Test
  public void testSepiaAfterGrayscale() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    imgImpl.applyGreyScale();
    imgImpl.applySepia();
    Pixel[][] resultImage = imgImpl.getImage();
    assertNotNull(resultImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineRgbDifferentDimensions() {
    ImageImplementation imgImpl = new ImageImplementation();
    int[][] red = new int[10][10];
    int[][] green = new int[20][20];
    int[][] blue = new int[10][10];
    imgImpl.combineChannels(red, green, blue);
  }

  @Test
  public void testBrightenWithLargeValue() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    imgImpl.brighten(300);
    Pixel[][] resultImage = imgImpl.getImage();
    assertNotNull(resultImage);
  }

  @Test
  public void testDarkenWithLargeValue() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    imgImpl.darken(300);
    Pixel[][] resultImage = imgImpl.getImage();
    assertNotNull(resultImage);
  }

  @Test
  public void testApplyBlurMultipleTimes() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    for (int i = 0; i < 5; i++) {
      imgImpl.applyBlur();
    }
    Pixel[][] resultImage = imgImpl.getImage();
    assertNotNull(resultImage);
  }

  @Test
  public void testApplySharpenMultipleTimes() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    for (int i = 0; i < 5; i++) {
      imgImpl.applySharpen();
    }
    Pixel[][] resultImage = imgImpl.getImage();
    assertNotNull(resultImage);
  }

  @Test
  public void testBrightenByMaxValue() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    imgImpl.brighten(255);
    Pixel[][] brightenedImage = imgImpl.getImage();

    for (int row = 0; row < brightenedImage.length; row++) {
      for (int col = 0; col < brightenedImage[0].length; col++) {
        assertEquals(255, brightenedImage[row][col].get(0));  // Red
        assertEquals(255, brightenedImage[row][col].get(1));  // Green
        assertEquals(255, brightenedImage[row][col].get(2));  // Blue
      }
    }
  }

  @Test
  public void testDarkenByMaxValue() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    imgImpl.darken(255);
    Pixel[][] darkenedImage = imgImpl.getImage();

    for (int row = 0; row < darkenedImage.length; row++) {
      for (int col = 0; col < darkenedImage[0].length; col++) {
        assertEquals(0, darkenedImage[row][col].get(0));  // Red
        assertEquals(0, darkenedImage[row][col].get(1));  // Green
        assertEquals(0, darkenedImage[row][col].get(2));  // Blue
      }
    }
  }

  @Test
  public void testBrightenWithNegativeValue() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.setImage(loadedImage);
    imgImpl.brighten(-50);
    Pixel[][] brightenedImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/building_out.jpg", brightenedImage);
    assertNotNull(brightenedImage);
  }


}