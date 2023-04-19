package enumeration.dictionary;

public enum Slogans {
    ONE(1, "fuck dutchman"),
    TWO(2, "hello world");
    private int number;
    private String slogan;

    private Slogans(int number, String slogan) {
        this.number = number;
        this.slogan = slogan;
    }

    public int getNumber() {
        return this.number;
    }

    public String getSlogan() {
        return this.slogan;
    }

    public static String getSloganByNumber(int number) {
        for (Slogans slogan : Slogans.values()) {
            if (slogan.getNumber() == number) return slogan.getSlogan();
        }
        return null;
    }
}
