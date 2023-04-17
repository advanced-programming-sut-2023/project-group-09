package Controller;

public class UserController {

    public static String createUser(String username, String password, String passwordConfirmation, String email, String slogan) {
        return "";
    }

    public static String pickSecurityQuestion(int questionNumber, String answer, String answerConfirmation) {
        return "";
    }

    public static String makeRandomPassword() {
        return "";
    }

    public static String makeRandomSlogan() {
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

    public static String changeUsername(String newUsername) {
        return "";
    }

    public static String changeNickname(String newNickname) {
        return "";
    }

    public static String changePassword(String oldPassword, String newPassword) {
        return "";
    }

    public static String changeEmail(String newEmail) {
        return "";
    }

    public static String changeSlogan(String newSlogan) {
        return "";
    }

    public static String removeSlogan() {
        return "";
    }

    public static String displayHighScore() {
        return "";
    }

    public static String displayRank() {
        return "";
    }

    public static String displaySlogan() {
        return "";
    }

    public static String displayProfile() {
        return "";
    }

    private static String convertPasswordToHash(){
        return "";
    }

    //---
}
