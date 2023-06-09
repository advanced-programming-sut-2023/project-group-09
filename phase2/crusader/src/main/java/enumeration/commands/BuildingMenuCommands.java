package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingMenuCommands {
    UNSELECT_BUILDING("unselect"),
    CHANGE_TAX_RATE("tax rate -r (?<ratenumber>.+)"),
    SHOW_TAX_RATE("tax rate show"),
    OPEN_OR_CLOSE_GATEHOUSE("(?<order>(close|open)) gatehouse"),
    REPAIR_IT("repair it"),
    SHOW_GATE_STATE("show state"),
    SHOW_WEAPONS("show weapons"),
    SHOW_UNITS_LIST("show units list"),
    BUY_UNIT("buy unit (?<unitname>.+)"),
    SHOW_GOODS_SAVED("show saved goods"),
    HOW_MANY_HORSES("how many horses"),
    CHANGE_WEAPON_IN_PRODUCTION("change weapon in production"),
    SHOW_STATE_OF_PRODUCTS("show state of product"),
    UNLEASH_WAR_DOGS("unleash war dogs");
    private String regex;

    private BuildingMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, BuildingMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return matcher;
    }
}
