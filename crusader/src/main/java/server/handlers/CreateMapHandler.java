package server.handlers;

import controller.gamestructure.GameMaps;
import model.TestForSend;
import server.Connection;
import server.Packet;

import java.io.IOException;

public class CreateMapHandler {
    Connection connection;
    Packet packet;

    public void handle(Packet packet, Connection connection) throws IOException {
        this.connection = connection;
        this.packet = packet;
        switch (packet.command) {
            case "get map names" -> {
                sendMapNames();
            }
            case "send Map" -> {
                sendMap();
            }
        }
    }

    private void sendMapNames() throws IOException {
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
        //connection.getObjectOutputStream().writeObject(new TestForSend("Hello!"));
    }
}
