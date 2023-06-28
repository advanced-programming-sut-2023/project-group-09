package enumeration;

public enum MilitaryStates {

    STAND_GROUND("standing"),
    DEFENSIVE_STANCE("defensive"),
    AGGRESSIVE_STANCE("offensive");
    private final String state;

    private MilitaryStates(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
