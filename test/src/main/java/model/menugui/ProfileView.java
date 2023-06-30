package model.menugui;

import controller.network.UsersController;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileView extends Pane {
    String username;
    String highScore;
    String rank;
    String nickname;

    public ProfileView(String username,int height,int width) throws IOException {
        this.username = username;
        this.highScore = UsersController.getHighScore(username);
        this.rank = UsersController.getRank(username);
        this.nickname = UsersController.getNickname(username);

        this.setMaxWidth(width);
        this.setMinWidth(width);
        this.setMaxHeight(height);
        this.setMinHeight(height);
        makeView();
    }

    public void makeView() throws IOException {
        HBox profilePart = new HBox();
        HBox avatarPart = new HBox();
        Rectangle rectangle = new Rectangle(30,30);
        ByteArrayOutputStream output = UsersController.getImageFromServerByUsername(username);
        rectangle.setFill(new ImagePattern(new Image(new ByteArrayInputStream(output.toByteArray()))));
        rectangle.setArcWidth(50);
        rectangle.setArcHeight(50);
        avatarPart.getChildren().add(rectangle);
        HBox.setMargin(rectangle,new Insets(10,20,10,20));
        HBox dataPart = new HBox();
        HBox.setMargin(dataPart,new Insets(10,0,0,0));
        VBox vBox = new VBox();
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        VBox vBox3 = new VBox();
        Label nicknameLabel = new Label("lord "+nickname);
        Label usernameLabel = new Label("@"+username);
        Label highScoreLabel = new Label("high score: "+ highScore);
        Label rankLabel = new Label("rank: "+ highScore);
        nicknameLabel.getStyleClass().add("big-label");
        usernameLabel.getStyleClass().add("small-label");
        highScoreLabel.getStyleClass().add("small-label");
        rankLabel.getStyleClass().add("small-label");
        vBox.getChildren().add(nicknameLabel);
        vBox.getChildren().add(usernameLabel);
        vBox.getChildren().add(highScoreLabel);
        vBox.getChildren().add(rankLabel);
        dataPart.getChildren().addAll(vBox,vBox1,vBox2,vBox3);
        profilePart.getChildren().addAll(avatarPart,dataPart);
        profilePart.setSpacing(10);
        this.getChildren().add(profilePart);
    }

}
