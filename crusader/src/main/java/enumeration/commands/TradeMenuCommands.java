package enumeration.commands;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    TRADE("trade request(?<items>.+)"),
    TRADE_LIST("trade list"),
    TRADE_ACCEPT("trade accept(?<items>.+)"),
    TRADE_HISTORY("trade history"),
    TYPE_ITEM(" -t (?<type>(\"[^\"]\"|\\S+))"),
    AMOUNT_ITEM(" -a (?<amount>\\d+)"),
    PRICE_ITEM(" -p (?<price>\\d+)"),
    MESSAGE_ITEM(" -m (?<message>(\"[^\"]\"|\\S+))"),
    ID_ITEM(" -a (?<id>(\"[^\"]\"|\\S+))"),
    ;
    private final String regex;

    TradeMenuCommands(String regex){
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, TradeMenuCommands command){
        Pattern pattern = Pattern.compile(command.regex);
        return pattern.matcher(input);
    }

    public String getRegex() {
        return regex;
    }
}
