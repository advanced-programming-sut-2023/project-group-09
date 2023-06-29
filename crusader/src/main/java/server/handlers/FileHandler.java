package server.handlers;

import controller.Application;
import controller.TokenController;
import controller.UserController;
import model.User;
import server.Connection;
import server.Packet;
import view.Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHandler {

    Connection connection;
    Packet packet;

    public void handle(Packet packet, Connection connection) throws IOException {
        this.connection = connection;
        this.packet = packet;
        //give commands to suitable methods
        switch (packet.command) {
            case "download image by username" -> downloadImageOfUser();
            case "upload image" -> uploadImage();
            case "download image" -> downloadImage();
            case "copy file" -> copyFile();
        }
    }

    private void downloadImage() throws IOException {
        String path = packet.attributes.get("path").toString();
        File imageFile = new File(path);
        byte[] buffer = new byte[8192];
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        // Send the image data size to the client
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getDataOutputStream());
        long imageSize = imageFile.length();
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

    private void uploadImage() throws IOException {
        String path = packet.attributes.get("path").toString();
        DataInputStream dataInputStream = new DataInputStream(connection.getDataInputStream());
        // Read the image data size
        long imageSize = dataInputStream.readLong();
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
        try {
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(byteArray);
            fileOutputStream.close();
            System.out.println("File created successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    public void copyFile() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        String path = enumeration.Paths.USER_AVATARS.getPath() + user.getUsername();
        boolean check = new File(path).mkdirs();

        String path1 = packet.attributes.get("path1").toString();
        String path2 = packet.attributes.get("path2").toString();
        File file = new File(path1);
        path2 = path2 + "/" + file.getName();
        user.setPath(path2);
        Files.copy(Paths.get(path1), new File(path2).toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public void downloadImageOfUser() throws IOException {

        String username = packet.attributes.get("username").toString();
        User user = Application.getUserByUsername(username);
        assert user != null;
        String path =  user.getPath();


        File imageFile = new File(path);
        byte[] buffer = new byte[8192];
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        // Send the image data size to the client
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getDataOutputStream());
        long imageSize = imageFile.length();
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
}
