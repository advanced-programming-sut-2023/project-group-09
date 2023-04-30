package enumeration;

public enum Textures {
    EARTH("earth", true, true, "\u001b[48;5;137m"),
    EARTH_AND_SAND("earthAndSand", true, true, "\u001b[48;5;173m"),
    BOULDER("boulder", true, true, "\u001b[48;5;94m"),
    ROCK_TEXTURE("rockTexture", false, false, "\u001b[48;2;148;63;6m"),
    IRON_TEXTURE("ironTexture", true, true, "\u001b[48;5;88m"),
    GRASS("grass", true, true, "\u001b[48;5;40m"),
    THICK_GRASS("thickGrass", true, true, "\u001b[48;5;28m"),
    OASIS_GRASS("oasisGrass", true, false, "\u001b[48;5;22m"),
    OIL("oil", true, true, "\u001b[48;5;17m"),
    MARSH("marsh", false, false, "\u001b[48;5;37m"),
    PLAIN_TEXTURE("plainTexture", true, true, ""),
    LOW_DEPTH_WATER("lowDepthWater", true, false, "\u001b[48;5;123m"),
    RIVER("river", false, false, "\u001b[48;5;45m"),
    SMALL_POND("smallPond", false, false, "\u001b[48;5;189m"),
    LARGE_POND("largePond", false, false, "\u001b[48;5;183m"),
    BEACH("beach", true, true, "\u001b[48;5;195m"),
    SEA("sea", false, false, "\u001b[48;5;27m");

    private String name;
    private boolean passable;
    private boolean canPutBuilding;
    private String color;

    private Textures(String name, boolean passable, boolean canPutBuilding, String color) {
        this.name = name;
        this.passable = passable;
        this.canPutBuilding = canPutBuilding;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
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
