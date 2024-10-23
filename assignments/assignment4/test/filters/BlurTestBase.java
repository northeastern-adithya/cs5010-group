package filters;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Base test class for Blur filter tests containing common setup and utilities.
 */
public abstract class BlurTestBase {
  protected Blur blurFilter;
  protected static final double DELTA = 0.0001;

  @Before
  public void setUp() {
    blurFilter = new Blur();
  }

  @Test
  public void testBlurConstructor() {
    assertNotNull("Blur filter should not be null", blurFilter);
    assertEquals("Filter option should be GAUSSIAN_BLUR",
            FilterOption.GAUSSIAN_BLUR,
            ((AbstractFilter) blurFilter).filterOption);
  }

  @Test
  public void testGaussianKernelValues() {
    double[][] kernel = FilterOption.GAUSSIAN_BLUR.getKernel();
    assertEquals("Kernel should be 3x3", 3, kernel.length);
    assertEquals("Kernel should be 3x3", 3, kernel[0].length);

    assertEquals("Center value should be 1/4", 0.25, kernel[1][1], DELTA);
    assertEquals("Corner value should be 1/16", 0.0625, kernel[0][0], DELTA);
    assertEquals("Edge value should be 1/8", 0.125, kernel[0][1], DELTA);
  }

  @Test(expected = NullPointerException.class)
  public void testApplyFilterWithNullImage() {
    blurFilter.applyFilter(null);
  }
}