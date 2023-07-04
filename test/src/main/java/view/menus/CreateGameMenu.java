package view.menus;

import client.Packet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.DBController;
import controller.GameController;
import controller.MapController;
import controller.gamestructure.GameMaps;
import enumeration.dictionary.Colors;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.FakeGame;
import model.Government;
import model.User;
import model.building.castlebuildings.MainCastle;
import model.game.Game;
import model.game.Map;
import model.menugui.*;
import view.Main;
import view.controllers.ViewController;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class CreateGameMenu extends Application {
    public static Stage stage;
    public static Pane createGamePane;
    private Game game;
    private ArrayList<String> castles;
    private ArrayList<Colors> colors;
    public MenuBox menuBox;
    public MenuChoiceBox mapsField;
    public PreviewMap previewMap;
    public Text governmentTitle;
    public Button addGovernment;
    public static User currentUser;
    public ArrayList<MenuTextField> governmentUsernames = new ArrayList<>();
    public ArrayList<MenuChoiceBox> governmentColors = new ArrayList<>();
    public ArrayList<MenuChoiceBox> castleNumbers = new ArrayList<>();
    public ArrayList<MenuFlag> castleFlags = new ArrayList<>();
    public ArrayList<String> addedGovernments = new ArrayList<>();
    public MenuButton startGame;
    public int governmentNumber;

    @Override
    public void start(Stage stage) throws Exception {
        CreateGameMenu.stage = stage;
        governmentNumber = 0;
        colors = new ArrayList<>();
        for (Colors color : Colors.values()) {
            colors.add(color);
        }
        Pane root = FXMLLoader.load(getClass().getResource("/FXML/createGameMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        DBController.loadAllUsers();
        createGamePane = ViewController.makePaneScreen(stage, root, 1000, -1);


        menuBox = new MenuBox("Create New Game", 365, 100, 800, 700);
        root.getChildren().add(menuBox);

        makeTitleStuff();
        makeGovernmentStuff();
        checkSelectedMap();
        addGovernment.setOnAction(actionEvent -> addGovernment());
        makeBackButton();
        makeStartGameButton();

        menuBox.getChildren().add(addGovernment);
        stage.setTitle("Create New Game");

        stage.show();


    }

    private void makeTitleStuff() throws IOException {
        ArrayList<String> mapNames = MapController.getMapNamesFromServer();
        mapNames.remove("Null Map 400*400");
        mapNames.remove("Null Map 200*200");
        mapsField = new MenuChoiceBox(menuBox, "Map", -90, -250,
                FXCollections.observableArrayList(mapNames), 300);
        menuBox.getChildren().add(mapsField);

        governmentTitle = new Text("Governments");
        governmentTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
        governmentTitle.setTranslateX(-305);
        governmentTitle.setTranslateY(-190);
        menuBox.getChildren().add(governmentTitle);

        addGovernment = new Button("+");
        addGovernment.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        addGovernment.setTranslateX(40);
        addGovernment.setTranslateY(-190);
        addGovernment.setDisable(true);
        addGovernment.getStyleClass().add("addButton");
    }

    private void makeGovernmentStuff() {
        for (int i = 1; i <= 8; i++) {
            MenuTextField governmentUsername = new MenuTextField(menuBox, "username",
                    "Government " + i, -140, 60 * i - 190, 200);
            governmentUsername.setDisable(true);
            menuBox.getChildren().add(governmentUsername);
            governmentUsernames.add(governmentUsername);

            MenuChoiceBox colorField = new MenuChoiceBox(menuBox, "", -10, 60 * i - 190,
                    FXCollections.observableArrayList(colors), 40);
            colorField.setDisable(true);
            menuBox.getChildren().add(colorField);
            governmentColors.add(colorField);
            checkSelectedColor(colorField);

            MenuFlag flag = new MenuFlag("transparent");
            flag.setVisible(false);
            castleFlags.add(flag);

            MenuChoiceBox castleNumber = new MenuChoiceBox(menuBox, "", 40, 60 * i - 190,
                    FXCollections.observableArrayList(new String[1]), 40);
            castleNumber.setDisable(true);
            menuBox.getChildren().add(castleNumber);
            castleNumbers.add(castleNumber);
        }
    }

    private void makeBackButton() {
        MenuFingerBack back = new MenuFingerBack(-450, 350);
        back.setScaleX(0.7);
        back.setScaleY(0.7);
        back.setOnAction(actionEvent -> {
            MapController.map = null;
            game = null;
            GameController.setGame(null);
            MainMenu mainMenu = new MainMenu();
            try {
                mainMenu.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        menuBox.getChildren().add(back);
    }

    private void makeStartGameButton() {
        startGame = new MenuButton("Start Game", menuBox, 240, 290, true);
        startGame.setOnAction(actionEvent -> {
            if (governmentNumber < 3) return;
            GameMenu gameMenu = new GameMenu();

//            Trade trade1 = new Trade("hello", "apple", 10, 100,
//                    GameController.getGame().getGovernments().get(1), GameController.getGame().getGovernments().get(0));
//            Trade trade2 = new Trade("good bye", "cheese", 20, 150,
//                    GameController.getGame().getGovernments().get(1), GameController.getGame().getGovernments().get(0));
//            Trade trade3 = new Trade("how are you?", "wood", 1, 10,
//                    GameController.getGame().getGovernments().get(1), GameController.getGame().getGovernments().get(0));
//            Trade trade4 = new Trade("how are you", "wood", 1, 10,
//                    GameController.getGame().getGovernments().get(1), GameController.getGame().getGovernments().get(0));
//            Trade trade5 = new Trade("how are yo", "wood", 1, 10,
//                    GameController.getGame().getGovernments().get(1), GameController.getGame().getGovernments().get(0));
//            Trade trade6 = new Trade("how are y", "wood", 1, 10,
//                    GameController.getGame().getGovernments().get(1), GameController.getGame().getGovernments().get(0));
//            Trade trade7 = new Trade("how are y", "wood", 1, 10,
//                    GameController.getGame().getGovernments().get(0), GameController.getGame().getGovernments().get(1));
//            Trade trade8 = new Trade("how are y", "wood", 1, 10,
//                    GameController.getGame().getGovernments().get(0), GameController.getGame().getGovernments().get(1));
//            trade1.accept();
//            trade2.accept();
//            trade3.accept();
//            GameController.getGame().getGovernments().get(0).addReceivedTrade(trade1);
//            GameController.getGame().getGovernments().get(0).addReceivedTrade(trade2);
//            GameController.getGame().getGovernments().get(0).addReceivedTrade(trade3);
//            GameController.getGame().getGovernments().get(0).addReceivedTrade(trade4);
//            GameController.getGame().getGovernments().get(0).addReceivedTrade(trade5);
//            GameController.getGame().getGovernments().get(0).addReceivedTrade(trade6);
//            GameController.getGame().getGovernments().get(0).addSentTrade(trade7);
//            GameController.getGame().getGovernments().get(0).addSentTrade(trade8);
//            TradeController.allTrades.put(trade1.getId(), trade1);
//            TradeController.allTrades.put(trade2.getId(), trade2);
//            TradeController.allTrades.put(trade3.getId(), trade3);
//            TradeController.allTrades.put(trade4.getId(), trade4);
//            TradeController.allTrades.put(trade5.getId(), trade5);
//            TradeController.allTrades.put(trade6.getId(), trade6);
//            TradeController.allTrades.put(trade7.getId(), trade7);
//            TradeController.allTrades.put(trade8.getId(), trade8);
            try {
                //////// fake game
                Packet packet = new Packet("create fake game", "Game");
                FakeGame fakeGame = createFakeGame();
                packet.setToken(Main.connection.getToken());
                packet.sendPacket();
                Main.connection.getObjectOutputStream().writeObject(fakeGame);
                GameController.setFakeGame(fakeGame);
                gameMenu.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        menuBox.getChildren().add(startGame);
    }

    private FakeGame createFakeGame() {
        FakeGame fakeGame = new FakeGame();
        for (Government government : game.getGovernments()) {
            fakeGame.addPlayer(government.getUser().getUsername(),
                    government.getColor(), government.getCastleX(), government.getCastleY());
        }
        fakeGame.setAdminUsername(game.getGovernments().get(0).getUser().getUsername());
        fakeGame.setMapName(MapController.map.getName());
        return fakeGame;

    }

    private void checkSelectedMap() {
        mapsField.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.equals("")) {
                governmentUsernames.get(0).setDisable(true);
                castleNumbers.get(0).setDisable(true);
                governmentColors.get(0).setDisable(true);
            } else {
                governmentNumber = 1;
                governmentUsernames.get(0).setDisable(false);
                castleNumbers.get(0).setDisable(false);
                governmentColors.get(0).setDisable(false);
                Packet currentUser = new Packet("current user");
                try {
                    currentUser.setToken(Main.connection.getToken());
                    currentUser.sendPacket();
                    CreateGameMenu.currentUser = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).
                            create().fromJson((String) Packet.receivePacket().
                            getAttribute("user"), User.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                governmentUsernames.get(0).setText(CreateGameMenu.currentUser.getUsername());
                governmentUsernames.get(0).setEditable(false);
                addGovernment.setDisable(false);
                castles = new ArrayList<>();
                Map selectedMap = null;
                try {
                    selectedMap = MapController.getMapFromServer(mapsField.getValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                menuBox.getChildren().remove(previewMap);
                previewMap = new PreviewMap(selectedMap, 230, -120);
                menuBox.getChildren().add(previewMap);
                for (int i = 0; i < 8; i++) {
                    castleFlags.get(i).setTranslateX(selectedMap.getDefaultCastles().get(i).getFirst() - selectedMap.getWidth() / 2);
                    castleFlags.get(i).setTranslateY(selectedMap.getDefaultCastles().get(i).getSecond() - selectedMap.getWidth() / 2);
                    castleFlags.get(i).setVisible(true);
                    previewMap.getChildren().add(castleFlags.get(i));
                    castles.add("Castle " + (i + 1));
                    selectCastle(castleFlags.get(i));
                }
                castleNumbers.get(0).setItems(FXCollections.observableArrayList(castles));
                MapController.map = selectedMap;
                game = new Game(selectedMap);
                Packet newGame = new Packet("new game");
                try {
                    newGame.addAttribute("mapNumber", mapsField.getValue());
                    newGame.addAttribute("game", new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create().toJson(game));
                    newGame.sendPacket();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                GameController.setGame(game);
            }
        });
    }

    private void selectCastle(MenuFlag flag) {
        flag.setOnMouseClicked(mouseEvent -> {
            int x = castleFlags.indexOf(flag);
            if (castles.contains("Castle " + (x + 1)))
                castleNumbers.get(governmentNumber - 1).setValue("Castle " + (x + 1));
        });
    }

    private void checkSelectedColor(MenuChoiceBox colors) {
        colors.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            Colors color = (Colors) newValue;
            colors.setStyle("-fx-background-color: " + color.getRgb());
        });
    }

    private void addGovernment() {
        governmentUsernames.get(governmentNumber - 1).clearErrorOrMessage();
        if (governmentUsernames.get(governmentNumber - 1).getText().equals("")) {
            governmentUsernames.get(governmentNumber - 1).handlingError("username is required!");
            return;
        }
        Packet usernameExistence = new Packet("username existence");
        usernameExistence.addAttribute("username", governmentUsernames.get(governmentNumber - 1).getText());
        try {
            usernameExistence.sendPacket();
            Packet usernameResponse = Packet.receivePacket();
            if (usernameResponse.getAttribute("error") != null) {
                governmentUsernames.get(governmentNumber - 1).handlingError(
                        (String) usernameResponse.getAttribute("error"));
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (addedGovernments.contains(governmentUsernames.get(governmentNumber - 1).getText())) {
            governmentUsernames.get(governmentNumber - 1).handlingError("this government has been added!");
            return;
        }
        if (governmentColors.get(governmentNumber - 1).getValue() == null) {
            governmentUsernames.get(governmentNumber - 1).handlingError("color is required!");
            return;
        }
        if (castleNumbers.get(governmentNumber - 1).getValue() == null) {
            governmentUsernames.get(governmentNumber - 1).handlingError("castle number is required!");
            return;
        }

        int castleNumber = Integer.parseInt(((String) castleNumbers.get(governmentNumber - 1).getValue()).substring(7, 8));
        int x = game.getMap().getDefaultCastles().get(castleNumber - 1).getFirst();
        int y = game.getMap().getDefaultCastles().get(castleNumber - 1).getSecond();
        castles.remove("Castle " + castleNumber);

        Colors color = (Colors) governmentColors.get(governmentNumber - 1).getValue();
        colors.remove(color);
        User user = null;
        Packet getUser = new Packet("get user");
        try {
            getUser.addAttribute("username", governmentUsernames.get(governmentNumber - 1).getText());
            getUser.sendPacket();
            user = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).
                    create().fromJson((String) Packet.receivePacket().getAttribute("user"), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Government government = new Government(user, x, y, color);
        government.addAmountToProperties("wood", "resource", 1000);
        government.addAmountToProperties("stone", "resource", 500);
        government.addAmountToProperties("iron", "resource", 500);
        government.addAmountToProperties("bread", "food", 60);
        government.setGold(4000);
        Packet addGovernmentPacket = new Packet("add government");
        try {
            addGovernmentPacket.addAttribute("government", new Gson().toJson(government));
            addGovernmentPacket.addAttribute("x", Integer.toString(x));
            addGovernmentPacket.addAttribute("y", Integer.toString(y));
            addGovernmentPacket.sendPacket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        game.addGovernment(government);

        if (governmentNumber == 1) mapsField.setDisable(true);
        governmentUsernames.get(governmentNumber - 1).setEditable(false);
        governmentColors.get(governmentNumber - 1).setDisable(true);
        castleNumbers.get(governmentNumber - 1).setDisable(true);
        castleFlags.get(castleNumber - 1).setColor(color.getName());
        castleFlags.get(castleNumber - 1).refresh();
        addedGovernments.add(government.getUser().getUsername());
        if (governmentNumber == 7) addGovernment.setDisable(true);
        else {
            governmentNumber++;
            governmentUsernames.get(governmentNumber - 1).setDisable(false);
            governmentColors.get(governmentNumber - 1).setDisable(false);
            castleNumbers.get(governmentNumber - 1).setDisable(false);
            governmentColors.get(governmentNumber - 1).setItems(FXCollections.observableArrayList(colors));
            castleNumbers.get(governmentNumber - 1).setItems(FXCollections.observableArrayList(castles));
        }
    }
}