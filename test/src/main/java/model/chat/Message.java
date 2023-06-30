package model.chat;

import model.User;

import java.util.ArrayList;

public class Message {
    private String data;
    private User sender;
//    TODO: time
    private ArrayList<User> readers = new ArrayList<>();

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public ArrayList<User> getReaders() {
        return readers;
    }

    public void setReaders(ArrayList<User> readers) {
        this.readers = readers;
    }
}
