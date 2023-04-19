package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginCommands {LOGIN_REGEX("user login(?<contents>.+)"),
    USERNAME_REGEX("-u ((?<username>([^\\-\\s\"]+))|(\"(?<username2>([^\"]+))\"))"),
    PASSWORD_REGEX("-p ((?<password>([^\\s\\-\"]+))|\"(?<password2>([^\"]+))\")"),
    STAY_LOGGED_IN_REGEX("--stay-logged-in"),

    FORGOT_PASSWORD_REGEX("forgot my password -u ((?<username>([^\\-\"\\s]+))|\"(?<username2>[^\"]+)\")"),
    BACK("back");
    private String regex;

    private LoginCommands(String regex) {
        this.regex = regex;
    }
    public static Matcher getMatcher(String input , LoginCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return matcher;
    }
}
