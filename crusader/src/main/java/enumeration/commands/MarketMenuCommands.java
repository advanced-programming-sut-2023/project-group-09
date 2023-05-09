package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MarketMenuCommands {

    SHOW_PRICE_LIST("show price list"),
    BUY("buy(?<items>).+)"),
    SELL("sell(?<items>.+)"),
    NAME_ITEM(" -i (?<name>(\"[^\"]*\"|\\S*))"),
    AMOUNT_ITEM(" -a (?<amount>\\d*)"),
    ;
    private final String regex;

    MarketMenuCommands(String regex){
        this.regex = regex;
    }

    public static Matcher getMatcher(String input,  MarketMenuCommands command){
        Pattern pattern = Pattern.compile(command.regex);
        return pattern.matcher(input);
    }

    public String getRegex() {
        return regex;
    }
}
