package controller;

import model.User;
import view.ProfileMenu;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController {

    public static String createUser(String username, String password, String passwordConfirmation, String email, String slogan) {
        return "";
    }

    public static String pickSecurityQuestion(int questionNumber, String answer, String answerConfirmation) {
        return "";
    }

    public static String generateRandomPassword() {
        return "";
    }

    public static String generateRandomSlogan() {
        return "";
    }

    public static String validateSignup() {
        return "";
    }

    public static String loginUser(String username, String password, boolean stayedLoggedIn) {
        return "";
    }

    public static String forgotPassword(String username) {
        return "";
    }

    public static boolean checkSecurityQuestion(String username, String answer) {
        return false;
    }

    public static void logout() {
        Application.setCurrentUser(null);
        DBController.saveCurrentUser();
    }


    //==================================================
    public static String changeUsername(String newUsername) {

        String message = validateUsername(newUsername);
        if (message != null) {
            return message;
        }
        User user = Application.getCurrentUser();
        user.setUsername(newUsername);
        return "username changed successfully!";
    }

    public static String changeNickname(String newNickname) {
        if (checkNullFields(newNickname)) {
            return "nickname field is required!";
        }

        User user = Application.getCurrentUser();
        user.setNickname(newNickname);
        return "nickname changed successfully!";
    }

    public static String changePassword(String oldPassword, String newPassword) {
        String message = validateChangePassword(oldPassword, newPassword);
        if (message != null) {
            return message;
        }
        boolean captchaVerification = CaptchaController.isCaptchaTrue(ProfileMenu.profileMenuScanner);
        if(!captchaVerification){
            return "your behavior was not verified by captcha!";
        }
        boolean check;
        if (newPassword.equals("random")) {
            newPassword = generateRandomPassword();
            check = ProfileMenu.acceptRandomPassword(newPassword);

        } else {
            check = ProfileMenu.acceptNewPassword(newPassword);
        }

        if(check){
            newPassword = convertPasswordToHash(newPassword);
            Application.getCurrentUser().setPassword(newPassword);
            return "password changed successfully!";
        }
        return "input does not match the new password![change password failed]";
    }

    public static String changeEmail(String newEmail) {

        String message = validateEmail(newEmail);
        if (message != null) {
            return message;
        }
        User user = Application.getCurrentUser();
        user.setEmail(newEmail);
        return "email changed successfully!";
    }

    public static String changeSlogan(String newSlogan) {

        if (checkNullFields(newSlogan)) {
            return "slogan field is required!";
        }

        if (newSlogan.equals("random")) {
            String slogan = generateRandomSlogan();
            Application.getCurrentUser().setSlogan(slogan);
            return "new slogan is : " + slogan;
        } else {
            Application.getCurrentUser().setSlogan(newSlogan);
            return "slogan changed successfully!";
        }

    }

    public static String removeSlogan() {
        Application.getCurrentUser().setSlogan(null);
        return "slogan removed successfully!";
    }

    public static String displayHighScore() {
        return "high score: " + Application.getCurrentUser().getHighScore();
    }

    public static String displayRank() {
        return "your rank: " + getRank();
    }

    public static String displaySlogan() {
        return "slogan: " + Application.getCurrentUser().getHighScore();
    }

    public static String displayProfile() {
        User user = Application.getCurrentUser();
        String output = "username: " + user.getUsername() + "\n";
        output += "nickname: " + user.getNickname() + "\n";
        output += "email: " + user.getEmail() + "\n";
        output += "slogan: " + user.getSlogan() + "\n";
        output += "highscore: " + user.getHighScore() + "\n";
        output += "rank: " + getRank();

        return output;
    }






    //profile menu functions
    private static int getRank() {
        ArrayList<User> users = getSortedListOfUsers();
        int index = 1;
        for (User user : users){
            if(user.getUsername().equals(Application.getCurrentUser().getUsername())){
                return index;
            }
            index++;
        }
        return -1;
    }

    private static ArrayList<User> getSortedListOfUsers() {
        ArrayList<User> sortedList = new ArrayList<>(Application.getUsers());
        sortedList.sort((user1, user2) -> {
            if (user1.getHighScore() > user2.getHighScore()) {
                return -1;
            }
            if (user1.getHighScore() < user2.getHighScore()) {
                return 1;
            }

            return user1.getUsername().compareTo(user2.getUsername());
        });
        return sortedList;
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

    //=================
    private static String validateUsername(String username) {
        if (checkNullFields(username)) {
            return "username field is required!";
        }
        if (checkUsernameChars(username)) {
            return "username is invalid!";
        }
        if (Application.isUserExistsByName(username)) {
            return "username is duplicate!";
        }
        return null;
    }

    private static String validateEmail(String email) {
        if (checkNullFields(email)) {
            return "email field is required!";
        }
        if (checkEmailFormat(email)) {
            return "email is invalid!";
        }
        if (Application.isUserExistsByEmail(email)) {
            return "email is duplicate!";
        }
        return null;
    }

    private static String validateChangePassword(String oldPassword, String newPassword) {
        if (checkNullFields(oldPassword)) {
            return "old password field is required!";
        }
        if (checkNullFields(newPassword)) {
            return "new password field is required!";
        }
        if (!Application.getCurrentUser().arePasswordsEqual(oldPassword)) {
            return "current password is incorrect!";
        }

        //============================
        if (newPassword.equals("random")) {
            return null;
        }
        if (checkPasswordPower(newPassword)) {
            return "new password is weak!";
        }
        if (Application.getCurrentUser().arePasswordsEqual(newPassword)) {
            return "please enter a new password!";
        }
        return null;
    }

    //=================
    private static boolean checkUsernameChars(String username) {
        Pattern pattern = Pattern.compile("[^a-zA-Z_\\d]");
        Matcher matcher = pattern.matcher(username);
        return matcher.find();
    }

    private static boolean checkNullFields(String input) {
        return input == null || input.length() == 0;
    }

    private static boolean checkEmailFormat(String email) {
        Pattern pattern = Pattern.compile("[a-zA-Z_\\d]+@[a-zA-Z_\\d]+\\.[a-zA-Z_\\d]+");
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    private static boolean checkPasswordPower(String password) {
        if (password.length() < 6) {
            return true;
        }

        Pattern pattern1 = Pattern.compile("[a-z]");
        Pattern pattern2 = Pattern.compile("[A-Z]");
        Pattern pattern3 = Pattern.compile("\\d");
        Pattern pattern4 = Pattern.compile("[^\\da-zA-Z]");
        Matcher matcher1 = pattern1.matcher(password);
        Matcher matcher2 = pattern2.matcher(password);
        Matcher matcher3 = pattern3.matcher(password);
        Matcher matcher4 = pattern4.matcher(password);
        return !(matcher1.find() && matcher2.find() && matcher3.find() && matcher4.find());
    }

    //==================

}
