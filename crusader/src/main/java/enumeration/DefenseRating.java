package enumeration;

public enum DefenseRating {
    EXTREMELY_LOW(2),
    VERY_LOW(3),
    LOW(7),
    MEDIUM(10),
    HIGH(15),
    VERY_HIGH(30);
    private final int rate;

    DefenseRating(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
