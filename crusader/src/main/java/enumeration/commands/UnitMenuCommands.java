package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UnitMenuCommands {
    MOVE_UNIT("move unit to(?<items>.+)"),
    PATROL_UNIT("patrol unit(?<items>.+)"),
    SET_UNIT_STATE("set(?<items>.+)"),
    ATTACK("attack(?<items>.+)"),
    POUR_OIL("pour oil -d (?<direction>(\"[^\"]\"|\\S+))"),
    DIG_TUNNEL("dig tunnel(?<items>.+)"),
    BUILD("build"),
    DIG_MOAT("dig moat"),
    DISBAND_UNIT("disband unit"),
    X_ITEM(" -x (?<x>\\d+)"),
    Y_ITEM(" -y (?<y>\\d+)"),
    X1_ITEM(" -x1 (?<x1>\\d+)"),
    Y1_ITEM(" -y1 (?<y1>\\d+"),
    X2_ITEM(" -x2 (?<x2>\\d+)"),
    Y2_ITEM(" -y2 (?<y2>\\d+"),
    S_ITEM(" -s (?<state>(\"[^\"]\"|\\S+))");
    private final String regex;

    UnitMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, UnitMenuCommands command) {
        Pattern pattern = Pattern.compile(command.regex);
        return pattern.matcher(input);
    }

    public String getRegex() {
        return regex;
    }
}
