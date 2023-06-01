package model.menugui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.*;


public class MenuBox extends StackPane {
    private String title;
    public Rectangle box;
    public Label titleLabel;
    public MenuBox(String title , double x , double y , double width , double height) {
        this.title = title;
        this.setTranslateX(x);
        this.setTranslateY(y);
        createBox(width , height);
        createTitleBox(width , height);
    }

    private void createBox(double width , double height) {
        Rectangle box = new Rectangle();
        box.setWidth(width);
        box.setHeight(height);
        box.setStroke(Color.DARKRED);
        box.setArcHeight(20);
        box.setArcWidth(20);
        box.setStyle("-fx-fill: rgba(150 , 150 , 150 , 0.4);");
        box.setStrokeWidth(5);
        this.box = box;
        this.getChildren().add(box);
    }

    private void createTitleBox(double width , double height) {
        Label label = new Label(this.title);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Times New Roman" , FontWeight.BOLD , 50));
        label.setTranslateY(-height/2 + 25);
        label.setMinWidth(width);
        label.setMaxWidth(width);
        label.setAlignment(Pos.BASELINE_CENTER);
        label.setStyle("-fx-background-color: rgba(128,1,16,0.7); -fx-background-radius: 20 20 0 0");
        titleLabel = label;
        this.getChildren().add(label);
    }
}
