package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String username, password, nickname, email, passwordRecoveryQuestion, passwordRecoveryAnswer, slogan;

    public User(String username, String password, String nickname, String email, String slogan) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
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
        this.email = email;
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
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
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
    public static String convertPasswordToHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // compute the hash of the input string
            byte[] hash = md.digest(password.getBytes());

            // convert the hash to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("An error occurred.[hashing password]");
            e.printStackTrace();
        }
        return "";
    }
    public boolean isPasswordCorrect(String password) {

        return convertPasswordToHash(password).equals(this.password);
    }
    public boolean isAnswerToSecurityQuestionCorrect(String answer) {
        return answer.equals(this.passwordRecoveryAnswer);
    }
}
