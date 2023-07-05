package controller.network;

import client.Packet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import view.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class UsersController {

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


    public static String getPath() throws IOException {
        Packet packet = new Packet("get path", "user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return receivePacket.attributes.get("path").toString();
    }

    public static void setPath(String path) throws IOException {
        Packet packet = new Packet("set path", "profile");
        packet.addAttribute("path", path);
        packet.sendPacket();
        Packet.receivePacket();
    }

    public static ByteArrayOutputStream getImageFromServer(String path) throws IOException {
        Packet packet = new Packet("download image", "file");
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

    public static ByteArrayOutputStream getImageFromServerByUsername(String username) throws IOException {
        Packet packet = new Packet("download image by username", "file");
        packet.addAttribute("username", username);
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

    public static void convertBytesToImage(String gson) {
        try {
            byte[] bytes = new GsonBuilder().setPrettyPrinting().create().fromJson(gson, byte[].class);
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(input);

            File outputFile = new File("files/img/temporary.png");
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getSortedUsersUsername() throws IOException {
        Packet packet = new Packet("get sorted user", "user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        ArrayList<String> users = new Gson().fromJson(receivePacket.attributes.get("users").toString(), new TypeToken<ArrayList<String>>() {
        }.getType());
        return users;
    }


    public static String getEmail(String username) throws IOException {
        Packet packet = new Packet("get email by username", "user");
        packet.addAttribute("username", username);
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return receivePacket.attributes.get("email").toString();
    }

    public static String getNickname(String username) throws IOException {
        Packet packet = new Packet("get nickname by username", "user");
        packet.addAttribute("username", username);
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return receivePacket.attributes.get("nickname").toString();
    }

    public static String getSlogan(String username) throws IOException {
        Packet packet = new Packet("get slogan by username", "user");
        packet.addAttribute("username", username);
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        if (receivePacket.attributes.get("slogan") == null) {
            return "";
        }
        return receivePacket.attributes.get("slogan").toString();
    }

    public static String getHighScore(String username) throws IOException {
        Packet packet = new Packet("get high score by username", "user");
        packet.addAttribute("username", username);
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        if (receivePacket.attributes.get("high score") == null) {
            return "";
        }
        return receivePacket.attributes.get("high score").toString();
    }

    public static String getRank(String username) throws IOException {
        Packet packet = new Packet("get rank by username", "user");
        packet.addAttribute("username", username);
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        if (receivePacket.attributes.get("rank") == null) {
            return "";
        }
        return receivePacket.attributes.get("rank").toString();
    }

    public static String getPath(String username) throws IOException {
        Packet packet = new Packet("get path by username", "user");
        packet.addAttribute("username", username);
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return receivePacket.attributes.get("path").toString();
    }

    public static Boolean getOnline() throws IOException {
        Packet packet = new Packet("get online", "user");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return (Boolean) receivePacket.attributes.get("online");
    }

    public static Boolean getOnline(String username) throws IOException {
        Packet packet = new Packet("get online by username", "user");
        packet.addAttribute("username", username);
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return (Boolean) receivePacket.attributes.get("online");
    }

    public static boolean areUserChanged() throws IOException {
        Packet packet = new Packet("are user changed", "profile");
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return (Boolean) receivePacket.attributes.get("check");
    }

    public static String getLastSeen(String username) throws IOException {
        Packet packet = new Packet("get last seen", "user");
        packet.addAttribute("username", username);
        packet.sendPacket();

        Packet receivePacket = Packet.receivePacket();
        return receivePacket.attributes.get("lastSeen").toString();
    }
}
