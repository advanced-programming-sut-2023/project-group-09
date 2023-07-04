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

public class LobbyChoiceBox extends ChoiceBox {
    private Pane pane;
    private Label label;

    public LobbyChoiceBox(Pane pane, String labelText, double x, double y, ObservableList items, int width) {
        this.setStyle("-fx-background-color: #eaeaea; -fx-text-inner-color: black;-fx-padding: 10px 0px;");
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setMaxWidth(width);
        this.setMinWidth(width);
        this.setItems(items);
        this.pane = pane;
        createLabel(labelText);
        this.setFocusTraversable(false);
    }

    public void createLabel(String text) {
        label = new Label(text);
        label.setTranslateX(this.getTranslateX() - 100);
        label.setTranslateY(this.getTranslateY()+10);;
        label.setMaxWidth(this.getMaxWidth() - 30);
        pane.getChildren().add(label);
    }

}
