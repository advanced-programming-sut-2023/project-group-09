package enumeration;

public enum Speed {
    EXTREMELY_SLOW(1),
    SLOW(2),
    AVERAGE(3),
    FAST(5),
    VERY_FAST(10),
    STATIONARY(0);
    private final int rate;

    Speed(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
