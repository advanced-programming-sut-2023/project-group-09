package enumeration;

public enum DefenseRating {;
    private final int rate;

    DefenseRating(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
