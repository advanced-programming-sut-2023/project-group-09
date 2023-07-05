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

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
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

    public void sendPacket(Packet packet, DataOutputStream outputStream) throws IOException {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(packet);
        outputStream.writeUTF(json);
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
                ArrayList<String> users = new ArrayList<>();
                for (User user : Application.getUsers()) {
                    if (!user.getUsername().equals(currentUser.getUsername()))
                        users.add(user.getUsername());
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
                Application.getRoomById("0").addMessage(message.getId());
                Application.addMessage(message);

                Packet newMessage = new Packet("* public message");
                for (int i = 0; i < Application.getRoomById("0").getMessages().size(); i++) {
                    newMessage.addAttribute("message" + i, new Gson().toJson(Application.getMessageById(Application.
                            getRoomById("0").getMessages().get(i))));
                }
                for (int i = 0; i < Application.getRoomById("0").getMembers().size(); i++) {
                    User user = Application.getUserByUsername(Application.getRoomById("0").getMembers().get(i));
                    if (!user.isOnline()) continue;
                    sendPacket(newMessage, Connection.getOutputByUsername(user.getUsername()));
                }
            }
            case "get messages - public" -> {
                Packet response = new Packet("public messages");
                Packet seen = new Packet("* seen message");
                User user = TokenController.getUserByToken(packet.getToken());
                Room room = Application.getRoomById("0");
                ArrayList<User> senders = new ArrayList<>();
                for (int i = 0; i < room.getMessages().size(); i++) {
                    Message message = Application.getMessageById(room.getMessages().get(i));
                    if (!user.getUsername().equals(message.getSender()) && !message.isSeen()) {
                        message.setSeen(true);
                        senders.add(Application.getUserByUsername(message.getSender()));
                    }
                    response.addAttribute("message" + i, new Gson().toJson(message));
                    seen.addAttribute("message" + i, new Gson().toJson(message));
                }
                sendPacket(response);
                seen.addAttribute("roomName", "public");
                for (int i = 0; i < senders.size(); i++) {
                    User sender = senders.get(i);
                    if (sender.isOnline())
                        sendPacket(seen, Connection.getOutputByUsername(sender.getUsername()));
                }
            }
            case "send message - private" -> {
                String roomId = (String) packet.getAttribute("roomId");
                User sender = TokenController.getUserByToken(packet.getToken());
                Message message = new Message((String) packet.getAttribute("message"), TokenController.
                        getUserByToken(packet.getToken()).getUsername(), roomId);
                Room room = Application.getRoomById(roomId);
                room.addMessage(message.getId());
                Application.addMessage(message);
                User receiver = (room.getMembers().get(0).equals(sender.getUsername())) ? Application.getUserByUsername(room.getMembers().get(1)) :
                        Application.getUserByUsername(room.getMembers().get(0));

                Packet newMessage = new Packet("* private message");
                for (int i = 0; i < room.getMessages().size(); i++) {
                    newMessage.addAttribute("message" + i, new Gson().toJson(Application.getMessageById(room.getMessages().get(i))));
                }
                newMessage.addAttribute("username", receiver.getUsername());
                if (sender.isOnline())
                    sendPacket(newMessage, Connection.getOutputByUsername(sender.getUsername()));
                newMessage.addAttribute("username", sender.getUsername());
                if (receiver.isOnline())
                    sendPacket(newMessage, Connection.getOutputByUsername(receiver.getUsername()));
            }
            case "get messages - private" -> {
                Packet response = new Packet("private messages");
                User user = TokenController.getUserByToken(packet.getToken());
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
                    Packet announcement = new Packet("new private");
                    announcement.addAttribute("username", sender.getUsername());
                    announcement.addAttribute("roomId", room.getId());
                    sendPacket(announcement, Connection.getOutputByUsername(receiver.getUsername()));
                } else room = Application.getRoomById((String) packet.getAttribute("roomId"));
                Packet seen = new Packet("* seen message");
                boolean newMessage = false;
                for (int i = 0; i < room.getMessages().size(); i++) {
                    Message message = Application.getMessageById(room.getMessages().get(i));
                    if (!user.getUsername().equals(message.getSender()) && !message.isSeen()) {
                        message.setSeen(true);
                        newMessage = true;
                    }
                    response.addAttribute("message" + i, new Gson().toJson(message));
                    seen.addAttribute("message" + i, new Gson().toJson(message));
                }
                response.addAttribute("username", packet.getAttribute("receiver"));
                sendPacket(response);
                User receiver = (room.getMembers().get(0).equals(user.getUsername())) ? Application.getUserByUsername(room.getMembers().get(1))
                        : Application.getUserByUsername(room.getMembers().get(0));
                seen.addAttribute("roomName", user.getUsername());
                if (receiver.isOnline())
                    sendPacket(seen, Connection.getOutputByUsername(receiver.getUsername()));
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
            case "users for room" -> {
                Packet response = new Packet("users for room");
                Room room = Application.getRoomById((String) packet.getAttribute("roomId"));
                ArrayList<String> users = new ArrayList<>();
                for (User user : Application.getUsers()) {
                    if (!user.getRooms().contains(room.getId())) users.add(user.getUsername());
                }
                response.addAttribute("list", new Gson().toJson(users));
                sendPacket(response);
            }
            case "new member" -> {
                Packet response = new Packet("new room");
                Room room = Application.getRoomById((String) packet.getAttribute("roomId"));
                User member = Application.getUserByUsername((String) packet.getAttribute("username"));
                room.addMember(member.getUsername());
                member.addRoom(room.getId());
                response.addAttribute("roomName", room.getName());
                response.addAttribute("roomId", room.getId());
                if (member.isOnline())
                    sendPacket(response, Connection.getOutputByUsername(member.getUsername()));
            }
            case "send message - room" -> {
                String roomId = (String) packet.getAttribute("roomId");
                User sender = TokenController.getUserByToken((String) packet.getToken());
                Message message = new Message((String) packet.getAttribute("message"), TokenController.
                        getUserByToken(packet.getToken()).getUsername(), (String) packet.getAttribute("roomId"));
                Room room = Application.getRoomById((String) packet.getAttribute("roomId"));
                room.addMessage(message.getId());
                Application.addMessage(message);

                Packet newMessage = new Packet("* room message");
                newMessage.addAttribute("roomName", room.getName());
                for (int i = 0; i < room.getMessages().size(); i++) {
                    newMessage.addAttribute("message" + i, new Gson().toJson(Application.getMessageById(room.getMessages().get(i))));
                }
                for (int i = 0; i < Application.getRoomById(roomId).getMembers().size(); i++) {
                    User user = Application.getUserByUsername(Application.getRoomById(roomId).getMembers().get(i));
                    if (!user.isOnline()) continue;
                    sendPacket(newMessage, Connection.getOutputByUsername(user.getUsername()));
                }
            }
            case "get messages - room" -> {
                Packet response = new Packet("room messages");
                Packet seen = new Packet("* seen message");
                ArrayList<User> senders = new ArrayList<>();
                User user = TokenController.getUserByToken(packet.getToken());
                Room room = Application.getRoomById((String) packet.getAttribute("roomId"));
                for (int i = 0; i < room.getMessages().size(); i++) {
                    Message message = Application.getMessageById(room.getMessages().get(i));
                    if (!user.getUsername().equals(message.getSender()) && !message.isSeen()) {
                        message.setSeen(true);
                        senders.add(Application.getUserByUsername(message.getSender()));
                    }
                    response.addAttribute("message" + i, new Gson().toJson(message));
                    seen.addAttribute("message" + i, new Gson().toJson(message));
                }
                response.addAttribute("roomName", room.getName());
                sendPacket(response);
                seen.addAttribute("roomName", room.getName());
                for (int i = 0; i < senders.size(); i++) {
                    User sender = senders.get(i);
                    if (sender.isOnline())
                        sendPacket(seen, Connection.getOutputByUsername(sender.getUsername()));
                }
            }
            case "delete for all" -> {
                Message message = Application.getMessageById((String) packet.getAttribute("messageId"));
                Room room = Application.getRoomById(message.getRoomId());
                Application.getMessages().remove(message);

                Packet deletedMessage = new Packet("* deleted message");
                for (int i = 0; i < room.getMessages().size(); i++) {
                    deletedMessage.addAttribute("message" + i, new Gson().toJson(Application.getMessageById(room.getMessages().get(i))));
                }
                if (!room.isPrivate()) {
                    deletedMessage.addAttribute("roomName", room.getName());
                    for (int i = 0; i < Application.getRoomById(room.getId()).getMembers().size(); i++) {
                        User user = Application.getUserByUsername(Application.getRoomById(room.getId()).getMembers().get(i));
                        if (!user.isOnline()) continue;
                        sendPacket(deletedMessage, Connection.getOutputByUsername(user.getUsername()));
                    }
                } else {
                    User sender = TokenController.getUserByToken(packet.getToken());
                    User receiver = (room.getMembers().get(0).equals(sender.getUsername())) ? Application.getUserByUsername(room.getMembers().get(1)) :
                            Application.getUserByUsername(room.getMembers().get(0));
                    deletedMessage.addAttribute("roomName", receiver.getUsername());
                    if (sender.isOnline())
                        sendPacket(deletedMessage, Connection.getOutputByUsername(sender.getUsername()));
                    deletedMessage.addAttribute("roomName", sender.getUsername());
                    if (receiver.isOnline())
                        sendPacket(deletedMessage, Connection.getOutputByUsername(receiver.getUsername()));
                }
            }
            case "edit" -> {
                Message message = Application.getMessageById((String) packet.getAttribute("messageId"));
                Room room = Application.getRoomById(message.getRoomId());
                message.setData((String) packet.getAttribute("newMessage"));

                Packet editedMessage = new Packet("* edited message");
                for (int i = 0; i < room.getMessages().size(); i++) {
                    editedMessage.addAttribute("message" + i, new Gson().toJson(Application.getMessageById(room.getMessages().get(i))));
                }
                if (!room.isPrivate()) {
                    editedMessage.addAttribute("roomName", room.getName());
                    for (int i = 0; i < Application.getRoomById(room.getId()).getMembers().size(); i++) {
                        User user = Application.getUserByUsername(Application.getRoomById(room.getId()).getMembers().get(i));
                        if (!user.isOnline()) continue;
                        sendPacket(editedMessage, Connection.getOutputByUsername(user.getUsername()));
                    }
                } else {
                    User sender = TokenController.getUserByToken(packet.getToken());
                    User receiver = (room.getMembers().get(0).equals(sender.getUsername())) ? Application.getUserByUsername(room.getMembers().get(1)) :
                            Application.getUserByUsername(room.getMembers().get(0));
                    editedMessage.addAttribute("roomName", receiver.getUsername());
                    if (sender.isOnline())
                        sendPacket(editedMessage, Connection.getOutputByUsername(sender.getUsername()));
                    editedMessage.addAttribute("roomName", sender.getUsername());
                    if (receiver.isOnline())
                        sendPacket(editedMessage, Connection.getOutputByUsername(receiver.getUsername()));
                }
            }
            case "new react" -> {
                Message message = Application.getMessageById((String) packet.getAttribute("messageId"));
                Room room = Application.getRoomById(message.getRoomId());
                User user = TokenController.getUserByToken(packet.getToken());
                message.react((String) packet.getAttribute("emoji"), user.getUsername());

                Packet reactedMessage = new Packet("* reacted message");
                for (int i = 0; i < room.getMessages().size(); i++) {
                    reactedMessage.addAttribute("message" + i, new Gson().toJson(Application.getMessageById(room.getMessages().get(i))));
                }
                if (!room.isPrivate()) {
                    reactedMessage.addAttribute("roomName", room.getName());
                    for (int i = 0; i < Application.getRoomById(room.getId()).getMembers().size(); i++) {
                        User currentUser = Application.getUserByUsername(Application.getRoomById(room.getId()).getMembers().get(i));
                        if (!currentUser.isOnline()) continue;
                        sendPacket(reactedMessage, Connection.getOutputByUsername(currentUser.getUsername()));
                    }
                } else {
                    User sender = TokenController.getUserByToken(packet.getToken());
                    User receiver = (room.getMembers().get(0).equals(sender.getUsername())) ? Application.getUserByUsername(room.getMembers().get(1)) :
                            Application.getUserByUsername(room.getMembers().get(0));
                    reactedMessage.addAttribute("roomName", receiver.getUsername());
                    if (sender.isOnline())
                        sendPacket(reactedMessage, Connection.getOutputByUsername(sender.getUsername()));
                    reactedMessage.addAttribute("roomName", sender.getUsername());
                    if (receiver.isOnline())
                        sendPacket(reactedMessage, Connection.getOutputByUsername(receiver.getUsername()));
                }
            }
            case "new seen" -> {
                Message message = Application.getMessageById((String) packet.getAttribute("messageId"));
                if (message.isSeen()) return;
                message.setSeen(true);
                Room room = Application.getRoomById(message.getRoomId());
                User user = TokenController.getUserByToken(packet.getToken());
                Packet seen = new Packet("* seen message");
                for (int i = 0; i < room.getMessages().size(); i++) {
                    seen.addAttribute("message" + i, new Gson().toJson(Application.getMessageById(room.getMessages().get(i))));
                }

                if (!room.isPrivate()) {
                    seen.addAttribute("roomName", room.getName());
                    User sender = Application.getUserByUsername(message.getSender());
                    if (sender.isOnline())
                        sendPacket(seen, Connection.getOutputByUsername(sender.getUsername()));
                } else {
                    User sender = Application.getUserByUsername(message.getSender());
                    User receiver = user;
                    seen.addAttribute("roomName", receiver.getUsername());
                    if (sender.isOnline())
                        sendPacket(seen, Connection.getOutputByUsername(sender.getUsername()));
                }
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
            case "create map":
                new CreateMapHandler().handle(packet, connection);
                break;
            case "friend":
                new FriendHandler().handle(packet, connection);
                break;
            case "Game":
                new GameHandler().handle(packet, connection);
            case "ShopTrade":
                new ShopTradeHandler().handle(packet, connection);
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
