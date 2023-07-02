package client;

import java.io.IOException;

public class PacketOnlineReceiver extends Thread {
    @Override
    public void run() {
        try {
            Packet packet = Packet.receivePacket();
            new PacketOnlineHandler(packet).handle();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
