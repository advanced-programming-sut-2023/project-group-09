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
    private ArrayList<String> readers = new ArrayList<>();

    public Message(String data, String senderUsername, String roomId) {
        this.sentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.data = data;
        this.senderUsername = senderUsername;
        this.roomId = roomId;
        this.id = generateNewId();
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

    public ArrayList<String> getReaders() {
        return readers;
    }

    public void setReaders(ArrayList<String> readers) {
        this.readers = readers;
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
}
