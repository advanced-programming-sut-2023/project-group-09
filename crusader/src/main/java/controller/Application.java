package controller;


import model.User;
import model.chat.Message;
import model.chat.Room;

import java.util.ArrayList;


public class Application {
    private static boolean stayLoggedIn;

    public static boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public static void setStayLoggedIn(boolean s) {
        stayLoggedIn = s;
    }

    private static User currentUser;
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<Message> messages = new ArrayList<>();

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Application.currentUser = currentUser;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        Application.users = users;
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static User getUserByEmail(String email) {
        for (User user : users) {
            email = email.toLowerCase();
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public static boolean isUserExistsByName(String username) {
        User user = getUserByUsername(username);
        return user != null;
    }

    public static boolean isUserExistsByEmail(String email) {
        User user = getUserByEmail(email);
        return user != null;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    public static void setRooms(ArrayList<Room> rooms) {
        Application.rooms = rooms;
    }

    public static void addRoom(Room room) {
        Application.rooms.add(room);
    }

    public static ArrayList<Message> getMessages() {
        return messages;
    }

    public static void setMessages(ArrayList<Message> messages) {
        Application.messages = messages;
    }

    public static void addMessage(Message message) {
        Application.messages.add(message);
    }

    public static Room getRoomById(String roomId) {
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (room.getId().equals(roomId)) return room;
        }
        return null;
    }

    public static Message getMessageById(String messageId) {
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message.getId().equals(messageId)) return message;
        }
        return null;
    }
}
