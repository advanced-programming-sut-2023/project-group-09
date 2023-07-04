package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.*;
import controller.gamestructure.GameMaps;
import model.Government;
import model.User;
import model.chat.Message;
import model.chat.Room;
import model.game.Game;
import model.game.Map;
import server.handlers.*;
import model.User;
import server.handlers.FileHandler;
import server.handlers.FriendHandler;
import server.handlers.ProfileHandler;
import server.handlers.UserHandler;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;

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


    public void handle() throws IOException, ClassNotFoundException{
        switch (packet.command) {
            case "login user" -> {
                Packet result = UserController.loginUser((String) packet.getAttribute("username"),
                        (String) packet.getAttribute("password"),
                        (boolean) packet.getAttribute("stayedLoggedIn"));
                if (result.token != null) {
                    connection.setToken(result.token);
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
                Application.addUser(new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create()
                        .fromJson((String) packet.getAttribute("user"), User.class));
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
                GameController.setGame(new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT)
                        .create().fromJson((String) packet.getAttribute("game"), Game.class));
                GameMaps.createMaps();
                Map selectedMap = (packet.getAttribute("mapNumber").equals("Map 1")) ?
                        GameMaps.largeMaps.get(0) : GameMaps.smallMaps.get(0);
                GameController.getGame().setMap(selectedMap);
                MapController.map = selectedMap;
            }
            case "current user" -> {
                Packet currentUser = new Packet("current user");
                currentUser.addAttribute("user", new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).
                        create().toJson(TokenController.getUserByToken(packet.getToken())));
                sendPacket(currentUser);
            }
            case "get user" -> {
                Packet sendUser = new Packet("send user");
                User user = controller.Application.getUserByUsername((String) packet.getAttribute("username"));
                sendUser.addAttribute("user", new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).
                        create().toJson(user));
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
            case "chat data" -> {
                Packet data = new Packet("chat data");
                User currentUser = TokenController.getUserByToken(packet.getToken());
                data.addAttribute("currentUser", new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).
                        create().toJson(currentUser));
                LinkedHashMap<String, String> users = new LinkedHashMap<>();
                for (User user : Application.getUsers()) {
                    if (!users.equals(currentUser))
                        users.put(user.getUsername(), user.getPath());
                }
                data.addAttribute("otherUsers", new Gson().toJson(users));
                LinkedHashMap<String, String> rooms = new LinkedHashMap<>();
                LinkedHashMap<String, String> privates = new LinkedHashMap<>();
                if (currentUser.getRooms() != null)
                    for (String roomId : currentUser.getRooms()) {
                    Room room = Application.getRoomById(roomId);
                    if (!room.isPrivate())
                        rooms.put(room.getName(), room.getId());
                    else privates.put(room.getName(currentUser.getUsername()), room.getId());
                }
                data.addAttribute("currentUserRooms", new Gson().toJson(rooms));
                data.addAttribute("currentUserPrivates", new Gson().toJson(privates));
                sendPacket(data);
            }
            case "send message - public" -> {
                Message message = new Message((String) packet.getAttribute("message"), TokenController.
                        getUserByToken(packet.getToken()).getUsername(), "0");
                Application.getRoomById("0").addMessage(message);
                Application.addMessage(message);
            }
            case "get messages - public" -> {
                Packet response = new Packet("public messages");
                for (int i = 0; i < Application.getRoomById("0").getMessages().size(); i++) {
                    response.addAttribute("message" + i, new Gson().toJson(Application.getRoomById("0").getMessages()
                            .get(i)));
                }
                sendPacket(response);
            }
            case "send message - private" -> {
                Message message = new Message((String) packet.getAttribute("message"), TokenController.
                        getUserByToken(packet.getToken()).getUsername(), (String) packet.getAttribute("roomId"));
                Room room = Application.getRoomById((String) packet.getAttribute("roomId"));
                room.addMessage(message);
                Application.addMessage(message);
            }
            case "get messages - private" -> {
                Packet response = new Packet("private messages");
                Room room = null;
                if ((String) packet.getAttribute("roomId") == null) {
                    room = new Room(null, null, true);
                    User sender = TokenController.getUserByToken((String) packet.getToken());
                    User receiver = Application.getUserByUsername((String) packet.getAttribute("receiver"));
                    room.addMember(sender.getUsername());
                    room.addMember(receiver.getUsername());
                    sender.addRoom(room.getId());
                    receiver.addRoom(room.getId());
                    Application.addRoom(room);
                    response.addAttribute("roomId", room.getId());
                } else room = Application.getRoomById((String) packet.getAttribute("roomId"));
                for (int i = 0; i < room.getMessages().size(); i++) {
                    response.addAttribute("message" + i, new Gson().toJson(room.getMessages().get(i)));
                }
                sendPacket(response);
            }
            case "new room" -> {
                Packet response = new Packet("new room id");
                User sender = TokenController.getUserByToken(packet.getToken());
                Room newRoom = new Room((String) packet.getAttribute("roomName"), sender.getUsername(), false);
                newRoom.addMember(sender.getUsername());
                sender.addRoom(newRoom.getId());
                Application.addRoom(newRoom);
                response.addAttribute("roomId", newRoom.getId());
                sendPacket(response);
            }
            case "send message - room" -> {
                Message message = new Message((String) packet.getAttribute("message"), TokenController.
                        getUserByToken(packet.getToken()).getUsername(), (String) packet.getAttribute("roomId"));
                Room room = Application.getRoomById((String) packet.getAttribute("roomId"));
                room.addMessage(message);
                Application.addMessage(message);
            }
            case "get messages - room" -> {
                Packet response = new Packet("room messages");
                Room room = Application.getRoomById((String) packet.getAttribute("roomId"));
                for (int i = 0; i < room.getMessages().size(); i++) {
                    response.addAttribute("message" + i, new Gson().toJson(room.getMessages().get(i)));
                }
                sendPacket(response);
            }
            case "delete for all" -> {
                Message message = Application.getMessageById((String) packet.getAttribute("messageId"));
                Room room = Application.getRoomById(message.getRoomId());
                room.getMessages().remove(room.getMessageById((String) packet.getAttribute("messageId")));
                Application.getMessages().remove(message);
            }
            case "edit" -> {
                Message message = Application.getMessageById((String) packet.getAttribute("messageId"));
                Room room = Application.getRoomById(message.getRoomId());
                message.setData((String) packet.getAttribute("newMessage"));
                room.getMessageById((String) packet.getAttribute("messageId")).setData(
                        (String) packet.getAttribute("newMessage"));
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
                break;
            case "create map" :
                new CreateMapHandler().handle(packet , connection);
                break;
            case "friend":
                new FriendHandler().handle(packet, connection);
                break;
            case "Game" :
                new GameHandler().handle(packet, connection);
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
