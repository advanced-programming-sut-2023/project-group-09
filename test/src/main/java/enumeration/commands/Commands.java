package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    //...
    LOGOUT("logout"),
    BACK("back"),
    EXIT_CRUSADER("exit")

    ;

    private final String regex;

    Commands(String regex){

        this.regex = regex;
    }

    public static Matcher getMatcher(String input, Commands command){
        Pattern pattern = Pattern.compile(command.regex);
        return pattern.matcher(input);
    }

    public String getRegex() {
        return regex;
    }
}
