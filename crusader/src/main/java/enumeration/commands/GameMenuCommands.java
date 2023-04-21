package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {

    DROP_BUILDING("dropbuilding(?<items>.+)"),
    SELECT_BUILDING("select building(?<items>).+"),
    SELECT_UNIT("select unit(?<items>.+)"),
    GOVERNMENT_MENU("government menu"),
    TRADE_MENU("trade menu"),
    MARKET_MENU("market menu"),
    NEXT_TURN("next turn"),
    SHOW_PLAYER("show player"),
    SHOW_ROUND("show round"),
    X_ITEM(" -x (?<x>\\d+)"),
    Y_ITEM(" -y (?<y>\\d+"),
    TYPE_ITEM(" -type (?<type>(\"[^\"]+\"|\\S+))"),
    ;
    private final String regex;

    GameMenuCommands(String regex){
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, GameMenuCommands command){
        Pattern pattern = Pattern.compile(command.regex);
        return pattern.matcher(input);
    }

    public String getRegex() {
        return regex;
    }
}

