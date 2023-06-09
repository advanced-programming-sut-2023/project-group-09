package view.menus.profile;

import controller.network.UsersController;
import enumeration.Paths;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.menugui.MenuBox;
import model.menugui.MenuFingerBack;
import view.controllers.ViewController;
import view.menus.MainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class Scoreboard extends Application {
    public static Stage stage;
    public static Pane root;
    public static MenuBox menuBox;
    public static MenuFingerBack back;
    public static TableView<ScoreboardData> tableView;
    public static int firstIndex;
    public ArrayList<String> users;

    public Timeline loaderTimeLine;

    public Timeline checkTimeline;
    public Pane loader;

    public static boolean usersChanged = false;

    @Override
    public void start(Stage stage) throws Exception {
        Scoreboard.stage = stage;
        firstIndex = 0;
        makeScene();
    }

    @FXML
    public void initialize() {
    }

    public void makeScene() throws IOException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(getClass().getResource("/FXML/profile/scoreboard.fxml")).toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        root = ViewController.makePaneScreen(stage, pane, 1100, -1);
        setBackground();
        menuBox = new MenuBox("scoreboard", 250, 110, 520, 600);
        makeTable();
        addLoader();
        root.getChildren().add(menuBox);
        menuBox.getChildren().add(tableView);
        Platform.runLater(() -> {
            ScrollBar verticalBar = (ScrollBar) tableView.lookup(".scroll-bar:vertical");
            verticalBar.valueProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue.doubleValue() >= verticalBar.getMax()) {
                    loader.setOpacity(1);
                    menuBox.getChildren().remove(loader);
                    menuBox.getChildren().add(loader);
                    setLoaderTimeLine();
                    try {
                        setIndexes();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        });
        back = new MenuFingerBack(100, (int) (stage.getHeight() / 2 + 300));
        back.setOnMouseClicked(mouseEvent -> {
            try {
                checkTimeline.stop();
                new MainMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        setUpdateData();
        root.getChildren().add(back);
        stage.show();
    }

    public void setBackground() {
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(getClass().getResource
                        (Paths.BACKGROUND_IMAGES.getPath()).toExternalForm() + "01.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        root.setBackground(background);
    }

    public void makeTable() throws IOException {
        users = UsersController.getSortedUsersUsername();
        firstIndex = 0;
        setupTable();
        setScoreboardData();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void setupTable() {

        TableColumn<ScoreboardData, Integer> dateCol = new TableColumn<>("");
        dateCol.setMinWidth(30);
        dateCol.setMaxWidth(30);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("rank"));

        TableColumn<ScoreboardData, Button> value1Col = new TableColumn<>("");
        value1Col.setPrefWidth(50);
        value1Col.setMinWidth(50);
        value1Col.setMaxWidth(50);
        value1Col.setCellValueFactory(new PropertyValueFactory<>("avatar"));

        TableColumn<ScoreboardData, String> value2Col = new TableColumn<>("username");
        value2Col.setMinWidth(80);
        value2Col.setMaxWidth(80);
        value2Col.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<ScoreboardData, Integer> value3Col = new TableColumn<>("high score");
        value3Col.setMinWidth(90);
        value3Col.setMaxWidth(90);
        value3Col.setCellValueFactory(new PropertyValueFactory<>("highScore"));

        TableColumn<ScoreboardData, String> value5Col = new TableColumn<>("last seen");
        value5Col.setMinWidth(180);
        value5Col.setMaxWidth(180);
        value5Col.setCellValueFactory(new PropertyValueFactory<>("lastSeen"));

        TableColumn<ScoreboardData, ImageView> value4Col = new TableColumn<>("");
        value4Col.setMinWidth(30);
        value4Col.setMaxWidth(30);
        value4Col.setCellValueFactory(new PropertyValueFactory<>("online"));

        TableColumn<ScoreboardData, Button> value6Col = new TableColumn<>("");
        value6Col.setMinWidth(30);
        value6Col.setMaxWidth(30);
        value6Col.setCellValueFactory(new PropertyValueFactory<>("friend"));

        tableView = new TableView<>();
        tableView.getColumns().addAll(dateCol, value1Col, value2Col, value3Col,value4Col,value6Col,value5Col);
        tableView.setMaxHeight(540);
        tableView.setMaxWidth(510);
        tableView.setMinWidth(510);
        tableView.setTranslateY(25);
    }

    public static void setScoreboardData() throws IOException {
        ArrayList<String> users = UsersController.getSortedUsersUsername();
        ArrayList<ScoreboardData> data = new ArrayList<>();
        for (int i = 0; i < Math.min(firstIndex + 11, users.size()); i++) {
            String username = users.get(i);
            System.out.println("last seen : "+UsersController.getLastSeen(username));
            data.add(new ScoreboardData(i + 1, username, UsersController.getHighScore(username),UsersController.getOnline(username)
            ,UsersController.getLastSeen(username)));
        }
        tableView.setItems(FXCollections.observableList(data));
    }


    public void setIndexes() throws IOException {
        firstIndex = Math.min(firstIndex + 11, users.size());
        setScoreboardData();
    }

    public void addLoader() {
        loader = new Pane();
        loader.setStyle("-fx-background-color: rgba(255,255,255,0.61)");
        ImageView imageView = new ImageView(new Image(getClass().getResource(Paths.MENU_IMAGES.getPath()).toExternalForm() + "loader.gif"));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setX(200);
        imageView.setY(250);
        loader.getChildren().add(imageView);
        loader.setOpacity(0);
        menuBox.getChildren().add(loader);
    }

    public void setLoaderTimeLine() {
        loaderTimeLine = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            loader.setOpacity(0);
            menuBox.getChildren().remove(loader);
            menuBox.getChildren().add(0, loader);
        }));
        loaderTimeLine.setCycleCount(1);
        loaderTimeLine.play();
    }

    public void setUpdateData() {
        checkTimeline = new Timeline(new KeyFrame(Duration.millis(2000), actionEvent -> {
            try {
                if (UsersController.areUserChanged()){
                    loader.setOpacity(1);
                    menuBox.getChildren().remove(loader);
                    menuBox.getChildren().add(loader);
                    setLoaderTimeLine();
                    try {
                        setScoreboardData();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        checkTimeline.setCycleCount(-1);
        checkTimeline.play();
    }
}
