package factories;


import exception.NotImplementedException;
import filters.Blur;
import filters.Filter;
import filters.FilterOptions;
import filters.Sharpen;

/**
 * A factory that creates filters.
 */
public class FilterFactory {

  private FilterFactory() {
    //Empty private constructor to prevent instantiation.
  }

  /**
   * Creates a filter based on the given options.
   *
   * @param options the options to create the filter
   * @return the filter based on the given options
   * @throws NotImplementedException if the filter is not implemented
   */
  public static Filter getFilter(FilterOptions options) throws NotImplementedException {
    switch (options) {
      case SHARPEN:
        return new Sharpen();
      case GAUSSIAN_BLUR:
        return new Blur();
      default:
        throw new NotImplementedException(String.format("Received an unsupported filter type: %s", options));
    }
  }
}
