package server.handlers;

import controller.Application;
import controller.GameController;
import controller.GovernmentController;
import controller.TokenController;
import model.User;
import server.Connection;
import model.FakeGame;
import server.Packet;
import server.PacketHandler;

import java.io.IOException;
import java.util.ArrayList;

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
            case "air attack enemy"->{
                airAttackEnemy();
            }
            case "air attack building" ->{
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
        }
    }

    private void changeFearRate() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void changeFoodRate() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void getLordName() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        String color = (String)packet.getAttribute("color");
        Packet packet1 = new Packet("send lord name" , "Game");
        packet1.addAttribute("name" , getUserByColorInGame(color , fakeGame).getNickname());
        PacketHandler packetHandler = new PacketHandler(packet1 , connection);
        packetHandler.sendPacket(packet1);
    }

    private User getUserByColorInGame(String color , FakeGame fakeGame) {
        for (int i = 0; i != fakeGame.getAllUsernames().size(); i++) {
            if (fakeGame.getColors().get(i).equals(color)) {
                return Application.getUserByUsername(fakeGame.getAllUsernames().get(i));
            }
        }
        return null;
    }

    private void changeTaxRate() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }


    private void changeWeapon() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void changeGateState() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }
    private void moveUnits() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void attackEnemies() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void airAttackEnemy() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void airAttackBuilding() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }


    private void stopUnits() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void patrolUnits() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void attackBuilding() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void repair() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void dropArabianMercenary() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void dropTree() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void dropRock() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void setTexture() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
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
            Packet packet1 = new Packet("create fake game" , "Game");
            packet1.addAttribute("username" , username);
            String token = TokenController.getTokenByUsername(username);
            packet1.setToken(token);
            PacketHandler packetHandler = new PacketHandler(packet1 , getConnection(username));
            packetHandler.sendPacket(packet1);
            getConnection(username).getObjectOutputStream().writeObject(fakeGame);
        }
    }

    private void dropBuilding() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList <Connection> connections = connectionsInGameExceptThis(fakeGame);
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
}
