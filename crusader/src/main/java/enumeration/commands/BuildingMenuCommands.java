package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingMenuCommands {DROP_BUILDING_REGEX("dropbuilding(?<content>.+)"),
    X_COORD_REGEX("-x (?<x>[\\S]+)"),
    Y_COORD_REGEX("-y (?<y>[\\S]+)"),
    TYPE_REGEX("-type (?<type>[^\\s\\-]+|\"[^\"]+\")"),
    SELECT_BUILDING_REGEX("select building(?<content>.+)"),
    CREATE_UNIT_REGEX("createunit( -t (?<type>.+)| -c (?<count>[\\S]+))*"),
    REPAIR_REGEX("repair");
    private String regex;

    private BuildingMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input , BuildingMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return matcher;
    }
}
