package enumeration;

public enum UnitMovingState {

    MOVE("move"),
    ATTACK("attack"),
    AIR_ATTACK("air attack"),
    ATTACK_BUILDING("attack building"),
    NORMAL("normal"),
    PATROL("patrol"),
    ;
    private final String state;

    private UnitMovingState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
