package model.menugui;

import controller.network.UsersController;
import enumeration.Paths;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.FakeGame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GameData extends Pane {
    String name;
    String id;
    String numberOfPlayer;
    String players;
    String state;
    String map;
    boolean privateSate;

    public FakeGame fakeGame;

    public GameData(FakeGame fakeGame, int height, int width) throws IOException {
        this.name = fakeGame.getGameName();
        this.id = String.valueOf(fakeGame.getGameId());
        this.numberOfPlayer = String.valueOf(fakeGame.getMaxPlayer());
        this.players= String.valueOf(fakeGame.getAllUsernames().size());
        if (fakeGame.isGameStarted()){
            this.state = "gaming...";
        }else{
            this.state = "waiting...";
        }

        this.map = fakeGame.getMapName();
        this.privateSate = fakeGame.isPrivate();

        this.setMaxWidth(width);
        this.setMinWidth(width);
        this.setMaxHeight(height);
        this.setMinHeight(height);
        makeView();
    }

    public void makeView(){
        HBox profilePart = new HBox();
        HBox dataPart = new HBox();
        HBox.setMargin(dataPart,new Insets(10,0,0,30));
        VBox vBox = new VBox();
        Label nameLabel = new Label(name);
        Label idLabel = new Label("id: "+ id);
        Label playerLabel = new Label("player: "+ players +"/" + numberOfPlayer);
        Label stateLabel = new Label("state: "+ state);
        Label mapLabel = new Label("map: "+ map);



        nameLabel.getStyleClass().add("big-label");
        idLabel.getStyleClass().add("small-label");
        playerLabel.getStyleClass().add("small-label");
        stateLabel.getStyleClass().add("small-label");
        mapLabel.getStyleClass().add("small-label");
        vBox.getChildren().add(nameLabel);
        vBox.getChildren().add(idLabel);
        vBox.getChildren().add(playerLabel);
        vBox.getChildren().add(stateLabel);
        vBox.getChildren().add(mapLabel);
        dataPart.getChildren().addAll(vBox);
        profilePart.getChildren().addAll(dataPart);
        profilePart.setSpacing(10);
        if (privateSate){
            Rectangle rectangle = new Rectangle(20,20);
            rectangle.setFill(new ImagePattern(new Image(
                    getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "lock.png"
            )));
            rectangle.setTranslateX(0);
            rectangle.setTranslateY(10);
            this.getChildren().add(rectangle);
        }


        this.getChildren().add(profilePart);
    }
}
