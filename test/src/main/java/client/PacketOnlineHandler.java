package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.BuildingController;
import controller.GameController;
import controller.GovernmentController;
import controller.MapController;
import controller.human.HumanController;
import enumeration.Textures;
import enumeration.dictionary.Colors;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
import javafx.application.Platform;
import model.FakeGame;
import model.Government;
import model.User;
import model.building.Building;
import model.building.producerbuildings.Barrack;
import model.building.producerbuildings.WeaponProducer;
import model.chat.Message;
import model.game.Game;
import model.game.Map;
import model.human.Human;
import model.human.military.Military;
import model.menugui.game.GameMap;
import view.Main;
import view.menus.GameMenu;
import view.menus.chat.ChatViewController;
import view.menus.Lobby;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
                Packet packet1 = new Packet("get map", "Game");
                packet1.addAttribute("map name", fakeGame.getMapName());
                packet1.sendPacket();
                Map map = (Map) Main.connection.getObjectInputStream().readObject();
                Game game = new Game(map);
                MapController.map = map;
                GameController.setGame(game);

                System.out.println("castle " + fakeGame.getCastleXs().size());
                System.out.println("users " + fakeGame.getAllUsernames().size());
                System.out.println("colors " + fakeGame.getColors().size());


                for (int i = 0; i != fakeGame.getAllUsernames().size(); i++) {
                    Government government = new Government(null, fakeGame.getCastleXs().get(i),
                            fakeGame.getCastleYs().get(i), Colors.getColor(fakeGame.getColors().get(i)));
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
                Lobby.receiver.stopThread();
                Platform.runLater(Lobby::createGame);
            }
            case "image bytes" -> {
                convertBytesToImage();
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
            case "change tax rate" -> {
                changeTaxRate();
            }
            case "send lord name" -> {
                sendLordName();
            }
            case "change food rate" -> {
                changeFoodRate();
            }
            case "change fear rate" -> {
                changeFearRate();
            }
            case "update fake game" -> {
                Lobby.fakeGame = (FakeGame) Main.connection.getObjectInputStream().readObject();
                Platform.runLater(Lobby::updateDatas);
                Lobby.receiver.pauseThread();
            }
            case "leave game" -> Lobby.receiver.stopThread();
            case "chat data" -> {
                chatData();
            }
            case "public messages" -> {
                getPublicMessages();
            }
            case "private messages" -> {
                getPrivateMessages();
            }
            case "new room id" -> {
                getNewRoomId();
            }
            case "users for room" -> {
                getUsersForRoomList();
            }
            case "new room" -> {
                getNewRoom();
            }
            case "new private" -> {
                getNewPrivate();
            }
            case "room messages" -> {
                getRoomMessages();
            }
            case "* public message" -> {
                getNewPublicMessage();
            }
            case "* private message" -> {
                getNewPrivateMessage();
            }
            case "* room message" -> {
                getNewRoomMessage();
            }
            case "* deleted message" -> {
                getDeletion();
            }
            case "* edited message" -> {
                getEditedMessage();
            }
            case "* reacted message" -> {
                getReactedMessage();
            }
            case "* seen message" -> {
                getSeenMessage();
            }
        }
    }

    private void changeFearRate() {
        double fearRate = (Double) packet.getAttribute("fearRate");
        String color = (String) packet.getAttribute("color");
        GovernmentController.changeFearRateOnline((int) fearRate, getGovernmentByColor(color));
    }

    private void changeFoodRate() {
        double foodRate = (Double) packet.getAttribute("foodRate");
        String color = (String) packet.getAttribute("color");
        GovernmentController.changeFoodRateOnline((int) foodRate, getGovernmentByColor(color));
    }

    private void sendLordName() {
        GovernmentController.setNickname((String) packet.getAttribute("name"));
    }

    private void changeTaxRate() {
        double taxRate = (Double) packet.getAttribute("taxRate");
        String color = (String) packet.getAttribute("color");
        GovernmentController.changeTaxRateOnline((int) taxRate, getGovernmentByColor(color));
    }


    private void changeWeapon() {
        double tileX = (Double) packet.getAttribute("tileX");
        double tileY = (Double) packet.getAttribute("tileY");
        String color = (String) packet.getAttribute("government");
        String weapon = (String) packet.getAttribute("weapon");
        Building building = GameMap.getGameTile((int) tileX, (int) tileY).getTile().getBuilding();
        ((WeaponProducer) building).changeItemName(weapon);
    }

    private void changeGateState() {
        double tileX = (Double) packet.getAttribute("tileX");
        double tileY = (Double) packet.getAttribute("tileY");
        String color = (String) packet.getAttribute("government");
        String state = (String) packet.getAttribute("state");
        BuildingController.changeGateStateOnline(tileX, tileY, getGovernmentByColor(color), state);
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
        Human human = GameController.getGame().humans.get((int) id);
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
        Human human = GameController.getGame().humans.get((int) id);
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
        double tileX = (Double) packet.getAttribute("tileX");
        double tileY = (Double) packet.getAttribute("tileY");
        String color = (String) packet.getAttribute("government");
        BuildingController.repairOnline((int) tileX, (int) tileY, getGovernmentByColor(color));
    }

    private void dropArabianMercenary() {
        double tileX = (Double) packet.getAttribute("x");
        double tileY = (Double) packet.getAttribute("y");
        int id = Integer.parseInt(packet.getAttribute("id").toString());
        String name = (String) packet.getAttribute("name");
        String color = (String) packet.getAttribute("color");
        Barrack.makeUnitThroughNetwork((int) tileX, (int) tileY, name, getGovernmentByColor(color), id);
    }

    private Government getGovernmentByColor(String color) {
        for (Government government : GameController.getGame().getGovernments()) {
            if (government.getColor().equals(color))
                return government;
        }
        return null;
    }

    private void dropTree() {
        double tileX = (Double) packet.getAttribute("tileX");
        double tileY = (Double) packet.getAttribute("tileY");
        String tree = (String) packet.getAttribute("tree");
        MapController.dropTree((int) tileX, (int) tileY, Trees.getTreeByName(tree));
        Platform.runLater(() -> {
            GameMap.getGameTile((int) tileX, (int) tileY).refreshTile();
        });
    }


    private void dropRock() {
        double tileX = (Double) packet.getAttribute("tileX");
        double tileY = (Double) packet.getAttribute("tileY");
        String rockDirection = (String) packet.getAttribute("rock");
        MapController.dropRock((int) tileX, (int) tileY, RockDirections.getRockByDirection(rockDirection));
        Platform.runLater(() -> {
            GameMap.getGameTile((int) tileX, (int) tileY).refreshTile();
        });
    }


    private void setTexture() {
        double tileX = (Double) packet.getAttribute("tileX");
        double tileY = (Double) packet.getAttribute("tileY");
        String textures = (String) packet.getAttribute("texture");
        MapController.setTexture((int) tileX, (int) tileY, Textures.getTextureByName(textures));
        Platform.runLater(() -> {
            GameMap.getGameTile((int) tileX, (int) tileY).refreshTile();
        });
    }

    private void dropBuilding() {
        double tileX = (Double) packet.getAttribute("tileX");
        double tileY = (Double) packet.getAttribute("tileY");
        String buildingName = (String) packet.getAttribute("droppedBuildingName");
        String side = (String) packet.getAttribute("side");
        String color = (String) packet.getAttribute("color");
        Government supposedGovernment = null;
        for (Government government : GameController.getGame().getGovernments()) {
            if (government.getColor().equals(color)) {
                supposedGovernment = government;
            }
        }
        GameController.dropBuilding((int) tileX, (int) tileY, buildingName, side, supposedGovernment);
        Platform.runLater(() -> {
            GameMap.getGameTile((int) tileX, (int) tileY).refreshTile();
        });
    }

    private void convertBytesToImage() {
        try {
            byte[] bytes = (byte[]) packet.getAttribute("bytes");
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(input);

            File outputFile = new File("files/img/temporary.png");
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chatData() {
        ChatViewController.currentUser = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).
                create().fromJson((String) packet.getAttribute("currentUser"), User.class);
        ChatViewController.users = new Gson().fromJson((String) packet.getAttribute("otherUsers"), ArrayList.class);
        ChatViewController.rooms = new Gson().fromJson((String) packet.getAttribute("currentUserRooms"), LinkedHashMap.class);
        ChatViewController.privates = new Gson().fromJson((String) packet.getAttribute("currentUserPrivates"), LinkedHashMap.class);
    }

    public void getPublicMessages() {
        ChatViewController.messages = new ArrayList<>();
        for (int i = 0; i < packet.getAttributes().size(); i++) {
            String message = (String) packet.getAttribute("message" + i);
            ChatViewController.messages.add(new Gson().fromJson((String) message, Message.class));
        }
        Platform.runLater(() -> {
            ChatViewController.addListBox(2);
            ChatViewController.showMessages(ChatViewController.messages);
        });
    }

    public void getPrivateMessages() {
        String username = (String) packet.getAttribute("username");
        if (ChatViewController.privates.get(username) == null)
            ChatViewController.privates.put(username, (String) packet.getAttribute("roomId"));
        ChatViewController.messages = new ArrayList<>();
        for (int i = 0; i < packet.getAttributes().size(); i++) {
            String message = (String) packet.getAttribute("message" + i);
            ChatViewController.messages.add(new Gson().fromJson((String) message, Message.class));
        }
        ChatViewController.currentRoomName = username;
        Platform.runLater(() -> {
            ChatViewController.list.getChildren().clear();
            ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.searchBar);
            ChatViewController.addListBox(2);
            ChatViewController.showMessages(ChatViewController.messages);
        });
    }

    public void getNewRoomId() {
        ChatViewController.rooms.put(ChatViewController.newRoomBox.getText(), (String) packet.getAttribute("roomId"));
        Platform.runLater(() -> {
            try {
                ChatViewController.list.getChildren().clear();
                ChatViewController.addListBox(0);
                ChatViewController.showListOfRooms(ChatViewController.rooms);
                ChatViewController.newRoomBox.setText("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void getNewRoom() {
        if (GameMenu.chatMenu != null) {
            if (ChatViewController.rooms == null) ChatViewController.rooms = new LinkedHashMap<>();
            ChatViewController.rooms.put((String) packet.getAttribute("roomName"),
                    (String) packet.getAttribute("roomId"));
        }
    }

    public void getNewPrivate() {
        if (GameMenu.chatMenu != null) {
            if (ChatViewController.privates == null) ChatViewController.privates = new LinkedHashMap<>();
            ChatViewController.privates.put((String) packet.getAttribute("username"),
                    (String) packet.getAttribute("roomId"));
        }
    }

    public void getUsersForRoomList() {
        ChatViewController.usersForRoom = new ArrayList<>();
        ChatViewController.usersForRoom = new Gson().fromJson((String) packet.getAttribute("list"), ArrayList.class);
        Platform.runLater(() -> {
            ChatViewController.addListBox(1);
            ChatViewController.showListOfUsersForRoom(ChatViewController.usersForRoom);
        });
    }

    public void getRoomMessages() {
        ChatViewController.messages = new ArrayList<>();
        for (int i = 0; i < packet.getAttributes().size(); i++) {
            String message = (String) packet.getAttribute("message" + i);
            ChatViewController.messages.add(new Gson().fromJson((String) message, Message.class));
        }
        ChatViewController.currentRoomName = (String) packet.getAttribute("roomName");
        Platform.runLater(() -> {
            ChatViewController.list.getChildren().clear();
            ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.searchBar);
            ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.newRoomBox);
            ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.addRoom);
            ChatViewController.addNewMember();
            ChatViewController.addListBox(3);
            ChatViewController.showMessages(ChatViewController.messages);
        });
    }

    public void getNewPublicMessage() {
        if (GameMenu.chatMenu != null && ChatViewController.currentTabName.equals("public")) {
            ChatViewController.messages = new ArrayList<>();
            for (int i = 0; i < packet.getAttributes().size(); i++) {
                String message = (String) packet.getAttribute("message" + i);
                ChatViewController.messages.add(new Gson().fromJson((String) message, Message.class));
            }

            try {
                Packet seen = new Packet("new seen");
                seen.setToken(Main.connection.getToken());
                Message message = null;
                for (int i = 0; ; i++) {
                    message = ChatViewController.messages.get(ChatViewController.messages.size() - 1 - i);
                    if (message == null) continue;
                    seen.addAttribute("messageId", message.getId());
                    break;
                }
                if (!message.getSender().equals(ChatViewController.currentUser.getUsername()))
                    seen.sendPacket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Platform.runLater(() -> {
                ChatViewController.typeBox.setText("");
                ChatViewController.list.getChildren().clear();
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.scrollPane);
                ChatViewController.addListBox(2);
                ChatViewController.showMessages(ChatViewController.messages);
            });
        }
    }

    public void getNewPrivateMessage() {
        if (GameMenu.chatMenu != null && ChatViewController.currentTabName.equals("private") &&
                ChatViewController.currentRoomName.equals(packet.getAttribute("username"))) {
            String username = (String) packet.getAttribute("username");
            ChatViewController.messages = new ArrayList<>();
            for (int i = 0; i < packet.getAttributes().size(); i++) {
                String message = (String) packet.getAttribute("message" + i);
                ChatViewController.messages.add(new Gson().fromJson((String) message, Message.class));
            }
            ChatViewController.currentRoomName = username;

            try {
                Packet seen = new Packet("new seen");
                seen.setToken(Main.connection.getToken());
                Message message = null;
                for (int i = 0; ; i++) {
                    message = ChatViewController.messages.get(ChatViewController.messages.size() - 1 - i);
                    if (message == null) continue;
                    seen.addAttribute("messageId", message.getId());
                    break;
                }
                if (!message.getSender().equals(ChatViewController.currentUser.getUsername()))
                    seen.sendPacket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Platform.runLater(() -> {
                ChatViewController.typeBox.setText("");
                ChatViewController.list.getChildren().clear();
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.searchBar);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.scrollPane);
                ChatViewController.addListBox(2);
                ChatViewController.showMessages(ChatViewController.messages);
            });
        }
    }

    public void getNewRoomMessage() {
        if (GameMenu.chatMenu != null && ChatViewController.currentTabName.equals("room") &&
                ChatViewController.currentRoomName.equals(packet.getAttribute("roomName"))) {
            ChatViewController.messages = new ArrayList<>();
            for (int i = 0; i < packet.getAttributes().size(); i++) {
                String message = (String) packet.getAttribute("message" + i);
                ChatViewController.messages.add(new Gson().fromJson((String) message, Message.class));
            }
            ChatViewController.currentRoomName = (String) packet.getAttribute("roomName");

            try {
                Packet seen = new Packet("new seen");
                seen.setToken(Main.connection.getToken());
                Message message = null;
                for (int i = 0; ; i++) {
                    message = ChatViewController.messages.get(ChatViewController.messages.size() - 1 - i);
                    if (message == null) continue;
                    seen.addAttribute("messageId", message.getId());
                    break;
                }
                if (!message.getSender().equals(ChatViewController.currentUser.getUsername()))
                    seen.sendPacket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Platform.runLater(() -> {
                ChatViewController.typeBox.setText("");
                ChatViewController.list.getChildren().clear();
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.searchBar);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.newRoomBox);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.addRoom);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.scrollPane);
                ChatViewController.addNewMember();
                ChatViewController.addListBox(3);
                ChatViewController.showMessages(ChatViewController.messages);
            });
        }
    }

    public void getDeletion() {
        boolean validation = false;
        try {
            if (packet.getAttribute("roomName").equals("public"))
                validation = ChatViewController.currentTabName.equals("public");
            else validation = !ChatViewController.currentTabName.equals("public") &&
                    ChatViewController.currentRoomName.equals((String) packet.getAttribute("roomName"));
        } catch (NullPointerException e) {
            System.out.println("null");
        }
        if (GameMenu.chatMenu != null && validation) {
            ChatViewController.messages = new ArrayList<>();
            for (int i = 0; i < packet.getAttributes().size(); i++) {
                String message = (String) packet.getAttribute("message" + i);
                ChatViewController.messages.add(new Gson().fromJson((String) message, Message.class));
            }
            ChatViewController.currentRoomName = (String) packet.getAttribute("roomName");
            Platform.runLater(() -> {
                ChatViewController.typeBox.setText("");
                ChatViewController.list.getChildren().clear();
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.searchBar);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.newRoomBox);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.addRoom);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.scrollPane);
                if (ChatViewController.currentTabName.equals("room")) {
                    ChatViewController.addNewMember();
                    ChatViewController.addListBox(3);
                } else ChatViewController.addListBox(2);
                ChatViewController.showMessages(ChatViewController.messages);
                ChatViewController.chat.getChildren().remove(ChatViewController.chat.getOptionsPart());
                ChatViewController.chat.getChildren().remove(ChatViewController.chat.getReactPart());
            });
        }
    }

    public void getEditedMessage() {
        boolean validation = false;
        try {
            if (packet.getAttribute("roomName").equals("public"))
                validation = ChatViewController.currentTabName.equals("public");
            else validation = !ChatViewController.currentTabName.equals("public") &&
                    ChatViewController.currentRoomName.equals((String) packet.getAttribute("roomName"));
        } catch (NullPointerException e) {
            System.out.println("null");
        }
        if (GameMenu.chatMenu != null && validation) {
            ChatViewController.messages = new ArrayList<>();
            for (int i = 0; i < packet.getAttributes().size(); i++) {
                String message = (String) packet.getAttribute("message" + i);
                ChatViewController.messages.add(new Gson().fromJson((String) message, Message.class));
            }
            ChatViewController.currentRoomName = (String) packet.getAttribute("roomName");
            Platform.runLater(() -> {
                ChatViewController.typeBox.setText("");
                ChatViewController.chat.getChildren().remove(ChatViewController.confirmEdit);
                ChatViewController.list.getChildren().clear();
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.searchBar);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.newRoomBox);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.addRoom);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.scrollPane);
                if (ChatViewController.currentTabName.equals("room")) {
                    ChatViewController.addNewMember();
                    ChatViewController.addListBox(3);
                } else ChatViewController.addListBox(2);
                ChatViewController.showMessages(ChatViewController.messages);
                ChatViewController.chat.getChildren().remove(ChatViewController.chat.getOptionsPart());
                ChatViewController.chat.getChildren().remove(ChatViewController.chat.getReactPart());
                ChatViewController.editingMessageBox.getStyleClass().remove("edit-mode");
                ChatViewController.editingMessageBox = null;
            });
        }
    }

    public void getReactedMessage() {
        boolean validation = false;
        try {
            if (packet.getAttribute("roomName").equals("public"))
                validation = ChatViewController.currentTabName.equals("public");
            else validation = !ChatViewController.currentTabName.equals("public") &&
                    ChatViewController.currentRoomName.equals((String) packet.getAttribute("roomName"));
        } catch (NullPointerException e) {
            System.out.println("null");
        }
        if (GameMenu.chatMenu != null && validation) {
            ChatViewController.messages = new ArrayList<>();
            for (int i = 0; i < packet.getAttributes().size(); i++) {
                String message = (String) packet.getAttribute("message" + i);
                ChatViewController.messages.add(new Gson().fromJson((String) message, Message.class));
            }
            ChatViewController.currentRoomName = (String) packet.getAttribute("roomName");
            Platform.runLater(() -> {
                ChatViewController.typeBox.setText("");
                ChatViewController.list.getChildren().clear();
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.searchBar);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.newRoomBox);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.addRoom);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.scrollPane);
                if (ChatViewController.currentTabName.equals("room")) {
                    ChatViewController.addNewMember();
                    ChatViewController.addListBox(3);
                } else ChatViewController.addListBox(2);
                ChatViewController.showMessages(ChatViewController.messages);
                ChatViewController.chat.getChildren().remove(ChatViewController.chat.getOptionsPart());
                ChatViewController.chat.getChildren().remove(ChatViewController.chat.getReactPart());
            });
        }
    }

    public void getSeenMessage() {
        boolean validation = false;
        try {
            if (packet.getAttribute("roomName").equals("public"))
                validation = ChatViewController.currentTabName.equals("public");
            else validation = !ChatViewController.currentTabName.equals("public") &&
                    ChatViewController.currentRoomName.equals((String) packet.getAttribute("roomName"));
        } catch (NullPointerException e) {
            System.out.println("null");
        }
        if (GameMenu.chatMenu != null && validation) {
            ChatViewController.messages = new ArrayList<>();
            for (int i = 0; i < packet.getAttributes().size(); i++) {
                String message = (String) packet.getAttribute("message" + i);
                ChatViewController.messages.add(new Gson().fromJson((String) message, Message.class));
            }
            ChatViewController.currentRoomName = (String) packet.getAttribute("roomName");
            Platform.runLater(() -> {
                ChatViewController.typeBox.setText("");
                ChatViewController.list.getChildren().clear();
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.searchBar);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.newRoomBox);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.addRoom);
                ChatViewController.chat.getChatPart().getChildren().remove(ChatViewController.scrollPane);
                if (ChatViewController.currentTabName.equals("room")) {
                    ChatViewController.addNewMember();
                    ChatViewController.addListBox(3);
                } else ChatViewController.addListBox(2);
                ChatViewController.showMessages(ChatViewController.messages);
                ChatViewController.chat.getChildren().remove(ChatViewController.chat.getOptionsPart());
                ChatViewController.chat.getChildren().remove(ChatViewController.chat.getReactPart());
            });
        }
    }
}
