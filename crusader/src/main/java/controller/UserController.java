package controller;

import enumeration.dictionary.SecurityQuestions;
import enumeration.answers.LoginAnswers;
import enumeration.commands.SignupMenuCommands;
import enumeration.dictionary.Slogans;
import javafx.scene.control.Alert;
import model.User;
import view.controllers.ViewController;
import view.menus.LoginMenu;
import viewphase1.ProfileMenu;
import viewphase1.SignupMenu;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        User newUser = new User(user.get("username"), convertPasswordToHash(user.get("password")), user.get("nickname"), user.get("email"), user.get("slogan"));
        Application.addUser(newUser);

        SignupMenu.currentUser = newUser;
        SignupMenu.signupState = 4;
        return "";
    }

    private static String validateSignup(HashMap<String, Matcher> matchers, HashMap<String, String> user, boolean isSlogan) {
        if (!matchers.get("username").find() || !matchers.get("password").find() || !matchers.get("email").find() || !matchers.get("nickname").find())
            return "invalid command";

        String fieldsValidation = validateSignupFields(matchers, isSlogan);
        if (!fieldsValidation.isEmpty()) return fieldsValidation;

        extractUserData(matchers, user, isSlogan);

        if (!SignupMenuCommands.USERNAME_VALIDATION.getMatcher(user.get("username")).matches())
            return "username is not valid";

        if (Application.isUserExistsByName(user.get("username")))
            return "username already exists";

        String passwordValidation = validatePassword(user.get("password"));
        if (!passwordValidation.isEmpty()) return passwordValidation;

        if (user.get("passwordConfirm") != null && !user.get("passwordConfirm").equals(user.get("password")))
            return "password confirmation doesn't match with password";

        if (Application.isUserExistsByEmail(user.get("email")))
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

        int number = random.nextInt(1, Slogans.values().length + 1);
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
        SignupMenu.signupState = 5;
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
        else questionAndAnswer.put("answer", matchers.get("answer").group("answer1"));

        if (matchers.get("answerConfirm").group("answerConfirm1") == null)
            questionAndAnswer.put("answerConfirm", matchers.get("answerConfirm").group("answerConfirm2"));
        else questionAndAnswer.put("answerConfirm", matchers.get("answerConfirm").group("answerConfirm1"));
    }

    public static String loginUser(String username, String password, boolean stayedLoggedIn) throws MalformedURLException {
        DBController.loadAllUsers();
        if (!Application.isUserExistsByName(username)) {
            LoginMenu.username.handlingError(LoginAnswers.USER_DOESNT_EXIST_MESSAGE.getMessage());
            return LoginAnswers.USER_DOESNT_EXIST_MESSAGE.getMessage();
        }
        User user = Application.getUserByUsername(username);
        if (!user.isPasswordCorrect(password)) {
            LoginMenu.password.handlingError(LoginAnswers.WRONG_PASSWORD_MESSAGE.getMessage());
            return LoginAnswers.WRONG_PASSWORD_MESSAGE.getMessage();
        }
        if (view.controllers.CaptchaController.getCaptcha().isInputCorrect()) {
            Application.setCurrentUser(user);
            Application.setStayLoggedIn(stayedLoggedIn);
            DBController.saveCurrentUser();
            ViewController.createAndShowAlert(Alert.AlertType.INFORMATION, "Login was successful!"
                    , "Login was successful!", "You logged in successfully!");
            // TODO : go to main menu
            return LoginAnswers.SUCCESSFUL_LOGIN_MESSAGE.getMessage();
        }
        return "";
    }

    public static String forgotPassword(String username) {
        if (!Application.isUserExistsByName(username)) {
            return LoginAnswers.USER_DOESNT_EXIST_MESSAGE.getMessage();
        }
        User user = Application.getUserByUsername(username);
        return user.getPasswordRecoveryQuestion().replace("my" , "your");
    }

    public static String changePasswordWithSecurityQuestion(String username, String newPassword, String newPasswordConfirmation) {
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
                }
                break;
                case 2: {
                    result += LoginAnswers.PASSWORD_LOWERCASE_ERROR.getMessage();
                }
                break;
                case 3: {
                    result += LoginAnswers.PASSWORD_UPPERCASE_ERROR.getMessage();
                }
                break;
                case 4: {
                    result += LoginAnswers.PASSWORD_NUMBER_ERROR.getMessage();
                }
                break;
                case 5: {
                    result += LoginAnswers.PASSWORD_OTHER_CHARACTERS_ERROR.getMessage();
                }
                break;
            }
            return result;
        }
        user.setPassword(UserController.convertPasswordToHash(newPassword));
        DBController.saveAllUsers();
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

    private static boolean checkUsernameChars(String username) {
        Pattern pattern = Pattern.compile("[^a-zA-Z_\\d]");
        Matcher matcher = pattern.matcher(username);
        return matcher.find();
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
        if (UserController.checkNullFields(newNickname)) {
            return "nickname field is required!";
        }

        User user = Application.getCurrentUser();
        user.setNickname(newNickname);
        return "nickname changed successfully!";
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

    public static String changePassword(String oldPassword, String newPassword) {
        String message = validateChangePassword(oldPassword, newPassword);
        if (message != null) {
            return message;
        }
        boolean captchaVerification = CaptchaController.isCaptchaTrue(ProfileMenu.profileMenuScanner);
        if (!captchaVerification) {
            return "your behavior was not verified by captcha!";
        }
        boolean check;
        if (newPassword.equals("random")) {
            newPassword = generateRandomPassword();
            check = ProfileMenu.acceptRandomPassword(newPassword);

        } else {
            check = ProfileMenu.acceptNewPassword(newPassword);
        }

        if (check) {
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

    private static boolean checkNullFields(String input) {
        return input == null || input.length() == 0;
    }

    private static boolean checkEmailFormat(String email) {
        Pattern pattern = Pattern.compile("[a-zA-Z_\\d]+@[a-zA-Z_\\d]+\\.[a-zA-Z_\\d]+");
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
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
        if (Application.getCurrentUser() == null) {
            return "You didn't log in!";
        } else {
            Application.getCurrentUser().setSlogan(null);
            return "slogan removed successfully!";
        }
    }

    public static String displayHighScore() {
        return "high score: " + Application.getCurrentUser().getHighScore();
    }

    public static String displayRank() {
        return "your rank: " + getRank();
    }

    public static String displaySlogan() {
        if (Application.getCurrentUser().getSlogan() == null ||
            Application.getCurrentUser().getSlogan().equals("")) {
            return "slogan: Slogan is empty!";
        }
        return "slogan: " + Application.getCurrentUser().getSlogan();
    }

    public static String displayProfile() {
        User user = Application.getCurrentUser();
        String output = "username: " + user.getUsername() + "\n";
        output += "nickname: " + user.getNickname() + "\n";
        output += "email: " + user.getEmail() + "\n";
        output += "slogan: " + ((user.getSlogan() == null || user.getSlogan().equals("")) ? "Slogan is empty!" : user.getSlogan()) + "\n";
        output += "highscore: " + user.getHighScore() + "\n";
        output += "rank: " + getRank();

        return output;
    }


    //profile menu functions
    private static int getRank() {
        ArrayList<User> users = getSortedListOfUsers();
        int index = 1;
        for (User user : users) {
            if (user.getUsername().equals(Application.getCurrentUser().getUsername())) {
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
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
    }



    public static int isPasswordStrong(String password){
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

    public static String showScoreboard() {
        String result = "";
        ArrayList<User> sortedUsers = getSortedListOfUsers();
        int rank = 1;
        for (User user : sortedUsers) {
            result += rank + ". " + user.getUsername() + " ***** highscore : " + user.getHighScore() + "\n";
            rank++;
        }
        return result;
    }


}
