package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Application;
import controller.DBController;
import controller.UserController;
import model.User;
import model.User;
import view.Main;

import java.io.IOException;

public class PacketHandler {
    Packet packet;
    Connection connection;
    Connection connection;

    public PacketHandler(Packet packet, Connection connection) {
        this.packet = packet;
        this.connection = connection;
    }


    public void sendPacket(Packet packet) throws IOException {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(packet);
        connection.getDataOutputStream().writeUTF(json);
    }

    public void sendUser(User user) throws IOException {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(user);
        connection.getDataOutputStream().writeUTF(json);
    }


    public void handle() throws IOException {
        if (!validateAuthenticationToken()){
            return;
        }

        switch (packet.handler) {
            case "profile":
                new ProfileHandler().handle(packet,connection);
                break;
            case "user":
                new UserHandler().handle(packet,connection);
        }
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
            case "is username exists?" -> {
                Packet result = UserController.isUserExists((String)packet.getAttribute("username"));
                sendPacket(result);
            }
            case "send user" -> {
                User user = Application.getUserByUsername((String)packet.getAttribute("username"));
                sendUser(user);
                String input = connection.getDataInputStream().readUTF();
                if (!input.equals("do nothing!")) {
                    Application.getUsers().remove(user);
                    user = new GsonBuilder().setPrettyPrinting().create().fromJson(input , User.class);
                    Application.getUsers().add(user);
                    DBController.saveAllUsers();
                }
            }
        }
    }
    public boolean validateAuthenticationToken() throws IOException {
        if (packet.handler.equals("login") || packet.handler.equals("register")|| packet.command.equals("make a fake token")){
            return true;
        }
        boolean check = TokenController.validateToken(packet.token);
        if (!check){
            Packet packet = new Packet("authentication error","error");
            Packet.sendPacket(packet,connection);
        }
        return check;
    }
}
