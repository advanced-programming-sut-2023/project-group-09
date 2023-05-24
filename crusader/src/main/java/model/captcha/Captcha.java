package model.captcha;

import controller.GameController;
import javafx.scene.layout.StackPane;
import view.controllers.CaptchaController;

import javax.swing.text.html.ImageView;
import java.awt.*;
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

}
