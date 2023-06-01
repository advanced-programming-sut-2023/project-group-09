package model.menugui;

import enumeration.Paths;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.captcha.Captcha;
import viewphase1.LoginMenu;

import java.io.IOException;
import java.util.Objects;

public class ChangePasswordDialog extends Pane {
    private Pane parent;
    private int width = 800;
    private int height = 600;
    public static MenuPasswordField newPassword;
    public static MenuPasswordField oldPassword;
    private String type;
    private String massage;
    public MenuBox block;

    public ChangePasswordDialog(Pane parent) throws IOException {
        this.parent = parent;
        this.setStyle("-fx-background-color: rgba(255,255,255,0.83)");
        setBlock();
        setFields();
        setButton();
        setTransitions();
    }

    public void setBlock() {
        block = new MenuBox("change password",0,0,width,height);
        block.setMaxWidth(width);
        block.setMinWidth(width);
        block.setMaxHeight(height);
        block.setMinHeight(height);
        block.setTranslateX(parent.getMaxWidth() / 2 - (double) width / 2);
        block.setTranslateY(200);
        this.getChildren().add(block);
    }



    public void setTransitions() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(this);
        fadeTransition.setDuration(Duration.millis(200));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
    }
    public void setFields() throws IOException {
        oldPassword = new MenuPasswordField(block , "old password..." ,
                "current password : " , 50 , -120);
        oldPassword.errorLabel.setTranslateX(-300);
        newPassword = new MenuPasswordField(block , "new password..." ,
                "new password : " , 50 , -40);
        Captcha captcha = new Captcha(block  , 0 , 70);
        oldPassword.textProperty().addListener(observable -> {

        });
        block.getChildren().addAll(oldPassword,newPassword);
    }
    public void closeDialog() {
        parent.getChildren().remove(this);
    }

    public void setButton() {
        MenuButton menuButton = new MenuButton("close", block, 0, 250,false);
        menuButton.setOnMouseClicked(mouseEvent -> closeDialog());
        block.getChildren().add(menuButton);

    }



}
