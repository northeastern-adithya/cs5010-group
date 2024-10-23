package model;

import exception.NotImplementedException;
import java.awt.image.BufferedImage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PixelTypeTest {

  @Test
  public void testFromBufferedImageTypeSupportedTypes() {
    assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_INT_RGB));
    assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_INT_ARGB));
    assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_INT_ARGB_PRE));
    assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_INT_BGR));
    assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_3BYTE_BGR));
    assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_4BYTE_ABGR));
    assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_4BYTE_ABGR_PRE));
    assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_USHORT_565_RGB));
    assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_USHORT_555_RGB));
  }

  @Test(expected = NotImplementedException.class)
  public void testFromBufferedImageTypeUnsupportedType() {
    PixelType.fromBufferedImageType(BufferedImage.TYPE_BYTE_BINARY);
  }

  @Test(expected = NotImplementedException.class)
  public void testFromBufferedImageTypeInvalidType() {
    PixelType.fromBufferedImageType(-1);
  }
}