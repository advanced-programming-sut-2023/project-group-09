package enumeration;

public enum Textures {
    EARTH("earth" , true),
    EARTH_AND_SAND("earthAndSand" , true),
    BOULDER("boulder" , true),
    ROCK_TEXTURE("rockTexture" , false),
    IRON_TEXTURE("ironTexture" , true),
    GRASS("grass" , true),
    THICK_GRASS("thickGrass" , true),
    OASIS_GRASS("oasisGrass" , true),
    OIL("oil" , true),
    PLAIN_TEXTURE("plainTexture" , true),
    LOW_DEPTH_WATER("lowDepthWater" , true),
    RIVER("river" , false),
    SMALL_POND("smallPond" , false),
    LARGE_POND("largePond" , false),
    BEACH("beach" , true),
    SEA("sea" , false);

    private Textures(String name , boolean passable) {
        this.name = name;
        this.passable = passable;
    }

    private String name;
    private boolean passable;

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
