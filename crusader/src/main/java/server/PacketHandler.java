package server;

public class PacketHandler {
    Packet packet;

    public PacketHandler(Packet packet) {
        this.packet = packet;
    }

    public void handle() {
        switch (packet.command) {

        }
    }
}
