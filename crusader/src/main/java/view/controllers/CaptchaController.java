package view.controllers;

import enumeration.Paths;
import javafx.css.Match;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.captcha.Captcha;

import javax.accessibility.AccessibleHyperlink;
import java.io.CharArrayReader;
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
        if (captcha.getNumberText().getText() == null || captcha.getNumberText().getText().equals("")) {
            showingAlertOfWrongAnswer();
            return false;
        }
        try {
            int input = Integer.parseInt(captcha.getNumberText().getText());
            if (input == captcha.getNumber()) {
                return true;
            } else {
                showingAlertOfWrongAnswer();
                return false;
            }
        } catch (Exception e) {
            showingAlertOfWrongAnswer();
            return false;
        }
    }

    public static void changingCaptcha() {
        final File folder = new File(CaptchaController.class.getResource(Paths.CAPTCHA_IMAGES.getPath())
                .toExternalForm().substring(6));
        File captchaFile = listFilesForFolder(folder);

        captcha.getCaptchaImage().setImage(new Image(CaptchaController.class.getResource
                (Paths.CAPTCHA_IMAGES.getPath() + captchaFile.getName()).toExternalForm()));

        Matcher matcher = Pattern.compile("(?<number>[\\d]+)\\.png").matcher
                (captchaFile.getName());
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group("number"));
        }
        captcha.setNumber(number);
    }

    public static void createCaptcha() throws MalformedURLException {
        final File folder = new File(CaptchaController.class.getResource(Paths.CAPTCHA_IMAGES.getPath())
                .toExternalForm().substring(6));
        File captchaFile = listFilesForFolder(folder);

        captcha.setCaptchaImage(new ImageView(new Image(CaptchaController.class.getResource
                (Paths.CAPTCHA_IMAGES.getPath() + captchaFile.getName()).toExternalForm())));

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
        int randomIndex;
        do {
            randomIndex = Math.abs(random.nextInt()) % captcha.size();
        } while (CaptchaController.getCaptcha().getNumber() == randomIndex);
        return captcha.get(randomIndex);
    }

    private static void showingAlertOfWrongAnswer() {
        changingCaptcha();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Captcha Error");
        alert.setHeaderText("Captcha Error");
        alert.setContentText("Your input doesn't match with captcha");
        alert.showAndWait();
    }

    public static void createRefreshButton() {
        captcha.setRefreshButton(new ImageView());
        captcha.getRefreshButton().setImage(new Image(CaptchaController.class.getResource
                (Paths.USER_ICONS.getPath() + "refreshIcon.png").toExternalForm()));
        captcha.getRefreshButton().setScaleY(0.02);
        captcha.getRefreshButton().setScaleX(0.02);
        captcha.getRefreshButton().setTranslateX(75);
        captcha.getRefreshButton().setTranslateY(20);
        captcha.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                    changingCaptcha();
            }
        });
    }



    public static void createLabelAndTextField() {
        captcha.setNumberLabel(new Label("type the number :"));
        captcha.getNumberLabel().setTranslateX(-50);
        captcha.getNumberLabel().setTranslateY(55);
        captcha.setNumberText(new TextField());
        captcha.getNumberText().setTranslateX(50);
        captcha.getNumberText().setTranslateY(55);
        captcha.getNumberText().setMaxWidth(80);
    }
}
