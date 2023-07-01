package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import controller.TokenController;
import model.User;
import server.handlers.UserHandler;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Connection extends Thread {
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
                System.out.println("connection interrupted!" + token);
                if (token != null){
                    User user = TokenController.getUserByToken(token);
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
}
