# Image Processor User Manual

## Table of Contents
- [Overview](#overview)
- [Basic Operations](#basic-operations)
- [Image Components](#image-components)
- [Image Transformations](#image-transformations)
- [Color Operations](#color-operations)
- [Effects and Filters](#effects-and-filters)
- [Analysis and Adjustment](#analysis-and-adjustment)
- [Program Control](#program-control)

## Overview
This document provides detailed instructions for using the Image Processor application. Each command follows a specific syntax and produces defined outputs.

## Basic Operations

### Loading and Saving Images
```
load image-path image-name
```
- **Purpose**: Load an image from the specified path
- **Parameters**:
    - `image-path`: Path to the source image file
    - `image-name`: Name to reference the image in the program
- **Example**: `load ./images/beach.jpg beach1`

```
save image-path image-name
```
- **Purpose**: Save an image to the specified path
- **Parameters**:
    - `image-path`: Destination path including filename
    - `image-name`: Name of the image to save
- **Example**: `save ./output/beach-modified.jpg beach1`

## Image Components

### Color Components
```
red-component image-name dest-image-name [p]
green-component image-name dest-image-name [p]
blue-component image-name dest-image-name [p]
```
- **Purpose**: Extract individual RGB color components
- **Parameters**:
    - `image-name`: Source image name
    - `dest-image-name`: Name for the resulting image
    - `p`: Optional parameter for split view
- **Example**: `red-component beach1 beach1-red`

### Value Components
```
value-component image-name dest-image-name
luma-component image-name dest-image-name
intensity-component image-name dest-image-name
```
- **Purpose**: Create different representations of image brightness
- **Parameters**:
    - `image-name`: Source image name
    - `dest-image-name`: Name for the resulting image
- **Example**: `luma-component beach1 beach1-luma`

## Image Transformations

### Flipping
```
horizontal-flip image-name dest-image-name
vertical-flip image-name dest-image-name
```
- **Purpose**: Flip image horizontally or vertically
- **Parameters**:
    - `image-name`: Source image name
    - `dest-image-name`: Name for the resulting image
- **Example**: `horizontal-flip beach1 beach1-flipped`

### Brightness Adjustment
```
brighten increment image-name dest-image-name
```
- **Purpose**: Adjust image brightness
- **Parameters**:
    - `increment`: Positive (brighten) or negative (darken) value
    - `image-name`: Source image name
    - `dest-image-name`: Name for the resulting image
- **Example**: `brighten 50 beach1 beach1-bright`

## Color Operations

### RGB Operations
```
rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue
```
- **Purpose**: Split image into RGB components
- **Parameters**:
    - `image-name`: Source image name
    - `dest-image-name-red/green/blue`: Names for component images
- **Example**: `rgb-split beach1 beach1-r beach1-g beach1-b`

```
rgb-combine image-name red-image green-image blue-image
```
- **Purpose**: Combine RGB components into single image
- **Parameters**:
    - `image-name`: Name for resulting image
    - `red/green/blue-image`: Names of component images
- **Example**: `rgb-combine beach1-new beach1-r beach1-g beach1-b`

## Effects and Filters

### Image Effects
```
blur image-name dest-image-name [p]
sharpen image-name dest-image-name [p]
sepia image-name dest-image-name [p]
```
- **Purpose**: Apply various image effects
- **Parameters**:
    - `image-name`: Source image name
    - `dest-image-name`: Name for the resulting image
    - `p`: Optional parameter for split view
- **Example**: `blur beach1 beach1-blurred`

### Compression
```
compress percentage image-name dest-image-name
```
- **Purpose**: Compress image by specified percentage
- **Parameters**:
    - `percentage`: Compression percentage (0-100)
    - `image-name`: Source image name
    - `dest-image-name`: Name for the resulting image
- **Example**: `compress 50 beach1 beach1-compressed`

## Analysis and Adjustment

### Image Analysis
```
histogram image-name dest-image-name
```
- **Purpose**: Generate image histogram
- **Parameters**:
    - `image-name`: Source image name
    - `dest-image-name`: Name for the resulting histogram image
- **Example**: `histogram beach1 beach1-hist`

### Color Adjustments
```
color-correct image-name dest-image-name [p]
```
- **Purpose**: Perform automatic color correction
- **Parameters**:
    - `image-name`: Source image name
    - `dest-image-name`: Name for the resulting image
    - `p`: Optional parameter for split view
- **Example**: `color-correct beach1 beach1-corrected`

```
levels-adjust b m w image-name dest-image-name [p]
```
- **Purpose**: Adjust image levels
- **Parameters**:
    - `b`: Black point value
    - `m`: Mid point value
    - `w`: White point value
    - `image-name`: Source image name
    - `dest-image-name`: Name for the resulting image
    - `p`: Optional parameter for split view
- **Example**: `levels-adjust 20 128 255 beach1 beach1-adjusted`

## Program Control

### Script Execution
```
run script-file
```
- **Purpose**: Execute commands from a script file
- **Parameters**:
    - `script-file`: Path to script file
- **Example**: `run ./scripts/process-beach.txt`

### Program Commands
```
quit
```
- **Purpose**: Exit the program

```
help
```
- **Purpose**: Display help message with command information