package model.menugui;


import enumeration.Paths;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.awt.*;

public class MenuButton extends Button {

    private Pane pane;
    public MenuButton(String text , Pane pane) {
        BackgroundImage backgroundImage =
                new BackgroundImage( new Image( getClass().getResource
                        (Paths.MENU_IMAGES.getPath()).toExternalForm() + "button.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        this.setAlignment(Pos.TOP_LEFT);
        this.setPadding(new Insets(5));
        this.setText("  " + text);
        setScale();
        this.setTextFill(Color.WHITE);
        this.setFont(Font.font("Times New Roman" , FontWeight.BOLD , 25));
        this.setBackground(background);
        hoverEvent();
    }

    private void hoverEvent() {
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                BackgroundImage backgroundImage =
                        new BackgroundImage( new Image( getClass().getResource
                                (Paths.MENU_IMAGES.getPath()).toExternalForm() + "buttonHover.jpg"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(1.0, 1.0, true, true, false, false));
                Background background = new Background(backgroundImage);
                addAxToButton();
                setBackground(background);
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                BackgroundImage backgroundImage =
                        new BackgroundImage( new Image( getClass().getResource
                                (Paths.MENU_IMAGES.getPath()).toExternalForm() + "button.jpg"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(1.0, 1.0, true, true, false, false));
                Background background = new Background(backgroundImage);
                removeAxFromButton();
                setBackground(background);
            }
        });
    }

    private void setScale(){
        this.setMaxWidth(250);
        this.setMinWidth(250);
        this.setMaxHeight(40);
        this.setMinHeight(40);
    }

    private void addAxToButton() {
        ImageView axImage = new ImageView(new Image(getClass().getResource
                (Paths.MENU_IMAGES.getPath()).toExternalForm() + "ax.jpg"));
        axImage.setTranslateX(this.getTranslateX());
        axImage.setTranslateY(this.getTranslateY());
        pane.getChildren().add(axImage);
    }

    private void removeAxFromButton() {
        // TODO : remove ax from button after hovering;
    }
}
