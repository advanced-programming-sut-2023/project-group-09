package model.menugui;

import enumeration.Paths;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.captcha.Captcha;
import view.controllers.UserController;
import viewphase1.LoginMenu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;

public class ChangePasswordDialog extends Pane {
    private Pane parent;
    private int width = 800;
    private int height = 600;
    public static MenuPasswordField newPassword;
    public static MenuPasswordField oldPassword;
    private String type;
    private String massage;
    private Button submit;
    private Captcha captcha;
    public MenuBox block;

    public ChangePasswordDialog(Pane parent) throws IOException {
        this.parent = parent;
        this.setStyle("-fx-background-color: rgba(255,255,255,0.83)");
        setBlock();
        setButton();
        setFields();

        setTransitions();
    }

    public void setBlock() {
        block = new MenuBox("change password",0,0,width,height);
        block.setMaxWidth(width);
        block.setMinWidth(width);
        block.setMaxHeight(height);
        block.setMinHeight(height);
        block.setTranslateX(parent.getMaxWidth() / 2 - (double) width / 2);
        block.setTranslateY(100);
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
        newPassword = new MenuPasswordField(block , "new password..." ,
                "new password : " , 50 , -40);
        captcha = new Captcha(block  , 0 , 70);
        oldPassword.textProperty().addListener((observable,o,n) -> {
            String massage = UserController.validateOldPassword(n);
            oldPassword.handlingError(massage);
        });
        newPassword.textProperty().addListener((observable,o,n) -> {
            String massage = UserController.validateNewPassword(n);
            newPassword.handlingError(massage);
        });


        block.getChildren().addAll(oldPassword,newPassword);
    }
    public void closeDialog() {
        parent.getChildren().remove(this);
    }

    public void setButton() {
        submit = new MenuButton("save", block, 0, 200,false);

        submit.setOnMouseClicked(mouseEvent -> {
            String massage = controller.UserController.validateChangePassword(oldPassword.getText(),newPassword.getText());
            try {
                if(!captcha.isInputCorrect()){
                    MenuPopUp menuPopUp = new MenuPopUp(parent, 400, 400,
                            "error", "captcha is not correct!");
                    parent.getChildren().add(menuPopUp);
                } else if (massage == null || massage.equals("")) {
                    MenuPopUp menuPopUp = new MenuPopUp(parent, 400, 400,
                            "success", controller.UserController.changePassword(oldPassword.getText(),newPassword.getText()));
                    parent.getChildren().add(menuPopUp);
                    oldPassword.setText("");
                    newPassword.setText("");

                }else{
                    MenuPopUp menuPopUp = new MenuPopUp(parent, 400, 400,
                            "error", massage);
                    parent.getChildren().add(menuPopUp);
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });

        MenuButton menuButton = new MenuButton("close", block, 0, 250,false);
        menuButton.setOnMouseClicked(mouseEvent -> closeDialog());
        block.getChildren().addAll(submit,menuButton);

    }



}
