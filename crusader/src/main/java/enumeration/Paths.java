package enumeration;


import controller.CaptchaController;

public enum Paths {
    USERS_PATH("crusader/files/user/users.json"),
    CURRENT_USER_PATH("crusader/files/user/currentUser.json"),
    CAPTCHA_DATA_PATH("crusader/files/captcha/data/"),
    CAPTCHA_NOISE_PATH("crusader/files/captcha/noise/"),
    CAPTCHA_KEY_PATH("crusader/files/captcha/key.txt")
    ;
    private final String path;

    Paths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
