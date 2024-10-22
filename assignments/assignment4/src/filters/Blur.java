package filters;

public class Blur extends AbstractFilter {
  public Blur() {
    this.kernel = FilterOptions.GAUSSIAN_BLUR.getKernel();
  }
}
