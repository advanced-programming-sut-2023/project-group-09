package view.menus.profile;

import controller.Application;
import controller.DBController;
import controller.network.FilesController;
import controller.network.UsersController;
import enumeration.Paths;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.User;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class ScoreboardData {
    private final ObjectProperty<Button> avatar;
    private final SimpleIntegerProperty rank;
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty highScore;
    private final ObjectProperty<Button> onlineGif;
    boolean online;

    public ScoreboardData(int rank, String username, String highScore,boolean online) throws IOException {
        ObjectProperty<Button> onlineGif1;
        this.rank = new SimpleIntegerProperty(rank);
        this.username = new SimpleStringProperty(username);
        this.highScore = new SimpleIntegerProperty(Integer.parseInt(highScore));
        this.online = online;
        Button button = new Button();
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(new ByteArrayInputStream(UsersController.getImageFromServerByUsername(username).toByteArray())),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        button.setBackground(background);


        Button button1 = new Button();
        BackgroundImage backgroundImage1 =
                new BackgroundImage(new Image(getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "online.gif"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background1 = new Background(backgroundImage1);
        button1.setBackground(background1);

        this.avatar = new SimpleObjectProperty<>(setButton(button));
        onlineGif1 = new SimpleObjectProperty<>(setOnlineGif(button1));
        this.onlineGif = onlineGif1;
    }

    public Button setButton(Button button){
        button.setMaxWidth(50);
        button.setMaxWidth(50);
        button.setMinHeight(50);
        button.setMinHeight(50);
        button.setOnMouseClicked(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("change profile photo");
            alert.setContentText("do you want to change your profile photo to this image?");
            ButtonType yes = new ButtonType("yes");
            ButtonType no = new ButtonType("no");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(yes, no);
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == yes) {
                try {
                    changeProfilePhoto();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return button;
    }


    public Button setOnlineGif(Button button){
        button.setMaxWidth(50);
        button.setMaxWidth(50);
        button.setMinHeight(50);
        button.setMinHeight(50);
        if (!online){
            button.setOpacity(0);
        }else{
            button.setOpacity(1);
        }
        return button;
    }


    public int getRank() {
        return rank.get();
    }

    public SimpleIntegerProperty rankProperty() {
        return rank;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public int getHighScore() {
        return highScore.get();
    }

    public SimpleIntegerProperty highScoreProperty() {
        return highScore;
    }

    public void setRank(int rank) {
        this.rank.set(rank);
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setHighScore(int highScore) {
        this.highScore.set(highScore);
    }

    public void changeProfilePhoto() throws IOException {
        String path = Paths.USER_AVATARS.getPath() + UsersController.getUsername();
        try {
            FilesController.copyFile(UsersController.getPath(getUsername()), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scoreboard.setScoreboardData();
    }

    public Button getAvatar() {
        return avatar.get();
    }

    public ObjectProperty<Button> avatarProperty() {
        return avatar;
    }

    public void setAvatar(Button avatar) {
        this.avatar.set(avatar);
    }
}