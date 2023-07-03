package model.chat;

import client.Packet;
import controller.Application;
import model.User;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    private String id;
    private String name;
    private User admin;
    private transient ArrayList<User> members = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();

    public Room(String name, User admin, boolean isPrivate) {
        this.name = name;
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void addMember(User member) {
        this.members.add(member);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
