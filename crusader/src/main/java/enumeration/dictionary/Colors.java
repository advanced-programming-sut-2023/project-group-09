package enumeration.dictionary;

import java.util.ArrayList;

public enum Colors {
    RED("red", "\u001B[38;2;219;37;37m"),
    BLUE("blue", "\u001B[38;2;29;44;219m"),
    GREEN("green", "\u001B[38;2;41;219;29m"),
    YELLOW("yellow", "\u001B[38;2;241;245;32m"),
    ORANGE("orange", "\u001B[38;2;219;119;31m"),
    PURPLE("purple", "\u001B[38;2;131;31;219m"),
    GREY("grey", "\u001B[38;2;93;92;94m"),
    SKY_BLUE("sky blue", "\u001B[38;2;52;247;234m");
    private String name;
    private String rgb;

    private Colors(String name, String hex) {
        this.name = name;
        this.rgb = hex;
    }

    public String getName() {
        return this.name;
    }

    public String getRgb() {
        return this.rgb;
    }

    public static void getColorsList(ArrayList<Colors> colors) {
        for (Colors color : Colors.values())
            colors.add(color);
    }
}
