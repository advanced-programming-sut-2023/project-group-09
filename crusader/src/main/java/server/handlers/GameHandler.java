package server.handlers;

import controller.Application;
import controller.GameController;
import controller.TokenController;
import model.User;
import server.Connection;
import model.FakeGame;
import server.Packet;
import server.PacketHandler;

import java.io.IOException;

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

    private Connection getConnection(String username) {
        String token = TokenController.getTokenByUsername(username);
        for (Connection connection : Connection.allConnections) {
            System.out.println(connection.getToken());
            if (connection != null && connection.getToken().equals(token))
                return connection;
        }
        return null;
    }
}
