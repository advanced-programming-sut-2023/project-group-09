package enumeration;

import javafx.scene.paint.Color;

public enum Textures {
    EARTH("earth", true, true, Color.WHEAT, Color.ROSYBROWN, 4),
    EARTH_AND_SAND("earthAndSand", true, true, Color.SLATEGRAY, Color.WHEAT, 2),
    BOULDER("boulder", true, true, Color.WHITE, Color.WHEAT, 4),
    ROCK_TEXTURE("rockTexture", true, false, Color.SADDLEBROWN, Color.WHEAT, 3),
    IRON_TEXTURE("ironTexture", true, true, Color.BROWN, Color.WHEAT, 10),
    GRASS("grass", true, true, Color.SEAGREEN, Color.WHEAT, 3),
    THICK_GRASS("thickGrass", true, true, Color.WHEAT, Color.SEAGREEN, 3),
    OASIS_GRASS("oasisGrass", true, false, Color.SEAGREEN, Color.DARKGREEN, 2),
    OIL("oil", true, false, Color.SLATEGRAY, Color.BLACK, 4),
    MARSH("marsh", false, false, Color.SLATEGRAY, Color.BLACK, 4),
    LOW_DEPTH_WATER("lowDepthWater", true, false, Color.STEELBLUE, Color.WHEAT, 2),
    RIVER("river", false, false, Color.SKYBLUE, Color.STEELBLUE, 6),
    SMALL_POND("smallPond", false, false, Color.SILVER, Color.SILVER, 2),
    LARGE_POND("largePond", false, false, Color.SILVER, Color.SILVER, 2),
    BEACH("beach", true, true, Color.WHEAT, Color.WHEAT, 2),
    SEA("sea", false, false, Color.STEELBLUE, Color.DARKBLUE, 6);


    private String name;
    private boolean passable;
    private boolean canPutBuilding;
    private Color color;
    private Color tempColor;
    private int ratio;

    private Textures(String name, boolean passable, boolean canPutBuilding, Color color, Color tempColor, int ratio) {
        this.name = name;
        this.passable = passable;
        this.canPutBuilding = canPutBuilding;
        this.color = color;
        this.tempColor = tempColor;
        this.ratio = ratio;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Color getTempColor() {
        return tempColor;
    }

    public int getRatio() {
        return ratio;
    }

    public boolean isPassable() {
        return passable;
    }

    public boolean getCanPutBuilding() {
        return canPutBuilding;
    }

    public String getTextureName() {
        return this.name;
    }

    public static Textures getTextureByName(String name) {
        for (Textures texture : Textures.values()) {
            if (texture.getTextureName().equals(name)) return texture;
        }
        return null;
    }
}
