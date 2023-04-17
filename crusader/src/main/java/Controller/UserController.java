package Controller;

import Enumeration.Answers.LoginAnswers;
import Model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (!Application.isUserExists(username)) {
            return LoginAnswers.USER_DOESNT_EXIST_MESSAGE.getMessage();
        }
        User user = Application.getUserByUsername(username);
        if (!user.isPasswordCorrect(password)) {
            return LoginAnswers.WRONG_PASSWORD_MESSAGE.getMessage();
        }
        Application.setCurrentUser(user);
        Application.setStayLoggedIn(stayedLoggedIn);
        // go to Main Menu
        return LoginAnswers.SUCCESSFUL_LOGIN_MESSAGE.getMessage();
    }

    public static String forgotPassword(String username) {
        if (!Application.isUserExists(username)) {
            return LoginAnswers.USER_DOESNT_EXIST_MESSAGE.getMessage();
        }
        User user = Application.getUserByUsername(username);
        return user.getPasswordRecoveryQuestion();
    }

    public static String changePasswordWithSecurityQuestion(String username , String newPassword , String newPasswordConfirmation) {
        User user = Application.getUserByUsername(username);
        if (!newPassword.equals(newPasswordConfirmation))
            return LoginAnswers.PASSWORD_AND_CONFIRMATION_DOESNT_MATCH.getMessage();
        int check = UserController.isPasswordStrong(newPassword);
        // for knowing what check is, go to isPasswordStrong function in this class
        if (UserController.isPasswordStrong(newPassword) != 6) {
            String result = LoginAnswers.WEAK_PASSWORD_MESSAGE.getMessage();
            switch (check) {
                case 1: {
                    result += LoginAnswers.PASSWORD_LENGTH_ERROR.getMessage();
                } break;
                case 2: {
                    result += LoginAnswers.PASSWORD_LOWERCASE_ERROR.getMessage();
                } break;
                case 3: {
                    result += LoginAnswers.PASSWORD_UPPERCASE_ERROR.getMessage();
                } break;
                case 4: {
                    result += LoginAnswers.PASSWORD_NUMBER_ERROR.getMessage();
                } break;
                case 5: {
                    result += LoginAnswers.PASSWORD_OTHER_CHARACTERS_ERROR.getMessage();
                } break;
            }
            return result;
        }
        user.setPassword(newPassword);
        return LoginAnswers.PASSWORD_CHANGE_SUCCESSFUL_MESSAGE.getMessage();
    }

    public static boolean checkSecurityQuestion(String username, String answer) {
        User user = Application.getUserByUsername(username);
        return user.isAnswerToSecurityQuestionCorrect(answer);
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
    private static int isPasswordStrong(String password){
        // if return value equals to 1: password is short, 2: a-z, 3: A-Z, 4: 0-9, 5: ^a-zA-Z0-9, 6: true
        if (password.length() < 6) {
            return 1;
        }
        Matcher matcher2 = Pattern.compile("[a-z]").matcher(password);
        Matcher matcher3 = Pattern.compile("[A-Z]").matcher(password);
        Matcher matcher4 = Pattern.compile("[0-9]").matcher(password);
        Matcher matcher5 = Pattern.compile("[^a-zA-Z0-9]").matcher(password);
        if (!matcher2.find())
            return 2;
        if (!matcher3.find())
            return 3;
        if (!matcher4.find())
            return 4;
        if (!matcher5.find())
            return 5;
        return 6;
    }

    //---
}
