package Enumeration.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginCommands {LOGIN_REGEX("user login(?<contents>.+)"),
    USERNAME_REGEX("-u (?<username>([\\S]+)|(\".+\"))"),
    PASSWORD_REGEX("-p (?<password>([\\S]+)|(\".+\"))"),
    STAY_LOGGED_IN_REGEX("--stay-logged-in"),

    FORGOT_PASSWORD_REGEX("forgot my password -u (?<username>([\\S]+)|(\".+\"))");
    private String regex;

    private LoginCommands(String regex) {
        this.regex = regex;
    }
    public static Matcher getMatcher(String input , LoginCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return matcher;
    }
}
