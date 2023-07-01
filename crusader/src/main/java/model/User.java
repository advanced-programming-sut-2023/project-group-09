package model;

import controller.UserController;
import enumeration.Pair;
import javafx.animation.Transition;
import server.handlers.UserHandler;
import model.chat.Room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private int highScore;
    private String passwordRecoveryQuestion;
    private String passwordRecoveryAnswer;
    private String slogan;
    private transient boolean online = false;
    private transient boolean updateFriend = false;
    private String path;
    private ArrayList<Room> rooms = new ArrayList<>();

    public ArrayList<String> friends = new ArrayList<>();
    public HashMap<String,String> friendsRequest = new HashMap<>();

    private long lastSeen;

    public User(String username, String password, String nickname, String email, String slogan) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email.toLowerCase();
        this.slogan = slogan;
        Random random = new Random();
        this.highScore = 0;
        path = "files/img/avatars/" + (random.nextInt(4) + 1) + ".png";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getPasswordRecoveryQuestion() {
        return passwordRecoveryQuestion;
    }

    public void setPasswordRecoveryQuestion(String passwordRecoveryQuestion) {
        this.passwordRecoveryQuestion = passwordRecoveryQuestion;
    }

    public String getPasswordRecoveryAnswer() {
        return passwordRecoveryAnswer;
    }

    public void setPasswordRecoveryAnswer(String passwordRecoveryAnswer) {
        this.passwordRecoveryAnswer = UserController.convertPasswordToHash(passwordRecoveryAnswer);
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }


    public User getUserByUsername() {
        return null;
    }

    public boolean isPasswordCorrect(String password) {

        return UserController.convertPasswordToHash(password).equals(this.password);
    }

    public boolean isAnswerToSecurityQuestionCorrect(String answer) {
        if (answer == null)
            return false;
        answer = UserController.convertPasswordToHash(answer);
        return this.passwordRecoveryAnswer.hashCode() == answer.hashCode();
    }

    //=============================
    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        UserHandler.sendChangedPacket();
    }

    public void addHighScore(int highScore) {
        this.highScore += highScore;
        UserHandler.sendChangedPacket();
    }

    public boolean arePasswordsEqual(String secondPassword) {
        secondPassword = UserController.convertPasswordToHash(secondPassword);
        return password.hashCode() == secondPassword.hashCode();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        UserHandler.sendChangedPacket();
    }

    public String getPassword() {
        return password;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
        if (!online){
            lastSeen = System.currentTimeMillis();
        }else{
            lastSeen = 0L;
        }
        UserHandler.sendChangedPacket();
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public Room getRoomByName(String name) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getName().equals(name))
                return rooms.get(i);
        }
        return null;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void addFriend(String username){
        if (friends == null){
            friends = new ArrayList<>();
        }
        if (friendsRequest == null){
            friendsRequest = new HashMap<>();
        }
        friends.add(username);
        updateFriend = true;
        if (friendsRequest.get(username) != null){
            friendsRequest.remove(username);
        }
    }
    public void addRequest(String username,String state){
        if (friendsRequest == null){
            friendsRequest = new HashMap<>();
        }
        if (friends == null){
            friends = new ArrayList<>();
        }
        if (friends.contains(username)) return;
        friendsRequest.put(username,state);
        updateFriend = true;
    }

    public void removeRequest(String username){
        if (friendsRequest == null){
            friendsRequest = new HashMap<>();
        }
        friendsRequest.remove(username);
        updateFriend = true;
    }

    public void deleteFriend(String username){
        if (friends == null){
            friends = new ArrayList<>();
        }
        friends.remove(username);
        updateFriend = true;
    }

    public boolean isUpdateFriend() {
        return updateFriend;
    }

    public void setUpdateFriend(boolean updateFriend) {
        this.updateFriend = updateFriend;
    }

    public Long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Long lastSeen) {
        this.lastSeen = lastSeen;
    }
}
