package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.DBController;
import view.Main;

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
        this.token = Main.connection.getToken();
    }

    public void addAttribute(String key , Object value) {
        this.attributes.put(key , value);
    }

    public void sendPacket() throws IOException {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(this);
        Main.connection.getDataOutputStream().writeUTF(json);
    }
}
