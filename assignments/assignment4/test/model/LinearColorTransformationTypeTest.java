package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class LinearColorTransformationTypeTest {
  @Test
  public void testLumaKernel() {
    double[][] expectedLumaKernel = {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };
    assertArrayEquals(expectedLumaKernel, LinearColorTransformationType.LUMA.getKernel());
  }

  @Test
  public void testSepiaKernel() {
    double[][] expectedSepiaKernel = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
    assertArrayEquals(expectedSepiaKernel, LinearColorTransformationType.SEPIA.getKernel());
  }
}