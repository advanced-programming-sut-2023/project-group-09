package model.menugui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.menugui.game.GameMap;
import view.menus.GameMenu;

import java.util.ArrayList;
import java.util.Collections;

public class MenuHoverBox extends StackPane {
    private Pane parent;
    private double width;
    private double height;
    private String text;

    public MenuHoverBox(Pane parent, String text, int x, int y) {
        this.parent = parent;
        this.text = text;
        this.width = getLineWidth() * 7 + 20;
        this.height = getNumberOfLines() * 20 + 20;
        this.setMaxHeight(height);
        this.setMinHeight(height);
        this.setMaxWidth(width);
        this.setMinWidth(width);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setStyle("-fx-background-radius: 5; -fx-background-color: rgba(256, 256, 256, 0.8); -fx-font-size: 15;" +
                "-fx-border-radius: 5; -fx-border-color: black; -fx-border-width: 0.5");
        this.setOnMouseExited(mouseEvent -> {
            this.parent.getChildren().remove(this);
        });
        Text textBox = new Text(text);
        this.getChildren().add(textBox);
        this.parent.getChildren().add(this);
    }

    public int getNumberOfLines() {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') count++;
        }
        return count + 1;
    }

    public int getLineWidth() {
        ArrayList<Integer> lineChars = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                lineChars.add(count);
                count = 0;
            } else count++;
        }
        lineChars.add(count);
        return Collections.max(lineChars);
    }

    public Pane getDetailsParent() {
        return this.parent;
    }
}
