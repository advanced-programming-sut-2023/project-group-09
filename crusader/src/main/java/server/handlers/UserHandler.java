package server.handlers;

import controller.TokenController;
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
        //give commands to suitable methods
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
            case "get Path":
                getPath();
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
        packet.addAttribute("path",user.getSlogan());
        Packet.sendPacket(packet,connection);
    }
}
