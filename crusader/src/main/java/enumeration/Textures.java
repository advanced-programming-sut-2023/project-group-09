package enumeration;

public enum Textures {
    EARTH("earth", true, true, "\u001b[48;5;137m",255, 233, 126),
    EARTH_AND_SAND("earthAndSand", true, true, "\u001b[48;5;173m",189, 168, 69),
    BOULDER("boulder", true, true, "\u001b[48;5;94m",140, 115, 104),
    ROCK_TEXTURE("rockTexture", true, false, "\u001b[48;2;148;63;6m",91, 9, 166),
    IRON_TEXTURE("ironTexture", true, true, "\u001b[48;5;88m",143, 14, 2),
    GRASS("grass", true, true, "\u001b[48;5;40m",73, 234, 27),
    THICK_GRASS("thickGrass", true, true, "\u001b[48;5;28m",73, 234, 27),
    OASIS_GRASS("oasisGrass", true, false, "\u001b[48;5;22m",73, 234, 27),
    OIL("oil", true, true, "\u001b[48;5;17m",140, 140, 140),
    MARSH("marsh", false, false, "\u001b[48;5;37m",255, 233, 126),
    PLAIN_TEXTURE("plainTexture", true, true, "",256,256,256),
    LOW_DEPTH_WATER("lowDepthWater", true, false, "\u001b[48;5;123m",149, 198, 248),
    RIVER("river", false, false, "\u001b[48;5;45m",40, 142, 255),
    SMALL_POND("smallPond", false, false, "\u001b[48;5;189m",83, 167, 255),
    LARGE_POND("largePond", false, false, "\u001b[48;5;183m",83, 167, 255),
    BEACH("beach", true, true, "\u001b[48;5;195m",82, 74, 58),
    SEA("sea", false, false, "\u001b[48;5;27m",3, 54, 103);

    public int getR() {
        return r;
    }

    public int getB() {
        return b;
    }

    public int getG() {
        return g;
    }

    private String name;
    private boolean passable;
    private boolean canPutBuilding;
    private String color;
    int r,b,g;

    private Textures(String name, boolean passable, boolean canPutBuilding, String color,int r, int b,int g) {
        this.name = name;
        this.passable = passable;
        this.canPutBuilding = canPutBuilding;
        this.color = color;
        this.r = r;
        this.b = b;
        this.g = g;
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
