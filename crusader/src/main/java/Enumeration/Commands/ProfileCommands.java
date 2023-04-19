package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileCommands {
    //regex of profile menu
    CHANGE_USERNAME("profile change -u (?<username>(\"[^\"]*\")|(\\S*))"),
    CHANGE_NICKNAME("profile change -n (?<nickname>(\"[^\"]*\")|(\\S*))"),
    CHANGE_PASSWORD_TYPE1("profile change password -o (?<oldPassword>(\"[^\"]*\")|(\\S*)) -n (?<newPassword>(\"[^\"]*\")|(\\S*))"),
    CHANGE_PASSWORD_TYPE2("profile change password -n (?<newPassword>(\"[^\"]*\")|(\\S*)) -o (?<oldPassword>(\"[^\"]*\")|(\\S*))"),
    CHANGE_EMAIL("profile change -e (?<email>(\"[^\"]*\")|(\\S*))"),
    CHANGE_SLOGAN("profile change slogan -s (?<slogan>(\"[^\"]*\")|(\\S*))"),
    REMOVE_SLOGAN("Profile remove slogan"),
    SHOW_HIGHSCORE("profile display highscore"),
    SHOW_RANK("profile display rank"),
    SHOW_SLOGAN("profile display slogan"),
    SHOW_DETAILS("profile display");

    private final String regex;

    ProfileCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ProfileCommands command) {
        Pattern pattern = Pattern.compile(command.regex);
        return pattern.matcher(input);
    }
}
