package model.captcha;

import controller.GameController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import view.controllers.CaptchaController;


import java.io.IOException;

public class Captcha extends StackPane {
    private int number;
    private ImageView captchaImage;
    private ImageView refreshButton;
    private Label numberLabel;
    private TextField numberText;
    public Captcha() throws IOException {
        CaptchaController.createCaptcha();
        CaptchaController.setCaptcha(this);
    }
    public boolean isInputCorrect() {
        return CaptchaController.isInputCorrect();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ImageView getCaptchaImage() {
        return captchaImage;
    }

    public void setCaptchaImage(ImageView captchaImage) {
        this.captchaImage = captchaImage;
    }
}
