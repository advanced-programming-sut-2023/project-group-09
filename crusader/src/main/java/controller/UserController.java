package controller;

import enumeration.SecurityQuestions;
import enumeration.commands.SignupMenuCommands;
import enumeration.dictionary.Slogans;
import model.User;
import view.SignupMenu;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;

public class UserController {
    public static String signup(HashMap<String, Matcher> matchers, boolean isSlogan) {
        HashMap<String, String> user = new HashMap<>();
        String validation = validateSignup(matchers, user, isSlogan);
        if (!validation.isEmpty()) return validation;

        if (user.get("slogan").equals("random")) {
            user.replace("slogan", generateRandomSlogan());
            SignupMenu.signupState = 1;
        }

        if (user.get("password").equals("random")) {
            user.replace("password", generateRandomPassword());
            if (SignupMenu.signupState == 1) SignupMenu.signupState = 3;
            else SignupMenu.signupState = 2;
        }

        if (SignupMenu.signupState == 0) return createUser(user, null);
        else if (SignupMenu.signupState == 1)
            return "your slogan is \"" + user.get("slogan") + "\"\n" + createUser(user, null);
        else if (SignupMenu.signupState == 2) {
            SignupMenu.user = user;
            return "your password is: " + user.get("password") + "\nplease enter the password:";
        } else if (SignupMenu.signupState == 3) {
            SignupMenu.user = user;
            return "your slogan is \"" + user.get("slogan") + "\"\nyour password is: " + user.get("password") +
                    "\nplease enter the password:";
        }
        return "";
    }

    public static String createUser(HashMap<String, String> user, String passwordReEnter) {
        if (SignupMenu.signupState == 2 || SignupMenu.signupState == 3) {
            if (!passwordReEnter.equals(user.get("password")))
                return "please re-enter the correct password:";
        }

//        TODO: remember to change password to hash after merge
        User newUser = new User(user.get("username"), user.get("password"), user.get("nickname"), user.get("email"), user.get("slogan"));
        Application.addUser(newUser);

        SignupMenu.currentUser = newUser;
        SignupMenu.signupState = 4;
        return "user " + user.get("username") + " added successfully";
    }

    private static String validateSignup(HashMap<String, Matcher> matchers, HashMap<String, String> user, boolean isSlogan) {
        if (!matchers.get("username").find() || !matchers.get("password").find() || !matchers.get("email").find() || !matchers.get("nickname").find())
            return "invalid command";

        String fieldsValidation = validateSignupFields(matchers, isSlogan);
        if (!fieldsValidation.isEmpty()) return fieldsValidation;

        extractUserData(matchers, user, isSlogan);

        if (!SignupMenuCommands.USERNAME_VALIDATION.getMatcher(user.get("username")).matches())
            return "username is not valid";

        if (Application.isUserExistsByName(user.get("username")) == true)
            return "username already exists";

        String passwordValidation = validatePassword(user.get("password"));
        if (!passwordValidation.isEmpty()) return passwordValidation;

        if (user.get("passwordConfirm") != null && !user.get("passwordConfirm").equals(user.get("password")))
            return "password confirmation doesn't match with password";

        if (Application.isUserExistsByEmail(user.get("email")) == true)
            return "email has already been used";

        if (!SignupMenuCommands.EMAIL_VALIDATION.getMatcher(user.get("email")).matches())
            return "email address is not valid";

        return "";
    }

    private static String validateSignupFields(HashMap<String, Matcher> matchers, boolean isSlogan) {
        String result = "";

        if (matchers.get("username").group("content").isEmpty()) result += "username field is empty\n";
        if (matchers.get("password").group("content") == null ||
                matchers.get("password").group("content").isEmpty()) result += "password field is empty\n";
        if (matchers.get("email").group("content").isEmpty()) result += "email field is empty\n";
        if (matchers.get("nickname").group("content").isEmpty()) result += "nickname field is empty\n";
        if (isSlogan && matchers.get("slogan").group("content").isEmpty())
            result += "slogan field is empty\n";

        if (!result.isEmpty()) return result.substring(0, result.length() - 1);
        else return "";
    }

    private static void extractUserData(HashMap<String, Matcher> matchers, HashMap<String, String> user, boolean isSlogan) {
        if (matchers.get("username").group("username1") == null)
            user.put("username", matchers.get("username").group("username2"));
        else user.put("username", matchers.get("username").group("username1"));

        if (matchers.get("password").group("randomPassword") != null)
            user.put("password", "random");
        else if (matchers.get("password").group("password1") == null)
            user.put("password", matchers.get("password").group("password2"));
        else user.put("password", matchers.get("password").group("password1"));

        if (matchers.get("password").group("passwordConfirm1") == null)
            user.put("passwordConfirm", matchers.get("password").group("passwordConfirm2"));
        else user.put("passwordConfirm", matchers.get("password").group("passwordConfirm1"));

        if (matchers.get("email").group("email1") == null)
            user.put("email", matchers.get("email").group("email2"));
        else user.put("email", matchers.get("email").group("email1"));

        if (matchers.get("nickname").group("nickname1") == null)
            user.put("nickname", matchers.get("nickname").group("nickname2"));
        else user.put("nickname", matchers.get("nickname").group("nickname1"));

        if (isSlogan) {
            if (matchers.get("slogan").group("randomSlogan") != null)
                user.put("slogan", "random");
            else if (matchers.get("slogan").group("slogan1") == null)
                user.put("slogan", matchers.get("slogan").group("slogan2"));
            else user.put("slogan", matchers.get("slogan").group("slogan1"));
        } else user.put("slogan", "");
    }

    private static String validatePassword(String password) {
        if (password.equals("random")) return "";
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
        else return "";
    }

    public static String showSecurityQuestions() {
        String text = "Pick your security question:\n";
        for (SecurityQuestions question : SecurityQuestions.values()) {
            text += question.getNumber() + ". " + question.getQuestion() + "\n";
        }

        return text.substring(0, text.length() - 1);
    }

    public static String generateRandomPassword() {
        Random random = new Random();

        char[] capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] smallLetters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] digits = "0123456789".toCharArray();
        char[] specialCharacters = "!@#$%^&*()-_=+-*/~`".toCharArray();
        char[] allCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+-*/~`".toCharArray();

        int length = random.nextInt(6, 16);
        char[] password = new char[length];

        password[0] = capitalLetters[random.nextInt(capitalLetters.length)];
        password[1] = smallLetters[random.nextInt(smallLetters.length)];
        password[2] = digits[random.nextInt(digits.length)];
        password[3] = specialCharacters[random.nextInt(specialCharacters.length)];
        for (int i = 4; i < length; i++) {
            password[i] = allCharacters[random.nextInt(allCharacters.length)];
        }

        for (int i = 0; i < 4; i++) {
            int j = random.nextInt(4, length);
            char temp = password[i];
            password[i] = password[j];
            password[j] = temp;
        }

        return new String(password);
    }

    public static String generateRandomSlogan() {
        Random random = new Random();

//        TODO: remember to change bound
        int number = random.nextInt(1, 3);
        return Slogans.getSloganByNumber(number);
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
        SignupMenu.signupState = 0;
        SignupMenu.currentUser = null;
        SignupMenu.user.clear();
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

        return "";
    }

    private static String validateQuestionFields(HashMap<String, Matcher> matchers) {
        String result = "";

        if (matchers.get("questionNumber").group("number").isEmpty()) result += "question number field is empty\n";
        if (matchers.get("answer").group("content").isEmpty()) result += "answer field is empty\n";
        if (matchers.get("answerConfirm").group("content").isEmpty()) result += "answer confirmation field is empty\n";

        if (!result.isEmpty()) return result.substring(0, result.length() - 1);
        else return "";
    }

    private static void extractQuestionData(HashMap<String, Matcher> matchers, HashMap<String, String> questionAndAnswer) {
        questionAndAnswer.put("questionNumber", matchers.get("questionNumber").group("number"));

        if (matchers.get("answer").group("answer1") == null)
            questionAndAnswer.put("answer", matchers.get("answer").group("answer2"));
        else matchers.get("answer").group("answer1");

        if (matchers.get("answerConfirm").group("answerConfirm1") == null)
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
