package model.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import exception.NotFoundException;
import model.visual.Image;

/**
 * A class that represents a memory that stores images in a HashMap.
 */
public class HashMapMemory implements ImageMemory {
  /**
   * A map that stores images.
   */
  private final Map<String, Image> memory;

  /**
   * Constructs a HashMapMemory object.
   * By default, creates an empty HashMap.
   */
  public HashMapMemory() {
    this.memory = new HashMap<>();
  }


  @Override
  public void addImage(String imageName, Image image) {
    memory.put(imageName, image);
  }

  @Override
  public Image getImage(String imageName) throws NotFoundException {
    return Optional.ofNullable(memory.get(imageName)).orElseThrow(
            () -> new NotFoundException(String.format("Image with name %s not found in memory", imageName))
    );
  }

  @Override
  public  boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if(!(obj instanceof HashMapMemory)) {
      return false;
    }
    HashMapMemory that = (HashMapMemory) obj;
    return memory.equals(that.memory);
  }

  @Override
  public int hashCode() {
    return memory.hashCode();
  }
}
