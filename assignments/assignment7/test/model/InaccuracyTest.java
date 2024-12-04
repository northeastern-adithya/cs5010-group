package model;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * A JUnit test class for the Inaccuracy Image testing.
 */
public class InaccuracyTest {


  @Test(expected = IllegalArgumentException.class)
  public void testCombineChannelsWithNullRedChannel() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.combineChannels(null, new int[2][2], new int[2][2]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineChannelsWithMismatchedDimensions() {
    ImageImplementation imgImpl = new ImageImplementation();
    int[][] red = new int[2][2];
    int[][] green = new int[3][3];
    int[][] blue = new int[2][2];
    imgImpl.combineChannels(red, green, blue);
  }


  @Test(expected = IllegalStateException.class)
  public void testApplyBlurOnEmptyImage() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Ensure the image is empty (initialized with 0x0 or set by default)
    imgImpl.applyBlur();

    // Ensure the image is still null or empty
    Pixel[][] image = imgImpl.getImage();
    assertNotNull(image);
    assertEquals(0, image.length);
    assertEquals(0, image[0].length);
  }


  @Test(expected = IllegalStateException.class)
  public void testApplySharpenOnEmptyImage() {
    ImageImplementation imgImpl = new ImageImplementation();

    // Ensure the image is empty (initialized with 0x0 or set by default)
    imgImpl.applySharpen();

    // Ensure the image is still null or empty
    Pixel[][] image = imgImpl.getImage();
    assertNotNull(image);
    assertEquals(0, image.length);
    assertEquals(0, image[0].length);
  }


  @Test
  public void testFlipHorizontallyWithNullImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.flipHorizontally();
    Pixel[][] flippedImage = imgImpl.getImage();
    assertNull(flippedImage);
  }

  @Test
  public void testFlipVerticallyWithNullImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.flipVertically();
    Pixel[][] flippedImage = imgImpl.getImage();
    assertNull(flippedImage);
  }

  @Test(expected = IllegalStateException.class)
  public void testSplitChannelsOnEmptyImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.splitChannels();
    assertNull(imgImpl.getRedChannelImage());
    assertNull(imgImpl.getGreenChannelImage());
    assertNull(imgImpl.getBlueChannelImage());
  }

  @Test(expected = IllegalStateException.class)
  public void testApplyGreyscaleOnNullImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.applyGreyScale();
    Pixel[][] greyImage = imgImpl.getImage();
    assertNull(greyImage);
  }

  @Test(expected = IllegalStateException.class)
  public void testApplySepiaOnNullImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.applySepia();
    Pixel[][] sepiaImage = imgImpl.getImage();
    assertNull(sepiaImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineEmptyChannels() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.combineChannels(null, null, null);
  }

  @Test
  public void testFlipHorizontallyNullImagePNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.flipHorizontally();
    Pixel[][] flippedImage = imgImpl.getImage();
    assertNull(flippedImage);
  }

  @Test
  public void testFlipVerticallyNullImagePNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.flipVertically();
    Pixel[][] flippedImage = imgImpl.getImage();
    assertNull(flippedImage);
  }

  @Test(expected = IllegalStateException.class)
  public void testApplyBlurOnNullImagePNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.applyBlur();
    Pixel[][] blurredImage = imgImpl.getImage();
    assertNull(blurredImage);
  }

  @Test(expected = IllegalStateException.class)
  public void testApplySharpenOnNullImagePNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.applySharpen();
    Pixel[][] sharpenedImage = imgImpl.getImage();
    assertNull(sharpenedImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveImageWithNullFilePath() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.saveImage(null, imgImpl.getImage());
  }

  @Test(expected = IllegalStateException.class)
  public void testSplitChannelsWithoutLoadingImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.splitChannels();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineChannelsWithDifferentSizes() {
    ImageImplementation imgImpl = new ImageImplementation();

    int[][] red = new int[10][10];
    int[][] green = new int[20][20];
    int[][] blue = new int[10][10];

    imgImpl.combineChannels(red, green, blue);
  }
}
