package model.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import exception.NotFoundException;
import model.visual.Image;

public class HashmapMemory  implements ImageMemory{
  private final Map<String, Image> memory;

  public HashmapMemory() {
    this(new HashMap<>());
  }

  public HashmapMemory(Map<String, Image> memory) {
    if (Objects.isNull(memory)) {
      memory = new HashMap<>();
    }
    this.memory = memory;
  }

  public void addImage(String imageName, Image image) {
    memory.put(imageName, image);
  }

  public Image getImage(String imageName) throws NotFoundException {
    return Optional.ofNullable(memory.get(imageName)).orElseThrow(
        () -> new NotFoundException(String.format("Image with name %s not found in memory", imageName))
    );
  }


  public void removeImage(String imageName) {
    memory.remove(imageName);
  }

  public boolean containsImage(String imageName) {
    return memory.containsKey(imageName);
  }
}
