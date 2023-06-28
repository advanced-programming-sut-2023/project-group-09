package test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageMergeAndSaveExample {

    public static void main(String[] args) {
        String[] colors = {"blue", "red", "orange", "yellow", "grey", "purple", "skyBlue", "green"};
        for (int i = 0; i < 8; i++) {
            int counter = 1;
            for (int j = 1; j <= 168; j++) {
                mergeImages(colors[i],counter);
                counter++;
            }
        }


    }


    public static void mergeImages(String color, int counter) {

        String imageFilePath1 = "files/test/tool./Image" + counter + ".png";
        String imageFilePath2 = "files/test/human/" + color + "/Image" + (counter + 168) + ".png";
        String mergedImageFilePath = "files/test/move/" + color + "/Image" + counter + ".png";

        try {
            // Load the two images to be merged
            BufferedImage image1 = ImageIO.read(new File(imageFilePath1));
            BufferedImage image2 = ImageIO.read(new File(imageFilePath2));

            // Create a merged image with dimensions equal to the larger of the two images
            int mergedWidth = Math.max(image1.getWidth(), image2.getWidth());
            int mergedHeight = Math.max(image1.getHeight(), image2.getHeight());
            BufferedImage mergedImage = new BufferedImage(mergedWidth, mergedHeight, BufferedImage.TYPE_INT_ARGB);

            // Overlay the first image onto the merged image
            Graphics2D g2d = mergedImage.createGraphics();
            g2d.drawImage(image1, 0, 0, null);

            // Overlay the second image onto the merged image
            g2d.drawImage(image2, 0, 0, null);
            g2d.dispose();

            // Save the merged image to a file
            File mergedImageFile = new File(mergedImageFilePath);
            ImageIO.write(mergedImage, "png", mergedImageFile);

            System.out.println("Merged image saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
