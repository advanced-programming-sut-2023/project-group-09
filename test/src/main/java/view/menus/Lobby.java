package view.menus;

import client.Packet;
import client.PacketOnlineReceiver;
import controller.MapController;
import enumeration.Paths;
import enumeration.dictionary.Colors;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.FakeGame;
import model.game.Map;
import model.menugui.LobbyPreviewMap;
import model.menugui.MenuFlag;
import model.menugui.ProfileView;
import view.controllers.ViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class Lobby extends Application {
    public static Stage stage;
    public static StackPane root;

    public static FakeGame fakeGame;
    public static BorderPane borderPane;
    public static Pane leftSide;
    public static Pane centerPane;
    public static Rectangle home;
    public static Rectangle back;
    public static Pane overPane;
    //--------- choose color ------------------
    public static Pane colorBlock;
    public static Pane governmentBlock;
    public String selectedColor;
    public LobbyPreviewMap previewMap;
    public ArrayList<MenuFlag> castleFlags = new ArrayList<>();

    public ArrayList<String> users = new ArrayList<>();
    public static VBox usersList;


    Map selectedMap;
    public static PacketOnlineReceiver receiver;

    @Override
    public void start(Stage stage) throws Exception {
        Lobby.stage = stage;
        makeScene();
    }

    public void makeScene() throws IOException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/lobby.fxml")).toExternalForm()));
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
        makeOverPane();
        setFlags();
        setColor();
        setUsers();
        stage.show();
    }

    public void setFlags() {
        try {
            selectedMap = MapController.getMapFromServer(fakeGame.getMapName());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 8; i++) {
            MenuFlag flag = new MenuFlag("transparent");
            flag.setVisible(false);
            castleFlags.add(flag);
        }

        receiver = new PacketOnlineReceiver();
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


    public void makeOverPane() {
        overPane = new Pane();
        overPane.setStyle("-fx-background-color: rgba(121, 121, 121, 0.58);");
    }

    public void setColor() {
        root.getChildren().add(overPane);
        colorBlock = new Pane();
        colorBlock.setStyle("-fx-background-color: #fff;-fx-background-radius: 10");
        colorBlock.setMaxWidth(500);
        colorBlock.setMinWidth(500);
        colorBlock.setMaxHeight(600);
        colorBlock.setMinHeight(600);
        colorBlock.setTranslateX(250);
        colorBlock.setTranslateY(100);

        Label label = new Label("Government Color");
        label.getStyleClass().add("request-label");
        label.setMaxWidth(500);
        label.setMinWidth(500);
        label.setMaxHeight(50);
        label.setMaxHeight(50);
        label.setTranslateY(50);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(10, 0, 10, 0));

        ArrayList<Colors> colors = new ArrayList<>();
        Colors.getColorsList(colors);
        int x = 150;
        int y = 150;
        int c = 0;
        for (Colors color : colors) {
            if (fakeGame.getColors() != null && !fakeGame.getColors().contains(color.getName())) {
                Rectangle rectangle = new Rectangle(50, 50);
                rectangle.setTranslateX(x);
                rectangle.setTranslateY(y);
                rectangle.setFill(getColor(color.getName()));
                c++;
                c %= 3;
                if (c == 0) {
                    x = 150;
                    y += 80;
                } else {
                    x += 80;
                }
                rectangle.setOnMouseEntered(this::scaleUp);
                rectangle.setOnMouseEntered(this::scaleDown);
                rectangle.setOnMouseClicked(mouseEvent -> {
                    System.out.println("clicked");
                    selectedColor = color.getName();
                    setGovernment();
                });
                colorBlock.getChildren().add(rectangle);
            }
        }
        colorBlock.getChildren().add(label);
        overPane.getChildren().add(colorBlock);
    }

    public void setGovernment() {
        overPane.getChildren().clear();

        Label label = new Label("Castle Place");
        label.getStyleClass().add("request-label");
        label.setMaxWidth(500);
        label.setMinWidth(500);
        label.setMaxHeight(50);
        label.setMaxHeight(50);
        label.setTranslateY(50);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(10, 0, 10, 0));

        governmentBlock = new Pane();
        governmentBlock.setStyle("-fx-background-color: #fff;-fx-background-radius: 10");
        governmentBlock.setMaxWidth(500);
        governmentBlock.setMinWidth(500);
        governmentBlock.setMaxHeight(600);
        governmentBlock.setMinHeight(600);
        governmentBlock.setTranslateX(250);
        governmentBlock.setTranslateY(100);
        governmentBlock.getChildren().add(label);
        previewMap = new LobbyPreviewMap(selectedMap, 80, 130);
        governmentBlock.getChildren().add(previewMap);
        for (int i = 0; i < 8; i++) {
            int x = selectedMap.getDefaultCastles().get(i).getFirst();
            int y = selectedMap.getDefaultCastles().get(i).getSecond();
            castleFlags.get(i).setTranslateX(x - (double) selectedMap.getWidth() / 2);
            castleFlags.get(i).setTranslateY(y - (double) selectedMap.getWidth() / 2);
            castleFlags.get(i).setVisible(true);
            if (fakeGame.getCastleXs().contains(x) && fakeGame.getCastleYs().contains(y)) {
                for (int j = 0; j < 8; j++) {
                    if (fakeGame.getCastleXs().get(j) == x && fakeGame.getCastleYs().get(j) == y) {
                        castleFlags.get(i).setColor(fakeGame.getColors().get(j));
                        castleFlags.get(i).refresh();
                        break;
                    }
                }
            } else {
                MenuFlag flag = castleFlags.get(i);
                flag.setOnMouseClicked(mouseEvent -> {
                    root.getChildren().remove(overPane);
                    overPane.getChildren().clear();
                    Packet packet = new Packet("add player", "Game");
                    packet.addAttribute("id", fakeGame.getGameId());
                    packet.addAttribute("x", x);
                    packet.addAttribute("y", y);
                    packet.addAttribute("color", selectedColor);
                    try {
                        packet.sendPacket();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    setThread();
                    Packet packet1 = new Packet("update player", "Game");
                    packet1.addAttribute("id", fakeGame.getGameId());
                    try {
                        packet1.sendPacket();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            previewMap.getChildren().add(castleFlags.get(i));
        }
        overPane.getChildren().add(governmentBlock);
    }

    public Color getColor(String name) {
        switch (name) {
            case "red" -> {
                return Color.RED;
            }
            case "blue" -> {
                return Color.BLUE;
            }
            case "green" -> {
                return Color.GREEN;
            }
            case "yellow" -> {
                return Color.YELLOW;
            }
            case "skyBlue" -> {
                return Color.SKYBLUE;
            }
            case "orange" -> {
                return Color.ORANGE;
            }
            case "grey" -> {
                return Color.BLACK;
            }
            case "purple" -> {
                return Color.PURPLE;
            }
        }
        return null;
    }

    public void setThread() {
        receiver.start();
    }

    public void setBackground() {
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(getClass().getResource
                        (Paths.BACKGROUND_IMAGES.getPath()).toExternalForm() + "07.png"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        root.setBackground(background);
    }

    public static synchronized void updateDatas() {
        try {
            showUsers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setUsers() throws IOException {
        Label label = new Label("users");
        label.getStyleClass().add("title-label");
        label.setMaxWidth(300);
        label.setMinWidth(300);
        label.setMaxHeight(50);
        label.setMaxHeight(50);
        label.setTranslateY(50);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(10, 0, 10, 0));
        usersList = new VBox();
        showUsers();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(usersList);
        usersList.setPrefHeight(700);
        usersList.setPrefWidth(293);
        scrollPane.setMaxHeight(700);
        scrollPane.setFitToHeight(false);
        scrollPane.setTranslateY(100);
        scrollPane.setTranslateX(0);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leftSide.getChildren().add(label);
        leftSide.getChildren().add(scrollPane);
    }

    private static void showUsers() throws IOException {
        usersList.getChildren().clear();
        if (fakeGame.getAllUsernames().size() == 0) {
            usersList.getChildren().add(new Label("no user in this game exist"));
        }
        for (String username : fakeGame.getAllUsernames()) {
            VBox vBox = new VBox();
            ProfileView profileView = new ProfileView(username, 80, 300);
            vBox.getChildren().add(profileView);
            usersList.getChildren().add(vBox);
        }
        receiver.resumeThread();
    }
}
