package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import model.visual.Image;

public class ImageMemory {
  private final Map<String, Image> memory;

  public ImageMemory() {
    this(new HashMap<>());
  }

  public ImageMemory(Map<String, Image> memory) {
    if (Objects.isNull(memory)) {
      memory = new HashMap<>();
    }
    this.memory = memory;
  }

  public void addImage(String imageName, Image image) {
    memory.put(imageName, image);
  }

  public Image getImage(String imageName) {
    return memory.get(imageName);
  }


  public void removeImage(String imageName) {
    memory.remove(imageName);
  }

  public boolean containsImage(String imageName) {
    return memory.containsKey(imageName);
  }

}
