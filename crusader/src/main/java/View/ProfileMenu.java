package View;

import Controller.UserController;
import Enumeration.Answers.Answers;
import Enumeration.Commands.Commands;
import Enumeration.Commands.ProfileCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {

    public static void run(Scanner scanner) {

        String input, output;

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
        Matcher logoutMatcher;
        Matcher exitMatcher;

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
            logoutMatcher = Commands.getMatcher(input, Commands.LOGOUT);
            exitMatcher = Commands.getMatcher(input, Commands.EXIT);

            if (logoutMatcher.matches()) {

                UserController.logout();
                System.out.println(Answers.LOGOUT_MASSAGE.getValue());
                return;

            } else if (changeUsernameMatcher.matches()) {

                String username = changeUsernameMatcher.group("username");
                output = UserController.changeUsername(username);
                System.out.println(output);

            } else if (changeNicknameMatcher.matches()) {

                String nickname1 = changeNicknameMatcher.group("nickname1");
                String nickname2 = changeNicknameMatcher.group("nickname2");

                if (nickname1 != null){
                    output = UserController.changeNickname(nickname1);
                }else{
                    output = UserController.changeNickname(nickname2);
                }

                System.out.println(output);

            } else if (changePasswordType1Matcher.matches()) {

                String oldPassword = changePasswordType1Matcher.group("old-password");
                String newPassword = changePasswordType1Matcher.group("new-password");
                output = UserController.changePassword(oldPassword, newPassword);
                System.out.println(output);

            } else if (changePasswordType2Matcher.matches()) {

                String oldPassword = changePasswordType2Matcher.group("old-password");
                String newPassword = changePasswordType2Matcher.group("new-password");
                output = UserController.changePassword(oldPassword, newPassword);
                System.out.println(output);

            } else if (changeEmailMatcher.matches()) {

                String email = changeEmailMatcher.group("email");
                output = UserController.changeEmail(email);
                System.out.println(output);

            } else if (changeSloganMatcher.matches()) {

                String slogan1 = changeSloganMatcher.group("slogan1");
                String slogan2 = changeSloganMatcher.group("slogan2");

                if (slogan1 != null){
                    output = UserController.changeSlogan(slogan1);
                }else{
                    output = UserController.changeSlogan(slogan2);
                }

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

                output = UserController.displayRank();
                System.out.println(output);

            } else if (showUserSloganMatcher.matches()) {

                output = UserController.displayRank();
                System.out.println(output);

            } else if (exitMatcher.matches()) {
                System.out.println("Main Menu:");
                return;
            } else {
                System.out.println();
            }
        }
    }

    public static void acceptPassword(String password){

    }

}
