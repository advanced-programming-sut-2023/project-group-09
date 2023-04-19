package view;

import controller.UserController;
import controller.ViewController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;
import enumeration.commands.ProfileCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    public static Scanner profileMenuScanner;
    public static void run(Scanner scanner) {

        String input, output;
        profileMenuScanner = scanner;
        //profile menu commands should check with this matchers
        Matcher changeUsernameMatcher;
        Matcher changeNicknameMatcher;
        Matcher changePasswordType1Matcher;
        Matcher changePasswordType2Matcher;
        Matcher changeEmailMatcher;
        Matcher changeSloganMatcher;
        Matcher removeSloganMatcher;
        Matcher showUserHighscoreMatcher;
        Matcher showUserRankMatcher;
        Matcher showUserSloganMatcher;
        Matcher showUserProfileMatcher;
        Matcher exitMatcher;

        System.out.println("<< Profile Menu >>");

        while (true) {

            input = scanner.nextLine();

            changeUsernameMatcher = ProfileCommands.getMatcher(input, ProfileCommands.CHANGE_USERNAME);
            changeNicknameMatcher = ProfileCommands.getMatcher(input, ProfileCommands.CHANGE_NICKNAME);
            changePasswordType1Matcher = ProfileCommands.getMatcher(input, ProfileCommands.CHANGE_PASSWORD_TYPE1);
            changePasswordType2Matcher = ProfileCommands.getMatcher(input, ProfileCommands.CHANGE_PASSWORD_TYPE2);
            changeEmailMatcher = ProfileCommands.getMatcher(input, ProfileCommands.CHANGE_EMAIL);
            changeSloganMatcher = ProfileCommands.getMatcher(input, ProfileCommands.CHANGE_SLOGAN);
            removeSloganMatcher = ProfileCommands.getMatcher(input, ProfileCommands.REMOVE_SLOGAN);
            showUserHighscoreMatcher = ProfileCommands.getMatcher(input, ProfileCommands.SHOW_HIGHSCORE);
            showUserRankMatcher = ProfileCommands.getMatcher(input, ProfileCommands.SHOW_RANK);
            showUserSloganMatcher = ProfileCommands.getMatcher(input, ProfileCommands.SHOW_SLOGAN);
            showUserProfileMatcher = ProfileCommands.getMatcher(input, ProfileCommands.SHOW_DETAILS);
            exitMatcher = Commands.getMatcher(input, Commands.BACK);

            if (changeUsernameMatcher.matches()) {

                String username = changeUsernameMatcher.group("username");
                username = ViewController.editItem(username);
                output = UserController.changeUsername(username);
                System.out.println(output);

            } else if (changeNicknameMatcher.matches()) {

                String nickname = changeNicknameMatcher.group("nickname");
                nickname = ViewController.editItem(nickname);
                output = UserController.changeNickname(nickname);
                System.out.println(output);

            } else if (changePasswordType1Matcher.matches()) {

                String newPassword = changePasswordType1Matcher.group("newPassword");
                String oldPassword = changePasswordType1Matcher.group("oldPassword");
                oldPassword = ViewController.editItem(oldPassword);
                newPassword = ViewController.editItem(newPassword);

                output = UserController.changePassword(oldPassword, newPassword);
                System.out.println(output);

            } else if (changePasswordType2Matcher.matches()) {

                String oldPassword = changePasswordType2Matcher.group("oldPassword");
                String newPassword = changePasswordType2Matcher.group("newPassword");
                oldPassword = ViewController.editItem(oldPassword);
                newPassword = ViewController.editItem(newPassword);

                output = UserController.changePassword(oldPassword, newPassword);
                System.out.println(output);

            } else if (changeEmailMatcher.matches()) {

                String email = changeEmailMatcher.group("email");
                email = ViewController.editItem(email);
                output = UserController.changeEmail(email);
                System.out.println(output);

            } else if (changeSloganMatcher.matches()) {

                String slogan = changeSloganMatcher.group("slogan");
                slogan = ViewController.editItem(slogan);

                output = UserController.changeSlogan(slogan);
                System.out.println(output);

            } else if (removeSloganMatcher.matches()) {

                output = UserController.removeSlogan();
                System.out.println(output);

            } else if (showUserHighscoreMatcher.matches()) {

                output = UserController.displayHighScore();
                System.out.println(output);

            } else if (showUserRankMatcher.matches()) {

                output = UserController.displayRank();
                System.out.println(output);

            } else if (showUserProfileMatcher.matches()) {

                output = UserController.displayProfile();
                System.out.println(output);

            } else if (showUserSloganMatcher.matches()) {

                output = UserController.displaySlogan();
                System.out.println(output);

            } else if (exitMatcher.matches()) {
                return;
            } else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }
        }
    }

    public static boolean acceptNewPassword(String password){
        System.out.println("please enter your new password again:");
        String input = profileMenuScanner.nextLine();
        return input.equals(password);
    }

    public static boolean acceptRandomPassword(String password){
        System.out.println("your new password is \"" + password +"\" please type it:");
        String input = profileMenuScanner.nextLine();
        return input.equals(password);
    }
}
