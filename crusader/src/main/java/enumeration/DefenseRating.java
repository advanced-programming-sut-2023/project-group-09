package enumeration;

public enum DefenseRating {POOR(1),
    VERY_LOW(2),
    LOW(3),
    MEDIUM(4),
    HIGH(5);
    private final int rate;

    DefenseRating(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
