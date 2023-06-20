package model.menugui.game;

import enumeration.Paths;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import view.menus.LoginMenu;

public class TypBTN {
    public ImageView imageView;
    public String name;
    public Pane parent;
    public Text text;
    public int count = 0;

    public TypBTN(String name, Pane parent, int count, double x, double y) {
        this.imageView = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/unit menu/" + name + ".png");
        this.count = count;
        this.name = name;
        text = new Text(count + "");
        this.imageView.setTranslateX(x);
        this.imageView.setTranslateY(y);
        this.imageView.setFitHeight(80);
        this.imageView.setFitWidth(45);
        this.text.setTranslateX(x + 12);
        this.text.setTranslateY(y + 120);
        this.text.setStyle("-fx-font-size: 24;-fx-fill: #fff");
        this.parent = parent;
        parent.getChildren().addAll(imageView, text);
    }

}
