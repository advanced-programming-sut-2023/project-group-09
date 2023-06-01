package model.menugui;

import enumeration.Paths;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import viewphase1.LoginMenu;

import java.util.Objects;

public class MenuPopUp extends Pane {

    private Pane parent;
    private int width;
    private int height;
    private String type;
    private String massage;
    public StackPane block;

    public MenuPopUp(Pane parent, int width, int height, String type, String massage) {
        this.parent = parent;
        this.width = width;
        this.height = height;
        this.type = type;
        this.massage = massage;
        this.setStyle("-fx-background-color: rgba(107,107,107,0.57)");
        setBlock();
        setIcon();
        setMassage();
        setButton();
        setTransitions();
        //setTimeline();
    }

    public void setBlock() {
        block =new StackPane();
        block.getStyleClass().add("pop-up");
        block.setMaxWidth(width);
        block.setMinWidth(width);
        block.setMaxHeight(height);
        block.setMinHeight(height);
        block.setTranslateX(parent.getMaxWidth() / 2 - (double) width / 2);
        block.setTranslateY(200);
        this.getChildren().add(block);
    }

    public void setIcon() {
        ImageView imageView;
        if (Objects.equals(type, "error")){
            imageView = new ImageView(new Image(
                    LoginMenu.class.getResource(Paths.ICONS.getPath()).toExternalForm() + "/cross.png"));
        }else{
            imageView = new ImageView(new Image(
                    LoginMenu.class.getResource(Paths.ICONS.getPath()).toExternalForm() + "/accept.png"));
        }

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setX(0);
        imageView.setTranslateY(-100);
        block.getChildren().add(imageView);
    }

    public void setMassage(){
        Label label = new Label(massage);
        label.setStyle("-fx-font-weight: bold;-fx-font-size: 24");
        VBox.setMargin(label,new Insets(0,0,30,0));
        block.getChildren().add(label);
    }

    public void setTransitions(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(this);
        fadeTransition.setDuration(Duration.millis(200));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
    }

    public void setTimeline(){
        Timeline timeline= new Timeline(new KeyFrame(Duration.millis(3000), actionEvent -> {
            closePopUp();
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void closePopUp(){
        parent.getChildren().remove(this);
    }

    public void setButton(){
        MenuButton menuButton = new MenuButton("close",block,0,100,false);
        menuButton.setOnMouseClicked(mouseEvent -> closePopUp());
        block.getChildren().add(menuButton);

    }
}
