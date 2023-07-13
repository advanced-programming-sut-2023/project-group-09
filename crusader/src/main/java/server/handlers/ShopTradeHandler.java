package server.handlers;

import controller.TokenController;
import model.FakeGame;
import server.Connection;
import server.Packet;
import server.PacketHandler;

import java.io.IOException;
import java.util.ArrayList;

public class ShopTradeHandler {

    Connection connection;
    Packet packet;

    public void handle(Packet packet, Connection connection) throws IOException, ClassNotFoundException {
        this.connection = connection;
        this.packet = packet;
        //give commands to suitable methods
        switch (packet.command) {
            case "buy item" -> sendFakeGameToAll();
            case "sell item" -> sendFakeGameToAll();
            case "trade goods" -> sendFakeGameToAll();
            case "accept trade" -> sendFakeGameToAll();
            case "reject trade" -> sendFakeGameToAll();
            case "request notification" -> sendFakeGameToTarget();
        }
    }

    private void sendFakeGameToAll() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        ArrayList<Connection> connections = connectionsInGameExceptThis(fakeGame);
        for (Connection connection1 : connections) {
            new PacketHandler(packet, connection1).sendPacket(packet);
        }
    }

    private void sendFakeGameToTarget() throws IOException, ClassNotFoundException {
        FakeGame fakeGame = (FakeGame) connection.getObjectInputStream().readObject();
        Connection connection1 = getConnection((String) packet.getAttribute("targetUsername"));
        new PacketHandler(packet, connection1).sendPacket(packet);
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