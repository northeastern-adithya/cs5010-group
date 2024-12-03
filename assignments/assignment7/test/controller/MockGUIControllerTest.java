package controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for MockGUIController to verify that the correct methods log the expected
 * actions.
 */

public class MockGUIControllerTest {

  private MockGUIController mockController;

  @Before
  public void setUp() {
    mockController = new MockGUIController();
  }

  @Test
  public void testLoadImageLogsCorrectly() {
    // Act
    mockController.loadImage();

    // Assert
    assertTrue(mockController.getLog().contains("loadImage called"));
  }

  @Test
  public void testSaveImageLogsCorrectly() {
    // Act
    mockController.saveImage();

    // Assert
    assertTrue(mockController.getLog().contains("saveImage called"));
  }

  @Test
  public void testApplySepiaEffectLogsCorrectly() {
    // Act
    mockController.applySepiaEffect();

    // Assert
    assertTrue(mockController.getLog().contains("applySepiaEffect called"));
  }

  @Test
  public void testApplyBlurEffectLogsCorrectly() {
    // Act
    mockController.applyBlurEffect();

    // Assert
    assertTrue(mockController.getLog().contains("applyBlurEffect called"));
  }

  @Test
  public void testApplySharpenEffectLogsCorrectly() {
    // Act
    mockController.applySharpenEffect();

    // Assert
    assertTrue(mockController.getLog().contains("applySharpenEffect called"));
  }

  @Test
  public void testApplyGreyscaleEffectLogsCorrectly() {
    // Act
    mockController.applyGreyscaleEffect();

    // Assert
    assertTrue(mockController.getLog().contains("applyGreyscaleEffect called"));
  }

  @Test
  public void testApplyBrightenEffectLogsCorrectly() {
    // Act
    mockController.applyBrightenEffect();

    // Assert
    assertTrue(mockController.getLog().contains("applyBrightenEffect called"));
  }

  @Test
  public void testApplyVerticalFlipLogsCorrectly() {
    // Act
    mockController.applyVerticalFlip();

    // Assert
    assertTrue(mockController.getLog().contains("applyVerticalFlip called"));
  }

  @Test
  public void testApplyHorizontalFlipLogsCorrectly() {
    // Act
    mockController.applyHorizontalFlip();

    // Assert
    assertTrue(mockController.getLog().contains("applyHorizontalFlip called"));
  }

  @Test
  public void testApplyLevelsAdjustLogsCorrectly() {
    // Act
    mockController.applyLevelsAdjust();

    // Assert
    assertTrue(mockController.getLog().contains("applyLevelsAdjust called"));
  }

  @Test
  public void testApplyColorCorrectionLogsCorrectly() {
    // Act
    mockController.applyColorCorrection();

    // Assert
    assertTrue(mockController.getLog().contains("applyColorCorrection called"));
  }

  @Test
  public void testApplyCompressLogsCorrectly() {
    // Act
    mockController.applyCompress();

    // Assert
    assertTrue(mockController.getLog().contains("applyCompress called"));
  }

  @Test
  public void testApplyDownscaleLogsCorrectly() {
    // Act
    mockController.applyDownscale();

    // Assert
    assertTrue(mockController.getLog().contains("applyDownscale called"));
  }

  @Test
  public void testApplyRedComponentLogsCorrectly() {
    // Act
    mockController.visualizeRedComponent();

    // Assert
    assertTrue(mockController.getLog().contains("visualizeRedComponent called"));
  }

  @Test
  public void testApplyGreenComponentLogsCorrectly() {
    // Act
    mockController.visualizeGreenComponent();

    // Assert
    assertTrue(mockController.getLog().contains("visualizeGreenComponent called"));
  }

  @Test
  public void testApplyBlueComponentLogsCorrectly() {
    // Act
    mockController.visualizeBlueComponent();

    // Assert
    assertTrue(mockController.getLog().contains("visualizeBlueComponent called"));
  }

}
