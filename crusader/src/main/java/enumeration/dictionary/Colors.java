package enumeration.dictionary;

import java.util.ArrayList;

public enum Colors {
    RED("red", "\u001B[38;2;219;37;37m", "rgb(219, 37, 37)"),
    BLUE("blue", "\u001B[38;2;29;44;219m", "rgb(29, 44, 219)"),
    GREEN("green", "\u001B[38;2;41;219;29m", "rgb(41, 219, 29)"),
    YELLOW("yellow", "\u001B[38;2;241;245;32m", "rgb(241, 245, 32)"),
    ORANGE("orange", "\u001B[38;2;219;119;31m", "rgb(219, 119, 31)"),
    PURPLE("purple", "\u001B[38;2;131;31;219m", "rgb(131, 31, 219)"),
    GREY("grey", "\u001B[38;2;93;92;94m", "rgb(93, 92, 94)"),
    SKY_BLUE("sky blue", "\u001B[38;2;52;247;234m", "rgb(52, 247, 234)");
    private String name;
    private String textColor;
    private String rgb;

    private Colors(String name, String textColor, String rgb) {
        this.name = name;
        this.textColor = textColor;
        this.rgb = rgb;
    }

    public String getName() {
        return this.name;
    }

    public String getTextColor() {
        return this.textColor;
    }

    public String getRgb() {
        return rgb;
    }

    public static void getColorsList(ArrayList<Colors> colors) {
        for (Colors color : Colors.values())
            colors.add(color);
    }
}
