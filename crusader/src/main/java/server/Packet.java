package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.DBController;
import controller.TokenController;
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
    public HashMap<String , Object> attributes = new HashMap<>();

    public Packet(String command) {
        this.command = command;
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void addAttribute(String key , Object value) {
        this.attributes.put(key , value);
    }

    public static Packet receivePacket(DataInputStream dataInputStream) throws IOException {
        String receivingPacket = dataInputStream.readUTF();
        Packet packet = null;
        try {
            packet = new GsonBuilder().setPrettyPrinting().create().fromJson(receivingPacket , Packet.class);
        } catch (Exception e) {
        }
        return packet;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
