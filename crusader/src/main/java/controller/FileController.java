package controller;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileController {
    public static void updateFile(String text, String address){
        try {
            FileWriter myWriter = new FileWriter(address);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String readFile(String address){
        try {

            File file = new File(address);
            Scanner myReader = new Scanner(file);
            StringBuilder output = new StringBuilder();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                output.append(data);
            }
            myReader.close();

            return output.toString();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return "";
    }

    public static String readFile(File file){
        try {
            Scanner myReader = new Scanner(file);
            StringBuilder output = new StringBuilder();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                output.append(data);
            }
            myReader.close();

            return output.toString();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return "";
    }

    public static String getClipboard() {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        try {
            if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String text = (String) t.getTransferData(DataFlavor.stringFlavor);

                return text.trim();
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static void copyBuildingNameToClipboard(String buildingName) {
        String myString = buildingName;
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

}
