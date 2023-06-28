package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.DBController;
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
        this.token = Main.connection.getToken();
    }

    public void addAttribute(String key , Object value) {
        this.attributes.put(key , value);
    }

    public static Packet receivePacket() throws IOException {
        DataInputStream dataInputStream = Main.connection.getDataInputStream();
        String receivingPacket = dataInputStream.readUTF();
        Packet packet = new GsonBuilder().setPrettyPrinting().create().fromJson(receivingPacket , Packet.class);
        System.out.println("receive packet: ");
        System.out.println(receivingPacket);
        return packet;
    }

    public void sendPacket() throws IOException {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(this);
        Main.connection.getDataOutputStream().writeUTF(json);
        System.out.println("send packet: ");
        System.out.println(json);
    }
}
