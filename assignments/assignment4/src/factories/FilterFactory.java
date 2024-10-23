package factories;


import exception.NotImplementedException;
import filters.Blur;
import filters.Filter;
import filters.FilterOptions;
import filters.Sharpen;

public class FilterFactory {

  private FilterFactory() {

  }
  public static Filter getFilter(FilterOptions options){
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
