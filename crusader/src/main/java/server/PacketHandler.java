package server;

import com.google.gson.GsonBuilder;
import controller.UserController;
import view.Main;

import java.io.IOException;
import java.net.MalformedURLException;

public class PacketHandler {
    Packet packet;
    Connection connection;

    public PacketHandler(Packet packet, Connection connection) {
        this.packet = packet;
        this.connection = connection;
    }

    public PacketHandler(Packet packet) {
        this.packet = packet;
    }

    public void sendPacket(Packet packet) throws IOException {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(packet);
        connection.getDataOutputStream().writeUTF(json);
    }



    public void handle() throws IOException {
        switch (packet.command) {
            case "login user" -> {
                Packet result = UserController.loginUser((String)packet.getAttribute("username") ,
                        (String)packet.getAttribute("password"),
                        (boolean) packet.getAttribute("stayedLoggedIn"));
                sendPacket(result);
            }
        }
    }
}
