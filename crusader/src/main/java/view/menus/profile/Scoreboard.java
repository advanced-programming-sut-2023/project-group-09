package view.menus.profile;

import controller.UserController;
import enumeration.Paths;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;
import model.menugui.MenuBox;
import view.controllers.ViewController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class Scoreboard extends Application {
    public static Stage stage;
    public static Pane root;
    public static MenuBox menuBox;

    public static TableView<ScoreboardData> tableView;
    public static int firstIndex;
    public ArrayList<User> users;

    public Timeline loaderTimeLine;

    public Pane loader;


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
        menuBox = new MenuBox("scoreboard", 250, 110, 500, 600);
        setBackground();
        makeTable();
        addLoader();
        root.getChildren().add(menuBox);
        menuBox.getChildren().add(tableView);

        stage.show();
        Platform.runLater(() -> {
            ScrollBar verticalBar = (ScrollBar) tableView.lookup(".scroll-bar:vertical");
            verticalBar.valueProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue.doubleValue() >= verticalBar.getMax()) {
                    loader.setOpacity(1);
                    menuBox.getChildren().remove(loader);
                    menuBox.getChildren().add(loader);
                    setLoaderTimeLine();
                    setIndexes();
                }
            });
        });
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

    public void makeTable() {
        users = UserController.getSortedListOfUsers();
        firstIndex = 0;
        setupTable();
        setScoreboardData();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<ScoreboardData,ImageView> column = (TableColumn<ScoreboardData, ImageView>) tableView.getColumns().get(1);

    }

    public void setupTable() {

        TableColumn<ScoreboardData, Integer> dateCol = new TableColumn<>("rank");
        dateCol.setPrefWidth(120);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("rank"));

        TableColumn<ScoreboardData, ImageView> value1Col = new TableColumn<>("avatar");
        value1Col.setPrefWidth(50);
        value1Col.setCellValueFactory(new PropertyValueFactory<>("avatar"));

        TableColumn<ScoreboardData, String> value2Col = new TableColumn<>("username");
        value2Col.setPrefWidth(90);
        value2Col.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<ScoreboardData, Integer> value3Col = new TableColumn<>("high score");
        value3Col.setPrefWidth(90);
        value3Col.setCellValueFactory(new PropertyValueFactory<>("highScore"));
        tableView = new TableView<>();
        tableView.getColumns().addAll(dateCol, value1Col, value2Col, value3Col);
        tableView.setMaxHeight(540);
        tableView.setMaxWidth(490);
        tableView.setTranslateY(25);
    }

    private static <T> TableCell<T, ?> findCell(MouseEvent event, TableView<T> table) {
        Node node = event.getPickResult().getIntersectedNode();
        // go up in node hierarchy until a cell is found or we can be sure no cell was clicked
        while (node != table && !(node instanceof TableCell)) {
            node = node.getParent();
        }
        return node instanceof TableCell ? (TableCell<T, ?>) node : null;
    }

    public static void setScoreboardData() {
        ArrayList<User> users = UserController.getSortedListOfUsers();
        ArrayList<ScoreboardData> data = new ArrayList<>();
        for (int i = 0; i < Math.min(firstIndex + 11, users.size()); i++) {
            User user = users.get(i);
            String path = String.valueOf(new File(user.getPath()).toURI());
            ImageView imageView = new ImageView(new Image(path));
            data.add(new ScoreboardData(imageView, i + 1, user.getUsername(), user.getHighScore(), user));
        }
        tableView.setItems(FXCollections.observableList(data));
    }


    public void setIndexes() {
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
        loaderTimeLine.setCycleCount(-1);
        loaderTimeLine.play();
    }

}
