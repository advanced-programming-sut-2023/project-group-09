package enumeration;

public enum MoveStates {
    MOVING("moving"),
    STOP("stop"),
    PATROL("patrol")

    ;
    private final String state;

    private MoveStates(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
