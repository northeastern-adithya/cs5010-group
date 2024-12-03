package model;
import controller.ImageUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * A JUnit test class for the PNG Image testing.
 */
public class PNGTest {

  @Test
  public void testLoadPNGImageSuccess() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] image = ImageUtil.loadImage("res/PNG/flower.png");
    assertNotNull(image);
  }

  @Test
  public void testFlipHorizontallyPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    imgImpl.flipHorizontally();
    Pixel[][] flippedImage = imgImpl.getImage();
    assertNotNull(flippedImage);
    Pixel[][] originalImage = ImageUtil.loadImage("res/PNG/flower.png");
    assertEquals(originalImage[0][0], flippedImage[0][flippedImage[0].length - 1]);
  }

  @Test
  public void testFlipVerticallyPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    imgImpl.flipVertically();
    Pixel[][] flippedImage = imgImpl.getImage();
    assertNotNull(flippedImage);

  }

  @Test
  public void testBrightenImagePNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    imgImpl.brighten(50);
    Pixel[][] brightenedImage = imgImpl.getImage();
    assertNotNull(brightenedImage);

    Pixel pixel = brightenedImage[0][0];
    assertTrue(pixel.get(0) <= 255 && pixel.get(1) <= 255 && pixel.get(2) <= 255);
  }


  @Test
  public void testDarkenImagePNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    imgImpl.darken(50);
    Pixel[][] darkenedImage = imgImpl.getImage();
    assertNotNull(darkenedImage);

    Pixel pixel = darkenedImage[0][0];
    assertTrue(pixel.get(0) <= 255 && pixel.get(1) <= 255 && pixel.get(2) <= 255);
  }

  @Test
  public void testSplitChannelsPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    imgImpl.splitChannels();
    assertNotNull(imgImpl.getRedChannelImage());
    assertNotNull(imgImpl.getGreenChannelImage());
    assertNotNull(imgImpl.getBlueChannelImage());
  }

  @Test
  public void testApplyGreyscaleOnPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    imgImpl.applyGreyScale();
    Pixel[][] greyImage = imgImpl.getImage();
    assertNotNull(greyImage);
    assertEquals(greyImage[0][0].get(0), greyImage[0][0].get(1));
    assertEquals(greyImage[0][0].get(1), greyImage[0][0].get(2));
  }

  @Test
  public void testApplySepiaOnPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    imgImpl.applySepia();
    Pixel[][] sepiaImage = imgImpl.getImage();
    assertNotNull(sepiaImage);

    Pixel pixel = sepiaImage[0][0];
    assertTrue(pixel.get(0) <= 255 && pixel.get(1) <= 255 && pixel.get(2) <= 255);
  }

  @Test
  public void testBrightenBeyondBoundsPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
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
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
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
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    imgImpl.splitChannels();
    Pixel[][] redChannel = imgImpl.getRedChannelImage();
    assertNotNull(redChannel);
  }

  @Test
  public void testVisualizeGreenChannelPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    imgImpl.splitChannels();
    Pixel[][] greenChannel = imgImpl.getGreenChannelImage();
    assertNotNull(greenChannel);
  }

  @Test
  public void testVisualizeBlueChannelPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    imgImpl.splitChannels();
    Pixel[][] blueChannel = imgImpl.getBlueChannelImage();
    assertNotNull(blueChannel);
  }

  @Test
  public void testVisualizeValuePNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    Pixel[][] valueImage = imgImpl.getValue();
    assertNotNull(valueImage);
  }

  @Test
  public void testVisualizeIntensityPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    Pixel[][] intensityImage = imgImpl.getIntensity();
    assertNotNull(intensityImage);
  }

  @Test
  public void testVisualizeLumaPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    Pixel[][] lumaImage = imgImpl.getLuma();
    assertNotNull(lumaImage);
  }

  @Test
  public void testApplyBlurPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    imgImpl.applyBlur();
    Pixel[][] blurredImage = imgImpl.getImage();
    assertNotNull(blurredImage);
  }

  @Test
  public void testApplySharpenPNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
    imgImpl.applySharpen();
    Pixel[][] sharpenedImage = imgImpl.getImage();
    assertNotNull(sharpenedImage);
  }

  @Test
  public void testDownscaleValidPercentage() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);
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
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);

    // Apply downscale with 100%
    imgImpl.downscaleImage(200, 200);  // No change expected

    // Verify that the image dimensions remain the same (200x200)
    Pixel[][] downscaledImage = imgImpl.getImage();
    assertNotNull(downscaledImage);
    assertEquals(200, downscaledImage.length);  // Height should remain 200
    assertEquals(200, downscaledImage[0].length);  // Width should remain 200
  }


  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleInvalidPercentageNegative() {
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");

    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.setImage(loadedImage);

    // Apply downscale with negative percentage
    imgImpl.downscaleImage(-50, -50);  // Invalid downscale percentage
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplySepiaInvalidSplitPercentageGreaterThan100() {
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
  public void testApplyLevelsAdjustShadowMidHighlight() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] loadedImage = ImageUtil.loadImage("res/PNG/flower.png");
    imgImpl.setImage(loadedImage);


    // Define shadow, midtone, highlight values
    int shadow = 50;    // Darken shadows
    int mid = 100;  // Neutralize midtones
    int highlight = 200;  // Brighten highlights

    // Apply levels adjustment with shadow < midtone < highlight
    imgImpl.levelsAdjust(shadow, mid, highlight);
    Pixel[][] adjustedImage = imgImpl.getImage();

    assertNotNull(adjustedImage);
  }

}