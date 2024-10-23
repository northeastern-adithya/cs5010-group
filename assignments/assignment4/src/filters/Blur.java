package filters;

/**
 * Represents a blur filter.
 * This is used to blur an image
 */
public class Blur extends AbstractFilter {
  /**
   * Constructs a Blur filter using gaussian blur kernel.
   */
  public Blur() {
    super(FilterOption.GAUSSIAN_BLUR);
  }
}