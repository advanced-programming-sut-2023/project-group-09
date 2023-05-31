package model.captcha;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import view.controllers.CaptchaController;


import java.io.IOException;
import java.net.MalformedURLException;

public class Captcha extends ImageView {
    private Pane pane;
    private int number;
    private ImageView refreshButton;
    private Label numberLabel;
    private TextField numberText;
    public Captcha(Pane pane , double x , double y) throws IOException {
        CaptchaController.setCaptcha(this);
        this.pane = pane;
        pane.getChildren().add(this);
        CaptchaController.createCaptcha();
        CaptchaController.createRefreshButton();
        CaptchaController.createLabelAndTextField();
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

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }
}
