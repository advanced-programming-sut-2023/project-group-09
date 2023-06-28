package controller.network;

import client.Packet;
import javafx.scene.image.Image;
import view.Main;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class DataController {

    public static String getUsername() throws IOException {
        Packet packet = new Packet("get username", "user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return receivePacket.attributes.get("username").toString();
    }

    public static String getEmail() throws IOException {
        Packet packet = new Packet("get email", "user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return receivePacket.attributes.get("email").toString();
    }

    public static String getNickname() throws IOException {
        Packet packet = new Packet("get nickname", "user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return receivePacket.attributes.get("nickname").toString();
    }

    public static String getSlogan() throws IOException {
        Packet packet = new Packet("get slogan", "user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        if (receivePacket.attributes.get("slogan") == null) {
            return "";
        }
        return receivePacket.attributes.get("slogan").toString();
    }

    public static String getHighScore() throws IOException {
        Packet packet = new Packet("get high score", "user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        if (receivePacket.attributes.get("high score") == null) {
            return "";
        }
        return receivePacket.attributes.get("high score").toString();
    }

    public static String getRank() throws IOException {
        Packet packet = new Packet("get rank", "user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        if (receivePacket.attributes.get("rank") == null) {
            return "";
        }
        return receivePacket.attributes.get("rank").toString();
    }

    public static ByteArrayOutputStream getImageFromServer(String path) throws IOException {
        Packet packet = new Packet("get image", "profile");
        packet.addAttribute("path", path);
        packet.sendPacket();

        DataInputStream dataInputStream = new DataInputStream(Main.connection.getDataInputStream());

        // Read the image data size
        long imageSize = dataInputStream.readLong();
        System.out.println(imageSize);
        // Read the image data
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        long totalBytesRead = 0;
        while (totalBytesRead < imageSize &&
                (bytesRead = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, imageSize - totalBytesRead))) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
            totalBytesRead += bytesRead;
        }

        return byteArrayOutputStream;
    }

    public static void setPath(String path) throws IOException {
        Packet packet = new Packet("set path", "profile");
        packet.addAttribute("path", path);
        packet.sendPacket();
        Packet.receivePacket();
    }

    public static String getPath() throws IOException {
        Packet packet = new Packet("get path", "user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return receivePacket.attributes.get("path").toString();
    }

    public static void copyFile(String path1,String path2) throws IOException {
        Packet packet = new Packet("copy file", "profile");
        packet.addAttribute("path1", path1);
        packet.addAttribute("path2", path2);
        packet.sendPacket();
    }
}
