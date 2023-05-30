package model.menugui;


import enumeration.Paths;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;

public class MenuButton extends Button {
    public MenuButton(String text) {
        BackgroundImage backgroundImage =
                new BackgroundImage( new Image( getClass().getResource
                        (Paths.MENU_IMAGES.getPath()).toExternalForm() + "button.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        this.setText(text);
        this.setMaxWidth(500);
        this.setMinWidth(500);
        this.setTextFill(Color.WHITE);
        this.setBackground(background);
    }
}
