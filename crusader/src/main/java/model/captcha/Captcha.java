package model.captcha;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import view.controllers.CaptchaController;


import java.io.IOException;
import java.net.MalformedURLException;

public class Captcha extends StackPane {
    private int number;
    private ImageView captchaImage;
    private ImageView refreshButton;
    private Label numberLabel;
    private TextField numberText;
    public Captcha() throws IOException {
        CaptchaController.setCaptcha(this);
        CaptchaController.createCaptcha();
        CaptchaController.createRefreshButton();
        CaptchaController.createLabelAndTextField();
        this.getChildren().addAll(this.captchaImage , this.numberLabel , this.refreshButton , this.numberText);
        this.setWidth(500);
        this.setHeight(400);
        this.setPadding(new Insets(50));
    }
    public boolean isInputCorrect() throws MalformedURLException {
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

    public TextField getNumberText() {
        return numberText;
    }

    public void setNumberText(TextField numberText) {
        this.numberText = numberText;
    }

    public ImageView getRefreshButton() {
        return refreshButton;
    }

    public void setRefreshButton(ImageView refreshButton) {
        this.refreshButton = refreshButton;
    }

    public Label getNumberLabel() {
        return numberLabel;
    }

    public void setNumberLabel(Label numberLabel) {
        this.numberLabel = numberLabel;
    }

    public void setTranslate(double x , double y) {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}
