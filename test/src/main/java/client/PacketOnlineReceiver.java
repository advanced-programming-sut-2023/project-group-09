package client;

import java.io.IOException;

public class PacketOnlineReceiver extends Thread {
    @Override
    public void run() {
        try {
            Packet packet = Packet.receivePacket();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
