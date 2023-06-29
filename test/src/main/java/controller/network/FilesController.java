package controller.network;

import client.Packet;
import view.Main;

import java.io.*;

public class FilesController {
    public static void uploadImage(String path, File file) throws IOException {
        Packet packet = new Packet("upload image","file");
        packet.addAttribute("path",path);
        packet.sendPacket();

        byte[] buffer = new byte[8192];
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        // Send the image data size to the client
        DataOutputStream dataOutputStream = new DataOutputStream(Main.connection.getDataOutputStream());
        long imageSize = file.length();
        dataOutputStream.writeLong(imageSize);
        dataOutputStream.flush();

        // Send the image data to the client
        int bytesRead;
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            dataOutputStream.write(buffer, 0, bytesRead);
        }
        dataOutputStream.flush();
        fileInputStream.close();
        bufferedInputStream.close();
    }



    public static void copyFile(String path1,String path2) throws IOException {
        Packet packet = new Packet("copy file", "file");
        packet.addAttribute("path1", path1);
        packet.addAttribute("path2", path2);
        packet.sendPacket();
    }
}
