package model.menugui;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class MenuChoiceBox extends ChoiceBox {
    private Pane pane;
    private Label errorLabel = new Label();

    public MenuChoiceBox(Pane pane, String labelText, double x, double y, ObservableList items) {
        this.setStyle("-fx-background-color: rgba(42 , 42 , 42 , 0.7); -fx-text-inner-color: white");
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setMaxWidth(300);
        this.setMinWidth(300);
        this.setItems(items);
        this.pane = pane;
        createLabel(labelText);
        this.setFocusTraversable(false);
    }

    public void createLabel(String text) {
        Label label = new Label(text);
        label.setTranslateX(this.getTranslateX() - 200);
        label.setTranslateY(this.getTranslateY());
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        label.setTextFill(Color.BLACK);
        pane.getChildren().add(label);
    }

    public void handlingError(String errorText) {
        errorLabel.setTranslateX(this.getTranslateX());
        errorLabel.setTranslateY(this.getTranslateY() + 33);
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
        errorLabel.setText(errorText);
        pane.getChildren().add(errorLabel);
    }

    public void handlingCorrect(String correctMessage) {
        errorLabel.setTranslateX(this.getTranslateX());
        errorLabel.setTranslateY(this.getTranslateY() + 33);
        errorLabel.setTextFill(Color.GREEN);
        errorLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
        errorLabel.setText(correctMessage);
        pane.getChildren().add(errorLabel);
    }

    public void clearErrorOrMessage() {
        pane.getChildren().remove(errorLabel);
    }
}
