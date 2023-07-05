package model.chat;

import controller.Application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class Message {
    private String id;
    private String data;
    private String senderUsername;
    private String sentTime;
    private String roomId;
    private int likeCount;
    private int dislikeCount;
    private int fireCount;
    private int shitCount;
    private boolean seen;
    private ArrayList<String> reactors = new ArrayList<>();

    public Message(String data, String senderUsername, String roomId) {
        this.sentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.data = data;
        this.senderUsername = senderUsername;
        this.roomId = roomId;
        this.id = generateNewId();
        this.likeCount = 0;
        this.dislikeCount = 0;
        this.fireCount = 0;
        this.shitCount = 0;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSender() {
        return senderUsername;
    }

    public void setSender(String sender) {
        this.senderUsername = sender;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public String generateNewId() {
        int id = new Random().nextInt(10000);
        String newId = Integer.toString(id);
        if (Application.getMessageById(newId) == null) return newId;
        return generateNewId();
    }

    public String getRoomId() {
        return roomId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public int getFireCount() {
        return fireCount;
    }

    public int getShitCount() {
        return shitCount;
    }

    public void react(String emoji, String username) {
        switch (emoji) {
            case "like" -> this.likeCount++;
            case "dislike" -> this.dislikeCount++;
            case "fire" -> this.fireCount++;
            case "shit" -> this.shitCount++;
        }
        if (reactors == null) reactors = new ArrayList<>();
        this.reactors.add(username);
    }

    public ArrayList<String> getReactors() {
        return reactors;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
