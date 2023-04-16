package Enumeration;

public enum Textures {
    EARTH("earth"),
    EARTH_AND_SAND("earthAndSand"),
    BOULDER("boulder"),
    ROCK_TEXTURE("rockTexture"),
    IRON_TEXTURE("ironTexture"),
    GRASS("grass"),
    THICK_GRASS("thickGrass"),
    OASIS_GRASS("oasisGrass"),
    OIL("oil"),
    PLAIN_TEXTURE("plainTexture"),
    LOW_DEPTH_WATER("lowDepthWater"),
    RIVER("river"),
    SMALL_POND("smallPond"),
    LARGE_POND("largePond"),
    BEACH("beach"),
    SEA("sea");

    private Textures(String name){
        this.name = name;
    }
    private String name;
}
