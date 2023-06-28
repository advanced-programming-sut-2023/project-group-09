package server;

import controller.TokenController;
import server.handlers.ProfileHandler;
import server.handlers.UserHandler;

import java.io.IOException;

public class PacketHandler {
    Packet packet;
    Connection connection;

    public PacketHandler(Packet packet,Connection connection) {
        this.packet = packet;
        this.connection = connection;
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
