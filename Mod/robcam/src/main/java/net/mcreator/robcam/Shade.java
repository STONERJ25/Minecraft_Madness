/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.robcam as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.robcam.procedures;


import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)

public class Shade {
    private static final int TARGET_WIDTH = 50;
    private static final int TARGET_HEIGHT = 50;
    private static final int COLOR_COUNT = 16;

    public static int[][] exe() {
                String inputImagePath = "C:\\Users\\Joshua Stoner\\Downloads\\green.jpg";  // Path to the original image
        String outputImagePath = "C:\\Users\\Joshua Stoner\\Downloads\\green1.jpg";  // Path for the compressed image
//ImageIO.read(new File("C:\\Users\\Joshua Stoner\\Downloads\\green.jpg"));
        try {
            // Read the original image
            BufferedImage originalImage = ImageIO.read(new File(inputImagePath));
            
            // Set the target width and height
            int targetWidth = 50;
            int targetHeight = 50;
            
            // Create a new BufferedImage with the target dimensions
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            
            // Draw the original image resized into the resizedImage
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            g2d.dispose();
            
            // Save the resized image to the specified output path
            ImageIO.write(resizedImage, "jpg", new File(outputImagePath));
            
            System.out.println("Image compressed and saved as " + outputImagePath);
             BufferedImage image = ImageIO.read(new File(outputImagePath));
            
            // Get image dimensions
            int width = image.getWidth();
            int height = image.getHeight();
            
            // Array to store hex values of each pixel

            
            // Loop through each pixel and convert RGB to hex
                    int[][] colorArray = new int[image.getHeight()][image.getWidth()];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Get RGB color as a single integer
                    int rgb = image.getRGB(x, y);
                    
                    // Extract 8-bit values for red, green, and blue
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    
                    // Combine RGB values into an 8-bit hex format
                    int hexValue = (red << 16) | (green << 8) | blue;

                    // Store in array
                    colorArray[y][x]= hexValue;
                }
            }
            return colorArray;
            
        } catch (IOException e) {
            System.err.println("Error processing the image: " + e.getMessage());
            return null;
        }}
    public static void main(String[] args) {
        exe(); // Execute the process
    }
}

