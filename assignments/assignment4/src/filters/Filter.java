package filters;

import model.visual.Image;

/**
 * Interface for applying filters to an image.
 * Provides a method to apply a filter to an image.
 */
public interface Filter {
  /**
   * Applies a filter to an image.
   * The filter is applied by involving the image with the filter's kernel.
   * For each pixel in the image, the kernel is centered on the pixel, and the
   * surrounding pixels are multiplied by the corresponding kernel values.
   * The results are summed to produce the new pixel value.
   *
   * @param image the image to apply the filter to
   * @return the image with the filter applied
   */
  Image applyFilter(Image image);
}
