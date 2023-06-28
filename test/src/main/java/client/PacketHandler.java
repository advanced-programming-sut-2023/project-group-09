package client;

public class PacketHandler {
    Packet packet;

    public PacketHandler(Packet packet) {
        this.packet = packet;
    }

    public void handle() {
        switch (packet.handler) {
            case "profile":
                break;
        }
    }
}
