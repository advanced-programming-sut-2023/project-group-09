package test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageProcessor {
    public static void main(String[] args) {
        int counter = 0;
        for (int i = 0;i < 8;i++){
            for(int j = 0; j < 8;j++){
                makeRed(counter);
                makeSkyBlue(counter);
                makeOrange(counter);
                makePurple(counter);
                makeGreen(counter);
                makeYellow(counter);
                makeGrey(counter);
                counter++;
            }
            counter+=8;
        }
    }


    public static void makeRed(int i){

        String inputImagePath = "files/test/blue/0_0img"+i+".png";
        String outputImagePath = "files/test/red/0_0img"+i+".png";

        try {
            // Read the input image file
            File inputFile = new File(inputImagePath);
            BufferedImage image = ImageIO.read(inputFile);

            // Process each pixel in the image
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Get the color of the current pixel
                    Color pixelColor = new Color(image.getRGB(x, y));

                    // Extract the RGB components
                    int red = pixelColor.getRed();
                    int green = pixelColor.getGreen();
                    int blue = pixelColor.getBlue();
                    if (red < blue && green < blue && Math.abs(blue - red) > 80 && Math.abs(blue - green) > 80){
                        Color newColor = new Color(blue, green, red);
                        image.setRGB(x, y, newColor.getRGB());
                    }

                }
            }

            // Save the modified image to the output file
            File outputFile = new File(outputImagePath);
            ImageIO.write(image, "png", outputFile);

            System.out.println("Image processed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void makeSkyBlue(int i){

        String inputImagePath = "files/test/blue/0_0img"+i+".png";
        String outputImagePath = "files/test/skyBlue/0_0img"+i+".png";

        try {
            // Read the input image file
            File inputFile = new File(inputImagePath);
            BufferedImage image = ImageIO.read(inputFile);

            // Process each pixel in the image
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Get the color of the current pixel
                    Color pixelColor = new Color(image.getRGB(x, y));

                    // Extract the RGB components
                    int red = pixelColor.getRed();
                    int green = pixelColor.getGreen();
                    int blue = pixelColor.getBlue();
                    if (red < blue && green < blue && Math.abs(blue - red) > 80 && Math.abs(blue - green) > 80) {
                        Color newColor = new Color(red, Math.min(225, green + 200), Math.min(225, blue));
                        image.setRGB(x, y, newColor.getRGB());
                    }

                }
            }

            // Save the modified image to the output file
            File outputFile = new File(outputImagePath);
            ImageIO.write(image, "png", outputFile);

            System.out.println("Image processed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void makeOrange(int i){

        String inputImagePath = "files/test/blue/0_0img"+i+".png";
        String outputImagePath = "files/test/orange/0_0img"+i+".png";

        try {
            // Read the input image file
            File inputFile = new File(inputImagePath);
            BufferedImage image = ImageIO.read(inputFile);

            // Process each pixel in the image
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Get the color of the current pixel
                    Color pixelColor = new Color(image.getRGB(x, y));

                    // Extract the RGB components
                    int red = pixelColor.getRed();
                    int green = pixelColor.getGreen();
                    int blue = pixelColor.getBlue();
                    if (red < blue && green < blue && Math.abs(blue - red) > 80 && Math.abs(blue - green) > 80) {
                        Color newColor = new Color(blue, Math.min(green + 80, 225), red);
                        image.setRGB(x, y, newColor.getRGB());
                    }
                }
            }

            // Save the modified image to the output file
            File outputFile = new File(outputImagePath);
            ImageIO.write(image, "png", outputFile);

            System.out.println("Image processed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void makePurple(int i){

        String inputImagePath = "files/test/blue/0_0img"+i+".png";
        String outputImagePath = "files/test/purple/0_0img"+i+".png";

        try {
            // Read the input image file
            File inputFile = new File(inputImagePath);
            BufferedImage image = ImageIO.read(inputFile);

            // Process each pixel in the image
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Get the color of the current pixel
                    Color pixelColor = new Color(image.getRGB(x, y));

                    // Extract the RGB components
                    int red = pixelColor.getRed();
                    int green = pixelColor.getGreen();
                    int blue = pixelColor.getBlue();
                    if (red < blue && green < blue && Math.abs(blue - red) > 80 && Math.abs(blue - green) > 80) {
                        Color newColor = new Color(Math.min(225,red+70), green, blue);
                        image.setRGB(x, y, newColor.getRGB());
                    }

                }
            }

            // Save the modified image to the output file
            File outputFile = new File(outputImagePath);
            ImageIO.write(image, "png", outputFile);

            System.out.println("Image processed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void makeGrey(int i){

        String inputImagePath = "files/test/blue/0_0img"+i+".png";
        String outputImagePath = "files/test/grey/0_0img"+i+".png";

        try {
            // Read the input image file
            File inputFile = new File(inputImagePath);
            BufferedImage image = ImageIO.read(inputFile);

            // Process each pixel in the image
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Get the color of the current pixel
                    Color pixelColor = new Color(image.getRGB(x, y));

                    // Extract the RGB components
                    int red = pixelColor.getRed();
                    int green = pixelColor.getGreen();
                    int blue = pixelColor.getBlue();
                    if (red < blue && green < blue && Math.abs(blue - red) > 80 && Math.abs(blue - green) > 80) {
                        int gray = (red + green + blue) / 3;
                        Color newColor = new Color(gray, gray, gray);
                        image.setRGB(x, y, newColor.getRGB());
                    }

                }
            }

            // Save the modified image to the output file
            File outputFile = new File(outputImagePath);
            ImageIO.write(image, "png", outputFile);

            System.out.println("Image processed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void makeYellow(int i){

        String inputImagePath = "files/test/blue/0_0img"+i+".png";
        String outputImagePath = "files/test/yellow/0_0img"+i+".png";

        try {
            // Read the input image file
            File inputFile = new File(inputImagePath);
            BufferedImage image = ImageIO.read(inputFile);

            // Process each pixel in the image
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Get the color of the current pixel
                    Color pixelColor = new Color(image.getRGB(x, y));

                    // Extract the RGB components
                    int red = pixelColor.getRed();
                    int green = pixelColor.getGreen();
                    int blue = pixelColor.getBlue();
                    if (red < blue && green < blue && Math.abs(blue - red) > 80 && Math.abs(blue - green) > 80) {
                        Color newColor = new Color(blue, blue, 0);
                        image.setRGB(x, y, newColor.getRGB());
                    }

                }
            }

            // Save the modified image to the output file
            File outputFile = new File(outputImagePath);
            ImageIO.write(image, "png", outputFile);

            System.out.println("Image processed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void makeGreen(int i){

        String inputImagePath = "files/test/blue/0_0img"+i+".png";
        String outputImagePath = "files/test/green/0_0img"+i+".png";

        try {
            // Read the input image file
            File inputFile = new File(inputImagePath);
            BufferedImage image = ImageIO.read(inputFile);

            // Process each pixel in the image
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Get the color of the current pixel
                    Color pixelColor = new Color(image.getRGB(x, y));

                    // Extract the RGB components
                    int red = pixelColor.getRed();
                    int green = pixelColor.getGreen();
                    int blue = pixelColor.getBlue();
                    if (red < blue && green < blue && Math.abs(blue - red) > 80 && Math.abs(blue - green) > 80){
                        Color newColor = new Color(red, blue, green);
                        image.setRGB(x, y, newColor.getRGB());
                    }

                }
            }

            // Save the modified image to the output file
            File outputFile = new File(outputImagePath);
            ImageIO.write(image, "png", outputFile);

            System.out.println("Image processed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
