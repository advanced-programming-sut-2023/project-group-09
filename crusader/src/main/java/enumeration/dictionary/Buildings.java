package enumeration.dictionary;

public enum Buildings {MAIN_CASTLE("mainCastle"),
    LOW_WALL("lowWall"),
    STONE_WALL("stoneWall"),
    CRENULATED_WALL("crenulatedWall"),
    STAIRS("stairs"),
    SMALL_STONE_GATEHOUSE("smallStoneGatehouse"),
    BIG_STONE_GATEHOUSE("bigStoneGatehouse"),
    DRAW_BRIDGE("drawBridge"),
    LOOKOUT_TOWER("lookoutTower"),
    PERIMETER_TURRET("perimeterTurret"),
    DEFENSE_TURRET("defenseTurret"),
    ROUND_TOWER("roundTower"),
    SQUARE_TOWER("squareTower"),
    ARMOURY("armoury"),
    BARRACK("barrack"),
    MERCENARY_POST("mercenaryPost"),
    ENGINEERS_GUILD("engineerGuild"),
    INN("inn"),
    MILL("mill"),
    IRON_MINE("ironMine"),
    SHOP("shop"),
    PITCH_RIG("pitchRig"),
    QUARRY("quarry"),
    STOCK_PILE("stockPile"),
    WOOD_CUTTER("woodCutter"),
    HOVEL("hovel"),
    CHURCH("church"),
    CATHEDRAL("cathedral"),
    ARMOURER("armourer"),
    BLACK_SMITH("blackSmith"),
    FLETCHER("fletcher"),
    POLE_TURNER("poleTurner"),
    TUNNEL_ENTRANCE("tunnelEntrance"),
    OIL_SMELTER("oilSmelter"),
    CAGED_WAR_DOGS("cagedWarDogs"),
    SIEGE_TENT("siegeTent"),
    STABLE("stable"),
    TUNNELERS_GUILD("tunnelersGuild"),
    APPLE_GARDEN("appleGarden"),
    DAIRY_PRODUCTS("dairyProducts"),
    HOP_FARM("hopFarm"),
    HUNTING_POST("huntingPost"),
    WHEAT_FARM("wheatFarm"),
    BAKERY("bakery"),
    BREWERY("brewery"),
    GRANARY("granary"),
    OX_TETHER("oxTether");

    private String name;
    private Buildings(String name) {
        this.name = name;
    }
    public static String getName(Buildings building) {
        return building.name;
    }
}
