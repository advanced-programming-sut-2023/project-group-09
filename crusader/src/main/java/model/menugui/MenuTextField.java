package model.menugui;

import enumeration.Paths;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class MenuTextField extends TextField {
    private Pane pane;
    private String promptText;
    private Label errorLabel = new Label();
    public MenuTextField(Pane pane , String promptText , String labelText , double x , double y) {
        this.promptText = promptText;
        this.setStyle("-fx-background-color: rgba(42 , 42 , 42 , 0.7); -fx-text-inner-color: white;");
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setMaxWidth(300);
        this.setMinWidth(300);
        this.pane = pane;
        createLabel(labelText);
        this.setFont(Font.font("Times New Roman" , FontWeight.BOLD , 20));
        this.setFocusTraversable(false);
        this.setPromptText(promptText);
    }

    private void createLabel(String text) {
        Label label = new Label(text);
        label.setTranslateX(this.getTranslateX() - 105);
        label.setTranslateY(this.getTranslateY() + 5);
        label.setFont(Font.font("Times New Roman" , FontWeight.BOLD , 20));
        pane.getChildren().add(label);
    }

    public void handlingError(String errorText) {
        errorLabel.setTranslateX(this.getTranslateX());
        errorLabel.setTranslateY(this.getTranslateY() + 33);
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("Times New Roman" , FontWeight.BOLD , FontPosture.ITALIC , 15));

        errorLabel.setText(errorText);
        pane.getChildren().add(errorLabel);
    }

    public void handlingCorrect(String correctMessage) {
        errorLabel.setTranslateX(this.getTranslateX());
        errorLabel.setTranslateY(this.getTranslateY() + 33);
        errorLabel.setTextFill(Color.GREEN);
        errorLabel.setFont(Font.font("Times New Roman" , FontWeight.BOLD , FontPosture.ITALIC, 15));
        errorLabel.setText(correctMessage);
        pane.getChildren().add(errorLabel);
    }

    public void clearErrorOrMessage() {
        pane.getChildren().remove(errorLabel);
    }
}
