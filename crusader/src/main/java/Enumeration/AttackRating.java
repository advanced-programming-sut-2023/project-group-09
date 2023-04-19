package enumeration;

public enum AttackRating {
    ;
    private final int rate;

    AttackRating(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
