package view.menus.profile;

import controller.DBController;
import controller.MainController;
import controller.UserController;
import enumeration.Paths;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

public class ScoreboardData {
    private User user;
    private final ObjectProperty<ImageView> avatar;
    private final SimpleIntegerProperty rank;
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty highScore;

    public ScoreboardData(ImageView avatar, int rank, String username, int highScore,User user) {
        this.rank = new SimpleIntegerProperty(rank);
        this.username = new SimpleStringProperty(username);
        this.highScore = new SimpleIntegerProperty(highScore);
        this.user = user;
        this.avatar = new SimpleObjectProperty<>(setImage(avatar));
    }
    public ImageView setImage(ImageView imageView){
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setOnMouseClicked(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("do you want to change your profile photo to this image?");
            ButtonType yes = new ButtonType("yes");
            ButtonType no = new ButtonType("no");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(yes, no);
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == yes) {
                changeProfilePhoto();
            }
        });
        return imageView;
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

    public ImageView getAvatar() {
        return avatar.get();
    }

    public ObjectProperty<ImageView> avatarProperty() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar.set(avatar);
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
        String path = Paths.USER_AVATARS.getPath() + user.getUsername();
        boolean check = new File(path).mkdirs();
        try {
            Files.copy(file.toPath(), (new File(path + "/" + file.getName())).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setPath(path + "/" + file.getName());
        DBController.saveCurrentUser();
        DBController.saveAllUsers();
        Scoreboard.setScoreboardData();
    }
}