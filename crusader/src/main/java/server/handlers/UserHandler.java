package server.handlers;

import controller.Application;
import controller.TokenController;
import controller.UserController;
import model.User;
import server.Connection;
import server.Packet;
import server.Server;

import java.io.IOException;
import java.util.ArrayList;

public class UserHandler {
    Connection connection;
    Packet packet;

    public void handle(Packet packet, Connection connection) throws IOException {
        this.connection = connection;
        this.packet = packet;
        switch (packet.command) {
            case "get username" -> getUsername();
            case "get email" -> getEmail();
            case "get nickname" -> getNickname();
            case "get slogan" -> getSlogan();
            case "get path" -> getPath();
            case "get online" -> getOnline();
            case "get high score" -> getHighScore();
            case "get rank" -> getRank();
            case "get email by username" -> getEmailByUsername();
            case "get nickname by username" -> getNicknameByUsername();
            case "get slogan by username" -> getSloganByUsername();
            case "get path by username" -> getPathByUsername();
            case "get online by username" -> getOnlineByUsername();
            case "get high score by username" -> getHighScoreByUsername();
            case "get rank by username" -> getRankByUsername();
            case "get sorted user" -> getSortedUsers();
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
        User user = TokenController.getUserByToken(packet.token);
        Packet packet = new Packet("success","user");
        packet.addAttribute("rank", Integer.toString(UserController.getRank(user)));
        Packet.sendPacket(packet,connection);
    }

    public void getEmailByUsername() throws IOException {
        String username = packet.attributes.get("username").toString();
        User user = Application.getUserByUsername(username);
        Packet packet = new Packet("success","user");
        packet.addAttribute("email",user.getEmail());
        Packet.sendPacket(packet,connection);
    }
    public void getNicknameByUsername() throws IOException {
        String username = packet.attributes.get("username").toString();
        User user = Application.getUserByUsername(username);
        Packet packet = new Packet("success","user");
        packet.addAttribute("nickname",user.getNickname());
        Packet.sendPacket(packet,connection);
    }
    public void getSloganByUsername() throws IOException {
        String username = packet.attributes.get("username").toString();
        User user = Application.getUserByUsername(username);
        Packet packet = new Packet("success","user");
        packet.addAttribute("slogan",user.getSlogan());
        Packet.sendPacket(packet,connection);
    }

    public void getPathByUsername() throws IOException {
        String username = packet.attributes.get("username").toString();
        User user = Application.getUserByUsername(username);
        Packet packet = new Packet("success","user");
        packet.addAttribute("path",user.getPath());
        Packet.sendPacket(packet,connection);
    }

    public void getHighScoreByUsername() throws IOException {
        String username = packet.attributes.get("username").toString();
        User user = Application.getUserByUsername(username);
        Packet packet = new Packet("success","user");
        packet.addAttribute("high score",Integer.toString(user.getHighScore()));
        Packet.sendPacket(packet,connection);
    }

    public void getRankByUsername() throws IOException {
        String username = packet.attributes.get("username").toString();
        User user = Application.getUserByUsername(username);
        packet.addAttribute("rank", Integer.toString(UserController.getRank(user)));
        Packet.sendPacket(packet,connection);
    }

    public void getSortedUsers() throws IOException {
        ArrayList<String> users = UserController.getSortedList();
        Packet packet = new Packet("sorted users","user");
        packet.addAttribute("users",users);
        Packet.sendPacket(packet,connection);
    }

    public void getOnline() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        Packet packet = new Packet("success","user");
        packet.addAttribute("online",user.isOnline());
        Packet.sendPacket(packet,connection);
    }

    public void getOnlineByUsername() throws IOException {
        String username = packet.attributes.get("username").toString();
        User user = Application.getUserByUsername(username);
        Packet packet = new Packet("success","user");
        packet.addAttribute("online",user.isOnline());
        Packet.sendPacket(packet,connection);
    }

    public static void sendChangedPacket() {
        for (Connection connection : Server.connections){
            if (connection.isAlive() && !connection.getSocket().isClosed()){
                connection.userChanged = true;
            }
        }
    }
}
