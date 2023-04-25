package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapCommands {
    SHOW_MAP("show map (?<content>.+)"),
    X_COORDINATE("-x (?<x>[\\d]*)"),
    Y_COORDINATE("-y (?<y>[\\d]*)"),
    MOVE_MAP("move map (?<content>.+)"),
    UP("up (?<count>[\\d]*)"),
    DOWN("down (?<count>[\\d]*)"),
    LEFT("left (?<count>[\\d]*)"),
    RIGHT("right (?<count>[\\d]*)"),
    SHOW_DETAILS_OF_TILE("show details (?<content>.+)"),
    SET_TEXTURE("set texture (?<content>.+)"),
    TYPE("-t (?<type>.+)"),
    DIRECTION("-d (?<direction>.+)"),
    COUNT("-c (?<count>[\\d]*)"),
    X1_COORDINATE("-x1 (?<x1>[\\d]*)"),
    X2_COORDINATE("-x2 (?<x2>[\\d]*)"),
    Y1_COORDINATE("-y1 (?<y1>[\\d]*)"),
    Y2_COORDINATE("-y2 (?<y2>[\\d]*)"),
    CLEAR_TILE("clear land (?<content>.+)"),
    DROP_TREE("drop tree (?<content>.+)"),
    DROP_ROCK("drop rock (?<content>.+)"),
    DROP_BUILDING("drop building (?<content>.+)"),
    DROP_UNIT("drop unit (?<content>.+)"),
    CONTINUE("continue");
    private String regex;

    private MapCommands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(this.regex);
        return pattern.matcher(input);
    }
}