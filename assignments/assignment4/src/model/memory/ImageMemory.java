package model.memory;

import exception.NotFoundException;
import model.visual.Image;

public interface ImageMemory {

  void addImage(String imageName, Image image);

  Image getImage(String imageName) throws NotFoundException;

}
