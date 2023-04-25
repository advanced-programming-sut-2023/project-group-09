package enumeration.dictionary;

import java.util.ArrayList;

public enum Colors {
    RED("red", "ff0000"),
    BLUE("blue", "0000ff"),
    GREEN("green", "00ff00");
    ;
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
