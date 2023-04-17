package controller;

import enumeration.SecurityQuestions;
import enumeration.commands.SignupMenuCommands;
import model.User;
import view.SignupMenu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class UserController {
    public static String createUser(HashMap<String, Matcher> matchers, boolean isSlogan) {
        HashMap<String, String> user = new HashMap<>();
        String validation = validateSignup(matchers, user, isSlogan);
        if (!validation.isEmpty()) return validation;

//        TODO: handle random attributes

//        TODO: remember to change password to hash after merge
        User newUser = new User(user.get("username"), user.get("password"), user.get("nickname"), user.get("email"), user.get("slogan"));
        Application.addUser(newUser);

        SignupMenu.validResult = true;
        SignupMenu.currentUser = newUser;
        return "user " + user.get("username") + " added successfully\n" + showSecurityQuestions();
    }

    private static String validateSignup(HashMap<String, Matcher> matchers, HashMap<String, String> user, boolean isSlogan) {
        if (!matchers.get("username").find() || !matchers.get("password").find() || !matchers.get("email").find() || !matchers.get("nickname").find())
            return "invalid command";

        String fieldsValidation = validateSignupFields(matchers, isSlogan);
        if (!fieldsValidation.isEmpty()) return fieldsValidation;

        extractUserData(matchers, user, isSlogan);

        if (!SignupMenuCommands.USERNAME_VALIDATION.getMatcher(user.get("username")).matches())
            return "username is not valid";

//        TODO: check if the username has not been used before

        String passwordValidation = validatePassword(user.get("password"));
        if (!passwordValidation.isEmpty()) return passwordValidation;

        if (!user.get("passwordConfirm").equals(user.get("password")))
            return "password confirmation doesn't match with password";

//        TODO: check if the email has not been used before

        if (!SignupMenuCommands.EMAIL_VALIDATION.getMatcher(user.get("email")).matches())
            return "email address is not valid";

        return null;
    }

    private static String validateSignupFields(HashMap<String, Matcher> matchers, boolean isSlogan) {
        String result = "";

        if (matchers.get("username").group("content").isEmpty()) result += "username field is empty\n";
        if (matchers.get("password").group("content").isEmpty()) result += "password field is empty\n";
        if (matchers.get("email").group("content").isEmpty()) result += "email field is empty\n";
        if (matchers.get("nickname").group("content").isEmpty()) result += "nickname field is empty\n";
        if (isSlogan && matchers.get("slogan").group("content").isEmpty())
            result += "slogan field is empty\n";

        if (!result.isEmpty()) return result.substring(0, result.length() - 1);
        else return null;
    }

    private static void extractUserData(HashMap<String, Matcher> matchers, HashMap<String, String> user, boolean isSlogan) {
        if (matchers.get("username").group("username1").isEmpty())
            user.put("username", matchers.get("username").group("username2"));
        else user.put("username", matchers.get("username").group("username1"));

        if (matchers.get("password").group("randomPassword").equals("random"))
            user.put("password", "random");
        else if (matchers.get("password").group("password1").isEmpty())
            user.put("password", matchers.get("password").group("password2"));
        else user.put("password", matchers.get("password").group("password1"));

        if (matchers.get("password").group("passwordConfirm1").isEmpty())
            user.put("passwordConfirm", matchers.get("password").group("passwordConfirm2"));
        else user.put("passwordConfirm", matchers.get("password").group("passwordConfirm1"));

        if (matchers.get("email").group("email1").isEmpty())
            user.put("user", matchers.get("email").group("email2"));
        else user.put("user", matchers.get("email").group("email1"));

        if (matchers.get("nickname").group("nickname1").isEmpty())
            user.put("nickname", matchers.get("nickname").group("nickname2"));
        else user.put("nickname", matchers.get("nickname").group("nickname1"));

        if (isSlogan) {
            if (matchers.get("slogan").group("randomSlogan").equals("random"))
                user.put("slogan", "random");
            else if (matchers.get("slogan").group("slogan1").isEmpty())
                user.put("slogan", matchers.get("slogan").group("slogan2"));
            else user.put("slogan", matchers.get("slogan").group("slogan1"));
        } else user.put("slogan", "");
    }

    private static String validatePassword(String password) {
        String result = "";

        if (password.length() < 6) result += "password should contain at least 6 characters\n";
        if (SignupMenuCommands.PASSWORD_NOT_CONTAINING_SPACE.getMatcher(password).find())
            result += "password should not contain space\n";
        if (!SignupMenuCommands.PASSWORD_CONTAINING_UPPERCASE_LETTER.getMatcher(password).find())
            result += "password should contain an uppercase letter\n";
        if (!SignupMenuCommands.PASSWORD_CONTAINING_LOWERCASE_LETTER.getMatcher(password).find())
            result += "password should contain a lowercase letter\n";
        if (!SignupMenuCommands.PASSWORD_CONTAINING_DIGIT.getMatcher(password).find())
            result += "password should contain a digit\n";
        if (!SignupMenuCommands.PASSWORD_CONTAINING_SPECIAL_LETTER.getMatcher(password).find())
            result += "password should contain a special character\n";

        if (!result.isEmpty()) return result.substring(0, result.length() - 1);
        else return null;
    }

    private static String showSecurityQuestions() {
        String text = "Pick your security question:\n";
        for (SecurityQuestions question : SecurityQuestions.values()) {
            text += question.getNumber() + ". " + question.getQuestion() + "\n";
        }

        return text.substring(0, text.length() - 1);
    }

    public static String makeRandomPassword() {
        return "";
    }

    public static String makeRandomSlogan() {
        return "";
    }

    public static String pickSecurityQuestion(User user, HashMap<String, Matcher> matchers) {
        HashMap<String, String> questionAndAnswer = new HashMap<>();
        String validation = validateSecurityQuestion(matchers, questionAndAnswer);
        if (!validation.isEmpty()) return validation;

        SecurityQuestions securityQuestion = null;
        for (SecurityQuestions question : SecurityQuestions.values()) {
            if (question.getNumber() == Integer.parseInt(questionAndAnswer.get("questionNumber"))) {
                securityQuestion = question;
                break;
            }
        }
        user.setPasswordRecoveryQuestion(securityQuestion.getQuestion());
        user.setPasswordRecoveryAnswer(questionAndAnswer.get("answer"));
        return "security question added successfully";
    }

    private static String validateSecurityQuestion(HashMap<String, Matcher> matchers, HashMap<String, String> questionAndAnswer) {
        if (!matchers.get("questionNumber").find() || !matchers.get("answer").find() || !matchers.get("answerConfirm").find())
            return "invalid command";

        String fieldsValidation = validateQuestionFields(matchers);
        if (!fieldsValidation.isEmpty()) return fieldsValidation;

        extractQuestionData(matchers, questionAndAnswer);

        int questionNumber = Integer.parseInt(questionAndAnswer.get("questionNumber"));
        if (questionNumber != 1 && questionNumber != 2 && questionNumber != 3)
            return "invalid question number";

        if (!questionAndAnswer.get("answerConfirm").equals(questionAndAnswer.get("answer")))
            return "answer confirmation doesn't match with answer";

        return null;
    }

    private static String validateQuestionFields(HashMap<String, Matcher> matchers) {
        String result = "";

        if (matchers.get("questionNumber").group("number").isEmpty()) result += "question number field is empty\n";
        if (matchers.get("answer").group("content").isEmpty()) result += "answer field is empty\n";
        if (matchers.get("answerConfirm").group("content").isEmpty()) result += "answer confirmation field is empty\n";

        if (!result.isEmpty()) return result.substring(0, result.length() - 1);
        else return null;
    }

    private static void extractQuestionData(HashMap<String, Matcher> matchers, HashMap<String, String> questionAndAnswer) {
        questionAndAnswer.put("questionNumber", matchers.get("questionNumber").group("number"));

        if (matchers.get("answer").group("answer1").isEmpty())
            questionAndAnswer.put("answer", matchers.get("answer").group("answer2"));
        else matchers.get("answer").group("answer1");

        if (matchers.get("answerConfirm").group("answerConfirm1").isEmpty())
            questionAndAnswer.put("answerConfirm", matchers.get("answerConfirm").group("answerConfirm2"));
        else questionAndAnswer.put("answerConfirm", matchers.get("answerConfirm").group("answerConfirm1"));
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

    public static String logout() {
        return "";
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

    private static String convertPasswordToHash() {
        return "";
    }
}
