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
import view.menus.MainMenu;

import javax.accessibility.AccessibleHyperlink;
import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
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

    private static void deleteFilesInFolder(File folder) {
        ArrayList<File> captcha = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            captcha.add(fileEntry);
        }
        Iterator itr = captcha.iterator();
        while (itr.hasNext()) {
            File file = (File)itr.next();
            System.out.println(file.getName());
            itr.remove();
            file.delete();
        }
    }

    public static void changingCaptcha() {
        File folder = new File(Paths.CAPTCHA_IMAGES.getPath());
        deleteFilesInFolder(folder);
        folder = new File(Paths.CAPTCHA_IMAGES.getPath());
        runPythonFile();
        File captchaFile = listFilesForFolder(folder);
        try {
            captcha.getCaptchaImage().setImage(new Image(captchaFile.toURI().toURL().toExternalForm()));
        } catch (Exception e) {
            System.out.println("an error occurred in captcha loading");
        }

        Matcher matcher = Pattern.compile("(?<number>[\\d]+)\\.png").matcher
                (captchaFile.getName());
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group("number"));
        }
        captcha.setNumber(number);
    }

    private static void runPythonFile() {
        try {
            Process p = Runtime.getRuntime().exec("python files/captcha/data/main.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int ret = new Integer(in.readLine());
            System.out.println(ret);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void createCaptcha() throws MalformedURLException {
        File folder = new File(Paths.CAPTCHA_IMAGES.getPath());
        deleteFilesInFolder(folder);
        folder = new File(Paths.CAPTCHA_IMAGES.getPath());
        runPythonFile();
        File captchaFile = listFilesForFolder(folder);

        try {
            captcha.setCaptchaImage(new ImageView());
            captcha.getCaptchaImage().setImage(new Image(captchaFile.toURI().toURL().toExternalForm()));
        } catch (Exception e) {
            System.out.println("an error occurred in captcha loading");
        }

        Matcher matcher = Pattern.compile("(?<number>[\\d]+)\\.png").matcher
                (captchaFile.getName());
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group("number"));
        }
        captcha.setNumber(number);
    }

    public static File listFilesForFolder(final File folder) {
        System.out.println(folder.getAbsolutePath());
        ArrayList<File> captcha = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            captcha.add(fileEntry);
        }
        System.out.println();
        return captcha.get(Math.abs(new Random().nextInt()) % captcha.size());
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
                System.out.println("change");
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
