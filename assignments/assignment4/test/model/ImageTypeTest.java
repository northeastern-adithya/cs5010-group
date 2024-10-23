package model;

import exception.ImageProcessingRunTimeException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImageTypeTest {

  @Test
  public void testFromExtension_png() {
    ImageType imageType = ImageType.fromExtension("png");
    assertEquals(ImageType.PNG, imageType);
  }

  @Test
  public void testFromExtension_ppm() {
    ImageType imageType = ImageType.fromExtension("ppm");
    assertEquals(ImageType.PPM, imageType);
  }

  @Test
  public void testFromExtension_jpg() {
    ImageType imageType = ImageType.fromExtension("jpg");
    assertEquals(ImageType.JPG, imageType);
  }

  @Test
  public void testFromExtension_jpeg() {
    ImageType imageType = ImageType.fromExtension("jpeg");
    assertEquals(ImageType.JPEG, imageType);
  }

  @Test(expected = ImageProcessingRunTimeException.NotImplementedException.class)
  public void testFromExtension_unsupported() {
    ImageType.fromExtension("gif");
  }

  @Test
  public void testGetExtension() {
    assertEquals("png", ImageType.PNG.getExtension());
    assertEquals("ppm", ImageType.PPM.getExtension());
    assertEquals("jpg", ImageType.JPG.getExtension());
    assertEquals("jpeg", ImageType.JPEG.getExtension());
  }
}