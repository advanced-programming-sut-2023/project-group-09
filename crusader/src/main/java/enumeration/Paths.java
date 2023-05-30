package enumeration;


public enum Paths {
    USERS_PATH("files/user/users.json"),
    CURRENT_USER_PATH("files/user/currentUser.json"),
    CAPTCHA_DATA_PATH("files/captcha/data/"),
    GOODS_PATH("files/model/goods/goods.json"),
    FOODS_PATH("files/model/goods/foods.json"),
    RESOURCES_PATH("files/model/goods/resources.json"),
    WEAPONS_PATH("files/model/goods/weapons.json"),
    EUROPEAN_TROOP_PATH("files/model/humans/europeanTroops.json"),
    ARABIAN_MERCENARY_PATH("files/model/humans/arabianMercenaries.json"),
    BUILDINGS_PATH("files/model/buildings/"),
    TOOLS_PATH("files/model/tools/tools.json"),
    CAPTCHA_IMAGES("files/captcha/images/"),
    USER_ICONS("/images/icons/usericons/")
    ;
    private final String path;

    private Paths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}