package enumeration;


public enum Paths {
    USERS_PATH("crusader/files/user/users.json"),
    CURRENT_USER_PATH("crusader/files/user/currentUser.json"),
    CAPTCHA_DATA_PATH("crusader/files/captcha/data/"),
    GOODS_PATH("crusader/files/model/goods/goods.json"),
    FOODS_PATH("crusader/files/model/goods/foods.json"),
    RESOURCES_PATH("crusader/files/model/goods/resources.json"),
    WEAPONS_PATH("crusader/files/model/goods/weapons.json"),
    EUROPEAN_TROOP_PATH("crusader/files/model/humans/europeanTroops.json"),
    ARABIAN_MERCENARY_PATH("crusader/files/model/humans/arabianMercenaries.json")

    ;
    private final String path;

    private Paths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}