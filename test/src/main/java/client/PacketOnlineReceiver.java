package client;

public class PacketOnlineReceiver extends Thread {
    private boolean isPaused = false;
    private boolean shouldStop = false;

    @Override
    public void run() {
        System.out.println("I'm in thread!");
        while (true) {
            try {
                Packet packet = Packet.receivePacket();
                new PacketOnlineHandler(packet).handle();
                if (shouldStop) {
                    break;
                }
                synchronized (this) {
                    while (isPaused) {
                        try {
                            wait(); // Pause the thread
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
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
        notify();
    }

    public void stopThread() {
        shouldStop = true;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isShouldStop() {
        return shouldStop;
    }

    public void setShouldStop(boolean shouldStop) {
        this.shouldStop = shouldStop;
    }
}
