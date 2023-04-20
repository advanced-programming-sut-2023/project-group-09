package enumeration;


public enum ActivityPermissions {
    CAN_CLIMB_WALLS("can climb walls"),
    CAN_DESTROY_BUILDING("can destroy building"),
    CAN_CLIMB_LADDERS("can climb ladders"),
    CAN_DIG_MOAT("can dig moat"),


    ;

    private final String permission;

    ActivityPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
