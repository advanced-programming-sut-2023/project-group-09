package view.controllers;

import enumeration.Paths;
import model.captcha.Captcha;

import java.io.IOException;

public class CaptchaController {
    private static Captcha captcha;

    public static Captcha getCaptcha() {
        return captcha;
    }

    public static void setCaptcha(Captcha captcha) {
        CaptchaController.captcha = captcha;
    }

    public static boolean isInputCorrect() {
        return false;
    }

    public static void createCaptcha() throws IOException {
        Process p = Runtime.getRuntime().exec("python  " + CaptchaController.class.getResource(
                Paths.CREATE_CAPTCHA_PYTHON_FILE.getPath()).toExternalForm());
    }
}
