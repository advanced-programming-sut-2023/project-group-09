package model.captcha;

import javafx.scene.layout.StackPane;

import javax.swing.text.html.ImageView;
import java.awt.*;

public class Captcha extends StackPane {
    private int number;
    private ImageView captchaImage;
    private ImageView refreshButton;
    private Label numberLabel;
    private TextField numberText;
    public Captcha() {

    }
    public boolean isInputCorrect() {
        try Integer.parseInt(numberText.getText()) {

        }
    }

}
