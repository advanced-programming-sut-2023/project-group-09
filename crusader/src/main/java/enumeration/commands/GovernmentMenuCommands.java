package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GovernmentMenuCommands {
    SHOW_POPULARITY_FACTORS("show popularity factors"),
    SHOW_POPULARITY("show popularity"),
    SHOW_FOOD_LIST("show food list"),
    FOOD_RATE("food rate -r (?<rate>\\-?\\d*)"),
    SHOW_FOOD_RATE("show food rate"),
    TAX_RATE("tax rate -r (?<rate>\\-?\\d*)"),
    SHOW_TAX_RATE("show tax rate"),
    FEAR_RATE("fear rate -r (?<rate>\\-?\\d*)"),
    SHOW_FEAR_RATE("show fear rate"),
    SHOW_PROPERTIES("show properties")
    ;
    private final String regex;

    GovernmentMenuCommands(String regex){
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, GovernmentMenuCommands command){
        Pattern pattern = Pattern.compile(command.regex);
        return pattern.matcher(input);
    }
}
