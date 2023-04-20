package enumeration;

public enum Speed {VERY_SLOW(2),
    SLOW(3),
    MEDIUM(4),
    FAST(5),
    VERY_FAST(6);


    private final int rate;

    Speed(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
