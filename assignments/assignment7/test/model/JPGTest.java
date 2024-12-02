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

    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.flipHorizontally();
    Pixel[][] flippedImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", flippedImage);
    assertNotNull(flippedImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyFlippedImage = ImageUtil.loadImage("res/JPG/building_horizontal.jpg");

    for (int row = 0; row < flippedImage.length; row++) {
      for (int col = 0; col < flippedImage[0].length; col++) {
        assertEquals("Mismatch at row " + row + ", col " + col,
            manuallyFlippedImage[row][col], flippedImage[row][col]);
      }
    }
  }


  @Test
  public void testFlipVerticallyJPG() {
    ImageImplementation imgImpl = new ImageImplementation();

    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.flipVertically();
    Pixel[][] flippedImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", flippedImage);
    assertNotNull(flippedImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyFlippedImage = ImageUtil.loadImage("res/JPG/Output/building_vertical.jpg");

    for (int row = 0; row < flippedImage.length; row++) {
      for (int col = 0; col < flippedImage[0].length; col++) {
        assertEquals("Mismatch at row " + row + ", col " + col,
            manuallyFlippedImage[row][col], flippedImage[row][col]);
      }
    }
  }

  @Test
  public void testBrightenJpgImage() {
    ImageImplementation imgImpl = new ImageImplementation();

    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.brighten(50);
    Pixel[][] brightenedImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", brightenedImage);
    assertNotNull(brightenedImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyBrightenedImage = ImageUtil.loadImage(
        "res/JPG/Output/brightened_building.jpg");

    for (int row = 0; row < brightenedImage.length; row++) {
      for (int col = 0; col < brightenedImage[0].length; col++) {
        Pixel actual = brightenedImage[row][col];
        Pixel expected = manuallyBrightenedImage[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }


  @Test
  public void testDarkenJpgImage() {
    ImageImplementation imgImpl = new ImageImplementation();

    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.darken(50);
    Pixel[][] darkenedImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", darkenedImage);
    assertNotNull(darkenedImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyDarkenedImage = ImageUtil.loadImage("res/JPG/darkened_building.jpg");

    for (int row = 0; row < darkenedImage.length; row++) {
      for (int col = 0; col < darkenedImage[0].length; col++) {
        Pixel actual = darkenedImage[row][col];
        Pixel expected = manuallyDarkenedImage[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }

  @Test
  public void testRedComponentJpg() {

    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.splitChannels();
    Pixel[][] redChannel = imgImpl.getRedChannelImage();
    imgImpl.saveImage("res/JPG/output.jpg", redChannel);
    assertNotNull(redChannel);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyRedChannelImage = ImageUtil.loadImage("res/JPG/Output/building_red.jpg");

    for (int row = 0; row < redChannel.length; row++) {
      for (int col = 0; col < redChannel[0].length; col++) {
        Pixel actual = redChannel[row][col];
        Pixel expected = manuallyRedChannelImage[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }


  @Test
  public void testGreenComponentJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.splitChannels();
    Pixel[][] greenChannel = imgImpl.getGreenChannelImage();
    imgImpl.saveImage("res/JPG/output.jpg", greenChannel);
    assertNotNull(greenChannel);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyGreenChannelImage = ImageUtil.loadImage("res/JPG/Output/building_green.jpg");

    for (int row = 0; row < greenChannel.length; row++) {
      for (int col = 0; col < greenChannel[0].length; col++) {
        Pixel actual = greenChannel[row][col];
        Pixel expected = manuallyGreenChannelImage[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }

  @Test
  public void testBlueComponentJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.splitChannels();
    Pixel[][] blueChannel = imgImpl.getBlueChannelImage();
    imgImpl.saveImage("res/JPG/output.jpg", blueChannel);
    assertNotNull(blueChannel);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyBlueChannelImage = ImageUtil.loadImage("res/JPG/Output/building_blue.jpg");

    for (int row = 0; row < blueChannel.length; row++) {
      for (int col = 0; col < blueChannel[0].length; col++) {
        Pixel actual = blueChannel[row][col];
        Pixel expected = manuallyBlueChannelImage[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }

  @Test
  public void testSplitJpgImageIntoRGB() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.splitChannels();
    assertNotNull(imgImpl.getRedChannelImage());
    assertNotNull(imgImpl.getGreenChannelImage());
    assertNotNull(imgImpl.getBlueChannelImage());
  }

  @Test
  public void testApplyBlurJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.applyBlur();
    Pixel[][] blurredImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", blurredImage);
    assertNotNull(blurredImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyBlurred = ImageUtil.loadImage("res/JPG/Output/blurred_building.jpg");

    for (int row = 0; row < blurredImage.length; row++) {
      for (int col = 0; col < blurredImage[0].length; col++) {
        Pixel actual = blurredImage[row][col];
        Pixel expected = manuallyBlurred[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }

  @Test
  public void testApplySharpenJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.applySharpen();
    Pixel[][] sharpenImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", sharpenImage);
    assertNotNull(sharpenImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallySharpen = ImageUtil.loadImage("res/JPG/Output/sharpened_building.jpg");

    for (int row = 0; row < sharpenImage.length; row++) {
      for (int col = 0; col < sharpenImage[0].length; col++) {
        Pixel actual = sharpenImage[row][col];
        Pixel expected = manuallySharpen[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }

  @Test
  public void testApplySepiaJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.applySepia();
    Pixel[][] sepiaImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", sepiaImage);
    assertNotNull(sepiaImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallySepia = ImageUtil.loadImage("res/JPG/Output/building_sepia.jpg");

    for (int row = 0; row < sepiaImage.length; row++) {
      for (int col = 0; col < sepiaImage[0].length; col++) {
        Pixel actual = sepiaImage[row][col];
        Pixel expected = manuallySepia[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }

  @Test
  public void testGrayscaleLumaJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.getLuma();
    Pixel[][] lumaImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", lumaImage);
    assertNotNull(lumaImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyLuma = ImageUtil.loadImage("res/JPG/Output/building_luma.jpg");

    for (int row = 0; row < lumaImage.length; row++) {
      for (int col = 0; col < lumaImage[0].length; col++) {
        Pixel actual = lumaImage[row][col];
        Pixel expected = manuallyLuma[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }

  @Test
  public void testGrayscaleIntensityJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.getIntensity();
    Pixel[][] intensityImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", intensityImage);
    assertNotNull(intensityImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyIntensity = ImageUtil.loadImage("res/JPG/Output/building_intensity.jpg");

    for (int row = 0; row < intensityImage.length; row++) {
      for (int col = 0; col < intensityImage[0].length; col++) {
        Pixel actual = intensityImage[row][col];
        Pixel expected = manuallyIntensity[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }

  @Test
  public void testGrayscaleValueJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.getValue();
    Pixel[][] valueImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", valueImage);
    assertNotNull(valueImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyValue = ImageUtil.loadImage("res/JPG/Output/building_value.jpg");

    for (int row = 0; row < valueImage.length; row++) {
      for (int col = 0; col < valueImage[0].length; col++) {
        Pixel actual = valueImage[row][col];
        Pixel expected = manuallyValue[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }

  @Test
  public void testLoadAndApplyMultipleFilters() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.flipHorizontally();
    imgImpl.brighten(20);
    Pixel[][] resultImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", resultImage);
    assertNotNull(resultImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyImage = ImageUtil.loadImage("res/JPG/Output/flipped_bright_building.jpg");

    for (int row = 0; row < resultImage.length; row++) {
      for (int col = 0; col < resultImage[0].length; col++) {
        Pixel actual = resultImage[row][col];
        Pixel expected = manuallyImage[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }

  @Test
  public void testBrightenByZeroJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.brighten(0);
    Pixel[][] brightenedImage = imgImpl.getImage();
    assertNotNull(brightenedImage);
  }

  @Test
  public void testDarkenByZeroJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.darken(0);
    Pixel[][] darkenedImage = imgImpl.getImage();
    assertNotNull(darkenedImage);
  }

  @Test
  public void testGrayscaleAndBlurJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.applyGreyScale();
    imgImpl.applyBlur();
    Pixel[][] resultImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", resultImage);
    assertNotNull(resultImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyImage = ImageUtil.loadImage("res/JPG/Output/grey_blur.jpg");

    for (int row = 0; row < resultImage.length; row++) {
      for (int col = 0; col < resultImage[0].length; col++) {
        Pixel actual = resultImage[row][col];
        Pixel expected = manuallyImage[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }
  }

  @Test
  public void testDoubleVerticalFlip() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.flipVertically();
    imgImpl.flipVertically();
    Pixel[][] verticalImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", verticalImage);
    assertNotNull(verticalImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyImage = ImageUtil.loadImage("res/JPG/building.jpg");

    for (int row = 0; row < verticalImage.length; row++) {
      for (int col = 0; col < verticalImage[0].length; col++) {
        Pixel actual = verticalImage[row][col];
        Pixel expected = manuallyImage[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }

  }

  @Test
  public void testDoubleHorizontalFlip() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.flipHorizontally();
    imgImpl.flipHorizontally();
    Pixel[][] horizontalImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/output.jpg", horizontalImage);
    assertNotNull(horizontalImage);

    ImageImplementation imgImpl2 = new ImageImplementation();
    Pixel[][] manuallyImage = ImageUtil.loadImage("res/JPG/building.jpg");

    for (int row = 0; row < horizontalImage.length; row++) {
      for (int col = 0; col < horizontalImage[0].length; col++) {
        Pixel actual = horizontalImage[row][col];
        Pixel expected = manuallyImage[row][col];

        assertEquals(
            "Mismatch at row " + row + ", col " + col + " (Expected RGB: " + expected.get(0)
                + "," + expected.get(1) + "," + expected.get(2) + " Actual RGB: " + actual.get(0)
                + "," + actual.get(1) + "," + actual.get(2) + ")", expected, actual);
      }
    }

  }


  @Test
  public void testRedChannelAfterBrighten() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.brighten(50);
    imgImpl.splitChannels();
    Pixel[][] redChannel = imgImpl.getRedChannelImage();
    assertNotNull(redChannel);
  }

  @Test
  public void testBlurAndSepiaJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.applyBlur();
    imgImpl.applySepia();
    Pixel[][] resultImage = imgImpl.getImage();
    assertNotNull(resultImage);
  }

  @Test
  public void testFlipVerticalAndSharpenJpg() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
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
    ImageUtil.loadImage("res/JPG/building.jpg");
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
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.brighten(300);
    Pixel[][] resultImage = imgImpl.getImage();
    assertNotNull(resultImage);
  }

  @Test
  public void testDarkenWithLargeValue() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.darken(300);
    Pixel[][] resultImage = imgImpl.getImage();
    assertNotNull(resultImage);
  }

  @Test
  public void testApplyBlurMultipleTimes() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    for (int i = 0; i < 5; i++) {
      imgImpl.applyBlur();
    }
    Pixel[][] resultImage = imgImpl.getImage();
    assertNotNull(resultImage);
  }

  @Test
  public void testApplySharpenMultipleTimes() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("test.jpg");
    for (int i = 0; i < 5; i++) {
      imgImpl.applySharpen();
    }
    Pixel[][] resultImage = imgImpl.getImage();
    assertNotNull(resultImage);
  }

  @Test
  public void testBrightenByMaxValue() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
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
    ImageUtil.loadImage("test.jpg");
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
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.brighten(-50);
    Pixel[][] brightenedImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/building_out.jpg", brightenedImage);
    assertNotNull(brightenedImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyFilterWithInvalidKernel() {
    ImageImplementation imgImpl = new ImageImplementation();
    double[][] invalidKernel = new double[1][2];
    imgImpl.applyFilter(invalidKernel);
  }


}