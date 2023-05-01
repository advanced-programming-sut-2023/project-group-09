package enumeration.dictionary;

import java.util.ArrayList;

public enum Colors {
    RED("red", "db2525"),
    BLUE("blue", "1d2cdb"),
    GREEN("green", "29db1d"),
    YELLOW("yellow", "f1f520"),
    ORANGE("orange", "db771f"),
    PURPLE("purple", "831fdb"),
    GREY("grey", "5d5c5e"),
    SKY_BLUE("sky blue", "34f7ea");
    private String name;
    private String hex;

    private Colors(String name, String hex) {
        this.name = name;
        this.hex = hex;
    }

    public String getName() {
        return this.name;
    }

    public String getHex() {
        return this.hex;
    }

    public static void getColorsList(ArrayList<Colors> colors) {
        for (Colors color : Colors.values())
            colors.add(color);
    }
}
