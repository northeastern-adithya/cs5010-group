package filters;

import model.visual.Image;

public interface Filter {
  Image applyFilter(Image image);
}
