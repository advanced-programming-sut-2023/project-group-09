package client;

import view.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Connection {
    private String token;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public Connection(String host, int port) throws IOException {
        System.out.println("Starting Client service...");
        Socket socket = new Socket(host, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
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

    //
    public void makeFakeConnection() throws IOException {
        //deleted
        Packet packet = new Packet("make a fake token","profile");
        packet.sendPacket();
        Packet packet1 = Packet.receivePacket();
        String token = packet1.attributes.get("authentication token").toString();
        setToken(token);
    }
}
