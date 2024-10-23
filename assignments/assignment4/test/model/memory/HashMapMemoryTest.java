package model.memory;

import org.junit.Before;
import org.junit.Test;

import exception.ImageProcessorException;
import model.pixels.Pixel;
import model.visual.Image;
import model.visual.RenderedImage;

import static org.junit.Assert.*;

public class HashMapMemoryTest {

  private HashMapMemory memory;
  private Image testImage;

  @Before
  public void setUp() {
    memory = new HashMapMemory();
    Pixel[][] pixels = new Pixel[0][0];
    testImage = new RenderedImage(pixels);
  }

  @Test
  public void testConstructor() {
    assertNotNull(memory);
  }

  @Test
  public void testAddAndRetrieveImage() throws ImageProcessorException.NotFoundException {
    memory.addImage("testImage", testImage);
    assertEquals(testImage, memory.getImage("testImage"));
  }

  @Test(expected= ImageProcessorException.NotFoundException.class)
  public void testAddNullImage() throws ImageProcessorException.NotFoundException {
    memory.addImage("nullImage", null);
    assertNull(memory.getImage("nullImage"));
  }

  @Test(expected = ImageProcessorException.NotFoundException.class)
  public void testRetrieveNonExistentImage() throws ImageProcessorException.NotFoundException {
    memory.getImage("nonExistentImage");
  }
}