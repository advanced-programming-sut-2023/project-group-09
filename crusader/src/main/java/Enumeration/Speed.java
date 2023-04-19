package enumeration;

public enum Speed {;
    private final int rate;

    Speed(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
