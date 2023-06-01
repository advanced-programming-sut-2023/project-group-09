package view.menus;

import enumeration.dictionary.SecurityQuestions;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.captcha.Captcha;
import model.menugui.MenuBox;
import model.menugui.MenuChoiceBox;
import model.menugui.MenuTextField;
import view.controllers.ViewController;

import java.io.IOException;

public class SecurityQuestionMenu extends Application {
    public static Pane pane;
    public User user;
    public Text title;
    public Label questionLabel;
    public ChoiceBox question;
    public Text questionError;
    public Label answerLabel;
    public TextField answer;
    public Text answerError;
    public Label captchaLabel;
    public Captcha captcha;

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("/FXML/securityQuestionMenu.fxml"));
        pane = ViewController.makePaneScreen(stage, root, 1000, -1);
        Scene scene = new Scene(root);

        MenuBox menuBox = new MenuBox("Seurit", 400, 130, 700, 400);
        root.getChildren().add(menuBox);

        String[] questions = {SecurityQuestions.QUESTION1.getQuestion(),
                SecurityQuestions.QUESTION2.getQuestion(), SecurityQuestions.QUESTION3.getQuestion()};
        MenuChoiceBox securityQuestionField = new MenuChoiceBox(menuBox, "Security Question",
                50, -200, FXCollections.observableArrayList(questions));
        menuBox.getChildren().add(securityQuestionField);

        MenuTextField answerField = new MenuTextField(menuBox, "answer", "Answer", 50, -150);
        answerField.setDisable(true);
        menuBox.getChildren().add(answerField);

        Captcha captcha = new Captcha(menuBox, 0, 0);

        stage.setTitle("Signup Menu");
        stage.setScene(scene);
        stage.show();
    }

    private void makeScene(Stage stage, BorderPane rootPane) throws IOException {
        pane = makeSignupScreen(stage, rootPane, 1000, -1);
        Scene scene = new Scene(rootPane);


        stage.setTitle("Security Question Menu");
        stage.setScene(scene);
        makeQuestionChoiceBox();
        makeAnswerTextBox();
        makeCaptcha();
        stage.show();
    }

    private GridPane makeSignupScreen(Stage stage, BorderPane pane, double width, double height) throws IOException {
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        pane.setStyle("-fx-background-color: #000");
        GridPane gridPane = FXMLLoader.load(SignupMenu.class.getResource("/FXML/securityQuestionMenu.fxml"));
        pane.setCenter(gridPane);
        gridPane.setMaxWidth(width);
        if (height != -1) {
            gridPane.setMaxHeight(height);
        }
        gridPane.setStyle("-fx-background-color: #fff");
        return gridPane;
    }

    public void makeQuestionChoiceBox() {
        String[] items = {SecurityQuestions.QUESTION1.getQuestion(), SecurityQuestions.QUESTION2.getQuestion(),
                SecurityQuestions.QUESTION3.getQuestion()};
        question = new ChoiceBox<>(FXCollections.observableArrayList(items));
        GridPane.setRowIndex(question, 1);
        GridPane.setColumnIndex(question, 1);

        questionError = new Text();
        GridPane.setRowIndex(questionError, 2);
        GridPane.setColumnIndex(questionError, 1);
        questionError.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 14));

        pane.getChildren().addAll(question, questionError);
    }

    public void makeAnswerTextBox() {
        answer = new TextField();
        GridPane.setRowIndex(answer, 3);
        GridPane.setColumnIndex(answer, 1);
        answer.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));

        answerError = new Text();
        GridPane.setRowIndex(answerError, 4);
        GridPane.setColumnIndex(answerError, 1);
        answerError.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 14));

        pane.getChildren().addAll(answer, answerError);
    }

    public void makeCaptcha() throws IOException {
        /*captcha = new Captcha();
        GridPane.setRowIndex(captcha, 5);
        GridPane.setColumnIndex(captcha, 1);
        pane.getChildren().add(captcha);*/
    }
}
