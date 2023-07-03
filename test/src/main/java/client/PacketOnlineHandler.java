package client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.BuildingController;
import controller.GameController;
import controller.GovernmentController;
import controller.MapController;
import controller.gamestructure.GameMaps;
import controller.human.HumanController;
import enumeration.Textures;
import enumeration.dictionary.Colors;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
import javafx.application.Platform;
import model.FakeGame;
import model.Government;
import model.building.Building;
import model.building.castlebuildings.MainCastle;
import model.building.producerbuildings.Barrack;
import model.building.producerbuildings.WeaponProducer;
import model.game.Game;
import model.human.Human;
import model.human.military.Military;
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
                String username = (String) packet.getAttribute("username");
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
                        game.getGovernments().add(0, government);
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
            case "drop arabian mercenary" -> {
                dropArabianMercenary();
            }
            case "repair" -> {
                repair();
            }
            case "change gate state" -> {
                changeGateState();
            }
            case "move units" -> {
                moveUnits();
            }
            case "attack enemy" -> {
                attackEnemies();
            }
            case "patrol unit" -> {
                patrolUnits();
            }
            case "stop" -> {
                stopUnits();
            }
            case "attack building" -> {
                attackBuilding();
            }
            case "air attack enemy" -> {
                airAttackEnemy();
            }
            case "air attack building" -> {
                airAttackBuilding();
            }
            case "change weapon" -> {
                changeWeapon();
            }
        }
    }

    private void changeWeapon() {
        double tileX = (Double)packet.getAttribute("tileX");
        double tileY = (Double)packet.getAttribute("tileY");
        String color = (String)packet.getAttribute("government");
        String weapon = (String)packet.getAttribute("weapon");
        Building building = GameMap.getGameTile((int)tileX , (int)tileY).getTile().getBuilding();
        ((WeaponProducer)building).changeItemName(weapon);
    }

    private void changeGateState() {
        double tileX = (Double)packet.getAttribute("tileX");
        double tileY = (Double)packet.getAttribute("tileY");
        String color = (String)packet.getAttribute("government");
        String state = (String)packet.getAttribute("state");
        BuildingController.changeGateStateOnline(tileX , tileY , getGovernmentByColor(color) , state);
    }

    private void moveUnits() {
        double x = (double) packet.getAttribute("x");
        double y = (double) packet.getAttribute("y");
        if (packet.attributes.get("ids") == null) return;
        String idsString = packet.attributes.get("ids").toString();
        ArrayList<Integer> ids = new Gson().fromJson(idsString, new TypeToken<ArrayList<Integer>>() {
        }.getType());
        for (int id : ids) {
            Human human = GameController.getGame().humans.get(id);
            if (human instanceof Military military) {
                HumanController.militaries.clear();
                HumanController.militaries.add(military);
                GameController.moveUnit((int) x, (int) y);
            }
        }
    }

    private void attackEnemies() {
        double x = (double) packet.getAttribute("x");
        double y = (double) packet.getAttribute("y");
        double id = (double) packet.getAttribute("id");
        Human human = GameController.getGame().humans.get((int)id);
        if (human instanceof Military military) {
            HumanController.militaries.clear();
            HumanController.militaries.add(military);
            GameController.attackEnemy((int) x, (int) y);
        }
    }


    private void attackBuilding() {
        double x = (double) packet.getAttribute("x");
        double y = (double) packet.getAttribute("y");
        double id = (double) packet.getAttribute("id");
        Human human = GameController.getGame().humans.get((int)id);
        if (human instanceof Military military) {
            HumanController.militaries.clear();
            HumanController.militaries.add(military);
            GameController.attackBuilding((int) x, (int) y);
        }
    }


    private void airAttackEnemy() {
        double x = (double) packet.getAttribute("x");
        double y = (double) packet.getAttribute("y");
        double id = (double) packet.getAttribute("id");
        Human human = GameController.getGame().humans.get(id);
        if (human instanceof Military military) {
            HumanController.militaries.clear();
            HumanController.militaries.add(military);
            GameController.airAttack((int) x, (int) y);
        }
    }

    private void airAttackBuilding() {
        double x = (double) packet.getAttribute("x");
        double y = (double) packet.getAttribute("y");
        double id = (double) packet.getAttribute("id");
        Human human = GameController.getGame().humans.get(id);
        if (human instanceof Military military) {
            HumanController.militaries.clear();
            HumanController.militaries.add(military);
            GameController.airAttackBuilding((int) x, (int) y);
        }
    }

    private void patrolUnits() {
        double x = (double) packet.getAttribute("x");
        double y = (double) packet.getAttribute("y");
        if (packet.attributes.get("ids") == null) return;
        String idsString = packet.attributes.get("ids").toString();
        ArrayList<Integer> ids = new Gson().fromJson(idsString, new TypeToken<ArrayList<Integer>>() {
        }.getType());
        for (int id : ids) {
            Human human = GameController.getGame().humans.get(id);
            if (human instanceof Military military) {
                HumanController.militaries.clear();
                HumanController.militaries.add(military);
                GameController.patrolUnit((int) x, (int) y);
            }
        }
    }

    private void stopUnits() {
        if (packet.attributes.get("ids") == null) return;
        String idsString = packet.attributes.get("ids").toString();
        ArrayList<Integer> ids = new Gson().fromJson(idsString, new TypeToken<ArrayList<Integer>>() {
        }.getType());
        for (int id : ids) {
            Human human = GameController.getGame().humans.get(id);
            if (human instanceof Military military) {
                military.getMove().stopMove();
            }
        }
    }

    private void repair() {
        double tileX = (Double)packet.getAttribute("tileX");
        double tileY = (Double)packet.getAttribute("tileY");
        String color = (String)packet.getAttribute("government");
        BuildingController.repairOnline((int)tileX , (int)tileY , getGovernmentByColor(color));
    }

    private void dropArabianMercenary() {
        double tileX = (Double)packet.getAttribute("x");
        double tileY = (Double)packet.getAttribute("y");
        int id = Integer.parseInt(packet.getAttribute("id").toString());
        String name = (String)packet.getAttribute("name");
        String color = (String)packet.getAttribute("color");
        Barrack.makeUnitThroughNetwork((int)tileX , (int)tileY , name , getGovernmentByColor(color),id);
    }

    private Government getGovernmentByColor(String color) {
        for (Government government : GameController.getGame().getGovernments()) {
            if (government.getColor().equals(color))
                return government;
        }
        return null;
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
