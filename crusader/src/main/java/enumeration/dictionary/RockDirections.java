package enumeration.dictionary;

public enum RockDirections {
    DESERT_SHRUB("desertShrub"),
    CHERRY_PALM("cherryPalm"),
    OLIVE_TREE("oliveTree"),
    COCONUT_PALM("coconutPalm"),
    DATE_PALM("datePalm");

    private RockDirections(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return this.direction;
    }

    public static RockDirections getRockByDirection(String direction) {
        for (RockDirections rock : RockDirections.values()) {
            if (rock.getDirection().equals(direction)) return rock;
        }
        return null;
    }

    private String direction;
}
