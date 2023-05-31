package view.menus;

import controller.DBController;
import controller.GameController;
import controller.UserController;
import enumeration.Paths;
import enumeration.answers.Answers;
import enumeration.answers.LoginAnswers;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.User;
import model.captcha.Captcha;
import model.menugui.MenuBox;
import model.menugui.MenuButton;
import model.menugui.MenuPasswordField;
import model.menugui.MenuTextField;
import view.controllers.CaptchaController;
import view.controllers.ViewController;

import java.io.IOException;
import java.net.MalformedURLException;

public class LoginMenu extends Application {
    public static MenuTextField username;
    public static MenuPasswordField password;
    public static Stage stage;
    public static Pane loginPane;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        Pane pane = FXMLLoader.load(LoginMenu.class.getResource("/FXML/loginMenu.fxml"));
        ViewController.makePaneScreen(stage , pane , 1000 , -1);
        loginPane = pane;
        Scene scene = new Scene(pane);
        setLoginMenu(pane);
        stage.setScene(scene);
        stage.show();
    }


    public void forgotPassword(MouseEvent mouseEvent) {
        username.clearErrorOrMessage();
        password.clearErrorOrMessage();
        CaptchaController.clearErrorOrMessage();
        if (username.getText() == null ||
                !controller.Application.isUserExistsByName(username.getText())) {
            username.handlingError(LoginAnswers.USER_DOESNT_EXIST_MESSAGE.getMessage());
            return;
        } else if (!CaptchaController.isInputCorrect()) {
            return;
        }
        User user = controller.Application.getUserByUsername(username.getText());
        loginPane.getChildren().clear();
        MenuBox menuBox = new MenuBox("Forgot Password" , 500, 150 , 500 , 500);
        Label label = new Label(UserController.getSecurityQuestionWithUsername(username.getText()));
        menuBox.getChildren().add(label);
        label.setFont(Font.font("Times New Roman" , FontWeight.BOLD , FontPosture.ITALIC , 25));
        label.setTextFill(Color.BLACK);
        label.setTranslateY(-100);

        MenuTextField menuTextField = new MenuTextField(menuBox , "answer" , "Answer : " ,
                0 , 0);
        menuBox.getChildren().add(menuTextField);

        MenuButton backButton = new MenuButton("Back" , menuBox , 0 , 75);
        MenuButton nextButton = new MenuButton("Next" , menuBox , 0 , 125);

        setEventForBackButton(backButton);
        setEventForNextButton(nextButton , user , menuTextField);

        menuBox.getChildren().add(backButton);
        menuBox.getChildren().add(nextButton);


        loginPane.getChildren().add(menuBox);
    }

    private void setEventForNextButton(Button button , User user , MenuTextField answer) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (user.isAnswerToSecurityQuestionCorrect(answer.getText())) {
                    setChangePassword(user);
                } else {
                    answer.handlingError("answer is wrong!");
                }
            }
        });
    }

    private void setChangePassword(User user) {
        loginPane.getChildren().clear();
        MenuBox menuBox = new MenuBox("Change Password" , 500, 150 , 500 , 500);
        MenuPasswordField passField = new MenuPasswordField(menuBox , "password" ,
                "Password : " , 50 , -60);
        menuBox.getChildren().add(passField);

        MenuPasswordField confirmationField = new MenuPasswordField(menuBox , "confirmation",
                "Confirmation : " , 50 , 0);
        menuBox.getChildren().add(confirmationField);

        MenuButton changeButton = new MenuButton("Change" , menuBox , 0 , 60);
        setChangeButtonEvent(changeButton , passField , confirmationField , user);
        menuBox.getChildren().add(changeButton);

        passField.requestFocus();
        confirmationField.requestFocus();

        passField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (UserController.isPasswordStrong(passField.getText()) != 6) {
                    passField.handlingError("password is weak!");
                } else {
                    passField.handlingCorrect("password is strong!");
                }
            }
        });

        confirmationField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (confirmationField != null && confirmationField.getText().equals(passField.getText())) {;
                    confirmationField.clearErrorOrMessage();
                } else {
                    confirmationField.handlingError("confirmation doesn't match!");
                }
            }
        });


        loginPane.getChildren().add(menuBox);
    }

    private void setChangeButtonEvent(Button button , MenuPasswordField passField ,
                                      MenuPasswordField confirmationField , User user) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (passField != null && UserController.isPasswordStrong(passField.getText()) == 6) {
                    if (passField.getText() != null && passField.getText().
                            equals(confirmationField.getText())) {
                        user.setPassword(UserController.convertPasswordToHash(passField.getText()));
                        DBController.saveAllUsers();
                        loginPane.getChildren().clear();
                        setLoginMenu(loginPane);
                    }
                }
            }
        });
    }

    private void setEventForBackButton(Button button) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginPane.getChildren().clear();
                setLoginMenu(loginPane);
            }
        });
    }

    public void login(MouseEvent mouseEvent) throws MalformedURLException {
        username.clearErrorOrMessage();
        password.clearErrorOrMessage();
        CaptchaController.clearErrorOrMessage();
        UserController.loginUser(username.getText() , password.getText() , true);
    }

    private void setLoginMenu(Pane pane){
        MenuBox menuBox = new MenuBox("Login" , 350, 0 , 500 , 500);

        MenuTextField userNameField = new MenuTextField(menuBox , "username" ,
                "Username : " ,  50, -150);
        menuBox.getChildren().add(userNameField);
        username = userNameField;

        MenuPasswordField passwordField = new MenuPasswordField(menuBox , "password" ,
                "Passowrd : " , 50 , -70);
        menuBox.getChildren().add(passwordField);
        password = passwordField;

        Hyperlink forgotPassword = new Hyperlink();
        forgotPassword.setText("forgot my password");
        forgotPassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                forgotPassword(mouseEvent);
            }
        });

        forgotPassword.setTranslateY(-20);
        forgotPassword.setBorder(Border.EMPTY);
        forgotPassword.setStyle("-fx-font-size: 15");


        menuBox.getChildren().add(forgotPassword);

        try {
            Captcha captcha = new Captcha(menuBox  , 0 , 40);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MenuButton loginButton = new MenuButton("Login" , menuBox , 0 , 170, true);
        menuBox.getChildren().add(loginButton);
        loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    login(mouseEvent);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        pane.getChildren().add(menuBox);
        stage.setTitle("Login Menu");
    }
}
