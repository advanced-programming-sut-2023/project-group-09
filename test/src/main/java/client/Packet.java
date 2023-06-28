package client;

import com.google.gson.GsonBuilder;
import view.Main;

import java.io.IOException;
import java.util.HashMap;

public class Packet {
    public String command;
    public String token;
    public HashMap<String, Object> attributes = new HashMap<>();

    public Packet(String command) {
        this.command = command;
        this.token = Main.connection.getToken();
    }

    public void addAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public void sendPacket() throws IOException {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(this);
        Main.connection.getDataOutputStream().writeUTF(json);
    }

    public static Packet receivePacket() throws IOException {
        String receivingPacket = Main.connection.getDataInputStream().readUTF();
        Packet packet = null;
        try {
            packet = new GsonBuilder().setPrettyPrinting().create().fromJson(receivingPacket, Packet.class);
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

    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }
}
