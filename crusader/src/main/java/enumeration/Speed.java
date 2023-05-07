package enumeration;

public enum Speed {
    EXTREMELY_SLOW(3),
    SLOW(8),
    AVERAGE(10),
    FAST(15),
    VERY_FAST(20),
    STATIONARY(0);
    private final int rate;

    Speed(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
