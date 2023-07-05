package model.chat;

import controller.Application;
import model.User;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    private boolean isPrivate;
    private String id;
    private String name;
    private String adminUsername;
    private ArrayList<String> members = new ArrayList<>();
    private ArrayList<String> messageIds = new ArrayList<>();

    public Room(String name, String admin, boolean isPrivate) {
        this.name = name;
        this.adminUsername = admin;
        this.isPrivate = isPrivate;
        this.id = generateNewId();
    }

    public String getId() {
        return id;
    }

    public String getAdmin() {
        return adminUsername;
    }

    public void setAdmin(String admin) {
        this.adminUsername = admin;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public void addMember(String member) {
        this.members.add(member);
    }

    public ArrayList<String> getMessages() {
        if(messageIds == null) messageIds = new ArrayList<>();
        return messageIds;
    }

    public void addMessage(String message) {
        this.messageIds.add(message);
    }

    public String getName() {
        return name;
    }

    public String getName(String currentUsername) {
        if (isPrivate) {
            if (members.get(0).equals(currentUsername)) return members.get(1);
            return members.get(0);
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String generateNewId() {
        int id = new Random().nextInt(10000);
        String newId = Integer.toString(id);
        if (Application.getRoomById(newId) == null) return newId;
        return generateNewId();
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
