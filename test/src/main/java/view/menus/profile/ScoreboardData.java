package view.menus.profile;

import controller.Application;
import controller.DBController;
import controller.network.FilesController;
import controller.network.FriendController;
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
    private final SimpleStringProperty lastSeen;
    private final SimpleIntegerProperty highScore;
    private final ObjectProperty<ImageView> online;
    private final ObjectProperty<Button> friend;
    boolean show;

    public ScoreboardData(int rank, String username, String highScore,boolean online, String lastSeen) throws IOException {
        this.rank = new SimpleIntegerProperty(rank);
        this.username = new SimpleStringProperty(username);
        this.highScore = new SimpleIntegerProperty(Integer.parseInt(highScore));
        this.lastSeen = new SimpleStringProperty(lastSeen);
        this.show = online;
        Button button = new Button();
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(new ByteArrayInputStream(UsersController.getImageFromServerByUsername(username).toByteArray())),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        button.setBackground(background);


        ImageView imageView = new ImageView(new Image(getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "online.gif"));

        Image image = new Image(getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "handshake.png");
        Button button1 = new Button();
        BackgroundImage backgroundImage1 =
                new BackgroundImage(image,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background1 = new Background(backgroundImage1);
        button1.setBackground(background1);



        this.avatar = new SimpleObjectProperty<>(setButton(button));
        this.online= new SimpleObjectProperty<>(setOnlineGif(imageView));
        this.friend = new SimpleObjectProperty<>(setFriend(button1));
    }

    private Button setFriend(Button button) throws IOException {
        button.setMaxWidth(20);
        button.setMaxWidth(20);
        button.setMinHeight(20);
        button.setMinHeight(20);
        if (!getUsername().equals(UsersController.getUsername())){
            button.setOnMouseClicked(mouseEvent -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("send request");
                alert.setContentText("do you want to send a request to " + getUsername() + " to be your friend?");
                ButtonType yes = new ButtonType("yes");
                ButtonType no = new ButtonType("no");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(yes, no);
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get() == yes) {
                    try {
                        boolean check = FriendController.sendRequest(getUsername());
                        if (check){
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("request recorded");
                            alert1.setContentText("request recorded successfully for more data go to friend part");
                            alert1.showAndWait();
                        }else{
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle("friend number limitation");
                            alert2.setContentText("you have 100 friends and achieve the friend number limitation!");
                            alert2.showAndWait();
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            });
        }else {
            button.setOpacity(0);
        }
        return button;
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


    public ImageView setOnlineGif(ImageView imageView){
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        System.out.println(show);
        if (!show){
            imageView.setOpacity(0);
        }else{
            imageView.setOpacity(1);
        }
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
        //Scoreboard.setScoreboardData();
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

    public ImageView getOnline() {
        return online.get();
    }

    public ObjectProperty<ImageView> onlineProperty() {
        return online;
    }

    public void setOnline(ImageView online) {
        this.online.set(online);
    }

    public String getLastSeen() {
        return lastSeen.get();
    }

    public SimpleStringProperty lastSeenProperty() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen.set(lastSeen);
    }

    public Button getFriend() {
        return friend.get();
    }

    public ObjectProperty<Button> friendProperty() {
        return friend;
    }
}