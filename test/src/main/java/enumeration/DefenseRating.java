package enumeration;

public enum DefenseRating {
    EXTREMELY_LOW(3),
    VERY_LOW(5),
    LOW(10),
    MEDIUM(15),
    HIGH(20),
    VERY_HIGH(30);
    private final int rate;

    DefenseRating(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
