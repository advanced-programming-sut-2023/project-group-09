package model;

import controller.UserController;
import model.chat.Room;

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
    private String path;
    private ArrayList<String> roomIds = new ArrayList<>();

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
    }

    public void addHighScore(int highScore) {
        this.highScore += highScore;
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
    }

    public ArrayList<String> getRooms() {
        return roomIds;
    }

    public String getRoomById(String id) {
        for (int i = 0; i < roomIds.size(); i++) {
            if (roomIds.get(i).equals(id))
                return roomIds.get(i);
        }
        return null;
    }
}
