package client;

import controller.UserController;
import view.Main;

public class PacketHandler {
    Packet packet;

    public PacketHandler(Packet packet) {
        this.packet = packet;
    }

    public void handle() {
        if (packet.handler != null) {
            switch (packet.handler) {
                case "profile":
                    break;
            }
        }
        switch (packet.command) {
            case "username doesn't exist" -> {
                UserController.loginUsernameExistsAct();
            }
            case "password isn't correct" -> {
                UserController.loginUserPasswordWrongAct();
            }
            case "successful login" -> {
                UserController.loginUserSuccessfulAct();
                Main.connection.setToken(packet.token);
            }

        }
    }
}
