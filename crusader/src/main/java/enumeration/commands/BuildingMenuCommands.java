package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingMenuCommands {USELECT_BUILDING("unselect"),
    CHANGE_TAX_RATE("tax rate -r (?<ratenumber>.+)"),
    SHOW_TAX_RATE("tax rate show"),
    OPEN_OR_CLOSE_GATEHOUSE("(?<order>(close|open)) gatehouse"),
    REPAIR_IT("repair it");
    private String regex;
    private BuildingMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input , BuildingMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return matcher;
    }
}
