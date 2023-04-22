package enumeration;

public enum Textures {
    EARTH("earth" , true,true),
    EARTH_AND_SAND("earthAndSand" , true,true),
    BOULDER("boulder" , true,true),
    ROCK_TEXTURE("rockTexture" , false,false),
    IRON_TEXTURE("ironTexture" , true,true),
    GRASS("grass" , true,true),
    THICK_GRASS("thickGrass" , true,true),
    OASIS_GRASS("oasisGrass" , true,false),
    OIL("oil" , true,true),
    MARSH("marsh" , false,false),
    PLAIN_TEXTURE("plainTexture" , true,true),
    LOW_DEPTH_WATER("lowDepthWater" , true,false),
    RIVER("river" , false,false),
    SMALL_POND("smallPond" , false,false),
    LARGE_POND("largePond" , false,false),
    BEACH("beach" , true,true),
    SEA("sea" , false,false);

    private Textures(String name , boolean passable,boolean canPutBuilding) {
        this.name = name;
        this.passable = passable;
        this.canPutBuilding = canPutBuilding;
    }

    private String name;
    private boolean passable;
    private boolean canPutBuilding;

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
