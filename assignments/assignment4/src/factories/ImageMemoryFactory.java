package factories;

import model.memory.HashmapMemory;
import model.memory.ImageMemory;

public class ImageMemoryFactory {

  private ImageMemoryFactory(){

  }


  public static ImageMemory getImageMemory(){
    return new HashmapMemory();
  }
}
