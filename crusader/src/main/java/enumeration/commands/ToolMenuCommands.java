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
