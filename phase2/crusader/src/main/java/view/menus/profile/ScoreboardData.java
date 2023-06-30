package view.menus.profile;

import controller.Application;
import controller.DBController;
import enumeration.Paths;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class ScoreboardData {
    private User user;
    private final ObjectProperty<Button> avatar;
    private final SimpleIntegerProperty rank;
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty highScore;

    public ScoreboardData(ImageView avatar, int rank, String username, int highScore,User user) {
        this.rank = new SimpleIntegerProperty(rank);
        this.username = new SimpleStringProperty(username);
        this.highScore = new SimpleIntegerProperty(highScore);
        this.user = user;
        Button button = new Button();
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(String.valueOf(new File(user.getPath()).toURI())),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        button.setBackground(background);
        this.avatar = new SimpleObjectProperty<>(setButton(button));
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
                changeProfilePhoto();
            }
        });
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

    public void changeProfilePhoto(){
        File file = new File(user.getPath());
        String path = Paths.USER_AVATARS.getPath() + Application.getCurrentUser().getUsername();
        boolean check = new File(path).mkdirs();
        try {
            Files.copy(file.toPath(), (new File(path + "/" + file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Application.getCurrentUser().setPath(path + "/" + file.getName());
        DBController.saveCurrentUser();
        DBController.saveAllUsers();
        Scoreboard.setScoreboardData();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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