package client;

import javafx.application.Platform;

import java.io.IOException;

public class PacketOnlineReceiver extends Thread {
    @Override
    public void run() {
        Platform.runLater(() -> {
            try {
                Packet packet = Packet.receivePacket();
                new PacketOnlineHandler(packet).handle();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
