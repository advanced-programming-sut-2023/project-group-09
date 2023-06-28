package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.DBController;
import enumeration.Pair;
import view.Main;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Packet {
    public String command;
    public String token;
    public String handler;
    public HashMap<String , Object> attributes = new HashMap<>();

    public Packet(String command,String handler) {
        this.command = command;
        this.handler = handler;
    }

    public void addAttribute(String key , Object value) {
        this.attributes.put(key , value);
    }

    public static Packet receivePacket(DataInputStream dataInputStream) throws IOException {
        String receivingPacket = dataInputStream.readUTF();
        System.out.println("receive packet: ");
        System.out.println(receivingPacket);
        Packet packet = new GsonBuilder().setPrettyPrinting().create().fromJson(receivingPacket , Packet.class);
        return packet;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
    public static void sendPacket(Packet packet,Connection connection) throws IOException {

        String json = new GsonBuilder().setPrettyPrinting().create().toJson(packet);
        System.out.println("send packet: ");
        System.out.println(json);
        connection.getDataOutputStream().writeUTF(json);
    }
}
