package enumeration.dictionary;

public enum Slogans {
    ONE(1, "Plan your moves, and move your plans!"),
    TWO(2, "The battle is won before it's ever fought!"),
    THREE(3, "Stay ahead of the game!"),
    FOUR(4, "Strategize to win!"),
    FIVE(5, "Fortune favors the prepared mind!"),
    SIX(6, "Attack the weakness, defend the strengths!"),
    SEVEN(7, "Every move counts!"),
    EIGHT(8, "Aim for victory, not just survival!"),
    NINE(9, "Think long term, act short term!"),
    TEN(10, "In this game, there are no second chances!"),
    ELEVEN(11, "The best offense is a good defense!"),
    TWELVE(12, "In this game, knowledge is power!"),
    THIRTEEN(13, "Every obstacle is an opportunity to strategize!"),
    FOURTEEN(14, "Innovate to dominate!"),
    FIFTEEN(15, "The only way to win is to outthink your opponents!"),
    SIXTEEN(16, "Use your resources wisely!"),
    SEVENTEEN(17, "Conquer with strategy, not brute force!");
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
