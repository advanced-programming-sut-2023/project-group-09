package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ToolMenuCommands {
    MOVE("move tool to (?<content>.+)"),
    DISBAND("disband"),
    STOP("stop"),
    PATROL("patrol (?<content>.+)"),
    ATTACK("attack (?<content>.+)"),
    FREE("free engineers"),
    ADD_STONE("add stone"),
    X_COORDINATE("-x (?<x>[\\d]*)"),
    Y_COORDINATE("-y (?<y>[\\d]*)"),
    X1_COORDINATE("-x1 (?<x1>[\\d]*)"),
    X2_COORDINATE("-x2 (?<x2>[\\d]*)"),
    Y1_COORDINATE("-y1 (?<y1>[\\d]*)"),
    Y2_COORDINATE("-y2 (?<y2>[\\d]*)"),
    BACK("back");
    private String regex;

    private ToolMenuCommands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(this.regex);
        return pattern.matcher(input);
    }
}
