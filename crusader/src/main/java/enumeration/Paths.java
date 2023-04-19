package enumeration;


public enum Paths {
    USERS_PATH("crusader/files/user/users.json"),
    CURRENT_USER_PATH("crusader/files/user/currentUser.json"),
    CAPTCHA_DATA_PATH("crusader/files/captcha/data/")
    ;
    private final String path;

    private Paths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}