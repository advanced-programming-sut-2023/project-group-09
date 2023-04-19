package Enumeration;

public enum SecurityQuestions {
    // list of questions
    ;
    private final String question;

    private SecurityQuestions(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
