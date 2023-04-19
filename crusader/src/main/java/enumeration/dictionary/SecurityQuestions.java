package enumeration;

public enum SecurityQuestions {
    QUESTION1(1, "What's my father's name?"),
    QUESTION2(2, "What was my first pet's name?"),
    QUESTION3(3, "What's my mother's last name?");

    private int number;
    private String question;

    private SecurityQuestions(int number, String question) {
        this.number = number;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public int getNumber() {
        return number;
    }
}
