package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Application;
import controller.DBController;
import controller.UserController;
import model.User;

import java.io.IOException;

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
                Packet result = UserController.loginUser((String) packet.getAttribute("username"),
                        (String) packet.getAttribute("password"),
                        (boolean) packet.getAttribute("stayedLoggedIn"));
                sendPacket(result);
            }
            case "email validation" -> {

                Packet validateEmail = new Packet("email validation");
                if (controller.Application.isUserExistsByEmail((String) packet.getAttribute("email")))
                    validateEmail.addAttribute("error", "email already exists!");
                else validateEmail.addAttribute("success", "");
                sendPacket(validateEmail);
            }
            case "username validation" -> {
                Packet validateUsername = new Packet("username validation");
                if (controller.Application.isUserExistsByName((String) packet.getAttribute("username")))
                    validateUsername.addAttribute("error", "username already exists!");
                else validateUsername.addAttribute("success", "valid username");
                sendPacket(validateUsername);
            }
            case "signup user" -> {
                Application.addUser(new Gson().fromJson((String) packet.getAttribute("user"), User.class));
                DBController.saveAllUsers();
            }
        }
    }
}
