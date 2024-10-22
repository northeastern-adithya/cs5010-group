package model.memory;


import java.util.Optional;

import exception.NotFoundException;
import model.visual.Image;

public interface ImageMemory {

   void addImage(String imageName, Image image);

   Image getImage(String imageName) throws NotFoundException;




   void removeImage(String imageName);

   boolean containsImage(String imageName);

}
