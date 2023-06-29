package model.menugui;

import enumeration.Paths;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


public class MenuFingerBack extends Button {
    public MenuFingerBack(int x, int y) {
        this.setMaxWidth(110);
        this.setMinWidth(110);
        this.setMaxHeight(50);
        this.setMinHeight(50);
        this.setTranslateX(x);
        this.setTranslateY(y);

        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(getClass().getResource
                        (Paths.MENU_IMAGES.getPath()).toExternalForm() + "free.png"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        this.setBackground(background);
        hoverEvent();
    }

    private void hoverEvent() {
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                BackgroundImage backgroundImage =
                        new BackgroundImage(new Image(getClass().getResource
                                (Paths.MENU_IMAGES.getPath()).toExternalForm() + "pointing.png"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(1.0, 1.0, true, true, false, false));
                Background background = new Background(backgroundImage);
                setBackground(background);
            }
        });

        this.setOnMouseExited(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                BackgroundImage backgroundImage =
                        new BackgroundImage(new Image(getClass().getResource
                                (Paths.MENU_IMAGES.getPath()).toExternalForm() + "free.png"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(1.0, 1.0, true, true, false, false));
                Background background = new Background(backgroundImage);
                setBackground(background);
            }
        });
    }
}
