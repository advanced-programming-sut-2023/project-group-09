package Enumeration.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    //...
    LOGOUT("logout"),
    EXIT("exit"),
    EXIT_CRUSADER("exit crusader")
    ;

    private final String regex;

    Commands(String regex){

        this.regex = regex;
    }

    public static Matcher getMatcher(String input, Commands command){
        Pattern pattern = Pattern.compile(command.regex);
        return pattern.matcher(input);
    }
}
