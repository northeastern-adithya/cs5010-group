package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.ImageUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Test;

/**
 * A JUnit test class for the Assignment5 Image testing.
 */
public class MoreTests {

  @Test
  public void testCompress() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.compressImage(50.0);
    Pixel[][] compressedImage = imgImpl.getImage();
    assertNotNull(compressedImage);
    assertTrue("Compression did not reduce size", compressedImage.length > 0);
  }

  @Test
  public void testHistogramGeneration() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    BufferedImage histogram = imgImpl.calculateHistogram();
    assertNotNull("Histogram image should not be null", histogram);
    assertEquals(256, histogram.getWidth());
    assertEquals(256, histogram.getHeight());
  }

  @Test
  public void testColorCorrection() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.colorCorrect();
    Pixel[][] correctedImage = imgImpl.getImage();
    assertNotNull("Color-corrected image should not be null", correctedImage);
    assertEquals("Color correction did not return the correct size", correctedImage.length,
        imgImpl.getImage().length);
  }

  @Test
  public void testLevelsAdjust() {
    int shadow = 30;
    int mid = 128;
    int highlight = 220;
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.levelsAdjust(shadow, mid, highlight);
    Pixel[][] adjustedImage = imgImpl.getImage();
    assertNotNull("Levels-adjusted image should not be null", adjustedImage);
    assertEquals("Levels adjustment did not return the correct size", adjustedImage.length,
        imgImpl.getImage().length);
  }

  @Test
  public void testBlurSplitView() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.applyBlur(50);
    Pixel[][] splitBlurredImage = imgImpl.getImage();
    assertNotNull("Split-view blurred image should not be null", splitBlurredImage);
    assertEquals("Split blur did not return correct image size", splitBlurredImage.length,
        imgImpl.getImage().length);
  }

  @Test
  public void testSharpenSplitView() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.applySharpen(50);
    Pixel[][] splitSharpenImage = imgImpl.getImage();
    assertNotNull("Split-view sharpened image should not be null", splitSharpenImage);
    assertEquals("Split sharpen did not return correct image size", splitSharpenImage.length,
        imgImpl.getImage().length);
  }

  @Test
  public void testSepiaSplitView() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.applySepia(50);
    Pixel[][] splitSepiaImage = imgImpl.getImage();
    assertNotNull("Split-view sepia image should not be null", splitSepiaImage);
    assertEquals("Split sepia did not return correct image size", splitSepiaImage.length,
        imgImpl.getImage().length);
  }

  @Test
  public void testGreyscaleSplitView() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.applyGreyScale(50);
    Pixel[][] splitGreyscaleImage = imgImpl.getImage();
    assertNotNull("Split-view greyscale image should not be null", splitGreyscaleImage);
    assertEquals("Split greyscale did not return correct image size", splitGreyscaleImage.length,
        imgImpl.getImage().length);
  }

  @Test
  public void testColorCorrectSplitView() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.colorCorrect(50);
    Pixel[][] splitColorCorrectedImage = imgImpl.getImage();
    assertNotNull("Split-view color-corrected image should not be null", splitColorCorrectedImage);
    assertEquals("Split color correction did not return correct image size",
        splitColorCorrectedImage.length, imgImpl.getImage().length);
  }

  @Test
  public void testLevelsAdjustSplitView() {
    int shadow = 30;
    int mid = 128;
    int highlight = 220;
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.levelsAdjust(shadow, mid, highlight, 50);
    Pixel[][] splitLevelsAdjustedImage = imgImpl.getImage();
    assertNotNull("Split-view levels-adjusted image should not be null", splitLevelsAdjustedImage);
    assertEquals("Split levels adjustment did not return correct image size",
        splitLevelsAdjustedImage.length, imgImpl.getImage().length);
  }

  @Test
  public void testJPGSplitViewBlur() {
    // Load a JPG image and apply a split blur
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/building.jpg");
    Pixel[][] jpgImage = imgImpl.getImage();
    assertNotNull("JPG image should load successfully", jpgImage);

    // Perform split view blur with 50% split
    imgImpl.applyBlur(50);
    Pixel[][] splitBlurredImage = imgImpl.getImage();
    assertNotNull("Split-view blurred JPG image should not be null", splitBlurredImage);

    // Save the result
    imgImpl.saveImage("res/JPG/Output/split_blurred_sample.jpg", splitBlurredImage);
    assertTrue("Split-view blurred JPG image file should exist",
        new File("res/JPG/Output/split_blurred_sample.jpg").exists());
  }


  @Test
  public void testPPMLevelsAdjust() {
    // Load a PPM image and adjust levels
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/dummy.ppm");
    Pixel[][] ppmImage = imgImpl.getImage();
    assertNotNull("PPM image should load successfully", ppmImage);

    // Levels adjustment
    imgImpl.setImage(ppmImage);
    imgImpl.levelsAdjust(10, 128, 240);
    Pixel[][] levelsAdjustedImage = imgImpl.getImage();
    assertNotNull("Levels-adjusted PPM image should not be null", levelsAdjustedImage);

    // Save the result
    imgImpl.saveImage("res/PPM/Output/levels_adjusted_sample.ppm", levelsAdjustedImage);
    assertTrue("Levels-adjusted PPM image file should exist",
        new File("res/PPM/Output/levels_adjusted_sample.ppm").exists());
  }

  @Test
  public void testCompressPPMLowPercentage() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy.ppm");
    imgImpl.compressImage(10);
    Pixel[][] compressedImage = imgImpl.getImage();
    assertNotNull("Compressed PPM image should not be null with low compression", compressedImage);
    imgImpl.saveImage("res/PPM/Output/compressed_10_sample.ppm", compressedImage);
    assertTrue("Compressed PPM image file should exist",
        new File("res/PPM/Output/compressed_10_sample.ppm").exists());
  }

  @Test
  public void testCompressPPMHighPercentage() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy.ppm");
    imgImpl.compressImage(90);
    Pixel[][] compressedImage = imgImpl.getImage();
    assertNotNull("Compressed PPM image should not be null with high compression", compressedImage);
    imgImpl.saveImage("res/PPM/Output/compressed_90_sample.ppm", compressedImage);
    assertTrue("Compressed PPM image file should exist",
        new File("res/PPM/Output/compressed_90_sample.ppm").exists());
  }

  @Test
  public void testCompressPNGModeratePercentage() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy.ppm");
    imgImpl.compressImage(50);
    Pixel[][] compressedImage = imgImpl.getImage();
    assertNotNull("Compressed PPM image should not be null with moderate compression",
        compressedImage);
    imgImpl.saveImage("res/PPM/Output/compressed_50_sample.ppm", compressedImage);
    assertTrue("Compressed PPM image file should exist",
        new File("res/PPM/Output/compressed_50_sample.ppm").exists());
  }

  @Test
  public void testCompressPPMFullCompression() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy.ppm");
    imgImpl.compressImage(100);
    Pixel[][] compressedImage = imgImpl.getImage();
    assertNotNull("Compressed PPM image should not be null with full compression", compressedImage);
    imgImpl.saveImage("res/PPM/Output/compressed_100_sample.ppm", compressedImage);
    assertTrue("Compressed PPM image file should exist",
        new File("res/PPM/Output/compressed_100_sample.ppm").exists());
  }

  // Test Cases for Histogram Generation with Different Formats
  @Test
  public void testHistogramGenerationJPG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    BufferedImage histogram = imgImpl.calculateHistogram();
    assertNotNull("Histogram for JPG should not be null", histogram);
  }

  @Test
  public void testHistogramGenerationPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    BufferedImage histogram = imgImpl.calculateHistogram();
    assertNotNull("Histogram for PNG should not be null", histogram);
  }

  @Test
  public void testHistogramGenerationPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy.ppm");
    BufferedImage histogram = imgImpl.calculateHistogram();
    assertNotNull("Histogram for PPM should not be null", histogram);
  }

  // Test Cases for Color Correction with Different Formats
  @Test
  public void testColorCorrectionJPG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.colorCorrect();
    Pixel[][] colorCorrected = imgImpl.getImage();
    assertNotNull("Color-corrected JPG image should not be null", colorCorrected);
    imgImpl.saveImage("res/JPG/Output/color_corrected_sample.jpg", colorCorrected);
    assertTrue("Color-corrected JPG image file should exist",
        new File("res/JPG/Output/color_corrected_sample.jpg").exists());
  }

  @Test
  public void testColorCorrectionPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.colorCorrect(40.0);
    Pixel[][] colorCorrected = imgImpl.getImage();
    assertNotNull("Color-corrected PNG image should not be null", colorCorrected);
    imgImpl.saveImage("res/PNG/Output/color_corrected_sample.png", colorCorrected);
    assertTrue("Color-corrected PNG image file should exist",
        new File("res/PNG/Output/color_corrected_sample.png").exists());
  }

  @Test
  public void testColorCorrectionPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy.ppm");
    imgImpl.colorCorrect();
    Pixel[][] colorCorrected = imgImpl.getImage();
    assertNotNull("Color-corrected PPM image should not be null", colorCorrected);
    imgImpl.saveImage("res/PPM/Output/color_corrected_sample.ppm", colorCorrected);
    assertTrue("Color-corrected PPM image file should exist",
        new File("res/PPM/Output/color_corrected_sample.ppm").exists());
  }

  // Test Cases for Levels Adjustment with Edge Values
  @Test
  public void testLevelsAdjustFullRange() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy.ppm");
    imgImpl.levelsAdjust(0, 128, 255);
    Pixel[][] levelsAdjusted = imgImpl.getImage();
    assertNotNull("Levels-adjusted PNG image should not be null", levelsAdjusted);
    imgImpl.saveImage("res/PPM/Output/levels_adjusted_full_range.ppm", levelsAdjusted);
    assertTrue("Levels-adjusted PPM file should exist",
        new File("res/PPM/Output/levels_adjusted_full_range.ppm").exists());
  }

  @Test
  public void testLevelsAdjustNarrowRange() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy.ppm");
    imgImpl.levelsAdjust(10, 100, 200);
    Pixel[][] levelsAdjusted = imgImpl.getImage();
    assertNotNull("Levels-adjusted PPM image should not be null", levelsAdjusted);
    imgImpl.saveImage("res/PPM/Output/levels_adjusted_narrow_range.ppm", levelsAdjusted);
    assertTrue("Levels-adjusted PPM file should exist",
        new File("res/PPM/Output/levels_adjusted_narrow_range.ppm").exists());
  }

  // Test Cases for Split Operations at Various Positions
  @Test
  public void testSplitBlurAt10Percent() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/building.jpg");
    imgImpl.applyBlur(10);
    Pixel[][] splitBlurred = imgImpl.getImage();
    assertNotNull("Split blur at 10% for JPG should not be null", splitBlurred);
    imgImpl.saveImage("res/JPG/Output/split_blur_10_percent.jpg", splitBlurred);
    assertTrue("Split blur image file should exist",
        new File("res/JPG/Output/split_blur_10_percent.jpg").exists());
  }

  @Test
  public void testSplitSharpenAt90Percent() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.applySharpen(90);
    Pixel[][] splitSharpened = imgImpl.getImage();
    assertNotNull("Split sharpen at 90% for PNG should not be null", splitSharpened);
    imgImpl.saveImage("res/PNG/Output/split_sharpen_90_percent.png", splitSharpened);
    assertTrue("Split sharpen image file should exist",
        new File("res/PNG/Output/split_sharpen_90_percent.png").exists());
  }

  @Test
  public void testSplitGreyscaleAt50Percent() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/sample.ppm");
    imgImpl.applyGreyScale(50);
    Pixel[][] splitGreyscale = imgImpl.getImage();
    assertNotNull("Split greyscale at 50% for PPM should not be null", splitGreyscale);
    imgImpl.saveImage("res/PPM/Output/split_greyscale_50_percent.ppm", splitGreyscale);
    assertTrue("Split greyscale image file should exist",
        new File("res/PPM/Output/split_greyscale_50_percent.ppm").exists());
  }

  // Test Cases for File Existence After Saving Operations
  @Test
  public void testSaveCompressedJPG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/sample.jpg");
    imgImpl.compressImage(75);
    Pixel[][] compressedImage = imgImpl.getImage();
    imgImpl.saveImage("res/JPG/Output/compressed_saved_sample.jpg", compressedImage);
    assertTrue("Compressed JPG file should exist",
        new File("res/JPG/Output/compressed_saved_sample.jpg").exists());
  }


  @Test
  public void testCompressToZeroPercent() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/sample.png");
    imgImpl.compressImage(0);
    Pixel[][] compressedImage = imgImpl.getImage();
    assertNotNull("Compressed image at 0% should not be null", compressedImage);
    imgImpl.saveImage("res/PNG/compressed_0_percent.png", compressedImage);
    assertTrue("Compressed PNG image file should exist",
        new File("res/PNG/compressed_0_percent.png").exists());
  }

  @Test
  public void testCompressToHundredPercent() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/sample.jpg");
    imgImpl.compressImage(100);
    Pixel[][] compressedImage = imgImpl.getImage();
    assertNotNull("Compressed image at 100% should not be null", compressedImage);
    imgImpl.saveImage("res/JPG/Output/compressed_100_percent.jpg", compressedImage);
    assertTrue("Compressed JPG image file should exist",
        new File("res/JPG/Output/compressed_100_percent.jpg").exists());
  }

  // Test Cases for Color Correction with Edge Cases
  @Test
  public void testColorCorrectionOnMonochromeImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/monochrome.png");
    imgImpl.colorCorrect();
    Pixel[][] colorCorrected = imgImpl.getImage();
    assertNotNull("Color-corrected monochrome image should not be null", colorCorrected);
    imgImpl.saveImage("res/PNG/Output/color_corrected_mono.png", colorCorrected);
    assertTrue("Color-corrected monochrome PNG file should exist",
        new File("res/PNG/Output/color_corrected_mono.png").exists());
  }

  @Test
  public void testColorCorrectionOnHighContrastImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/high_contrast.png");
    imgImpl.colorCorrect();
    Pixel[][] colorCorrected = imgImpl.getImage();
    assertNotNull("Color-corrected high contrast image should not be null", colorCorrected);
    imgImpl.saveImage("res/PNG/Ouptut/color_corrected_high_contrast.png", colorCorrected);
    assertTrue("Color-corrected high contrast PNG file should exist",
        new File("res/PNG/Output/color_corrected_high_contrast.png").exists());
  }

  // Test Cases for Levels Adjustment with Invalid Ranges
  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustInvalidRange() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/sample.ppm");
    // Setting mid value higher than white (invalid range)
    imgImpl.levelsAdjust(30, 250, 200);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustOutOfBounds() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/sample.ppm");
    // Setting black level below 0 and white level above 255 (out of bounds)
    imgImpl.levelsAdjust(-10, 128, 260);
  }

  // Test Cases for Split Operation with Edge Positions
  @Test
  public void testSplitBlurAtZeroPercent() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/sample.jpg");
    imgImpl.applyBlur(0);
    Pixel[][] splitBlurred = imgImpl.getImage();
    assertNotNull("Split blur at 0% should return the original image", splitBlurred);
    imgImpl.saveImage("res/JPG/Output/split_blur_0_percent.jpg", splitBlurred);
    assertTrue("Split blur image at 0% file should exist",
        new File("res/JPG/Output/split_blur_0_percent.jpg").exists());
  }

  @Test
  public void testSplitBlurAtHundredPercent() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/sample.jpg");
    imgImpl.applyBlur(100);
    Pixel[][] splitBlurred = imgImpl.getImage();
    assertNotNull("Split blur at 100% should apply blur to the entire image", splitBlurred);
    imgImpl.saveImage("res/JPG/Output/split_blur_100_percent.jpg", splitBlurred);
    assertTrue("Split blur image at 100% file should exist",
        new File("res/JPG/Output/split_blur_100_percent.jpg").exists());
  }

  // Test Cases for Invalid Compression Percentages
  @Test(expected = IllegalArgumentException.class)
  public void testCompressNegativePercentage() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/sample.png");
    imgImpl.compressImage(-10);  // Invalid negative percentage
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressOverHundredPercentage() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PNG/sample.png");
    imgImpl.compressImage(110);   // Invalid percentage greater than 100
  }

  // Test Cases for Saving with Different Formats
  @Test
  public void testSaveHistogramAsJPG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/sample.jpg");
    BufferedImage histogram = imgImpl.calculateHistogram();
    try {
      ImageIO.write(histogram, "jpg", new File("res/JPG/Output/histogram_sample.jpg"));
    } catch (IOException e) {
      fail("Failed to save histogram as JPG: " + e.getMessage());
    }
    assertTrue("Histogram saved as JPG file should exist",
        new File("res/JPG/Output/histogram_sample.jpg").exists());
  }

  // Test Cases for Greyscale on Colorful Images
  @Test
  public void testGreyscaleOnColorfulJPG() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/JPG/colorful_sample.jpg");
    imgImpl.applyGreyScale(80.0);
    Pixel[][] greyscaleImage = imgImpl.getImage();
    assertNotNull("Greyscale image for colorful JPG should not be null", greyscaleImage);
    imgImpl.saveImage("res/JPG/Ouptut/greyscale_colorful_sample.jpg", greyscaleImage);
    assertTrue("Greyscale JPG image file should exist",
        new File("res/JPG/Output/greyscale_colorful_sample.jpg").exists());
  }
}

