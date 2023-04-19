package enumeration.answers;

public enum Answers {

    //needed in all menus
    INVALID_COMMAND("invalid command!"),
    LOGOUT_MASSAGE("user logged out successfully!")
    ;


    private final String value;

    Answers(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
