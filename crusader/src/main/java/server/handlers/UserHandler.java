package server.handlers;

import controller.TokenController;
import controller.UserController;
import model.User;
import server.Connection;
import server.Packet;

import java.io.IOException;

public class UserHandler {
    Connection connection;
    Packet packet;

    public void handle(Packet packet, Connection connection) throws IOException {
        this.connection = connection;
        this.packet = packet;
        switch (packet.command) {
            case "get username":
                getUsername();
                break;
            case "get email":
                getEmail();
                break;
            case "get nickname":
                getNickname();
                break;
            case "get slogan":
                getSlogan();
                break;
            case "get path":
                getPath();
                break;
            case "get high score" :
                getHighScore();
                break;
            case "get rank":
                getRank();
                break;
        }
    }

    public void getUsername() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        Packet packet = new Packet("success","user");
        packet.addAttribute("username",user.getUsername());
        Packet.sendPacket(packet,connection);
    }

    public void getEmail() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        Packet packet = new Packet("success","user");
        packet.addAttribute("email",user.getEmail());
        Packet.sendPacket(packet,connection);
    }
    public void getNickname() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        Packet packet = new Packet("success","user");
        packet.addAttribute("nickname",user.getNickname());
        Packet.sendPacket(packet,connection);
    }
    public void getSlogan() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        Packet packet = new Packet("success","user");
        packet.addAttribute("slogan",user.getSlogan());
        Packet.sendPacket(packet,connection);
    }

    public void getPath() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        Packet packet = new Packet("success","user");
        packet.addAttribute("path",user.getPath());
        Packet.sendPacket(packet,connection);
    }

    public void getHighScore() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        Packet packet = new Packet("success","user");
        packet.addAttribute("high score",Integer.toString(user.getHighScore()));
        Packet.sendPacket(packet,connection);
    }

    public void getRank() throws IOException {
        Packet packet = new Packet("success","user");
        packet.addAttribute("rank", Integer.toString(UserController.getRank(this.packet.token)));
        Packet.sendPacket(packet,connection);
    }
}
