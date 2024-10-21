package model.memory;


import java.util.Optional;

import model.visual.Image;

public interface ImageMemory {

   void addImage(String imageName, Image image);

   Optional<Image> getImage(String imageName) ;


   void removeImage(String imageName);

   boolean containsImage(String imageName);

}
