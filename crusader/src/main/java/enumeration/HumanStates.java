package enumeration;

public enum HumanStates {

    STAND_GROUND("standing"),
    DEFENSIVE_STANCE("defensive"),
    AGGRESSIVE_STANCE("offensive");
    private final String state;

    private HumanStates(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
