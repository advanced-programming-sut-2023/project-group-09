package model.menugui;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class MenuHoverBox extends Pane {
    private Pane parent;
    private double width;
    private double height;
    private String text;

    public MenuHoverBox(Pane parent, double x, double y, double width, double height, String text) {
        this.parent = parent;
        this.width = width;
        this.height = height;
        this.text = text;
        this.setMaxHeight(height);
        this.setMinHeight(height);
        this.setMaxWidth(width);
        this.setMinWidth(width);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setStyle("-fx-background-color: rgba(256, 256, 256, 0.5)");
        Text textBox = new Text(text);
        textBox.setTranslateX(width / 2 - textBox.getWrappingWidth() / 2);
        textBox.setTranslateY(height / 2);
        this.getChildren().add(textBox);
        this.parent.getChildren().add(this);
    }
}
