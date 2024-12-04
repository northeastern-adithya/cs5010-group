# USEME File for Image Processing Application

This file summarizes the commands supported by the image processing application. It provides
examples of how to use each command and notes any prerequisites or conditions necessary for
executing them.

## Supported Commands

### 1. Load Image

Command: `load <filepath> <image_name>`  
Description: Loads an image from the specified file path into the application.  
Example:

```
load res/PNG/flower.png flower
```

### 2. Save Image

Command: `save <filepath> <image_name>`  
Description: Saves the currently loaded image or the specified image in the application to the given
file path.  
Example:

```
save res/PNG/Output/flower.png flower
```

### 3. Brighten Image

Command: `brighten <amount> <image_name> <output_image_name>`  
Description: Brightens the specified image by the given amount. Values are clamped to the
range [0, 255].  
Example:

```
brighten 50 flower flower_brightened
```

### 4. Darken Image

Command: `brighten <amount> <image_name> <output_image_name>`  
Description: Darkens the specified image by the given amount. Values are clamped to the
range [0, 255].  
Example:

```
brighten -30 flower flower_darkened
```

### 5. Flip Image Horizontally

Command: `horizontal-flip <image_name> <output_image_name>`  
Description: Flips the specified image horizontally.  
Example:

```
horizontal-flip flower flower_horizontal
```

### 6. Flip Image Vertically

Command: `vertical-flip <image_name> <output_image_name>`  
Description: Flips the specified image vertically.  
Example:

```
vertical-flip flower flower_vertical
```

### 7. Split Channels

Command: `rgb-split <image_name> <red_output_name> <green_output_name> <blue_output_name>`  
Description: Splits the specified image into its red, green, and blue channels.  
Example:

```
rgb-split flower red_split green_split blue_split
```

### 8. Combine Channels

Command: `rgb-combine <output_image_name> <red_image_name> <green_image_name> <blue_image_name>`  
Description: Combines the individual red, green, and blue channel images back into a single RGB
image.  
Example:

```
rgb-combine combined_image red_split green_split blue_split
```

### 9. Apply Grayscale

Command: `greyscale <image_name> <output_image_name> [split <split_percentage>]`  
Description: Converts the specified image to grayscale. If a split percentage is provided, the
effect will only apply to the specified portion of the image.  
Example:

```
greyscale flower flower_grey
greyscale flower flower_grey split 30.0
```

### 10. Apply Blur

Command: `blur <image_name> <output_image_name> [split <split_percentage>]`  
Description: Applies a blur filter to the specified image. If a split percentage is provided, the
blur effect will only apply to the specified portion of the image.  
Example:

```
blur flower flower_blurred
blur flower flower_blurred split 50.0
```

### 11. Apply Sharpen

Command: `sharpen <image_name> <output_image_name> [split <split_percentage>]`  
Description: Applies a sharpening filter to the specified image. If a split percentage is provided,
the sharpen effect will only apply to the specified portion of the image.  
Example:

```
sharpen flower flower_sharpen
sharpen flower flower_sharpen split 20.0
```

### 12. Calculate Luma

Command: `luma-component <image_name> <output_image_name>`  
Description: Computes the luma component of the specified image.  
Example:

```
luma-component flower flower_luma
```

### 13. Calculate Intensity

Command: `intensity-component <image_name> <output_image_name>`  
Description: Computes the intensity of the specified image as the average of its color channels.  
Example:

```
intensity-component flower flower_intensity
```

### 14. Calculate Value

Command: `value-component <image_name> <output_image_name>`  
Description: Computes the value (maximum RGB channel) of the specified image.  
Example:

```
value-component flower flower_value
```

### 15. Apply Color Correction

Command: `color-correct <image_name> <output_image_name> [split <split_percentage>]`  
Description: Applies color correction to the specified image to balance color distribution. If a
split percentage is provided, the effect will only apply to the specified portion of the image.  
Example:

```
color-correct flower corrected_image
color-correct flower corrected_image split 30.0
```

### 16. Adjust Levels

Command:
`levels-adjust <shadow> <mid> <highlight> <image_name> <output_image_name> [split <split_percentage>]`  
Description: Adjusts the color levels of the specified image based on the provided shadow, midtone,
and highlight values. The adjustment can apply to the entire image or a split portion.  
Example:

```
levels-adjust 30 50 100 flower flower_adjust
levels-adjust 30 50 100 flower flower_adjust split 40.0
```

### 17. Compress Image

Command: `compress <threshold_percentage> <image_name> <output_image_name>`  
Description: Compresses the specified image using the Haar wavelet transform, applying a threshold
to reduce data size.  
Example:

```
compress 50.0 flower flower_compressed
```

### 18. Save Histogram

**Command:** `histogram <image_name> <output_image_name>`  
Description: Calculates the histogram of the specified image, showing the distribution of color
intensities.  
Example:

```
histogram flower display_histogram
```

### 19. Save Image

Command: `save <filepath> <image_name>`  
Description: Saves the generated images to the specified file path.  
Example:

```
save res/PNG/Output/display_histogram.png histogram
```

### 20. Downscale Image

Command: `downscale <width> <height> <input_image> <output_image>`  
Description: Downscales the input image to the specified width and height, and saves it under the
output image name.
Example:

```
downscale 30 30 original original_downscale
```

### 21. Red Component with a mask

Command: `red-component <input_image> <mask_image> <output_image>`  
Description: Extracts and applies the red component of the input image using the mask, and saves it
as the output image.
Example:

```
red-component original mask-image masked_red_image
```

### 22. Green Component with a mask

Command: `green-component <input_image> <mask_image> <output_image>`  
Description: Extracts and applies the green component of the input image using the mask, and saves
it as the output image.
Example:

```
green-component original mask-image masked_green_image
```

### 23. Blue Component with a mask

Command: `blue-component <input_image> <mask_image> <output_image>`  
Description: Extracts and applies the blue component of the input image using the mask, and saves it
as the output image.
Example:

```
blue-component original mask-image masked_blue_image
```

### 24. Value Component with a mask

Command: `value-component <input_image> <mask_image> <output_image>`  
Description: Extracts and applies the value component of the input image using the mask, and saves
it as the output image.
Example:

```
value-component original mask-image masked_value_image
```

### 25. Intensity Component with a mask

Command: `intensity-component <input_image> <mask_image> <output_image>`  
Description: Extracts and applies the intensity component of the input image using the mask, and
saves it as the output image.  
Example:

```
intensity-component original mask-image masked_intensity_image
```

### 26. Luma Component with a mask

Command: `luma-component <input_image> <mask_image> <output_image>`  
Description: Extracts and applies the luma component of the input image using the mask,
and saves it as the output image.
Example:

```
luma-component original mask-image masked_luma_image
```

### 27. Blur with a mask

Command: `blur <input_image> <mask_image> <output_image>`  
Description:Applies a blur effect to the input image using the mask, and saves it as the output
image.
Example:

```
blur original mask-image blur_masked_image
```

### 28. Sharpen with a mask

Command: `sharpen <input_image> <mask_image> <output_image>`  
Description: Applies a sharpen effect to the input image using the mask, and saves it as the output
image.
Example:

```
sharpen original mask-image sharpen_masked_image
```

### 29.Greyscale with a mask

Command: `greyscale <input_image> <mask_image> <output_image>`  
Description: Applies a greyscale effect to the input image using the mask, and saves it as the
output image.
Example:

```
greyscale original mask-image greyscale_masked_image
```

### 30.Greyscale with a mask

Command: `sepia <input_image> <mask_image> <output_image>`  
Description: Applies a sepia effect to the input image using the mask, and saves it as the output
image.
Example:

```
sepia original mask-image sepia_masked_image
```

### 31. Dithering 
Command: `dither <input_image> <output_image>`
Description: Applies a dithering effect to the input image and saves it as the output image.
Example:

```
dither original dithered_image
```

### 32. Dithering with split
Command: `dither <input_image> <output_image> split <split_percentage>`
Description: Applies a dithering effect to the input image and saves it as the output image. The effect will only apply to the specified portion of the image.
Example:

```
dither original dithered_image split 50
```

### How to run the application:

### Three command-line inputs are valid:

1. java -jar Program.jar -file path-of-script-file : when invoked in this manner the program should
   open the script file, execute it and then shut down.

2. java -jar Program.jar -text : when invoked in this manner the program should open in an
   interactive text mode, allowing the user to type the script and execute it one line at a time.
   This is how the program worked in the last assignment.

3. java -jar Program.jar : when invoked in this manner the program should open the graphical user
   interface. This is what will happen if you simply double-click on the jar file.

### Conditions

- Ensure an image is loaded before executing commands that require an image.
- When using commands that modify an image (e.g., `compress`, `sharpen`), the command should refer
  to a previously loaded image.

### GUI Interface (How to use)
- Dithering
   1. Load an image.
   2. Select the image to be loaded and hit "Open".
   3. Once image is loaded click on the "Dither" button.
   4. A pop-up box will appear. Enter the split % in the pop-up box and hit OK.

