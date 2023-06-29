package enumeration;

public enum AttackRating {
    NONE(0),
    EXTREMELY_LOW(1),
    VERY_LOW(2),
    LOW(3),
    MEDIUM(4),
    HIGH(5),
    VERY_HIGH(6);
    private final int rate;

    AttackRating(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
