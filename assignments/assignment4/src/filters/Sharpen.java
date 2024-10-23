package filters;

/**
 * Represents a sharpen filter.
 * This is used to sharpen an image
 */
public class Sharpen extends AbstractFilter {
  /**
   * Constructs a Sharpen filter using sharpen kernel.
   */
  public Sharpen() {
    super(FilterOption.SHARPEN);
  }
}
