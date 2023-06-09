package model.menugui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class MenuChoiceBox extends ChoiceBox {
    private Pane pane;
    private Label errorLabel;

    public MenuChoiceBox(Pane pane, String labelText, double x, double y, ObservableList items, int width) {
        this.setStyle("-fx-background-color: rgba(42 , 42 , 42 , 0.7); -fx-text-inner-color: white");
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setMaxWidth(width);
        this.setMinWidth(width);
        this.setItems(items);
        this.pane = pane;
        createLabel(labelText);
        this.setFocusTraversable(false);
        createErrorOrMessage();
    }

    public void createLabel(String text) {
        Label label = new Label(text);
        label.setTranslateX(this.getTranslateX() - this.getMaxWidth());
        label.setTranslateY(this.getTranslateY());
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        label.setTextFill(Color.BLACK);
        label.setMaxWidth(this.getMaxWidth() - 30);
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
}
