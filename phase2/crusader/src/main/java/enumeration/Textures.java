package enumeration;

import javafx.scene.paint.Color;

public enum Textures {
    EARTH("earth", true, true, Color.WHEAT, Color.ROSYBROWN, 4,4),
    EARTH_AND_SAND("earthAndSand", true, true, Color.SLATEGRAY, Color.WHEAT, 2,3),
    BOULDER("boulder", true, true, Color.WHITE, Color.WHEAT, 4,1),
    ROCK_TEXTURE("rockTexture", true, false, Color.SADDLEBROWN, Color.WHEAT, 3,1),
    IRON_TEXTURE("ironTexture", true, true, Color.BROWN, Color.WHEAT, 10,1),
    GRASS("grass", true, true, Color.SEAGREEN, Color.WHEAT, 3,3),
    THICK_GRASS("thickGrass", true, true, Color.WHEAT, Color.SEAGREEN, 3,3),
    OASIS_GRASS("oasisGrass", true, false, Color.SEAGREEN, Color.DARKGREEN, 2,3),
    OIL("oil", true, true, Color.SLATEGRAY, Color.BLACK, 4,3),
    MARSH("marsh", false, false, Color.SLATEGRAY, Color.BLACK, 4,3),
    LOW_DEPTH_WATER("lowDepthWater", true, false, Color.STEELBLUE, Color.WHEAT, 2,1),
    RIVER("river", false, false, Color.SKYBLUE, Color.STEELBLUE, 6,3),
    SMALL_POND("smallPond", false, false, Color.SILVER, Color.SILVER, 2,1),
    LARGE_POND("largePond", false, false, Color.SILVER, Color.SILVER, 2,1),
    BEACH("beach", true, true, Color.WHEAT, Color.WHEAT, 2,3),
    SEA("sea", false, false, Color.STEELBLUE, Color.DARKBLUE, 6,3);


    private String name;
    private boolean passable;
    private boolean canPutBuilding;
    private Color color;
    private Color tempColor;
    private int ratio;
    private int count;

    private Textures(String name, boolean passable, boolean canPutBuilding, Color color, Color tempColor, int ratio,int count) {
        this.name = name;
        this.passable = passable;
        this.canPutBuilding = canPutBuilding;
        this.color = color;
        this.tempColor = tempColor;
        this.ratio = ratio;
        this.count = count;
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

    public int getCount() {
        return count;
    }

    public static Textures getTextureByName(String name) {
        for (Textures texture : Textures.values()) {
            if (texture.getTextureName().equals(name)) return texture;
        }
        return null;
    }
}
