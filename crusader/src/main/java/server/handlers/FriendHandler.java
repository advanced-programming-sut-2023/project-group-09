package server.handlers;

import controller.Application;
import controller.TokenController;
import model.User;
import server.Connection;
import server.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FriendHandler {
    Connection connection;
    Packet packet;

    public void handle(Packet packet, Connection connection) throws IOException {
        this.connection = connection;
        this.packet = packet;
        //give commands to suitable methods
        switch (packet.command) {
            case "send request" -> sendRequest();
            case "accept request" -> acceptRequest();
            case "reject request" -> rejectRequest();
            case "delete friend" -> deleteFriend();
            case "get friends" -> getFriends();
            case "get requests" -> getRequests();
            case "search user" -> searchUser();
            case "update user" -> updateUser();
        }
    }

    private void updateUser() throws IOException {
        User sender = TokenController.getUserByToken(packet.token);
        Packet packet = new Packet("success","user");
        packet.addAttribute("update",sender.isUpdateFriend());
        sender.setUpdateFriend(false);
        Packet.sendPacket(packet,connection);
    }

    public void sendRequest() throws IOException {
        User sender = TokenController.getUserByToken(packet.token);
        String receiverUsername = packet.attributes.get("username").toString();
        User receiver = Application.getUserByUsername(receiverUsername);

        if (sender.friends != null && sender.friends.size() > 100){
            Packet packet = new Packet("failed");
            Packet.sendPacket(packet,connection);
            return;
        }
        assert receiver != null;
        sender.addRequest(receiver.getUsername(),"requesting");
        receiver.addRequest(sender.getUsername(),"requested");
        Packet packet = new Packet("success");
        Packet.sendPacket(packet,connection);
    }
    public void deleteFriend(){
        User sender = TokenController.getUserByToken(packet.token);
        String receiverUsername = packet.attributes.get("username").toString();
        User receiver = Application.getUserByUsername(receiverUsername);

        assert receiver != null;
        sender.deleteFriend(receiver.getUsername());
        receiver.deleteFriend(sender.getUsername());
    }
    public void rejectRequest(){
        User sender = TokenController.getUserByToken(packet.token);
        String receiverUsername = packet.attributes.get("username").toString();
        User receiver = Application.getUserByUsername(receiverUsername);

        assert receiver != null;
        sender.removeRequest(receiver.getUsername());
        receiver.removeRequest(sender.getUsername());
    }
    public void acceptRequest() throws IOException {
        User sender = TokenController.getUserByToken(packet.token);
        String receiverUsername = packet.attributes.get("username").toString();
        User receiver = Application.getUserByUsername(receiverUsername);
        if (sender.friends != null && sender.friends.size() > 100){
            Packet packet = new Packet("failed");
            Packet.sendPacket(packet,connection);
            return;
        }
        assert receiver != null;
        sender.addFriend(receiver.getUsername());
        receiver.addFriend(sender.getUsername());
        Packet packet = new Packet("success");
        Packet.sendPacket(packet,connection);
    }
    public void getFriends() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        ArrayList<String> friends = user.friends;
        Packet packet = new Packet("success","user");
        packet.addAttribute("friends",friends);
        Packet.sendPacket(packet,connection);
    }
    public void getRequests() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        HashMap<String,String> requests = user.friendsRequest;
        Packet packet = new Packet("success","user");
        packet.addAttribute("requests",requests);
        Packet.sendPacket(packet,connection);
    }
    public void searchUser() throws IOException {
        User client = TokenController.getUserByToken(packet.token);
        String word = packet.attributes.get("word").toString();
        ArrayList<String> users = new ArrayList<>();
        for (User user : Application.getUsers()){
            if (user.getUsername().contains(word) && !user.getUsername().equals(client.getUsername())){
                users.add(user.getUsername());
            }else if (user.getNickname().contains(word) && !user.getUsername().equals(client.getUsername())){
                users.add(user.getUsername());
            }
        }
        Packet packet = new Packet("success","user");
        packet.addAttribute("users",users);
        Packet.sendPacket(packet,connection);
    }
}
