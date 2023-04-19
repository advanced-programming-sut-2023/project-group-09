package enumeration.dictionary;

public enum Foodstuffs {
    ;
    private final String foodstuff;

    Foodstuffs(String foodstuff) {
        this.foodstuff = foodstuff;
    }

    public String getFoodstuff() {
        return foodstuff;
    }
}
