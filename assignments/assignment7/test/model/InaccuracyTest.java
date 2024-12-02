package model;

import controller.ImageUtil;
import org.junit.Test;

import java.io.File;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

/**
 * A JUnit test class for the Inaccuracy Image testing.
 */
public class InaccuracyTest {

  @Test
  public void testLoadImageWithEmptyFilepath() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] image = ImageUtil.loadImage("");
    assertNull(image);
  }

  @Test
  public void testSaveImageWithEmptyFilepath() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] image = new Pixel[2][2];
    image[0][0] = new Pixel(255, 0, 0);
    imgImpl.saveImage("", image);
    assertFalse(new File("").exists());
  }

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

  @Test
  public void testLoadImageWithInvalidFilepath() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] image = ImageUtil.loadImage("res/PPM/invalid_file.ppm");
    assertNull(image);
  }

  @Test
  public void testApplyBlurOnEmptyImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.applyBlur();
    Pixel[][] image = imgImpl.getImage();
    assertNull(image);
  }

  @Test
  public void testApplySharpenOnEmptyImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.applySharpen();
    Pixel[][] image = imgImpl.getImage();
    assertNull(image);
  }

  @Test
  public void testApplyCustomKernelPPM() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage("res/PPM/dummy_image.ppm");
    double[][] customKernel = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
    imgImpl.applyFilter(customKernel);
    Pixel[][] filteredImage = imgImpl.getImage();
    assertNotNull(filteredImage);
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

  @Test
  public void testSplitChannelsOnEmptyImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.splitChannels();
    assertNull(imgImpl.getRedChannelImage());
    assertNull(imgImpl.getGreenChannelImage());
    assertNull(imgImpl.getBlueChannelImage());
  }

  @Test
  public void testApplyGreyscaleOnNullImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.applyGreyScale();
    Pixel[][] greyImage = imgImpl.getImage();
    assertNull(greyImage);
  }

  @Test
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
  public void testLoadingNonExistingFile() {
    ImageImplementation imgImpl = new ImageImplementation();
    Pixel[][] image = ImageUtil.loadImage("res/JPG/non_existent.jpg");
    assertNull(image);
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

  @Test
  public void testApplyBlurOnNullImagePNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.applyBlur();
    Pixel[][] blurredImage = imgImpl.getImage();
    assertNull(blurredImage);
  }

  @Test
  public void testApplySharpenOnNullImagePNG() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.applySharpen();
    Pixel[][] sharpenedImage = imgImpl.getImage();
    assertNull(sharpenedImage);
  }

  @Test(expected = NullPointerException.class)
  public void testFlipWithoutLoadingImage() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.flipHorizontally();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadNullFilePath() {
    ImageImplementation imgImpl = new ImageImplementation();
    ImageUtil.loadImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveImageWithNullFilePath() {
    ImageImplementation imgImpl = new ImageImplementation();
    imgImpl.saveImage(null, imgImpl.getImage());
  }

  @Test
  public void testGrayscaleWithoutImageLoaded() {
    ImageImplementation imgImpl = new ImageImplementation();
    assertThrows(NullPointerException.class, imgImpl::applyGreyScale);
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
