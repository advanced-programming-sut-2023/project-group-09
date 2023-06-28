package model.menugui;

import enumeration.Paths;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuFlag extends ImageView {
    private String color;

    public MenuFlag(String color) {
        this.setImage(new Image(MenuFlag.class.getResource(Paths.FLAG_IMAGES.getPath() +
                color + "Flag.png").toExternalForm()));
        this.color = color;
        this.setViewOrder(-1);
        makeHoverBehavior();
    }

    private void makeHoverBehavior() {
        this.setOnMouseEntered(mouseEvent -> {
            this.setImage(new Image(MenuFlag.class.getResource(Paths.FLAG_IMAGES.getPath() +
                    color + "BrightFlag.png").toExternalForm()));
        });
        this.setOnMouseExited(mouseEvent -> {
            this.setImage(new Image(MenuFlag.class.getResource(Paths.FLAG_IMAGES.getPath() +
                    color + "Flag.png").toExternalForm()));
        });
    }

    public void refresh() {
        this.setImage(new Image(MenuFlag.class.getResource(Paths.FLAG_IMAGES.getPath() +
                color + "Flag.png").toExternalForm()));
    }

    public void setColor(String color) {
        this.color = color;
    }
}
