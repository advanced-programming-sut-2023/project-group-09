package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import controller.GameController;
import controller.TokenController;
import model.FakeGame;
import model.User;
import server.handlers.GameHandler;
import server.handlers.UserHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Connection extends Thread {

    public static ArrayList<Connection> allConnections = new ArrayList<>();
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;
    private String token;
    public boolean userChanged = false;


    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        allConnections.add(this);
    }

    @Override
    public synchronized void run() {
        while (true) {
            Packet packet;
            try {
                packet = Packet.receivePacket(dataInputStream);
                PacketHandler packetHandler = new PacketHandler(packet , this);
                packetHandler.handle();
            } catch (IOException e) {
                System.out.println("connection interrupted! " + token);
                if (token != null){
                    User user = TokenController.getUserByToken(token);
                    FakeGame fakeGame = GameController.getFakeGames().get(user);
                    if ( fakeGame != null && !fakeGame.isGameStarted()){
                        try {
                            new GameHandler().disconnectInLobby(token,fakeGame);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }else if (fakeGame != null){
                        new GameHandler().disconnectInGame(fakeGame , user);
                    }
                    user.setOnline(false);
                    TokenController.tokens.remove(token);
                    TokenController.expires.remove(token);
                    token = null;
                }
                Server.connections.remove(this);
                try {
                    socket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public void setDataInputStream(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public static DataOutputStream getOutputByUsername(String username) {
        String token = TokenController.getTokenByUsername(username);
        for (int i = 0; i < allConnections.size(); i++) {
            Connection connection = allConnections.get(i);
            if (connection.getToken().equals(token))
                return connection.dataOutputStream;
        }
        return null;
    }

    public static Connection getConnectionByUsername(String username) {
        String token = TokenController.getTokenByUsername(username);
        for (int i = 0; i < allConnections.size(); i++) {
            Connection connection = allConnections.get(i);
            if (connection.getToken().equals(token))
                return connection;
        }
        return null;
    }
}
