package client;

import java.io.IOException;

public class PacketOnlineReceiver extends Thread {
    private boolean isPaused = false;
    @Override
    public void run() {
        while (true) {
            try {
                Packet packet = Packet.receivePacket();
                new PacketOnlineHandler(packet).handle();
                synchronized (this){
                    while (isPaused) {
                        try {
                            wait(); // Pause the thread
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void pauseThread() {
        isPaused = true;
    }

    public synchronized void resumeThread() {
        isPaused = false;
        notify(); // Resume the thread
    }

}
