package server.handlers;

import server.Connection;
import server.Packet;

import java.io.IOException;

public class GameHandler {

    Connection connection;
    Packet packet;

    public void handle(Packet packet, Connection connection) throws IOException {
        this.connection = connection;
        this.packet = packet;
        //give commands to suitable methods
        switch (packet.command) {

        }
    }
}
