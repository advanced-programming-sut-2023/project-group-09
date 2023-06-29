package model.menugui;

import enumeration.Paths;
import javafx.geometry.Pos;
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
    private Label errorLabel;
    public MenuTextField(Pane pane , String promptText , String labelText , double x , double y, int width) {
        this.promptText = promptText;
        this.setStyle("-fx-background-color: rgba(42 , 42 , 42 , 0.7); -fx-text-inner-color: white;");
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setMaxWidth(width);
        this.setMinWidth(width);
        this.pane = pane;
        createLabel(labelText);
        this.setFont(Font.font("Times New Roman" , FontWeight.BOLD , 20));
        this.setPromptText(promptText);
        createErrorOrMessage();
    }

    public void createLabel(String text) {
        Label label = new Label(text);
        label.setTranslateX(this.getTranslateX() - getMaxWidth());
        label.setTranslateY(this.getTranslateY());
        label.setFont(Font.font("Times New Roman" , FontWeight.BOLD , 20));
        label.setTextFill(Color.BLACK);
        label.setMaxWidth(getMaxWidth() - 30);
        label.setAlignment(Pos.BASELINE_RIGHT);
        pane.getChildren().add(label);
    }

    private void createErrorOrMessage() {
        errorLabel = new Label();
        errorLabel.setTranslateX(this.getTranslateX());
        errorLabel.setTranslateY(this.getTranslateY() + 25);
        errorLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
        pane.getChildren().add(errorLabel);
    }

    public void handlingError(String errorText) {
        errorLabel.setTextFill(new Color(0.6, 0, 0.1, 1));
        errorLabel.setText(errorText);
    }

    public void handlingCorrect(String correctMessage) {
        errorLabel.setTextFill(new Color(0.2, 0.9, 0.2, 1));
        errorLabel.setText(correctMessage);
    }

    public void clearErrorOrMessage() {
        errorLabel.setText("");
    }

    public Label getErrorLabel() {
        return errorLabel;
    }
}
