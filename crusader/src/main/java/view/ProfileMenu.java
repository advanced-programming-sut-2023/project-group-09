package view;

import controller.UserController;
import controller.ViewController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;
import enumeration.commands.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    public static Scanner profileMenuScanner;
    public static void run(Scanner scanner) {

        String input, output;
        profileMenuScanner = scanner;
        System.out.println("<< Profile Menu >>");

        while (true) {
            input = scanner.nextLine();

            //profile menu commands should check with this matchers
            Matcher changeUsernameMatcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.CHANGE_USERNAME);
            Matcher changeNicknameMatcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.CHANGE_NICKNAME);
            Matcher changePasswordType1Matcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.CHANGE_PASSWORD_TYPE1);
            Matcher changePasswordType2Matcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.CHANGE_PASSWORD_TYPE2);
            Matcher changeEmailMatcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.CHANGE_EMAIL);
            Matcher changeSloganMatcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.CHANGE_SLOGAN);
            Matcher removeSloganMatcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.REMOVE_SLOGAN);
            Matcher showUserHighscoreMatcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.SHOW_HIGHSCORE);
            Matcher showUserRankMatcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.SHOW_RANK);
            Matcher showUserSloganMatcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.SHOW_SLOGAN);
            Matcher showUserProfileMatcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.SHOW_DETAILS);
            Matcher exitMatcher = Commands.getMatcher(input, Commands.BACK);

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
