package view.menus.profile;

import controller.network.FriendController;
import enumeration.Paths;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.menugui.MenuPopUp;
import model.menugui.ProfileView;
import view.controllers.ViewController;
import view.menus.LoginMenu;
import view.menus.MainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FriendMenu extends Application {
    public static Stage stage;
    public static StackPane root;
    public static BorderPane borderPane;
    public static Pane leftSide;
    public static Pane centerPane;
    public static Pane searchResult;
    public static Rectangle search;
    public static VBox friendList;
    public static VBox searchResults;
    public static Rectangle home;
    public static Rectangle back;
    public static Rectangle showRequests;
    public static TextField searchInput;
    //--------- requests------------------
    public static Pane requestPane;

    public static Pane requestsBlock;

    public static VBox requests;
    public Timeline updateData;


    public ArrayList<String> updateFriend = new ArrayList<>();
    public HashMap<String ,String> updateRequests = new HashMap<>();



    @Override
    public void start(Stage stage) throws Exception {
        FriendMenu.stage = stage;
        makeScene();
    }

    @FXML
    public void initialize() {
    }

    public void makeScene() throws IOException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/profile/friendMenu.fxml")).toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        root = ViewController.makeStackPaneScreen(stage, pane, 1000, 800);
        setBackground();
        borderPane = new BorderPane();
        leftSide = new Pane();
        centerPane = new Pane();

        leftSide.setMaxWidth(300);
        leftSide.setMinWidth(300);
        borderPane.setLeft(leftSide);
        borderPane.setCenter(centerPane);
        leftSide.setStyle("-fx-background-color: #fff");
        root.getChildren().addAll(borderPane);
        makeFriendPart();
        setSearchBox();
        setSearchResult();
        putIcons();
        setRequestBox();
        setUpdateTimeline();
        stage.show();
    }

    public void makeFriendPart() throws IOException {
        Label label = new Label("friends");
        label.getStyleClass().add("title-label");
        label.setMaxWidth(300);
        label.setMinWidth(300);
        label.setMaxHeight(50);
        label.setMaxHeight(50);
        label.setTranslateY(50);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(10, 0, 10, 0));
        friendList = new VBox();
        showFriends();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(friendList);
        friendList.setPrefHeight(700);
        friendList.setPrefWidth(295);
        scrollPane.setMaxHeight(700);
        scrollPane.setFitToHeight(false);
        scrollPane.setTranslateY(100);
        scrollPane.setTranslateX(0);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leftSide.getChildren().add(label);
        leftSide.getChildren().add(scrollPane);
    }

    public void setSearchBox() {
        TextField textField = new TextField();
        textField.getStyleClass().add("input-text");
        textField.setMaxWidth(500);
        textField.setMinWidth(500);
        textField.setMaxHeight(45);
        textField.setMinHeight(45);
        textField.setPadding(new Insets(0, 10, 0, 10));
        textField.setPromptText("search...");
        searchInput = textField;
        VBox vBox = new VBox();
        vBox.getChildren().add(textField);
        vBox.setTranslateY(100);
        vBox.setTranslateX(100);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #000");
        vBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        Rectangle rectangle = new Rectangle(50, 50);
        search = new Rectangle(20, 20);
        rectangle.setFill(Color.GREEN);
        String path = getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "search.png";
        search.setFill(new ImagePattern(new Image(path)));
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        search.setArcHeight(20);
        search.setArcWidth(20);
        rectangle.setTranslateX(610);
        rectangle.setTranslateY(100);
        search.setTranslateX(625);
        search.setTranslateY(115);

        rectangle.setOnMouseClicked(mouseEvent -> {
            try {
                if (searchInput.getText() == null || searchInput.getText().length() == 0) {
                    centerPane.getChildren().remove(searchResult);
                } else {
                    showResult();
                    centerPane.getChildren().add(searchResult);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        search.setOnMouseClicked(mouseEvent -> {
            try {
                if (searchInput.getText() == null || searchInput.getText().length() == 0) {
                    centerPane.getChildren().remove(searchResult);
                } else {
                    showResult();
                    centerPane.getChildren().add(searchResult);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        centerPane.getChildren().addAll(vBox, rectangle, search);
    }

    public void setSearchResult() throws IOException {
        searchResult = new Pane();
        searchResults = new VBox();
        searchResult.setStyle("-fx-background-color: #fff;-fx-background-radius: 10");
        searchResult.setMaxWidth(500);
        searchResult.setMinWidth(500);
        searchResult.setMaxHeight(550);
        searchResult.setMinHeight(550);
        searchResult.setTranslateX(100);
        searchResult.setTranslateY(200);
        showResult();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(searchResults);
        searchResults.setPrefHeight(500);
        searchResults.setPrefWidth(460);
        scrollPane.setMaxHeight(700);
        scrollPane.setFitToHeight(false);
        scrollPane.setTranslateY(25);
        scrollPane.setTranslateX(20);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        searchResult.getChildren().add(scrollPane);
    }

    public void setRequestBox() throws IOException {
        Label label = new Label("requests");
        label.getStyleClass().add("request-label");
        label.setMaxWidth(500);
        label.setMinWidth(500);
        label.setMaxHeight(40);
        label.setMaxHeight(40);
        label.setTranslateY(10);
        ;
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(10, 0, 10, 0));
        requestPane = new Pane();
        requestsBlock = new Pane();
        requests = new VBox();
        requestsBlock.setStyle("-fx-background-color: #fff;-fx-background-radius: 10");
        requestsBlock.setMaxWidth(500);
        requestsBlock.setMinWidth(500);
        requestsBlock.setMaxHeight(600);
        requestsBlock.setMinHeight(600);
        requestsBlock.setTranslateX(250);
        requestsBlock.setTranslateY(100);
        showRequests();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(requests);
        requests.setPrefHeight(500);
        requests.setPrefWidth(460);
        scrollPane.setMaxHeight(700);
        scrollPane.setFitToHeight(false);
        scrollPane.setTranslateY(60);
        scrollPane.setTranslateX(20);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        back.setTranslateX(20);
        back.setTranslateY(20);
        requestsBlock.getChildren().addAll(label, scrollPane, back);
        requestPane.getChildren().add(requestsBlock);
        requestPane.setStyle("-fx-background-color: rgba(121, 121, 121, 0.58);");
        //root.getChildren().add(requestPane);

    }

    public void showRequests() throws IOException {
        HashMap<String, String> updateRequests = FriendController.getRequests();
        System.out.println("ooo_iii"+updateRequests.size());
        for (String username : updateRequests.keySet()) {
            VBox vBox = new VBox();
            ProfileView profileView = new ProfileView(username, 80, 460);
            vBox.getChildren().add(profileView);
            requests.getChildren().add(vBox);
        }
    }


    public void showResult() throws IOException {
        searchResults.getChildren().clear();
        ArrayList<String> users = FriendController.searchUser(searchInput.getText());
        if (users.size() == 0) {
            searchResults.getChildren().add(new Label("no user with name exist"));
        }
        for (String username : users) {
            VBox vBox = new VBox();
            ProfileView profileView = new ProfileView(username, 80, 460);
            Button button = new Button();
            button.setPadding(new Insets(10,20,10,20));
            button.setTranslateX(340);
            button.setTranslateY(20);
            button.getStyleClass().add("friend-btn");
            System.out.println("i_i_i_: "+updateRequests.size());
            if (updateRequests.get(username) != null){
                String state = updateRequests.get(username);
                if (state.equals("requested")){
                    button.setText("accept");
                    button.setOnMouseClicked(mouseEvent -> {
                        try {
                            boolean check = FriendController.acceptRequest(username);
                            if (!check){
                                new MenuPopUp(root,400,400,"error","limitation of friends number!");
                            }else{
                                profileView.getChildren().remove(button);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    profileView.getChildren().add(button);
                }else{
                    button.setText("requesting");
                    button.setOpacity(0.5);
                    profileView.getChildren().add(button);
                }
            } else if (!updateFriend.contains(username)) {
                button.setText("request");
                button.setOnMouseClicked(mouseEvent -> {
                    try {
                        boolean check = FriendController.sendRequest(username);
                        if (!check){
                            new MenuPopUp(root,400,400,"error","limitation of friends number!");
                        }else{
                            button.setOnMouseClicked(null);
                            button.setText("requesting");
                            button.setOpacity(0.5);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                profileView.getChildren().add(button);
            }
            vBox.getChildren().add(profileView);
            searchResults.getChildren().add(vBox);
        }
    }

    public void showFriends() throws IOException {
        friendList.getChildren().clear();
        updateFriend = FriendController.getFriends();
        if (updateFriend.size() == 0) {
            friendList.getChildren().add(new Label("you have no friend :("));
        }
        for (String username : updateFriend) {
            VBox vBox = new VBox();
            ProfileView profileView = new ProfileView(username, 80, 300);
            vBox.getChildren().add(profileView);
            friendList.getChildren().add(vBox);
        }
    }

    public void putIcons() {
        home = new Rectangle(20, 20);
        home.setFill(new ImagePattern(new Image(
                getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "home.png"
        )));
        showRequests = new Rectangle(20, 20);
        showRequests.setFill(new ImagePattern(new Image(
                getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "heart.png"
        )));
        home.setTranslateY(20);
        home.setTranslateX(20);
        showRequests.setTranslateY(20);
        showRequests.setTranslateX(60);
        leftSide.getChildren().addAll(home, showRequests);

        back = new Rectangle(20, 20);
        back.setFill(new ImagePattern(new Image(
                getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "back.png"
        )));


        home.setOnMouseClicked(mouseEvent -> {
            try {
                new MainMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        showRequests.setOnMouseClicked(mouseEvent -> {
            if (!root.getChildren().contains(requestPane)) {
                try {
                    showRequests();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                root.getChildren().add(requestPane);
            }
        });

        back.setOnMouseClicked(mouseEvent -> {
            root.getChildren().remove(requestPane);
        });


    }

    public void setUpdateTimeline() {
        updateData = new Timeline(new KeyFrame(Duration.millis(2000), actionEvent -> {
            boolean update;
            try {
                update = FriendController.shouldUpdate();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (update) {
                try {
                    showRequests();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    showFriends();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
        updateData.setCycleCount(-1);
        updateData.play();
    }

    public void setBackground() {
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(getClass().getResource
                        (Paths.BACKGROUND_IMAGES.getPath()).toExternalForm() + "02.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        root.setBackground(background);
    }
}