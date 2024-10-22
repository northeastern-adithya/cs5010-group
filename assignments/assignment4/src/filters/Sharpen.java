package filters;

public class Sharpen extends AbstractFilter {
  public Sharpen() {
    this.kernel = FilterOptions.SHARPEN.getKernel();
  }
}
