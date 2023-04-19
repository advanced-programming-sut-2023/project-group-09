package enumeration.dictionary;

public enum Trees {
    DESERT_SHRUB("desertShrub"),
    CHERRY_PALM("cherryPalm"),
    OLIVE_TREE("oliveTree"),
    COCONUT_PALM("coconutPalm"),
    DATE_PALM("datePalm");

    private Trees(String name) {
        this.name = name;
    }

    private String name;
}
