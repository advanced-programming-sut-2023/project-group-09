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

    public String getTreeName() {
        return this.name;
    }

    public static Trees getTreeByName(String name) {
        for (Trees tree : Trees.values()) {
            if (tree.getTreeName().equals(name)) return tree;
        }
        return null;
    }

    private String name;
}
