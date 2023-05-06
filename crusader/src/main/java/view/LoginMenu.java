package view;

import controller.UserController;
import enumeration.answers.LoginAnswers;
import enumeration.commands.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    static int delayTime = 0;
    static int wrongPassTimes = 0;

    static void delayForWrongPass() {
        wrongPassTimes++;
        if (wrongPassTimes % 3 != 0)
            return;
        delayTime += 5;
        System.out.println("Try for " + delayTime + " seconds later...");
        System.out.println("Please don't type anything before time out.");
        try {
            Thread.sleep(1000 * delayTime);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static void run(Scanner scanner) {
        System.out.println("<< Login Menu >>");
        delayTime = wrongPassTimes = 0;
        while (true) {
            String command = scanner.nextLine();
            Matcher loginMatcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.LOGIN_REGEX);
            Matcher forgotPasswordMatcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.FORGOT_PASSWORD_REGEX);
            Matcher backMatcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.BACK);
            if (loginMatcher.matches()) {
                String contents = loginMatcher.group("contents");
                Matcher usernameMatcher = LoginMenuCommands.getMatcher(contents, LoginMenuCommands.USERNAME_REGEX);
                Matcher passwordMatcher = LoginMenuCommands.getMatcher(contents, LoginMenuCommands.PASSWORD_REGEX);
                Matcher stayLoggedInMatcher = LoginMenuCommands.getMatcher(contents, LoginMenuCommands.STAY_LOGGED_IN_REGEX);
                if (usernameMatcher.results().count() != 1) {
                    System.out.println(LoginAnswers.INVALID_USERNAME_INPUT_MESSAGE.getMessage());
                    continue;
                }
                if (passwordMatcher.results().count() != 1) {
                    System.out.println(LoginAnswers.INVALID_PASSWORD_INPUT_MESSAGE.getMessage());
                    continue;
                }
                if (stayLoggedInMatcher.results().count() > 1) {
                    System.out.println(LoginAnswers.INVALID_LOGIN_INPUT_MESSAGE.getMessage());
                    continue;
                }
                String result = "";
                usernameMatcher = LoginMenuCommands.getMatcher(contents, LoginMenuCommands.USERNAME_REGEX);
                passwordMatcher = LoginMenuCommands.getMatcher(contents, LoginMenuCommands.PASSWORD_REGEX);
                stayLoggedInMatcher = LoginMenuCommands.getMatcher(contents, LoginMenuCommands.STAY_LOGGED_IN_REGEX);
                if (usernameMatcher.find() && passwordMatcher.find()) {
                    String username = usernameMatcher.group("username") != null ?
                            usernameMatcher.group("username") : usernameMatcher.group("username2");
                    String password = passwordMatcher.group("password") != null ?
                            passwordMatcher.group("password") : passwordMatcher.group("password2");
                    result = UserController.loginUser(username, password, stayLoggedInMatcher.find());
                }
                System.out.println(result);
                if (result.equals(LoginAnswers.WRONG_PASSWORD_MESSAGE.getMessage())) {
                    delayForWrongPass();
                } else if (result.equals(LoginAnswers.SUCCESSFUL_LOGIN_MESSAGE.getMessage())) {
                    MainMenu.run(scanner);
                    break;
                }
            } else if (forgotPasswordMatcher.matches()) {
                String username = forgotPasswordMatcher.group("username");
                String result = UserController.forgotPassword(username);
                System.out.println(result);
                if (!result.equals(LoginAnswers.USER_DOESNT_EXIST_MESSAGE.getMessage())) {
                    String answerToQuestion = scanner.nextLine();
                    if (UserController.checkSecurityQuestion(username, answerToQuestion)) {
                        System.out.println(LoginAnswers.ENTER_YOUR_PASSWORD_MESSAGE.getMessage());
                        String newPassword = scanner.nextLine();
                        System.out.println(LoginAnswers.PASSWORD_CONFIRMATION_MESSAGE.getMessage());
                        String newPasswordConfirmation = scanner.nextLine();
                        System.out.println(UserController.changePasswordWithSecurityQuestion(username, newPassword, newPasswordConfirmation));
                    } else {
                        System.out.println(LoginAnswers.WRONG_ANSWER_MESSAGE.getMessage());
                    }
                }
            } else if (backMatcher.matches()) {
                break;
            } else {
                System.out.println(LoginAnswers.INVALID_COMMAND_MESSAGE.getMessage());
            }
        }
    }
}
