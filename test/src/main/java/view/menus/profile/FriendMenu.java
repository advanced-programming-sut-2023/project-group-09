package view.menus.profile;

import controller.network.FriendController;
import enumeration.Paths;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
import java.util.Optional;

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

    public Circle requestCount;
    public Label requestCountLabel;
    public ArrayList<String> updateFriend = new ArrayList<>();
    public HashMap<String, String> updateRequests = new HashMap<>();

    public String lastSearch;
    public ScaleTransition heartBeat;


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
        putIcons();
        setRequestBox();
        makeFriendPart();
        setSearchBox();
        setSearchResult();
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
        friendList.setPrefWidth(293);
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
        rectangle.setOnMouseEntered(this::scaleUp);
        rectangle.setOnMouseExited(this::scaleDown);
        rectangle.setOnMouseClicked(mouseEvent -> {
            try {
                if (searchInput.getText() == null || searchInput.getText().length() == 0) {
                    centerPane.getChildren().remove(searchResult);
                    lastSearch = null;
                } else {
                    showResult(false);
                    if (!centerPane.getChildren().contains(searchResult)) {
                        centerPane.getChildren().add(searchResult);
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        search.setOnMouseClicked(mouseEvent -> {
            try {
                if (searchInput.getText() == null || searchInput.getText().length() == 0) {
                    centerPane.getChildren().remove(searchResult);
                    lastSearch = null;
                } else {
                    showResult(false);
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
        showResult(false);
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
        requests.getChildren().clear();
        HashMap<String, String> updateRequests = FriendController.getRequests();
        if (updateRequests.size() == 0) {
            requests.getChildren().add(new Label("there is no request"));
        }
        for (String username : updateRequests.keySet()) {
            VBox vBox = new VBox();
            ProfileView profileView = new ProfileView(username, 80, 460);
            Button button = new Button();
            button.setPadding(new Insets(10, 20, 10, 20));
            button.setTranslateX(340);
            button.setTranslateY(20);
            button.getStyleClass().add("friend-btn");
            button.setOnMouseEntered(this::scaleUp);
            button.setOnMouseExited(this::scaleDown);
            if (updateRequests.get(username) != null) {
                String state = updateRequests.get(username);
                if (state.equals("requested")) {
                    Button button1 = new Button();
                    button1.setPadding(new Insets(10, 20, 10, 20));
                    button1.setTranslateX(260);
                    button1.setTranslateY(20);
                    button1.getStyleClass().add("friend-btn");
                    button1.setText("reject");
                    button1.setStyle("-fx-background-color: red");
                    button.setText("accept");
                    button1.setOnMouseEntered(this::scaleUp);
                    button1.setOnMouseExited(this::scaleDown);
                    button.setOnMouseClicked(mouseEvent -> {
                        try {
                            boolean check = FriendController.acceptRequest(username);
                            if (!check) {
                                new MenuPopUp(root, 400, 400, "error", "limitation of friends number!");
                            } else {
                                profileView.getChildren().removeAll(button, button1);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    button1.setOnMouseClicked(mouseEvent -> {
                        try {
                            FriendController.rejectFriend(username);
                            profileView.getChildren().removeAll(button1);
                            button.setText("request");
                            button.setOnMouseClicked(mouseEvent1 -> {
                                try {
                                    boolean check = FriendController.sendRequest(username);
                                    if (!check) {
                                        new MenuPopUp(root, 400, 400, "error", "limitation of friends number!");
                                    } else {
                                        button.setOnMouseClicked(null);
                                        button.setText("requesting");
                                        button.setOpacity(0.5);
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    profileView.getChildren().addAll(button, button1);
                } else {
                    button.setText("requesting");
                    button.setOpacity(0.5);
                    profileView.getChildren().add(button);
                }
            }
            vBox.getChildren().add(profileView);
            requests.getChildren().add(vBox);
        }
    }


    public void showResult(boolean repeat) throws IOException {
        searchResults.getChildren().clear();
        if (!repeat) {
            lastSearch = searchInput.getText();
        }
        ArrayList<String> users = FriendController.searchUser(lastSearch);
        updateRequests = FriendController.getRequests();
        updateFriend = FriendController.getFriends();
        if (users.size() == 0) {
            searchResults.getChildren().add(new Label("no user with this name exist"));
        }
        for (String username : users) {
            VBox vBox = new VBox();
            ProfileView profileView = new ProfileView(username, 80, 460);
            Button button = new Button();
            button.setPadding(new Insets(10, 20, 10, 20));
            button.setTranslateX(340);
            button.setTranslateY(20);
            button.getStyleClass().add("friend-btn");
            button.setOnMouseEntered(this::scaleUp);
            button.setOnMouseExited(this::scaleDown);
            if (updateRequests.get(username) != null) {
                String state = updateRequests.get(username);
                if (state.equals("requested")) {
                    Button button1 = new Button();
                    button1.setPadding(new Insets(10, 20, 10, 20));
                    button1.setTranslateX(260);
                    button1.setTranslateY(20);
                    button1.getStyleClass().add("friend-btn");
                    button1.setText("reject");
                    button1.setStyle("-fx-background-color: red");
                    button.setText("accept");
                    button1.setOnMouseEntered(this::scaleUp);
                    button1.setOnMouseExited(this::scaleDown);
                    button.setOnMouseClicked(mouseEvent -> {
                        try {
                            boolean check = FriendController.acceptRequest(username);
                            if (!check) {
                                new MenuPopUp(root, 400, 400, "error", "limitation of friends number!");
                            } else {
                                profileView.getChildren().removeAll(button, button1);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    button1.setOnMouseClicked(mouseEvent -> {
                        try {
                            FriendController.rejectFriend(username);
                            profileView.getChildren().removeAll(button1);
                            button.setText("request");
                            button.setOnMouseClicked(mouseEvent1 -> {
                                try {
                                    boolean check = FriendController.sendRequest(username);
                                    if (!check) {
                                        new MenuPopUp(root, 400, 400, "error", "limitation of friends number!");
                                    } else {
                                        button.setOnMouseClicked(null);
                                        button.setText("requesting");
                                        button.setOpacity(0.5);
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    profileView.getChildren().addAll(button, button1);
                } else {
                    button.setText("requesting");
                    button.setOpacity(0.5);
                    profileView.getChildren().add(button);
                }
            } else if (!updateFriend.contains(username)) {
                button.setText("request");
                button.setOnMouseClicked(mouseEvent -> {
                    try {
                        boolean check = FriendController.sendRequest(username);
                        if (!check) {
                            new MenuPopUp(root, 400, 400, "error", "limitation of friends number!");
                        } else {
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
            Rectangle rectangle = new Rectangle(20, 20);
            rectangle.setFill(new ImagePattern(new Image(
                    getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "delete.png"
            )));
            rectangle.setOnMouseEntered(this::scaleUp);
            rectangle.setOnMouseExited(this::scaleDown);
            rectangle.setOnMouseClicked(mouseEvent -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("delete friend");
                alert.setContentText("do you want to delete " + username + " from your friends?");
                ButtonType yes = new ButtonType("yes");
                ButtonType no = new ButtonType("no");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(yes, no);
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get() == yes) {
                    try {
                        FriendController.deleteFriend(username);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            });
            rectangle.setTranslateX(240);
            rectangle.setTranslateY(30);
            profileView.getChildren().add(rectangle);
            vBox.getChildren().add(profileView);
            friendList.getChildren().add(vBox);
        }
    }

    public void putIcons() throws IOException {
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

        requestCount = new Circle(6);
        requestCount.setFill(Color.PINK);
        int reqCnt = FriendController.getRequestsCount(FriendController.getRequests());
        if (reqCnt != 0) {
            setHeartBeat();
        }
        requestCountLabel = new Label(reqCnt + "");
        requestCount.setTranslateY(20);
        requestCount.setTranslateX(80);
        requestCountLabel.setTranslateY(14);
        requestCountLabel.setTranslateX(78);
        requestCountLabel.setStyle("-fx-font-size: 8");


        leftSide.getChildren().addAll(home, showRequests, requestCount, requestCountLabel);

        back = new Rectangle(20, 20);
        back.setFill(new ImagePattern(new Image(
                getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "back.png"
        )));

        home.setOnMouseEntered(this::scaleUp);
        home.setOnMouseExited(this::scaleDown);
        home.setOnMouseClicked(mouseEvent -> {
            try {
                updateData.stop();
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
                if (lastSearch != null) {
                    try {
                        showResult(true);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                int reqCnt = FriendController.getRequestsCount(updateRequests);
                if (reqCnt != 0) {
                    if (heartBeat == null) {
                        setHeartBeat();
                    } else {
                        heartBeat.stop();
                        heartBeat.play();
                    }
                } else {
                    if (heartBeat != null) {
                        heartBeat.stop();
                        showRequests.setScaleX(1);
                        showRequests.setScaleY(1);
                    }
                }
                requestCountLabel.setText(reqCnt + "");
            }
        }));
        updateData.setCycleCount(-1);
        updateData.play();
    }

    public void setHeartBeat() {
        heartBeat = new ScaleTransition(Duration.seconds(1), showRequests);
        heartBeat.setFromX(1.0);
        heartBeat.setFromY(1.0);
        heartBeat.setToX(1.1);
        heartBeat.setToY(1.1);
        heartBeat.setAutoReverse(true);
        heartBeat.setCycleCount(ScaleTransition.INDEFINITE);
        heartBeat.setDuration(Duration.millis(500));

        // Start the heartbeat animation
        heartBeat.play();
    }

    public void scaleDown(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        ScaleTransition st = new ScaleTransition(Duration.millis(100), node);
        st.setFromX(1.1);
        st.setFromY(1.1);
        st.setToX(1);
        st.setToY(1);
        st.setAutoReverse(true);
        st.play();
    }

    public void scaleUp(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();

        ScaleTransition st = new ScaleTransition(Duration.millis(100), node);
        st.setFromX(1);
        st.setFromY(1);
        st.setToX(1.1);
        st.setToY(1.1);
        st.setAutoReverse(true);
        st.play();

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