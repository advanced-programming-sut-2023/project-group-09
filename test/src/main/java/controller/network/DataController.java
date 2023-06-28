package controller.network;

import client.Packet;
import view.Main;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DataController {

    public static String getUsername() throws IOException {
        Packet packet = new Packet("get username","user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return receivePacket.attributes.get("username").toString();
    }

    public static String getEmail() throws IOException {
        Packet packet = new Packet("get email","user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return receivePacket.attributes.get("email").toString();
    }

    public static String getNickname() throws IOException {
        Packet packet = new Packet("get nickname","user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return receivePacket.attributes.get("nickname").toString();
    }

    public static String getSlogan() throws IOException {
        Packet packet = new Packet("get slogan","user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        if (receivePacket.attributes.get("slogan") == null){
            return "";
        }
        return receivePacket.attributes.get("slogan").toString();
    }

    public static ByteArrayOutputStream getImageFromServer(String path) throws IOException {
        Packet packet = new Packet("get image","profile");
        packet.addAttribute("path",path);
        packet.sendPacket();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(Main.connection.getDataInputStream());

        // Read the image data
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        return byteArrayOutputStream;
    }
}
