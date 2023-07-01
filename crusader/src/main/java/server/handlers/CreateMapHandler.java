package server.handlers;

import controller.DBController;
import controller.GameController;
import controller.gamestructure.GameMaps;
import model.TestForSend;
import model.game.Map;
import server.Connection;
import server.Packet;
import view.Main;

import java.io.IOException;

public class CreateMapHandler {
    Connection connection;
    Packet packet;

    public void handle(Packet packet, Connection connection) throws IOException, ClassNotFoundException {
        this.connection = connection;
        this.packet = packet;
        switch (packet.command) {
            case "get map names" -> {
                sendMapNames();
            }
            case "send Map" -> {
                sendMap();
            }
            case "give my map" -> {
                receiveMap();
            }
        }
    }

    private void receiveMap() throws IOException, ClassNotFoundException {
        Map map = (Map)connection.getObjectInputStream().readObject();
        DBController.saveMap(map , "src/main/resources/savedmaps/" + map.getName() + ".json");
    }

    private void sendMapNames() throws IOException {
        DBController.loadAllMaps();
        Packet resultPacket = new Packet("map Names");
        int index = 0;
        for (String name : GameMaps.allMaps.keySet()) {
            resultPacket.addAttribute("maps-" + (++index) , name);
        }
        Packet.sendPacket(resultPacket , connection);
    }

    private void sendMap() throws IOException {
        connection.getObjectOutputStream().writeObject(GameMaps.allMaps.get((
                String)packet.getAttribute("map name")));
    }
}
