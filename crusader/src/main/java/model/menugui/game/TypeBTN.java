package model.menugui.game;

import enumeration.Paths;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import view.menus.LoginMenu;

public class TypeBTN {
    public ImageView imageView;
    public String name;
    public Pane parent;
    public Text text;
    public int count = 0;

    public TypeBTN(String name, Pane parent, int count, double x, double y) {
        this.imageView = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/unit menu/" + name + ".png");
        this.count = count;
        this.name = name;
        text = new Text(count + "");
        this.imageView.setTranslateX(x);
        this.imageView.setTranslateY(y);
        this.imageView.setFitHeight(55);
        this.imageView.setFitWidth(55);
        this.text.setTranslateX(x + 18);
        this.text.setTranslateY(y + 80);
        this.text.setStyle("-fx-font-size: 24;-fx-fill: #fff");
        this.parent = parent;
        if (!name.equals("lord")){
            parent.getChildren().addAll(imageView, text);
        }

    }

}
