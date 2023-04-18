package Enumeration.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingCommands {DROP_BUILDING_REGEX("dropbuilding( -x (?<x>.+)| -y (?<y>.+)| -type (?<type>.+))*"),
    X_COORD_REGEX("-x (?<x>[\\S]+)"),
    Y_COORD_REGEX("-y (?<y>[\\S]+)"),
    TYPE_REGEX("-type (?<type>[^\\s\\-]+|\".+\")"),
    SELECT_BUILDING_REGEX("select building( -x (?<x>.+)| -y (?<y>.+))*"),
    CREATE_UNIT_REGEX("createunit( -t (?<type>.+)| -c (?<count>[\\S]+))*"),
    REPAIR_REGEX("repair");
    private String regex;

    private BuildingCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input , BuildingCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return matcher;
    }
}
