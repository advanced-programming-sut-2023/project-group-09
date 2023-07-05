package server.handlers;

import controller.*;
import controller.gamestructure.GameMaps;
import model.FakeGame;
import model.User;
import server.Connection;
import server.Packet;
import server.PacketHandler;

import javax.print.attribute.UnmodifiableSetException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class GameHandler {

    Connection connection;
    Packet packet;

    public void handle(Packet packet, Connection connection) throws IOException, ClassNotFoundException {
        this.connection = connection;
        this.packet = packet;
        //give commands to suitable methods
        switch (packet.command) {
            case "create fake game" -> {
                createFakeGame();
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
            case "get lord name" -> {
                getLordName();
            }
            case "change food rate" -> {
                changeFoodRate();
            }
            case "change fear rate" -> {
                changeFearRate();
            }
            case "end game" -> {
                FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
                for (String username : fakeGame.getAllUsernames()) {
                    User user = Application.getUserByUsername(username);
                    GameController.getFakeGames().remove(user);
                }
                for (String username : fakeGame.getSpectatorsUsernames()) {
                    User user = Application.getUserByUsername(username);
                    GameController.getFakeGames().remove(user);
                }
                GameController.getAllFakeGames().remove(fakeGame);
                GameController.fakeGameHashMap.remove(fakeGame.getGameId());
                System.out.println("wow your game is ended!");
                for (String username : fakeGame.getAllUsernames()) {
                    System.out.println(username);
                }
                for (String color : fakeGame.getColors()) {
                    System.out.println(color);
                }
            }
            case "add score" -> {
                FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
                String color = (String) packet.getAttribute("color");
                double score = (Double) packet.getAttribute("score");
                for (int i = 0; i != fakeGame.getAllUsernames().size(); i++) {
                    if (fakeGame.getColors().get(i).equals(color)) {
                        Application.getUserByUsername(fakeGame.getAllUsernames().get(i)).addHighScore((int) score);
                    }
                }
            }
            case "get map" -> {
                sendMap();
            }
            case "get fake games" -> {
                for (FakeGame fakeGame : GameController.getAllFakeGames()) {
                    if (fakeGame.isGameStarted()){
                        continue;
                    }else if (!fakeGame.isGameStarted() && System.currentTimeMillis() - fakeGame.getGameId() > 120000){
                        continue;
                    }
                    FakeGame fakeGame1 = new FakeGame(fakeGame);
                    connection.getObjectOutputStream().writeObject(fakeGame1);
                }
                FakeGame fakeGame = new FakeGame();
                fakeGame.setMapName("Null Map 400*400");
                connection.getObjectOutputStream().writeObject(fakeGame);


            }
            case "check fake game"-> checkFakeGame();
            case "create a fake game" -> CreateFakeGame();
            case "add player" -> addPlayer();
            case "update player" -> updateGame();
            case "get fake game" -> getFakeGame();
            case "update private game" -> updatePrivateState();
            case "leave game" -> leaveGame();
            case "start game" -> startFakeGame(true);
            case "add spectator" -> {
                addSpectator();
            }
        }
    }

    private void addSpectator() {
        String username = TokenController.getUserByToken(connection.getToken()).getUsername();
        double id = (double) packet.getAttribute("id");
        FakeGame fakeGame = GameController.fakeGameHashMap.get((long)id);
        fakeGame.getSpectatorsUsernames().add(username);
        GameController.addFakeGame(Application.getUserByUsername(username) , fakeGame);
    }

    private void checkFakeGame() throws IOException {
        double id = (double) packet.getAttribute("id");
        FakeGame fakeGame = GameController.fakeGameHashMap.get((long)id);
        if (fakeGame != null && System.currentTimeMillis() - fakeGame.getGameId() > 110000){
            for (String username : fakeGame.getAllUsernames()){
                User user = Application.getUserByUsername(username);
                Connection connection1 = getConnection(username);
                GameController.fakeGameHashMap.remove(fakeGame.getGameId());
                GameController.getAllFakeGames().remove(fakeGame);
                GameController.getFakeGames().remove(user);
                if (connection1 != null && connection1.isAlive() && !connection1.getSocket().isClosed()){
                    Packet packet1 = new Packet("exit lobby","Game");
                    Packet.sendPacket(packet1,connection1);
                }

            }
        }
    }


    private void leaveGame() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        double id = (double) packet.getAttribute("id");
        FakeGame fakeGame = GameController.fakeGameHashMap.get((long) id);
        int index = fakeGame.getAllUsernames().indexOf(user.getUsername());
        if (fakeGame.getAllUsernames().size() <= 1) {
            GameController.fakeGameHashMap.remove((long) id);
            GameController.getAllFakeGames().remove(fakeGame);
            GameController.getFakeGames().remove(user);
            Packet packet1 = new Packet("leave game", "Game");
            Packet.sendPacket(packet1, connection);
            return;
        }
        if (checkPermission(fakeGame, user)) {
            fakeGame.getAllUsernames().remove(index);
            fakeGame.getColors().remove(index);
            fakeGame.getCastleXs().remove(index);
            fakeGame.getCastleYs().remove(index);
            fakeGame.setAdminUsername(fakeGame.getAllUsernames().get(0));
            Packet packet1 = new Packet("leave game", "Game");
            Packet.sendPacket(packet1, connection);
            updateGame(fakeGame);
            return;
        }
        fakeGame.getAllUsernames().remove(index);
        fakeGame.getColors().remove(index);
        fakeGame.getCastleXs().remove(index);
        fakeGame.getCastleYs().remove(index);

        Packet packet1 = new Packet("leave game", "Game");
        Packet.sendPacket(packet1, connection);
        updateGame(fakeGame);
    }

    private void updatePrivateState() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        double id = (double) packet.getAttribute("id");
        FakeGame fakeGame = GameController.fakeGameHashMap.get((long) id);
        if (!checkPermission(fakeGame, user)) {
            return;
        }
        boolean isPrivate = (boolean) packet.getAttribute("private");
        String password = (String) packet.getAttribute("password");
        if (isPrivate) {
            fakeGame.setPrivate(true);
            fakeGame.setPassword(password);
        } else {
            fakeGame.setPrivate(false);
            fakeGame.setPassword("");
        }
        updateGame(fakeGame);
    }

    private void getFakeGame() throws IOException {
        double id = (double) packet.getAttribute("id");
        FakeGame fakeGame = GameController.fakeGameHashMap.get((long) id);
        connection.getObjectOutputStream().writeObject(fakeGame);
    }

    private void addPlayer() throws IOException {
        User user = TokenController.getUserByToken(packet.token);
        double id = (double) packet.getAttribute("id");
        double x = (double) packet.getAttribute("x");
        double y = (double) packet.getAttribute("y");
        String color = packet.getAttribute("color").toString();
        FakeGame fakeGame = GameController.fakeGameHashMap.get((long) id);
        fakeGame.addPlayer(user.getUsername(), color, (int) x, (int) y);
        GameController.addFakeGame(user, fakeGame);
        if (fakeGame.getMaxPlayer() == fakeGame.getAllUsernames().size()){
            startFakeGame(false);
        }else {
            updateGame(fakeGame);
        }
    }

    private void updateGame() throws IOException {
        double id = (double) packet.getAttribute("id");
        FakeGame fakeGame = GameController.fakeGameHashMap.get((long) id);
        for (String username : fakeGame.getAllUsernames()) {
            Connection connection = getConnection(username);
            if (connection != null) {
                Packet packet = new Packet("update fake game", "Game");
                Packet.sendPacket(packet, connection);
                FakeGame fakeGame1 = new FakeGame(fakeGame);
                connection.getObjectOutputStream().writeObject(fakeGame1);
            }
        }
    }

    private void updateGame(FakeGame fakeGame) throws IOException {
        for (String username : fakeGame.getAllUsernames()) {
            Connection connection = getConnection(username);
            if (connection != null) {
                Packet packet = new Packet("update fake game", "Game");
                Packet.sendPacket(packet, connection);
                FakeGame fakeGame1 = new FakeGame(fakeGame);
                connection.getObjectOutputStream().writeObject(fakeGame1);
            }
        }
    }

    private void CreateFakeGame() throws IOException {
        String name = packet.getAttribute("name").toString();
        double count = (Double) packet.getAttribute("count");
        boolean isPrivate = (boolean) packet.getAttribute("private");
        String map = packet.getAttribute("map").toString();
        String password = "";
        if (isPrivate) {
            password = packet.getAttribute("password").toString();
        }
        User user = TokenController.getUserByToken(packet.token);
        FakeGame fakeGame = new FakeGame();
        fakeGame.setGameName(name);
        fakeGame.setMaxPlayer((int) count);
        fakeGame.setMapName(map);
        fakeGame.setPrivate(isPrivate);
        fakeGame.setPassword(password);
        fakeGame.setGameId();
        GameController.fakeGameHashMap.put(fakeGame.getGameId(), fakeGame);
        fakeGame.setAdminUsername(user.getUsername());
        GameController.getAllFakeGames().add(fakeGame);
        connection.getObjectOutputStream().writeObject(fakeGame);
    }

    private void sendMap() throws IOException {
        DBController.loadAllMaps();
        connection.getObjectOutputStream().writeObject(GameMaps.allMaps.get((String) packet.getAttribute("map name")));
    }

    private void changeFearRate() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void changeFoodRate() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void getLordName() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        String color = (String) packet.getAttribute("color");
        Packet packet1 = new Packet("send lord name", "Game");
        packet1.addAttribute("name", getUserByColorInGame(color, fakeGame).getNickname());
        PacketHandler packetHandler = new PacketHandler(packet1, connection);
        packetHandler.sendPacket(packet1);
    }

    private User getUserByColorInGame(String color, FakeGame fakeGame) {
        for (int i = 0; i != fakeGame.getAllUsernames().size(); i++) {
            if (fakeGame.getColors().get(i).equals(color)) {
                return Application.getUserByUsername(fakeGame.getAllUsernames().get(i));
            }
        }
        return null;
    }

    private void changeTaxRate() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }


    private void changeWeapon() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void changeGateState() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void moveUnits() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void attackEnemies() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void airAttackEnemy() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void airAttackBuilding() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }


    private void stopUnits() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void patrolUnits() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void attackBuilding() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void repair() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void dropArabianMercenary() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void dropTree() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void dropRock() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void setTexture() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }


    private void createFakeGame() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        for (String username : fakeGame.getAllUsernames()) {
            User user = Application.getUserByUsername(username);
            GameController.addFakeGame(user, fakeGame);
            if (username.equals(fakeGame.getAdminUsername())) continue;
            Packet packet1 = new Packet("create fake game", "Game");
            packet1.addAttribute("username", username);
            String token = TokenController.getTokenByUsername(username);
            packet1.setToken(token);
            PacketHandler packetHandler = new PacketHandler(packet1, getConnection(username));
            packetHandler.sendPacket(packet1);
        }
        GameController.addFakeGame(fakeGame);
    }

    private void startFakeGame(boolean checkPer) throws IOException {
        User admin = TokenController.getUserByToken(packet.token);
        double id = (double) packet.getAttribute("id");
        FakeGame fakeGame = GameController.fakeGameHashMap.get((long) id);
        if (checkPer && (!checkPermission(fakeGame, admin) || fakeGame.getAllUsernames().size() < 2)) {
            return;
        }
        fakeGame.setGameStarted(true);
        System.out.println("wow your game is started!");
        for (String username : fakeGame.getAllUsernames()) {
            System.out.println(username);
        }
        for (String color : fakeGame.getColors()) {
            System.out.println(color);
        }
        for (String username : fakeGame.getAllUsernames()) {
            Packet packet1 = new Packet("create fake game", "Game");
            packet1.addAttribute("username", username);
            String token = TokenController.getTokenByUsername(username);
            packet1.setToken(token);
            PacketHandler packetHandler = new PacketHandler(packet1, getConnection(username));
            packetHandler.sendPacket(packet1);
            FakeGame fg = new FakeGame(fakeGame);
            getConnection(username).getObjectOutputStream().writeObject(fg);
        }
        for (String username : fakeGame.getSpectatorsUsernames()) {
            Packet packet1 = new Packet("create fake game spectator", "Game");
            packet1.addAttribute("username", username);
            String token = TokenController.getTokenByUsername(username);
            packet1.setToken(token);
            PacketHandler packetHandler = new PacketHandler(packet1, getConnection(username));
            packetHandler.sendPacket(packet1);
            FakeGame fg = new FakeGame(fakeGame);
            getConnection(username).getObjectOutputStream().writeObject(fg);
        }
        GameController.addFakeGame(fakeGame);
    }

    private void dropBuilding() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }

    }

    private ArrayList<Connection> connectionsInGameExceptThis(FakeGame fakeGame) {
        ArrayList<Connection> connections = new ArrayList<>();
        for (String username : fakeGame.getAllUsernames()) {
            Connection connection1 = getConnection(username);
            if (connection1.equals(connection)) continue;
            connections.add(connection1);
        }
        for (String username : fakeGame.getSpectatorsUsernames()) {
            Connection connection1 = getConnection(username);
            connections.add(connection1);
        }
        return connections;
    }

    private Connection getConnection(String username) {
        String token = TokenController.getTokenByUsername(username);
        for (Connection connection : Connection.allConnections) {
            if (connection.getToken() != null && connection.getToken().equals(token))
                return connection;
        }
        return null;
    }

    private boolean checkPermission(FakeGame fakeGame, User user) {
        if (user.getUsername().equals(fakeGame.getAdminUsername())) {
            return true;
        }
        return false;
    }

    private void closeLobby(){

    }
    public void disconnectInLobby(String token,FakeGame fakeGame) throws IOException {
        User user = TokenController.getUserByToken(token);
        int index = fakeGame.getAllUsernames().indexOf(user.getUsername());
        if (fakeGame.getAllUsernames().size() <= 1) {
            GameController.fakeGameHashMap.remove(fakeGame.getGameId());
            GameController.getAllFakeGames().remove(fakeGame);
            GameController.getFakeGames().remove(user);
            return;
        }
        if (checkPermission(fakeGame, user)) {
            fakeGame.getAllUsernames().remove(index);
            fakeGame.getColors().remove(index);
            fakeGame.getCastleXs().remove(index);
            fakeGame.getCastleYs().remove(index);
            fakeGame.setAdminUsername(fakeGame.getAllUsernames().get(0));
            updateGame(fakeGame);
            return;
        }
        fakeGame.getAllUsernames().remove(index);
        fakeGame.getColors().remove(index);
        fakeGame.getCastleXs().remove(index);
        fakeGame.getCastleYs().remove(index);
        updateGame(fakeGame);
    }

    public void disconnectInGame(FakeGame fakeGame , User user) {
        Packet packet1 = new Packet("remove lord" , "Game");
        packet1.addAttribute("color" , fakeGame.getColors().get(fakeGame.getAllUsernames().indexOf(user.getUsername())));
        System.out.println("disconnect from game :");
        System.out.println("ooo user "+ user.getUsername());
        System.out.println("ooo index "+ fakeGame.getAllUsernames().indexOf(user.getUsername()));



        for (String username : fakeGame.getAllUsernames()) {
            Connection connection1 = getConnection(username);
            if (connection1 != null && connection1.isAlive() && !connection1.getSocket().isClosed()) {
                try {
                    Packet.sendPacket(packet1 , connection1);
                } catch (IOException e) {
                    //
                }
            }
        }
        for (String color : fakeGame.getColors()) {
            System.out.println(color);
        }
        GameController.getFakeGames().remove(user , fakeGame);
    }
}
