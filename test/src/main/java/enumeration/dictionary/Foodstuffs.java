package enumeration.dictionary;

public enum Foodstuffs {MEAT("meat"),
    CHEESE("cheese"),
    APPLE("apple"),
    BREAD("bread")
    ;
    private final String foodstuff;

    Foodstuffs(String foodstuff) {
        this.foodstuff = foodstuff;
    }

    public String getFoodstuff() {
        return foodstuff;
    }
}
