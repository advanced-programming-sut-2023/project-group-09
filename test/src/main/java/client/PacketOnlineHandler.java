package client;

import controller.GameController;
import controller.GovernmentController;
import controller.MapController;
import controller.gamestructure.GameMaps;
import enumeration.Textures;
import enumeration.dictionary.Colors;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
import javafx.application.Platform;
import model.FakeGame;
import model.Government;
import model.building.castlebuildings.MainCastle;
import model.game.Game;
import model.game.Map;
import model.menugui.game.GameMap;
import view.Main;
import view.menus.CreateGameMenu;
import view.menus.GameMenu;
import view.menus.MainMenu;

import java.util.ArrayList;

public class PacketOnlineHandler {
    private Packet packet;
    private String token;

    public PacketOnlineHandler(Packet packet) {
        this.packet = packet;
    }

    public PacketOnlineHandler(Packet packet, String token) {
        this.packet = packet;
        this.token = token;
    }

    public void handle() throws Exception {
        switch (packet.command) {
            case "create fake game" -> {
                String username = (String)packet.getAttribute("username");
                FakeGame fakeGame = (FakeGame) Main.connection.getObjectInputStream().readObject();
                GameMaps.createMap1();
                Game game = new Game(GameMaps.largeMaps.get(0));
                MapController.map = GameMaps.largeMaps.get(0);
                GameController.setGame(game);
                for (int i = 0; i != fakeGame.getAllUsernames().size(); i++) {
                    Government government = new Government(null, fakeGame.getCastleXs().get(i),
                            fakeGame.getCastleYs().get(i), Colors.getColor(fakeGame.getColors().get(i)));
                    MapController.dropBuilding(government.getCastleX(), government.getCastleY(), "mainCastle", government);
                    MainCastle mainCastle = (MainCastle) GameController.getGame().getMap()
                            .getTile(government.getCastleX(), government.getCastleY()).getBuilding();
                    mainCastle.setGovernment(government);
                    government.setMainCastle(mainCastle);
                    government.addAmountToProperties("wood", "resource", 1000);
                    government.addAmountToProperties("stone", "resource", 500);
                    government.addAmountToProperties("iron", "resource", 500);
                    government.addAmountToProperties("bread", "food", 60);
                    government.setGold(4000);
                    if (fakeGame.getAllUsernames().get(i).equals(username)) {
                        System.out.println("test");
                        game.getGovernments().add(0 , government);
                        GameController.getGame().setCurrentGovernment(government);
                        GovernmentController.setCurrentGovernment(government);
                    } else {
                        game.addGovernment(government);
                    }
                }
                GameController.setFakeGame(fakeGame);
            }
            case "drop building" -> {
                dropBuilding();
            }
            case "set texture" -> {
                setTexture();
            }
            case "drop rock" -> {
                dropRock();
            }
            case "drop tree" -> {
                dropTree();
            }
        }
    }

    private void dropTree() {
        double tileX = (Double)packet.getAttribute("tileX");
        double tileY = (Double)packet.getAttribute("tileY");
        String tree = (String)packet.getAttribute("tree");
        MapController.dropTree((int)tileX , (int)tileY , Trees.getTreeByName(tree));
        Platform.runLater(() -> {
            GameMap.getGameTile((int)tileX , (int)tileY).refreshTile();
        });
    }


    private void dropRock() {
        double tileX = (Double)packet.getAttribute("tileX");
        double tileY = (Double)packet.getAttribute("tileY");
        String rockDirection = (String)packet.getAttribute("rock");
        MapController.dropRock((int)tileX , (int)tileY , RockDirections.getRockByDirection(rockDirection));
        Platform.runLater(() -> {
            GameMap.getGameTile((int)tileX , (int)tileY).refreshTile();
        });
    }


    private void setTexture() {
        double tileX = (Double)packet.getAttribute("tileX");
        double tileY = (Double)packet.getAttribute("tileY");
        String textures = (String) packet.getAttribute("texture");
        MapController.setTexture((int)tileX , (int)tileY , Textures.getTextureByName(textures));
        Platform.runLater(() -> {
            GameMap.getGameTile((int)tileX , (int)tileY).refreshTile();
        });
    }

    private void dropBuilding() {
        double tileX = (Double)packet.getAttribute("tileX");
        double tileY = (Double)packet.getAttribute("tileY");
        String buildingName = (String)packet.getAttribute("droppedBuildingName");
        String side = (String) packet.getAttribute("side");
        String color = (String) packet.getAttribute("color");
        Government supposedGovernment = null;
        for (Government government : GameController.getGame().getGovernments()) {
            if (government.getColor().equals(color)) {
                supposedGovernment = government;
            }
        }
        GameController.dropBuilding((int)tileX , (int)tileY , buildingName , side ,supposedGovernment);
        Platform.runLater(() -> {
            GameMap.getGameTile((int) tileX, (int) tileY).refreshTile();
        });
    }


}
