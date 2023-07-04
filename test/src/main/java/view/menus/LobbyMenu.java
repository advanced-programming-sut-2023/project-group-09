package view.menus;

import controller.GameController;
import controller.network.LobbyController;
import enumeration.Paths;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.FakeGame;
import model.menugui.GameData;
import model.menugui.MakeNewGame;
import model.menugui.MenuButton;
import model.menugui.MenuFingerBack;
import view.controllers.ViewController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class LobbyMenu extends Application {
    public static Stage stage;
    public static Pane root;
    public static StackPane stackPane;
    public static MenuButton profile;
    public static MenuFingerBack back;
    public Pane gameListPane;
    public VBox gameList;
    //============ make game block ==========;
    public static Pane makeNewGamePane;
    public static MakeNewGame makeGameBlock;



    @Override
    public void start(Stage stage) throws Exception {
        LobbyMenu.stage = stage;
        makeScene();
    }

    @FXML
    public void initialize() {

    }

    public void makeScene() throws IOException, ClassNotFoundException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/lobbyMenu.fxml")).toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        root = ViewController.makeStackPaneScreen(stage, pane, 1000, 800);
        stackPane = new StackPane();
        root.getChildren().add(stackPane);
        setBackground();
        setButtonsEvent();
        setGameList();
        setMakeNewGamePane();
        back = new MenuFingerBack(-400,300);
        back.setOnMouseClicked(mouseEvent -> {
            try {
                new MainMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        root.getChildren().add(back);
        stage.show();
    }

    public void setButtonsEvent() {

    }

    public void setGameList() throws IOException, ClassNotFoundException {
        Label label = new Label("Games");
        label.getStyleClass().add("request-label");
        label.setMaxWidth(600);
        label.setMinWidth(600);
        label.setMaxHeight(40);
        label.setMaxHeight(40);
        label.setTranslateY(20);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(10, 0, 10, 0));
        gameListPane = new Pane();
        gameList = new VBox();
        gameListPane.setStyle("-fx-background-color: #fff;-fx-background-radius: 10");
        gameListPane.setMaxWidth(600);
        gameListPane.setMinWidth(600);
        gameListPane.setMaxHeight(700);
        gameListPane.setMinHeight(700);
        Button makeNewGameButton = new Button("new game");
        makeNewGameButton.getStyleClass().add("new-game-btn");
        makeNewGameButton.setOnMouseEntered(this::scaleUp);
        makeNewGameButton.setOnMouseExited(this::scaleDown);
        makeNewGameButton.setOnMouseClicked(mouseEvent -> {
            makeNewGamePane.getChildren().remove(makeGameBlock);
            try {
                makeGameBlock = new MakeNewGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            makeNewGamePane.getChildren().add(makeGameBlock);
            if (!stackPane.getChildren().contains(makeNewGamePane)){
                stackPane.getChildren().add(makeNewGamePane);
            }
            makeGameBlock.makeGame.setOnMouseClicked(mouseEvent1 -> {
                try {
                    makeGameBlock.submit();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
        makeNewGameButton.setTranslateX(60);
        makeNewGameButton.setTranslateY(30);

        Rectangle reload = new Rectangle(20,20);
        reload.setFill(new ImagePattern(new Image(
                getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "reload.png"
        )));
        reload.setOnMouseEntered(mouseEvent -> {
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(800), reload);
            rotateTransition.setByAngle(360);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
        });
        reload.setOnMouseClicked(mouseEvent -> {
            try {
                showGames();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        reload.setTranslateX(530);
        reload.setTranslateY(60);
        showGames();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gameList);
        gameList.setPrefHeight(500);
        gameList.setPrefWidth(460);
        scrollPane.setMaxHeight(700);
        scrollPane.setFitToHeight(false);
        scrollPane.setTranslateY(100);
        scrollPane.setTranslateX(70);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        gameListPane.getChildren().addAll(label, scrollPane,makeNewGameButton,reload);
        stackPane.getChildren().add(gameListPane);
    }

    public void setMakeNewGamePane(){
        makeNewGamePane = new Pane();
        makeNewGamePane.setStyle("-fx-background-color: rgba(121, 121, 121, 0.58);");
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


    public void showGames() throws IOException, ClassNotFoundException {
        gameList.getChildren().clear();
        ArrayList<FakeGame> fakeGames = GameController.getAllRunningFakeGames();
        if (fakeGames.size() == 0){
            gameList.getChildren().add(new Label("no game exist"));
        }
        for (FakeGame fakeGame : fakeGames){
            VBox vBox = new VBox();
            GameData gameData = new GameData(fakeGame,100,460);
            vBox.getChildren().add(gameData);
            Button button = new Button();
            button.setPadding(new Insets(10, 20, 10, 20));
            button.setTranslateX(340);
            button.setTranslateY(20);
            button.getStyleClass().add("new-game-btn");
            button.setOnMouseEntered(this::scaleUp);
            button.setOnMouseExited(this::scaleDown);
            button.setText("join");

            Rectangle rectangle = new Rectangle(20,20);
            rectangle.setFill(new ImagePattern(new Image(
                    getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "view.png"
            )));
            rectangle.setTranslateX(300);
            rectangle.setTranslateY(30);
            gameData.getChildren().addAll(button,rectangle);
            gameList.getChildren().add(vBox);
        }
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
                        (Paths.BACKGROUND_IMAGES.getPath()).toExternalForm() + "06.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        root.setBackground(background);
    }
}
