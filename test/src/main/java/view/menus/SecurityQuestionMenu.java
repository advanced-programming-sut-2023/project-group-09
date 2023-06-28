package view.menus;

import client.Packet;
import com.google.gson.Gson;
import controller.DBController;
import enumeration.Paths;
import enumeration.dictionary.SecurityQuestions;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;
import model.captcha.Captcha;
import model.menugui.*;
import view.controllers.ViewController;

import java.io.IOException;
import java.net.MalformedURLException;

public class SecurityQuestionMenu extends Application {
    private Stage stage;
    public static Pane pane;
    public static User user;
    private MenuBox menuBox;
    private MenuChoiceBox securityQuestionField;
    private MenuTextField answerField;
    private Captcha captcha;
    private MenuButton signup;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Pane root = FXMLLoader.load(getClass().getResource("/FXML/securityQuestionMenu.fxml"));
        pane = ViewController.makePaneScreen(stage, root, 1000, -1);
        Scene scene = new Scene(root);

        menuBox = new MenuBox("Security Question", 350, 0, 700, 500);
        root.getChildren().add(menuBox);

        String[] questions = {SecurityQuestions.QUESTION1.getQuestion(),
                SecurityQuestions.QUESTION2.getQuestion(), SecurityQuestions.QUESTION3.getQuestion()};
        securityQuestionField = new MenuChoiceBox(menuBox, "Security Question",
                50, -120, FXCollections.observableArrayList(questions), 300);
        menuBox.getChildren().add(securityQuestionField);

        answerField = new MenuTextField(menuBox, "answer", "Answer", 50, -20, 300);
        answerField.setDisable(true);
        menuBox.getChildren().add(answerField);

        captcha = new Captcha(menuBox, 0, 80);

        signup = new MenuButton("Signup", menuBox, 0, 200, true);
        signup.setOnAction(actionEvent -> signup());
        menuBox.getChildren().add(signup);

        checkQuestion();
        stage.setTitle("Signup Menu");
        stage.setScene(scene);
        stage.show();
    }

    private void checkQuestion() {
        securityQuestionField.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if (!newValue.equals("")) answerField.setDisable(false);
            }
        });
    }

    private void signup() {
        if (securityQuestionField.getValue() == null) {
            securityQuestionField.handlingError("security question is required!");
            return;
        }
        if (answerField.getText().equals("")) {
            answerField.handlingError("");
            return;
        }
        try {
            if (!captcha.isInputCorrect()) return;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        user.setPasswordRecoveryQuestion(securityQuestionField.getValue().toString());
        user.setPasswordRecoveryAnswer(answerField.getText());
        Packet signupUser = new Packet("signup user");
        signupUser.addAttribute("user", new Gson().toJson(user));
        try {
            signupUser.sendPacket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.user = null;
        LoginMenu loginMenu = new LoginMenu();
        try {
            LoginMenu.afterSignup = true;
            loginMenu.start(this.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
