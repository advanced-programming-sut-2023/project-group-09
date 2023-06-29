package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.*;
import controller.gamestructure.GameMaps;
import model.Government;
import model.User;
import model.game.Game;
import model.game.Map;
import server.handlers.FileHandler;
import server.handlers.ProfileHandler;
import server.handlers.UserHandler;

import java.io.IOException;

public class PacketHandler {
    Packet packet;
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
        switch (packet.command) {
            case "login user" -> {
                Packet result = UserController.loginUser((String) packet.getAttribute("username"),
                        (String) packet.getAttribute("password"),
                        (boolean) packet.getAttribute("stayedLoggedIn"));
                if (result.token != null) {
                    connection.setToken(packet.token);
                    System.out.println("set token connection");
                }
                sendPacket(result);
                return;
            }
            case "email validation" -> {

                Packet validateEmail = new Packet("email validation");
                if (controller.Application.isUserExistsByEmail((String) packet.getAttribute("email")))
                    validateEmail.addAttribute("error", "email already exists!");
                else validateEmail.addAttribute("success", "");
                sendPacket(validateEmail);
                return;
            }
            case "username validation" -> {
                Packet validateUsername = new Packet("username validation");
                if (controller.Application.isUserExistsByName((String) packet.getAttribute("username")))
                    validateUsername.addAttribute("error", "username already exists!");
                else validateUsername.addAttribute("success", "valid username");
                sendPacket(validateUsername);
                return;
            }
            case "username existence" -> {
                Packet usernameExistence = new Packet("username existence");
                if (!controller.Application.isUserExistsByName((String) packet.getAttribute("username")))
                    usernameExistence.addAttribute("error", "username doesn't exist!");
                else usernameExistence.addAttribute("success", "");
                sendPacket(usernameExistence);
                return;
            }
            case "signup user" -> {
                Application.addUser(new Gson().fromJson((String) packet.getAttribute("user"), User.class));
                DBController.saveAllUsers();
                return;
            }
            case "is username exists?" -> {
                Packet result = UserController.isUserExists((String) packet.getAttribute("username"));
                sendPacket(result);
                return;
            }
            case "send user" -> {
                User user = Application.getUserByUsername((String) packet.getAttribute("username"));
                sendUser(user);
                String input = connection.getDataInputStream().readUTF();
                if (!input.equals("do nothing!")) {
                    Application.getUsers().remove(user);
                    user = new GsonBuilder().setPrettyPrinting().create().fromJson(input, User.class);
                    Application.getUsers().add(user);
                    DBController.saveAllUsers();
                }
                return;
            }
            case "new game" -> {
                GameController.setGame(new GsonBuilder().setPrettyPrinting().create()
                        .fromJson((String) packet.getAttribute("game"), Game.class));
                GameMaps.createMaps();
                Map selectedMap = (packet.getAttribute("mapNumber").equals("Map 1")) ?
                        GameMaps.largeMaps.get(0) : GameMaps.smallMaps.get(0);
                GameController.getGame().setMap(selectedMap);
                MapController.map = selectedMap;
            }
            case "current user" -> {
                Packet currentUser = new Packet("current user");
                currentUser.addAttribute("user", new Gson().toJson(TokenController.getUserByToken(packet.getToken())));
                sendPacket(currentUser);
            }
            case "get user" -> {
                Packet sendUser = new Packet("send user");
                User user = controller.Application.getUserByUsername((String) packet.getAttribute("username"));
                sendUser.addAttribute("user", new Gson().toJson(user));
                sendPacket(sendUser);
            }
            case "add government" -> {
                Government government = new Gson().fromJson((String) packet.getAttribute("government"), Government.class);
                int x = Integer.parseInt((String) packet.getAttribute("x"));
                int y = Integer.parseInt((String) packet.getAttribute("y"));
                System.out.println(government);
                GameController.getGame().addGovernment(government);
//                MapController.dropBuilding(x, y, "mainCastle", government);
//                MainCastle mainCastle = (MainCastle) GameController.getGame().getMap().getTile(x, y).getBuilding();
//                mainCastle.setGovernment(government);
//                government.setMainCastle(mainCastle);
            }
        }
        if (!validateAuthenticationToken()) {
            return;
        }
        switch (packet.handler) {
            case "profile":
                new ProfileHandler().handle(packet, connection);
                break;
            case "user":
                new UserHandler().handle(packet, connection);
                break;
            case "file":
                new FileHandler().handle(packet, connection);
        }

    }

    public boolean validateAuthenticationToken() throws IOException {
        if (packet.handler == null) return false;
        if (packet.handler.equals("login") || packet.handler.equals("register") || packet.command.equals("make a fake token")) {
            return true;
        }
        boolean check = TokenController.validateToken(packet.token);
        if (!check) {
            Packet packet = new Packet("authentication error", "error");
            Packet.sendPacket(packet, connection);
        }
        return check;
    }
}
