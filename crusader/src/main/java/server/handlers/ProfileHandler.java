package server.handlers;

import controller.Application;
import controller.TokenController;
import controller.UserController;
import model.User;
import server.Connection;
import server.Packet;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ProfileHandler {

    Connection connection;
    Packet packet;

    public void handle(Packet packet, Connection connection) throws IOException {
        this.connection = connection;
        this.packet = packet;
        //give commands to suitable methods
        switch (packet.command) {
            case "make a fake token":
                makeFakeToken();
                break;
            case "change username":
                changeUsername();
                break;
            case "change email":
                changeEmail();
                break;
            case "change slogan":
                changeSlogan();
                break;
            case "change nickname":
                changeNickname();
                break;
            case "validate username":
                validateUsername();
                break;
            case "validate email":
                validateEmail();
                break;
            case "change password":
                changePassword();
                break;
            case "validate password":
                validatePassword();
                break;
            case "set path":
                setPath();
                break;
            case "get image":
                downloadImage();
                break;
            case "copy file":
                copyFile();
        }
    }

    public void makeFakeToken() throws IOException {
        User user = Application.getUserByUsername("a");
        String token = TokenController.generateToken(user);
        Packet packet = new Packet("fake token", "main");
        packet.addAttribute("authentication token", token);
        Packet.sendPacket(packet, connection);
    }

    public void changeUsername() throws IOException {
        Packet packet = new Packet("success", "user");
        System.out.println(packet.token);
        packet.addAttribute("massage", UserController.changeUsername(this.packet.attributes.get("username").toString(), this.packet.token));
        Packet.sendPacket(packet, connection);
    }

    public void validateUsername() throws IOException {
        Packet packet = new Packet("success", "user");
        packet.addAttribute("massage", UserController.validateUsername(this.packet.attributes.get("username").toString()));
        Packet.sendPacket(packet, connection);
    }

    public void changeNickname() throws IOException {
        Packet packet = new Packet("success", "user");
        packet.addAttribute("massage", UserController.changeNickname(this.packet.attributes.get("nickname").toString(), this.packet.token));
        Packet.sendPacket(packet, connection);
    }

    public void validateEmail() throws IOException {
        Packet packet = new Packet("success", "user");
        packet.addAttribute("massage", UserController.validateEmail(this.packet.attributes.get("email").toString()));
        Packet.sendPacket(packet, connection);
    }

    public void changeEmail() throws IOException {
        Packet packet = new Packet("success", "email");
        packet.addAttribute("massage", UserController.changeEmail(this.packet.attributes.get("email").toString(), this.packet.token));
        Packet.sendPacket(packet, connection);
    }

    public void changeSlogan() throws IOException {
        Packet packet = new Packet("success", "slogan");
        packet.addAttribute("massage", UserController.changeSlogan(this.packet.attributes.get("slogan").toString(), this.packet.token));
        Packet.sendPacket(packet, connection);
    }

    public void validatePassword() throws IOException {
        Packet packet = new Packet("success", "user");
        String oldPassword = null;
        String newPassword = null;
        if (this.packet.attributes.get("oldPassword") != null) {
            oldPassword = this.packet.attributes.get("oldPassword").toString();
        }
        if (this.packet.attributes.get("newPassword") != null) {
            newPassword = this.packet.attributes.get("newPassword").toString();
        }

        packet.addAttribute("massage", UserController.validateChangePassword(oldPassword, newPassword, this.packet.token));
        Packet.sendPacket(packet, connection);
    }

    public void changePassword() throws IOException {
        Packet packet = new Packet("success", "user");
        String oldPassword = null;
        String newPassword = null;
        if (this.packet.attributes.get("oldPassword") != null) {
            oldPassword = this.packet.attributes.get("oldPassword").toString();
        }
        if (this.packet.attributes.get("newPassword") != null) {
            newPassword = this.packet.attributes.get("newPassword").toString();
        }

        packet.addAttribute("massage", UserController.changePassword(oldPassword, newPassword, this.packet.token));
        Packet.sendPacket(packet, connection);
    }

    public void downloadImage() throws IOException {
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

    public void setPath() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        String path = packet.attributes.get("path").toString();
        user.setPath(path);

        Packet newPacket = new Packet("success", "user");
        Packet.sendPacket(newPacket, connection);
    }

    public void copyFile() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        String path = enumeration.Paths.USER_AVATARS.getPath() + user.getUsername();
        boolean check = new File(path).mkdirs();
        String path1 = packet.attributes.get("path1").toString();
        String path2 = packet.attributes.get("path2").toString();
        System.out.println(Paths.get(path1));
        Files.copy(Paths.get(path1), new File(path2).toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
