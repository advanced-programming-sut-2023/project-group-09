package enumeration;


public enum Paths {
    USERS_PATH("crusader/files/user/users.json"),
    CURRENT_USER_PATH("crusader/files/user/currentUser.json")
    ;
    private final String path;

    Paths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
