package view.controllers;

import enumeration.Paths;
import javafx.css.Match;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.captcha.Captcha;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void createCaptcha() throws MalformedURLException {
        final File folder = new File(CaptchaController.class.getResource(Paths.CAPTCHA_IMAGES.getPath())
                .toExternalForm().substring(6));
        File captchaFile = listFilesForFolder(folder);
        captcha.setCaptchaImage(new ImageView(new Image(captchaFile.getAbsolutePath())));
        Matcher matcher = Pattern.compile("(?<number>[\\d]+)\\.png").matcher
                (captchaFile.getName());
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group("number"));
        }
        captcha.setNumber(number);
    }

    public static File listFilesForFolder(final File folder) {
        ArrayList<File> captcha = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            captcha.add(fileEntry);
        }
        Random random = new Random();
        int randomIndex = random.nextInt() % captcha.size();
        return captcha.get(randomIndex);
    }


}
