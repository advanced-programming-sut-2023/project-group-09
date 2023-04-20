package enumeration;

public enum AttackRating {VERY_LOW(1),
    LOW(2),
    MEDIUM(3),
    HIGH(4),
    VERY_HIGH(5)
    ;
    private final int rate;

    AttackRating(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
