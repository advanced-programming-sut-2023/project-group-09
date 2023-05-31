package model.menugui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class MenuComboBox extends ComboBox {
    private Pane pane;
    private Label errorLabel = new Label();

    public MenuComboBox(Pane pane, String labelText, double x, double y, ObservableList items) {
        this.setStyle("-fx-background-color: rgba(42 , 42 , 42 , 0.7); -fx-text-inner-color: white;");
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setMaxWidth(300);
        this.setMinWidth(300);
        this.setItems(items);
        this.pane = pane;
        this.setEditable(true);
        createLabel(labelText);
        this.setFocusTraversable(false);
    }

    public void createLabel(String text) {
        Label label = new Label(text);
        label.setTranslateX(this.getTranslateX() - 300);
        label.setTranslateY(this.getTranslateY());
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        label.setTextFill(Color.BLACK);
        label.setMaxWidth(270);
        label.setAlignment(Pos.BASELINE_RIGHT);
        pane.getChildren().add(label);
        createErrorOrMessage();
    }

    private void createErrorOrMessage() {
        errorLabel.setTranslateX(this.getTranslateX());
        errorLabel.setTranslateY(this.getTranslateY() + 33);
        errorLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
        pane.getChildren().add(errorLabel);
    }

    public void handlingError(String errorText) {
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText(errorText);
    }

    public void handlingCorrect(String correctMessage) {
        errorLabel.setTextFill(Color.GREEN);
        errorLabel.setText(correctMessage);
    }

    public void clearErrorOrMessage() {
        pane.getChildren().remove(errorLabel);
    }
}

