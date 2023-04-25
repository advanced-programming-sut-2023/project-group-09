package enumeration;

public enum StateOfMilitary {AGGRESSIVE("aggressive"),
    DEFENSIVE("defensive"),
    STAND("stand")
    ;
    private String state;
    StateOfMilitary(String state) {
        this.state = state;
    }
    public static String getState(StateOfMilitary stateOfMilitary) {
        return stateOfMilitary.state;
    }
}
